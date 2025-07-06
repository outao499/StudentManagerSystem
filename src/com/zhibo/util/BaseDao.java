package com.zhibo.util;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/6/24 22:33
 */

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 将共性的数据库的操作代码封装在BaseDao里
 */
public class BaseDao {
    /**
     * 通用的增删改的方法
     * @param sql 调用者要执行的sql语句
     * @param params sql语句中的占位符要赋值的参数
     * @return 受影响的行数
     */
    public int executeUpdate(String sql,Object... params) throws Exception{
        //通过JDBCUtilV2获取数据库连接
        Connection connection = JDBCUtil.getConnection();

        //编译sql语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //为每一个占位符赋值，前提是有占位符
        if (params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1,params[i]);
            }
        }

        //接收返回结果，返回int整数
        int row = preparedStatement.executeUpdate();

        //关闭资源
        preparedStatement.close();
        if (connection.getAutoCommit()){
            JDBCUtil.release();
        }

        //返回所修改的行数
        return row;
    }

    /*
    通用的查询多个Javabean对象的方法，例如：多个员工对象，多个部门对象等
    这里的clazz接收的是T类型的Class对象，
    如果查询员工信息，clazz代表Employee.class，
    如果查询部门信息，clazz代表Department.class，
    返回List<T> list
     */
    protected <T> ArrayList<T> executeQuery(Class<T> clazz,String sql,Object... args) throws Exception {
        //        创建PreparedStatement对象，对sql预编译
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        //设置?的值
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++) {
                ps.setObject(i+1, args[i]);//?的编号从1开始，不是从0开始，数组的下标是从0开始
            }
        }

        ArrayList<T> list = new ArrayList<>();
        ResultSet res = ps.executeQuery();

        /*
        获取结果集的元数据对象。
        元数据对象中有该结果集一共有几列、列名称是什么等信息
         */
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();//获取结果集列数

        //遍历结果集ResultSet，把查询结果中的一条一条记录，变成一个一个T 对象，放到list中。
        while(res.next()){
            //循环一次代表有一行，代表有一个T对象
            T t = clazz.newInstance();//要求这个类型必须有公共的无参构造

            //把这条记录的每一个单元格的值取出来，设置到t对象对应的属性中。
            for(int i=1; i<=columnCount; i++){
                //for循环一次，代表取某一行的1个单元格的值
                Object value = res.getObject(i);

                //这个值应该是t对象的某个属性值
                //获取该属性对应的Field对象
                //String columnName = metaData.getColumnName(i);//获取第i列的字段名
                //这里再取别名可能没办法对应上
                String columnName = metaData.getColumnLabel(i);//获取第i列的字段名或字段的别名
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);//这么做可以操作private的属性

                field.set(t,value);
            }

            list.add(t);
        }

        res.close();
        ps.close();
        //这里检查下是否开启事务，开启不关闭连接，业务方法关闭!
        //没有开启事务的话，直接回收关闭即可!
        if (connection.getAutoCommit()) {
            //回收
            JDBCUtil.release();
        }
        return list;
    }

    protected <T> T executeQueryBean(Class<T> clazz,String sql, Object... args) throws Exception {
        List<T> list = this.executeQuery(clazz, sql,args);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}
