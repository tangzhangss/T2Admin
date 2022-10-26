package com.tangzhangss.commonutils.datasource.request;

import com.tangzhangss.commonutils.datasource.entity.DatasourceEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasourceRequest {
    protected String query;
    protected DatasourceEntity datasourceEntity;
    protected long datasourceId;
}
