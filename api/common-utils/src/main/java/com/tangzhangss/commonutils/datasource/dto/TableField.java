package com.tangzhangss.commonutils.datasource.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TableField {

    private String fieldName;//("字段名称")
    private String remarks;//"重新标记"
    private String fieldType;//"字段类型"
    private int fieldSize;//"字段大小"
}
