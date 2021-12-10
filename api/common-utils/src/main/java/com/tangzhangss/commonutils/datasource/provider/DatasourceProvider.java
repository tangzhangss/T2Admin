package com.tangzhangss.commonutils.datasource.provider;

import com.tangzhangss.commonutils.datasource.dto.TableData;
import com.tangzhangss.commonutils.datasource.dto.TableField;
import com.tangzhangss.commonutils.datasource.request.DatasourceRequest;
import com.tangzhangss.commonutils.exception.ServiceException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public abstract class DatasourceProvider {

    //获取数据 tables信息
    abstract public TableData getTableData(DatasourceRequest datasourceRequest);

    //获取schema
    abstract public TableData getSchema(DatasourceRequest datasourceRequest);

    //获取视图
    abstract public TableData getViews(DatasourceRequest dsr);

    //获取table
    abstract public TableData getTables(DatasourceRequest dsr);


    //测试连接
    abstract public void checkStatus(DatasourceRequest datasourceRequest);
}

