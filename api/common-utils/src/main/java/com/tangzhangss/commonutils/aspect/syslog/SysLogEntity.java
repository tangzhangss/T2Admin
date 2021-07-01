package com.tangzhangss.commonutils.aspect.syslog;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_common_service_log")
@Data
public class SysLogEntity extends SysBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户操作
    private String operation;
    //请求方法
    private String method;
    //请求参数
    private String params;
    //执行时长(毫秒)
    private Long time;
    //IP地址
    private String ip;
    //返回结果
    @Column(columnDefinition = "TEXT")
    private String result;
}
