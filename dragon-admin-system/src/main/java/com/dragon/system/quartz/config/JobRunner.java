package com.dragon.system.quartz.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dragon.system.quartz.model.QuartzJobModel;
import com.dragon.system.quartz.service.QuartzJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 在容器启动的时候需要初始化一些内容。比如读取配置文件，数据库连接之类的。
 * SpringBoot给我们提供了两个接口来帮助我们实现这种需求。
 * 这两个接口分别为CommandLineRunner和ApplicationRunner。
 * 他们的执行时机为容器启动完成的时候
 */
@Component
public class JobRunner implements ApplicationRunner {
    private static Logger log = LoggerFactory.getLogger(JobRunner.class);

    @Autowired
    private QuartzJobService quartzJobService;
    @Autowired
    private QuartzManage quartzManage;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("--------------------注入定时任务---------------------");
        QueryWrapper<QuartzJobModel> query = new QueryWrapper<>();
        query.lambda().eq(QuartzJobModel::getIsPause, false);
        List<QuartzJobModel> quartzJobs = quartzJobService.list(query);
        quartzJobs.forEach(quartzJob -> {
            quartzManage.addJob(quartzJob);
        });
        log.debug("--------------------定时任务注入完成---------------------");
    }
}
