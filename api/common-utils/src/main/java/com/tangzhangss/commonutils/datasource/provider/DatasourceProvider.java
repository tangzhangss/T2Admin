package com.tangzhangss.commonutils.datasource.provider;

import com.tangzhangss.commonutils.datasource.dto.TableData;
import com.tangzhangss.commonutils.datasource.dto.TableField;
import com.tangzhangss.commonutils.datasource.entity.DatasourceEntity;
import com.tangzhangss.commonutils.datasource.request.DatasourceRequest;
import com.tangzhangss.commonutils.exception.ServiceException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public abstract class DatasourceProvider {

    //获取数据 tables信息
    public abstract  TableData getTableData(DatasourceRequest datasourceRequest);

    //获取schema
    public abstract TableData getSchema(DatasourceEntity datasourceEntity);

    //获取视图
    public abstract  TableData getViews(DatasourceEntity datasourceEntity);

    //获取table
    public abstract  TableData getTables(DatasourceEntity datasourceEntity);


    //测试连接
    public abstract DatasourceEntity checkStatus(DatasourceEntity datasourceEntity);

    //执行语句
    public abstract  boolean execute(DatasourceRequest datasourceRequest);
}

