package com.dragon.thread.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskExecutePool {

//    @Autowired
//    private TaskThreadPoolConfig taskThreadPoolConfig;

//    @Bean
//    public Executor taskThreadPool() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 核心线程池大小
//        executor.setCorePoolSize(taskThreadPoolConfig.getCorePoolSize());
//        // 最大线程数
//        executor.setMaxPoolSize(taskThreadPoolConfig.getMaxPoolSize());
//        // 空闲线程活跃时间
//        executor.setKeepAliveSeconds(taskThreadPoolConfig.getKeepAliveSeconds());
//        // 队列容量
//        executor.setQueueCapacity(taskThreadPoolConfig.getQueueCapacity());
//        // 线程名称前缀
//        executor.setThreadNamePrefix(taskThreadPoolConfig.getThreadNamePrefix());
//        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
//        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.initialize();
//        return executor;
//    }

}
