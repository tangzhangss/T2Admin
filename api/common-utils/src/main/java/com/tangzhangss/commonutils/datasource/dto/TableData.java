package com.tangzhangss.commonutils.datasource.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TableData {
    private List<TableField> fields; //查询结果字段
    private List<String> tables; //所有的数据表
    private List<String> views; //所有的视图
    private List<String> schemas; //所有的schema
    private List<String[]> dataList; //查出的数据
    private List<Map<String,Object>> dataMapList;//Map数据
}
