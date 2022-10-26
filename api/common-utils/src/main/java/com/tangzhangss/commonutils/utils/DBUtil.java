package com.tangzhangss.commonutils.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.logging.log4j.util.PropertiesUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库查询工具类
 * 这个类不是单例的需要 new,满足多样数据库
 */
public class DBUtil {
    private  DruidPooledConnection connection = null;
    private  String driverClassName = null;
    private  String url = null;
    private  String username = null;
    private  String password = null;

    public DBUtil(String driverClassName, String url, String username, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
        setConnection();
    }

    private void setConnection() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(1);//初始化连接数
        dataSource.setMinIdle(1);//最小空闲连接数
        dataSource.setMinIdle(5);//最大空闲连接
        dataSource.setMaxActive(10);//连接池的最大数据库连接数。设为0表示无限制。
        dataSource.setMaxWait(3000000);//最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
        dataSource.setQueryTimeout(600000);
        //dataSource.setConnectProperties(arg0);
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            ExceptionUtil.throwException("Failed to obtain a database connection, " + e.getMessage());
        }
    }

    /**
     * 功能：实现数据插入操作
     *
     * @param sql  数据库预处理语句，实现插入数据功能
     * @param objs 根据预处理语句填写对应内容，有多少个问号就填几个
     */
    public int save(String sql, Object... objs) throws Exception {
        checkConnection();

        int result = 0;
        try(PreparedStatement ptmt = connection.prepareStatement(sql)){
            for (int i = 0; i < objs.length; i++) {
                ptmt.setObject(i + 1, objs[i]);
            }
            result = ptmt.executeUpdate();
        }
        return result;
    }

    /**
     * 批处理
     */
    public int[] batch(String sql, List<Object[]> values) throws Exception {
        checkConnection();

        int[] result = null;
        try(PreparedStatement ptmt = connection.prepareStatement(sql)){
            for (int i = 0; i < values.size(); i++) {
                Object[] objs = values.get(i);
                for (int j = 0; j < objs.length; j++) {
                    ptmt.setObject(j + 1, objs[j]);
                }
                ptmt.addBatch();
            }
            result = ptmt.executeBatch();
        }

        return result;
    }

    /**
     * 查询
     */
    public List<Map<String, Object>> query(String sql, Object... objs) throws Exception {
        checkConnection();

        List<Map<String, Object>> list = null;
        try(PreparedStatement ptmt = connection.prepareStatement(sql)){
            ptmt.setQueryTimeout(600);
            if (objs != null) {
                for (int i = 0; i < objs.length; i++) {
                    ptmt.setObject(i + 1, objs[i]);
                }
            }
            ResultSet rs = ptmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] keys = new String[rsmd.getColumnCount()];
            for (int m = 1; m <= rsmd.getColumnCount(); m++) {
                keys[m - 1] = rsmd.getColumnLabel(m);
            }
            list = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int n = 0; n < keys.length; n++) {
                    map.put(keys[n], rs.getObject(keys[n]));
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 检查连接状态
     */
    private void checkConnection() {
        if (connection==null) {
            setConnection();
        }
    }
    /**
     * 关闭连接
     */
    public void close() throws SQLException {
        connection.close();
        connection = null;
    }


    public static void main(String[] args) throws Exception {
        DBUtil mysqlDB = new DBUtil("com.mysql.cj.jdbc.Driver","jdbc:mysql://192.168.0.175:3306/nsmap?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true",
                "root", "Cytech@123");

        mysqlDB.query("show databases");
        mysqlDB.query("select * from sys_oper_log where title like '%资产%'");
    }
}
