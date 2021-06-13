package com.dragon.system.quartz.utils;

import com.dragon.system.quartz.model.QuartzJobModel;
import org.quartz.JobBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzManage {
    private static final Logger log = LoggerFactory.getLogger(QuartzManage.class);

    /**
     * 添加定时任务
     * @param quartzJob
     */
    public void addJob(QuartzJobModel quartzJob){
        JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class)
    }
}
