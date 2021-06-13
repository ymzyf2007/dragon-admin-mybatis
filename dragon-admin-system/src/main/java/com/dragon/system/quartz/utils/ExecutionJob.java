package com.dragon.system.quartz.utils;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Async
public class ExecutionJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(ExecutionJob.class);


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }
}
