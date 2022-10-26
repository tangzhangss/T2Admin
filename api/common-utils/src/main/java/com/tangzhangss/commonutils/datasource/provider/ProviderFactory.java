package com.tangzhangss.commonutils.datasource.provider;

import com.tangzhangss.commonutils.datasource.constants.DatasourceTypes;

import java.util.HashMap;

public class ProviderFactory{

    private static final HashMap<String,DatasourceProvider> providerMap = new HashMap<>();

    static{
        providerMap.put("jdbc",new JdbcProvider());
    }

    public static DatasourceProvider getProvider(String type) {
        DatasourceTypes datasourceType = DatasourceTypes.valueOf(type);
        switch (datasourceType) {
            case es:
                return providerMap.get("es");
            default:
                return providerMap.get("jdbc");
        }
    }

    public static DDLProvider getDDLProvider(String type) {
        return  null;
    }

}

