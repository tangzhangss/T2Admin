package com.tangzhangss.commonservice.dict;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_common_service_dict")
@Data
@ApiModel("系统字典")
public class DictEntity extends SysBaseEntity {
    @ApiModelProperty("字典使用者：开发，用户，运维")
    private String user;

    @ApiModelProperty("字典名")
    private String name;

    @ApiModelProperty("字典类型")
    private String type;

    @ApiModelProperty("字典内容--数据格式自定义-拆分也自定义")
    @Column(length = 4000)
    private String data;
}
