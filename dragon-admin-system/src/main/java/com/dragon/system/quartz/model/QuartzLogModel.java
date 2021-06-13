package com.dragon.system.quartz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName("sys_quartz_log")
public class QuartzLogModel implements Serializable {
    private static final long serialVersionUID = -3945321521715325766L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Long id;
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
     * 状态
     */
    private Boolean isSuccess;
    /**
     * 异常详情
     */
    private String exceptionDetail;
    /**
     * 执行耗时
     */
    private Long time;
    /**
     * 创建时间
     */
    private Timestamp createTime;

}
