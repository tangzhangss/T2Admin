package com.tangzhangss.commonutils.datasource.provider;

public abstract class DDLProvider {
    public abstract String createView(String name, String viewSQL);

    public abstract String dropTable(String name);

    public abstract String dropView(String name);
}
