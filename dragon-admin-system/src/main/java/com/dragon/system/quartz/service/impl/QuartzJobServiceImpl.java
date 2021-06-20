package com.dragon.system.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.system.quartz.mapper.QuartzJobMapper;
import com.dragon.system.quartz.model.QuartzJobModel;
import com.dragon.system.quartz.service.QuartzJobService;
import com.dragon.system.quartz.utils.QuartzManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "quartzJobService")
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJobModel> implements QuartzJobService {

    @Autowired
    private QuartzManage quartzManage;

    /**
     * 启动或暂停Job
     * @param quartzJob
     */
    @Override
    public void updateIsPause(QuartzJobModel quartzJob) {
        if (quartzJob.getIsPause()) {   // 是否暂停，true: 暂停; false: 启动
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        } else {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        }
        this.saveOrUpdate(quartzJob);
    }

}
