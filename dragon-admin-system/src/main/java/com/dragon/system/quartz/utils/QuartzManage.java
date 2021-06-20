package com.dragon.system.quartz.utils;

import com.dragon.common.exception.BadRequestException;
import com.dragon.system.quartz.model.QuartzJobModel;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class QuartzManage {
    private static final Logger log = LoggerFactory.getLogger(QuartzManage.class);

    private static final String JOB_NAME_PREFIX = "TASK_";
    private static final String TRIGGER_NAME_PREFIX = "TRIGGER_";

    @Autowired
    private Scheduler scheduler;

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

            // 设置数据到JobData中，执行的时候取出
            cronTrigger.getJobDataMap().put(QuartzJobModel.JOB_KEY, quartzJob);
            // 重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            // 执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (quartzJob.getIsPause()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
            throw new BadRequestException("创建定时任务失败");
        }
    }

    /**
     * 暂停一个job
     *
     * @param quartzJob
     */
    public void pauseJob(QuartzJobModel quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME_PREFIX + quartzJob.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务暂停失败", e);
            throw new BadRequestException("定时任务暂停失败");
        }
    }

    /**
     * 恢复一个job
     * @param quartzJob
     */
    public void resumeJob(QuartzJobModel quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_NAME_PREFIX + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                addJob(quartzJob);
            }
            JobKey jobKey = JobKey.jobKey(JOB_NAME_PREFIX + quartzJob.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e){
            log.error("恢复定时任务失败", e);
            throw new BadRequestException("恢复定时任务失败");
        }
    }

}
