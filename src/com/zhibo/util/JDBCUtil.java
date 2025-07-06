package com.zhibo.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/6/24 21:38
 */

/**
 *  JDBC工具类（V2.0）：
 *      1、维护一个连接池对象、维护了一个线程绑定变量的ThreadLocal对象
 *      2、对外提供在ThreadLocal中获取连接的方法
 *      3、对外提供回收连接的方法，回收过程中，将要回收的连接从ThreadLocal中移除！
 *  注意：工具类仅对外提供共性的功能代码，所以方法均为静态方法！
 *  注意：使用ThreadLocal就是为了一个线程在多次数据库操作过程中，使用的是同一个连接！
 */
public class JDBCUtil {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        try {
            Connection connection = threadLocal.get();
            if (connection == null){
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void release(){
        try {
            Connection connection = threadLocal.get();
            if (connection != null){
                threadLocal.remove();
                //如果开启了事物的手动提交，操作完毕后，归还连接池之前，要将事物的自动提交改为true
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
