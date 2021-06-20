package com.dragon.thread.config;

import com.dragon.common.utils.SpringContextHolder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于获取自定义线程池
 */
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getThreadPool() {
        TaskThreadPoolConfig config = SpringContextHolder.getBean(TaskThreadPoolConfig.class);
        return new ThreadPoolExecutor(
                config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(config.getQueueCapacity()),
                new CustomThreadFactory());
    }

}
