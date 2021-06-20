package com.dragon.system.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.system.quartz.model.QuartzJobModel;

public interface QuartzJobService extends IService<QuartzJobModel> {

    /**
     * 启动或暂停Job
     * @param quartzJob
     */
    void updateIsPause(QuartzJobModel quartzJob);

}
