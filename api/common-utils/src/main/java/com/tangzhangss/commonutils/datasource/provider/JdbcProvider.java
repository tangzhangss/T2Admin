package com.tangzhangss.commonutils.datasource.provider;

import cn.hutool.json.JSONUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tangzhangss.commonutils.datasource.constants.DatasourceTypes;
import com.tangzhangss.commonutils.datasource.dto.*;
import com.tangzhangss.commonutils.datasource.entity.DatasourceEntity;
import com.tangzhangss.commonutils.datasource.request.DatasourceRequest;
import com.tangzhangss.commonutils.exception.ServiceException;
import com.tangzhangss.commonutils.i18n.Translator;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcProvider extends DatasourceProvider{

    private static ConcurrentHashMap<Long, ComboPooledDataSource> jdbcConnection = new ConcurrentHashMap<>();

    /**
     *
     */
    @Override
    public TableData getTableData(DatasourceRequest dsr){
        TableData tableData = new TableData();

        try(Connection connection = getConnectionFromPool(dsr.getDatasourceEntity())){
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(dsr.getQuery());
            //查出的数据
            List<String[]> list = fetchResult(rs);
            tableData.setDataList(list);

            //字段信息
            List<TableField> fields = fetchResultField(rs);
            tableData.setFields(fields);

            //dataMap
            List<Map<String,Object>> dataMapList = new ArrayList<>(list.size());
            list.forEach(data->{
                Map<String,Object> map = new HashMap<>();
                for (int i = 0; i < data.length; i++) {
                    map.put(fields.get(i).getFieldName(),data[i]);
                }
                dataMapList.add(map);
            });
            tableData.setDataMapList(dataMapList);

            rs.close();
            stat.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return tableData;
    }


    private List<TableField> fetchResultField(ResultSet rs) throws Exception {
        List<TableField> fieldList = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int j = 0; j < columnCount; j++) {
            String f = metaData.getColumnName(j + 1);
            String l = StringUtils.isNotEmpty(metaData.getColumnLabel(j + 1)) ? metaData.getColumnLabel(j + 1) : f;
            String t = metaData.getColumnTypeName(j + 1);
            TableField field = new TableField();
            field.setFieldName(l);
            field.setRemarks(l);
            field.setFieldType(t);

            field.setFieldSize(metaData.getColumnDisplaySize(j + 1));

            fieldList.add(field);
        }
        return fieldList;
    }

    @Override
    public DatasourceEntity checkStatus(DatasourceEntity datasourceEntity){
        try(
            //线程池操作为检查数据源状态
            Connection con = getConnection(datasourceEntity);
            Statement statement = con.createStatement()){
            String queryStr = getTablesSql(datasourceEntity);
            ResultSet resultSet = statement.executeQuery(queryStr);
            resultSet.close();
            datasourceEntity.setStatus(1);
        }catch (Exception e){
            datasourceEntity.setStatus(2);
            datasourceEntity.setMessage(e.getMessage());
        }
        return datasourceEntity;
    }

    public void handleDatasource(DatasourceEntity datasourceEntity, String type) throws Exception {
        //连接成功的数据源才能被使用
        if(datasourceEntity.getStatus()!=1)ExceptionUtil.throwException("datasource_cannot_used");
        ComboPooledDataSource dataSource = null;
        switch (type){
            case "add":
                dataSource = jdbcConnection.get(datasourceEntity.getId());
                if (dataSource == null) {
                    addToPool(datasourceEntity);
                }
                break;
            case "edit":
                dataSource = jdbcConnection.get(datasourceEntity.getId());
                if (dataSource != null) {
                    dataSource.close();
                }
                addToPool(datasourceEntity);
                break;
            case "delete":
                dataSource = jdbcConnection.get(datasourceEntity.getId());
                if (dataSource != null) {
                    dataSource.close();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public TableData getSchema(DatasourceEntity datasourceEntity){
        TableData tableData = new TableData();
        List<String> schemas = new ArrayList<>();
        String queryStr = getSchemaSql(datasourceEntity);
        try(Connection con = getConnectionFromPool(datasourceEntity);
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(queryStr)){
                while (resultSet.next()) {
                    schemas.add(resultSet.getString(1));
                }
            tableData.setSchemas(schemas);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return tableData;
    }

    @Override
    public TableData getViews(DatasourceEntity datasourceEntity){
        TableData tableData = new TableData();
        String queryView = getViewSql(datasourceEntity);
        List<String> views = new ArrayList<>();
        if(StringUtils.isBlank(queryView))return null;
        try(Connection con = getConnectionFromPool(datasourceEntity);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(queryView)){
            while (resultSet.next()) {
                views.add(resultSet.getString(1));
            }
            tableData.setViews(views);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return tableData;
    }
    @Override
    public TableData getTables(DatasourceEntity datasourceEntity) throws ServiceException {
        TableData tableData = new TableData();
        String queryView = getTablesSql(datasourceEntity);
        List<String> table = new ArrayList<>();

        try(Connection con = getConnectionFromPool(datasourceEntity);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(queryView)){
            while (resultSet.next()) {
                table.add(resultSet.getString(1));
            }
            tableData.setTables(table);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return tableData;
    }

    private String getSchemaSql(DatasourceEntity datasourceEntity) {
        DatasourceTypes datasourceType = DatasourceTypes.valueOf(datasourceEntity.getType());
        switch (datasourceType) {
            case oracle:
                return "select * from all_users";
            case sqlServer:
                return "select name from sys.schemas;";
            case postgresql:
                return "SELECT nspname FROM pg_namespace;";
            default:
                return "show tables;";
        }
    }

    private String getViewSql(DatasourceEntity datasourceEntity){
        DatasourceTypes datasourceType = DatasourceTypes.valueOf(datasourceEntity.getType());
        switch (datasourceType) {
            case mysql:
            case mariadb:
            case de_doris:
            case ds_doris:
            case ck:
                return null;
            case sqlServer:
                SqlServerConfiguration sqlServerConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), SqlServerConfiguration.class);
                if(StringUtils.isEmpty(sqlServerConfiguration.getSchema())){
                    ExceptionUtil.throwException("schema_is_empty");
                }
                return "SELECT TABLE_NAME FROM DATABASE.INFORMATION_SCHEMA.VIEWS WHERE  TABLE_SCHEMA = 'DS_SCHEMA' ;"
                        .replace("DATABASE", sqlServerConfiguration.getDataBase())
                        .replace("DS_SCHEMA", sqlServerConfiguration.getSchema());
            case oracle:
                OracleConfiguration oracleConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), OracleConfiguration.class);
                if(StringUtils.isEmpty(oracleConfiguration.getSchema())){
                    ExceptionUtil.throwException("schema_is_empty");
                }
                return "select VIEW_NAME  from all_views where owner='" + oracleConfiguration.getSchema() + "'";
            case postgresql:
                PgConfiguration pgConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), PgConfiguration.class);
                if(StringUtils.isEmpty(pgConfiguration.getSchema())){
                    ExceptionUtil.throwException("schema_is_empty");
                }
                return "SELECT viewname FROM  pg_views WHERE schemaname='SCHEMA' ;".replace("SCHEMA", pgConfiguration.getSchema());
            default:
                return null;
        }
    }

    private String getTablesSql(DatasourceEntity datasourceEntity){
        DatasourceTypes datasourceType = DatasourceTypes.valueOf(datasourceEntity.getType());
        switch (datasourceType) {
            case mysql:
            case mariadb:
            case de_doris:
            case ds_doris:
                return "show tables;";
            case sqlServer:
                SqlServerConfiguration sqlServerConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), SqlServerConfiguration.class);
                if(StringUtils.isEmpty(sqlServerConfiguration.getSchema())){
                    ExceptionUtil.throwException("schema_is_empty");
                }
                return "SELECT TABLE_NAME FROM DATABASE.INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA = 'DS_SCHEMA' ;"
                        .replace("DATABASE", sqlServerConfiguration.getDataBase())
                        .replace("DS_SCHEMA", sqlServerConfiguration.getSchema());
            case oracle:
                OracleConfiguration oracleConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), OracleConfiguration.class);
                if(StringUtils.isEmpty(oracleConfiguration.getSchema())){
                    ExceptionUtil.throwException("schema_is_empty");
                }
                return "select table_name, owner from all_tables where owner='" + oracleConfiguration.getSchema() + "'";
            case postgresql:
                PgConfiguration pgConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), PgConfiguration.class);
                if(StringUtils.isEmpty(pgConfiguration.getSchema())){
                    ExceptionUtil.throwException("schema_is_empty");
                }
                return "SELECT tablename FROM  pg_tables WHERE  schemaname='SCHEMA' ;".replace("SCHEMA", pgConfiguration.getSchema());
            case ck:
                CHConfiguration chConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), CHConfiguration.class);
                return "SELECT name FROM system.tables where database='DATABASE';".replace("DATABASE", chConfiguration.getDataBase());
            default:
                return "show tables;";
        }
    }

    private void addToPool(DatasourceEntity datasourceEntity) throws PropertyVetoException {
        ComboPooledDataSource dataSource;
        dataSource = new ComboPooledDataSource();
        JdbcConfiguration jdbcConfiguration = setCredential(datasourceEntity, dataSource);
        dataSource.setMaxIdleTime(jdbcConfiguration.getMaxIdleTime()); // 最大空闲时间
        dataSource.setAcquireIncrement(jdbcConfiguration.getAcquireIncrement());// 增长数
        dataSource.setInitialPoolSize(jdbcConfiguration.getInitialPoolSize());// 初始连接数
        dataSource.setMinPoolSize(jdbcConfiguration.getMinPoolSize()); // 最小连接数
        dataSource.setMaxPoolSize(jdbcConfiguration.getMaxPoolSize()); // 最大连接数
        dataSource.setAcquireRetryAttempts(30);// 获取连接重试次数
        dataSource.setIdleConnectionTestPeriod(60); // 每60s检查数据库空闲连接
        dataSource.setMaxStatements(0); // c3p0全局的PreparedStatements缓存的大小
        dataSource.setBreakAfterAcquireFailure(false);  // 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。Default: false
        dataSource.setTestConnectionOnCheckout(false); // 在每个connection 提交是校验有效性
        dataSource.setTestConnectionOnCheckin(true); // 取得连接的同时将校验连接的有效性
        dataSource.setCheckoutTimeout(60000); // 从连接池获取连接的超时时间，如设为0则无限期等待。单位毫秒，默认为0
//        dataSource.setPreferredTestQuery("SELECT 1");
        dataSource.setDebugUnreturnedConnectionStackTraces(true);
        dataSource.setUnreturnedConnectionTimeout(3600);
        jdbcConnection.put(datasourceEntity.getId(), dataSource);
    }

    private JdbcConfiguration setCredential(DatasourceEntity datasourceEntity, ComboPooledDataSource dataSource) throws PropertyVetoException {
        DatasourceTypes datasourceType = DatasourceTypes.valueOf(datasourceEntity.getType());
        JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();
        switch (datasourceType) {
            case mysql:
            case mariadb:
            case de_doris:
            case ds_doris:
                MysqlConfiguration mysqlConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), MysqlConfiguration.class);
                dataSource.setUser(mysqlConfiguration.getUsername());
                dataSource.setDriverClass(mysqlConfiguration.getDriver());
                dataSource.setPassword(mysqlConfiguration.getPassword());
                dataSource.setJdbcUrl(mysqlConfiguration.getJdbc());
                jdbcConfiguration = mysqlConfiguration;
                break;
            case sqlServer:
                SqlServerConfiguration sqlServerConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), SqlServerConfiguration.class);
                dataSource.setUser(sqlServerConfiguration.getUsername());
                dataSource.setDriverClass(sqlServerConfiguration.getDriver());
                dataSource.setPassword(sqlServerConfiguration.getPassword());
                dataSource.setJdbcUrl(sqlServerConfiguration.getJdbc());
                jdbcConfiguration = sqlServerConfiguration;
                break;
            case oracle:
                OracleConfiguration oracleConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), OracleConfiguration.class);
                dataSource.setUser(oracleConfiguration.getUsername());
                dataSource.setDriverClass(oracleConfiguration.getDriver());
                dataSource.setPassword(oracleConfiguration.getPassword());
                dataSource.setJdbcUrl(oracleConfiguration.getJdbc());
                jdbcConfiguration = oracleConfiguration;
                break;
            case postgresql:
                PgConfiguration pgConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), PgConfiguration.class);
                dataSource.setUser(pgConfiguration.getUsername());
                dataSource.setDriverClass(pgConfiguration.getDriver());
                dataSource.setPassword(pgConfiguration.getPassword());
                dataSource.setJdbcUrl(pgConfiguration.getJdbc());
                jdbcConfiguration = pgConfiguration;
                break;
            case ck:
                CHConfiguration chConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), CHConfiguration.class);
                dataSource.setUser(chConfiguration.getUsername());
                dataSource.setDriverClass(chConfiguration.getDriver());
                dataSource.setPassword(chConfiguration.getPassword());
                dataSource.setJdbcUrl(chConfiguration.getJdbc());
                jdbcConfiguration = chConfiguration;
                break;
            default:
                break;
        }
        return jdbcConfiguration;
    }

    @Override
    public boolean execute(DatasourceRequest datasourceRequest){
        boolean res = false;
        try(Connection connection = getConnectionFromPool(datasourceRequest.getDatasourceEntity());Statement stat = connection.createStatement();){
             res = stat.execute(datasourceRequest.getQuery());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return res;
    }

    private List<String[]> fetchResult(ResultSet rs) throws Exception {
        List<String[]> list = new LinkedList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (rs.next()) {
            String[] row = new String[columnCount];
            for (int j = 0; j < columnCount; j++) {
                int columType = metaData.getColumnType(j + 1);
                switch (columType) {
                    case Types.DATE:
                        if(rs.getDate(j + 1) != null){
                            row[j] = rs.getDate(j + 1).toString();
                        }
                        break;
                    default:
                        row[j] = rs.getString(j + 1);
                        break;
                }
            }
            list.add(row);
        }
        return list;
    }

    private Connection getConnectionFromPool(DatasourceEntity datasourceEntity) throws Exception {
        ComboPooledDataSource dataSource = jdbcConnection.get(datasourceEntity.getId());
        if (dataSource == null) {
            handleDatasource(datasourceEntity, "add");
        }
        dataSource = jdbcConnection.get(datasourceEntity.getId());
        Connection co = dataSource.getConnection();
        return co;
    }

    private static Connection getConnection(DatasourceEntity datasourceEntity) throws Exception {
        String username = null;
        String password = null;
        String driver = null;
        String jdbcurl = null;
        DatasourceTypes datasourceType = DatasourceTypes.valueOf(datasourceEntity.getType());
        Properties props = new Properties();
        switch (datasourceType) {
            case mysql:
            case mariadb:
            case de_doris:
            case ds_doris:
                MysqlConfiguration mysqlConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(),MysqlConfiguration.class);
                username = mysqlConfiguration.getUsername();
                password = mysqlConfiguration.getPassword();
                driver = mysqlConfiguration.getDriver();
                jdbcurl = mysqlConfiguration.getJdbc();
                break;
            case sqlServer:
                SqlServerConfiguration sqlServerConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), SqlServerConfiguration.class);
                username = sqlServerConfiguration.getUsername();
                password = sqlServerConfiguration.getPassword();
                driver = sqlServerConfiguration.getDriver();
                jdbcurl = sqlServerConfiguration.getJdbc();
                break;
            case oracle:
                OracleConfiguration oracleConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), OracleConfiguration.class);
                username = oracleConfiguration.getUsername();
                password = oracleConfiguration.getPassword();
                driver = oracleConfiguration.getDriver();
                jdbcurl = oracleConfiguration.getJdbc();
                props.put( "oracle.net.CONNECT_TIMEOUT" , "5000") ;
//                props.put( "oracle.jdbc.ReadTimeout" , "5000" ) ;
                break;
            case postgresql:
                PgConfiguration pgConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), PgConfiguration.class);
                username = pgConfiguration.getUsername();
                password = pgConfiguration.getPassword();
                driver = pgConfiguration.getDriver();
                jdbcurl = pgConfiguration.getJdbc();
                break;
            case ck:
                CHConfiguration chConfiguration = JSONUtil.toBean(datasourceEntity.getConfiguration(), CHConfiguration.class);
                username = chConfiguration.getUsername();
                password = chConfiguration.getPassword();
                driver = chConfiguration.getDriver();
                jdbcurl = chConfiguration.getJdbc();
                break;
            default:
                break;
        }

        Class.forName(driver);
        props.setProperty("user", username);
        if (StringUtils.isNotBlank(password)) {
            props.setProperty("password", password);
        }
        return DriverManager.getConnection(jdbcurl, props);
    }
}
