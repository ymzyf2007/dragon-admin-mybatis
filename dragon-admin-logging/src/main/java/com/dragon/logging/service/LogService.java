package com.dragon.logging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.logging.model.LogModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

public interface LogService extends IService<LogModel> {

    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, LogModel log);

}
