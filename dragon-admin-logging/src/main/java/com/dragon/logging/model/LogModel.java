package com.dragon.logging.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 系统日志
 */
@Data
@NoArgsConstructor
@TableName("sys_log")
public class LogModel {

    @TableId(value = "log_id", type = IdType.AUTO)
    private Long id;

    /** 日志类型 */
    @TableField(value = "log_type")
    private String logType;

    /** 操作用户 */
    private String username;

    /** 方法名 */
    private String method;

    /** 参数 */
    private String params;

    /** 请求ip */
    @TableField(value = "request_ip")
    private String requestIp;

    /** 地址 */
    private String address;

    /** 浏览器  */
    private String browser;

    /** 请求耗时 */
    private Long time;

    /** 描述 */
    private String description;

    /** 异常详细 */
    @TableField(value = "exception_detail")
    private byte[] exceptionDetail;

    /** 创建日期 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    public LogModel(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

}