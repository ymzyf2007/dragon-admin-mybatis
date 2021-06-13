package com.dragon.thread.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池配置属性类
 */
@Data
@ConfigurationProperties(prefix = "task.pool")
@Component
public class TaskThreadPoolConfig {

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer keepAliveSeconds;

    private Integer queueCapacity;

    private String threadNamePrefix;

}
