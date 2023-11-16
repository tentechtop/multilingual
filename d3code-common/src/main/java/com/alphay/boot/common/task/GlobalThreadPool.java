package com.alphay.boot.common.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class GlobalThreadPool {
    private static final int DEFAULT_CORE_POOL_SIZE = 35;
    private static final int MAXIMUM_POOL_SIZE = 95;
    private static final long KEEP_ALIVE_TIME = 60L;

    private final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("pool-kwunphi-thread-%d")
            .setDaemon(true)
            .build();

    private ThreadPoolExecutor executor;

    private boolean shutdown = false;

    // 使用饿汉加载模式，在类加载时初始化线程池
    private static final GlobalThreadPool INSTANCE = new GlobalThreadPool(DEFAULT_CORE_POOL_SIZE);

    private GlobalThreadPool(int corePoolSize) {
        if (corePoolSize <= 0) {
            corePoolSize = DEFAULT_CORE_POOL_SIZE;
        }
        if (corePoolSize > MAXIMUM_POOL_SIZE) {
            corePoolSize = MAXIMUM_POOL_SIZE;
        }
        System.out.println("创建全局线程池");
        executor = new ThreadPoolExecutor(corePoolSize, MAXIMUM_POOL_SIZE * 2,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(MAXIMUM_POOL_SIZE), namedThreadFactory);
    }

    public static GlobalThreadPool getInstance() {
        return INSTANCE;
    }

    private Semaphore taskSemaphore = new Semaphore(95);
    public boolean addTask(Runnable task) {
        System.out.println("添加任务");
        if (task == null) {
            return true;
        }
        if (shutdown) {
            return false;
        }
        try {
            taskSemaphore.acquire(); // 尝试获取Semaphore许可
            executor.execute(() -> {
                try {
                    task.run();
                } finally {
                    taskSemaphore.release(); // 任务执行完毕后释放Semaphore许可
                }
            });
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public int getTaskSize() {
        return executor.getActiveCount() + executor.getQueue().size();
    }

    public boolean isTooManyTask() {
        return getTaskSize() >= MAXIMUM_POOL_SIZE;
    }

    public void shutdown() {
        shutdown = true;
        while (getTaskSize() > 0) {
            sleep(50);
        }
        executor.shutdown();
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isShutdown() {
        return shutdown;
    }
}
