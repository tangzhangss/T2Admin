package com.tangzhangss.commonservice.client;

import com.tangzhangss.commonutils.base.SysDynamic;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 客户表主要用于--公司信息
 * 注册需要这个信息，超级管理员审核之后自动创建账号
 */
@Entity
@Table(name = "tbl_common_service_client")
@Data
@SysDynamic
public class ClientEntity {
    @Id
    private String id = "";//id

    private String name = "";//企业名

    private String logo = "";//企业logo

    private String phone = "";//联系方式

    private String email = "";//邮箱

    private String address = "";//地址

    private String username = "";//负责人名字

    private String remark = "";//备注

    @Column(nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();//创建时间

    private LocalDateTime auditTime;//审核时间

    private boolean approved;//是否审核

    private boolean usable = true;//是否正常使用,默认非不能使用-审核通过可用

    protected boolean systemic = false;//是否是系统初始化-做一些其他操作,比如:系统提供的不可删除和修改甚至查看

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
