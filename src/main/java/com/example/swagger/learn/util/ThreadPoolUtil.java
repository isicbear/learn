package com.example.swagger.learn.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.*;

/**
 * 单例模式返回线程池  用于资源隔离
 */
public class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    private static volatile ExecutorService threadPool = null;

    public static ExecutorService getThreadPool() {

        if (threadPool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue());
                }
            }
        }
        return threadPool;

    }
}
