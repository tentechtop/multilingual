package com.alphay.boot.official.utile;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.annotation.TranslationField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;
import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

public class TranslationUtils1 {




    /**
     * 翻译对象
     *
     * @param source 源
     * @param target 目标
     * @return {@link T}
     */
    public static <T> T translationObject(Object source, Class<T> target,String from,String to) {
        T temp = null;

        try {
            temp = target.newInstance();
            if (null != source) {
                copyProperties(source,temp,from,to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 拷贝集合
     *
     * @param source 源
     * @param target 目标
     * @return {@link List<T>} 集合
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target,String from,String to) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(source.size() - 1); // 初始值是线程数量减一
            for (Object obj : source) {
                taskManager.addTask(()->{
                list.add(TranslationUtils1.translationObject(obj, target,from,to));
                latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return list;
    }

    /**
     * 约束
     * @param source
     * @param target
     * @param from
     * @param to
     * @param ignoreProperties
     * @throws BeansException
     */
    private static void copyProperties(Object source, Object target,String from,String to, String... ignoreProperties) throws BeansException {
        copyProperties(source, target,from,to, null, ignoreProperties);
    }

    /**
     * 赋值类的值到另一个类的值
     * @param source
     * @param target
     * @param from
     * @param to
     * @param editable
     * @param ignoreProperties
     * @throws BeansException
     */
    private static void copyProperties(Object source, Object target,String from,String to, @Nullable Class<?> editable,
                                       @Nullable String... ignoreProperties) throws BeansException {
        GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        List<Future<?>> futures = new ArrayList<>();

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null) {
                        ResolvableType sourceResolvableType = ResolvableType.forMethodReturnType(readMethod);
                        ResolvableType targetResolvableType = ResolvableType.forMethodParameter(writeMethod, 0);
                        // Ignore generic types in assignable check if either ResolvableType has unresolvable generics.
                        boolean isAssignable =
                                (sourceResolvableType.hasUnresolvableGenerics() || targetResolvableType.hasUnresolvableGenerics() ?
                                        ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType()) :
                                        targetResolvableType.isAssignableFrom(sourceResolvableType));
                        if (isAssignable) {
                            try {
                                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                    readMethod.setAccessible(true);
                                }
                                //目标对象
                                // 检查字段是否带有@Filed注解
                                Field targetField = actualEditable.getDeclaredField(targetPd.getName());
                                boolean annotationPresent = targetField.isAnnotationPresent(TranslationField.class);
                                Object value = readMethod.invoke(source);
                                if (value != null && value instanceof String && annotationPresent) {
                                    String stringValue = (String) value;
                                    if (!stringValue.startsWith("http") && !stringValue.startsWith("/")) {
                                        Integer type = determineFieldType(stringValue);
                                        if (type==1) {
                                            Document doc = Jsoup.parse(stringValue);
                                            // 提取所有的<p>标签和<a>标签内的文本内容
                                            Elements elements = doc.select("p, a,span,div");
                                            CountDownLatch latch = new CountDownLatch(elements.size());
                                            for (Element element : elements) {
                                                String text = element.text();
                                                taskManager.addTask(()->{
                                                String translateResult = BaiduTranslationUtils.getTranslateResult(text, from, to);
                                                System.out.println("html文本翻译的结果"+translateResult);
                                                element.text(translateResult); // 将翻译后的文本设置回元素
                                                latch.countDown(); // 任务完成时减少计数
                                                });
                                            }
                                            // 等待所有任务完成
                                            try {
                                                latch.await();
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                            }
                                            value=elements.outerHtml();
                                        } else if (type==2) {
                                            String[] strings = splitStringValue(stringValue, 2000);
                                            final String[] tValue = {""};
                                            CountDownLatch latch = new CountDownLatch(strings.length);
                                            for (String chunk : strings) {
                                                String text = chunk;
                                                taskManager.addTask(()->{
                                                String translateResult = BaiduTranslationUtils.getTranslateResult(text, from, to);
                                                System.out.println("文本翻译的结果"+translateResult);
                                                tValue[0] += translateResult;
                                                latch.countDown(); // 任务完成时减少计数
                                                });
                                            }
                                            // 等待所有任务完成
                                            try {
                                                latch.await();
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                            }
                                            value= tValue[0];
                                        } else {
                                            String translateResult = BaiduTranslationUtils.getTranslateResult(stringValue, from, to);
                                            value=translateResult;
                                        }
                                    }
                                }
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                            catch (Throwable ex) {
                                throw new FatalBeanException(
                                        "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 判断是否是富文本类型
     * @param fieldValueStr
     * @return
     */
    private static boolean isHtmlRichText(String fieldValueStr) {
        Document doc = Jsoup.parse(fieldValueStr);
        // 在这里添加更多判断条件，例如判断是否包含其他富文本标签
        return doc.select("p, a").size() > 0;
    }

    /**
     * 判断值是否是文本类型
     * @param stringValue
     * @return
     */
    private static Integer determineFieldType(String stringValue) {
        if (isHtmlRichText(stringValue)) {
            return 1; // 包含富文本标签，认定为 HTML 富文本
        } else if (stringValue.length()>2000) {
            return 2; // 超过 500 个字符，认定为文本类型
        }else {
            return 3;
        }
    }

    /**
     *
     * @param text
     * @param chunkSize  每100字一段
     * @return
     */
    private static String[] splitStringValue(String text, int chunkSize){
        if (text == null || text.isEmpty() || chunkSize <= 0) {
            return new String[0]; // 返回空数组
        }
        int length = text.length();
        int numberOfChunks = (length + chunkSize - 1) / chunkSize;
        String[] chunks = new String[numberOfChunks];

        for (int i = 0; i < numberOfChunks; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, length);
            chunks[i] = text.substring(startIndex, endIndex);
        }
        return chunks;
    }




}
