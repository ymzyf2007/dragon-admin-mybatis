package com.dragon.system.quartz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dragon.common.base.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName("sys_quartz_job")
public class QuartzJobModel extends BaseModel<QuartzJobModel> implements Serializable {
    private static final long serialVersionUID = 7247088141083039713L;

    @TableId(value = "job_id", type = IdType.AUTO)
    private Long id;
    /**
     * 用于子任务唯一标识
     */
    @TableField(exist = false)
    private String uuid;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * bean名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数
     */
    private String params;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 状态，暂停或启动
     */
    private Boolean isPause = false;
    /**
     * 负责人
     */
    private String personInCharge;
    /**
     * 报警邮箱
     */
    private String email;
    /**
     * 子任务
     */
    private String subTask;
    /**
     * 失败后暂停
     */
    private Boolean pauseAfterFailure;
    /**
     * 备注
     */
    private String description;
}
