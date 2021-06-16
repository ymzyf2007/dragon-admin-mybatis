package com.dragon.system.quartz.utils;

import com.dragon.common.exception.BadRequestException;
import com.dragon.system.quartz.model.QuartzJobModel;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzManage {
    private static final Logger log = LoggerFactory.getLogger(QuartzManage.class);

    private static final String JOB_NAME_PREFIX = "TASK_";
    private static final String TRIGGER_NAME_PREFIX = "TRIGGER_";

    /**
     * 添加定时任务
     * @param quartzJob
     */
    public void addJob(QuartzJobModel quartzJob) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).withIdentity(JOB_NAME_PREFIX + quartzJob.getId()).build();
            // 构建触发器
            Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME_PREFIX + quartzJob.getId())
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression())).build();
            // 重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            // 执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (quartzJob.getIsPause()) {
//                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
            throw new BadRequestException("创建定时任务失败");
        }
    }
}
