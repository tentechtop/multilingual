package com.alphay.boot.official.utile;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度翻译工具类
 */

public class BaiduTranslationUtils {

    // 对接的api为百度翻译
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private static String appid = "XXXX";
    private static String securityKey = "XXXX";
    // 发送查询

    /**
     * 发送查询并获取翻译结果
     *
     * @param query 要翻译的文本
     * @param from  源语言
     * @param to    目标语言
     * @return 翻译结果
     */
    public static String getTranslateResult(String query, String from, String to) {
        Map<String, Object> params = new HashMap();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appid);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = appid + query + salt + securityKey;
        // 加密前的原文
        params.put("sign", SecureUtil.md5(src));
        String translateResult1 = HttpUtil.get(TRANS_API_HOST, params);
        // 使用Gson解析JSON
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(translateResult1).getAsJsonObject();
        // 获取"trans_result"字段的值（数组）
        JsonArray transResultArray = jsonObject.getAsJsonArray("trans_result");
        if (transResultArray != null && transResultArray instanceof JsonArray) {
            JsonArray jsonElements = (JsonArray) transResultArray;
            // 现在可以安全地访问 'transResultArray' 并进行相应的操作
            if (transResultArray.size() > 0) {
                // 获取数组中的第一个元素
                JsonObject transResultObject = transResultArray.get(0).getAsJsonObject();
                // 获取"dst"字段的值
                String dstValue = transResultObject.get("dst").getAsString();
                return dstValue;
            }
        }
        return null;

    }


}
