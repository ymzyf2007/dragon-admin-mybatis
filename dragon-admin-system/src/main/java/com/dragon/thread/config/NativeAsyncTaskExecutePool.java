package com.dragon.thread.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class NativeAsyncTaskExecutePool implements AsyncConfigurer {

    @Autowired
    private TaskThreadPoolConfig taskThreadPoolConfig;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        executor.setCorePoolSize(taskThreadPoolConfig.getCorePoolSize());
        // 最大线程数
        executor.setMaxPoolSize(taskThreadPoolConfig.getMaxPoolSize());
        // 空闲线程活跃时间
        executor.setKeepAliveSeconds(taskThreadPoolConfig.getKeepAliveSeconds());
        // 队列容量
        executor.setQueueCapacity(taskThreadPoolConfig.getQueueCapacity());
        // 线程名称前缀
        executor.setThreadNamePrefix(taskThreadPoolConfig.getThreadNamePrefix());
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     *  异步任务中异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                log.error(throwable.getMessage(), throwable);
                log.error("exception method:" + method.getName());
            }
        };
    }
}
