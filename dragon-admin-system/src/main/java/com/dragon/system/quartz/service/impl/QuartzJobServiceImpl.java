package com.dragon.system.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.system.quartz.mapper.QuartzJobMapper;
import com.dragon.system.quartz.model.QuartzJobModel;
import com.dragon.system.quartz.service.QuartzJobService;
import org.springframework.stereotype.Service;

@Service(value = "quartzJobService")
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJobModel> implements QuartzJobService {
}
