package com.dragon.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTask.class);

    @Async
    public void doTask(int i) {
        logger.info("currentThreadName: {}, Task {} started.", Thread.currentThread().getName(), i);
    }

}
