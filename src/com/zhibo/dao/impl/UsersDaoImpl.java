package com.zhibo.dao.impl;

import com.zhibo.dao.UsersDao;
import com.zhibo.pojo.UsersInfo;
import com.zhibo.util.BaseDao;
import com.zhibo.util.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/5 11:40
 */
public class UsersDaoImpl extends BaseDao implements UsersDao {

    @Override
    public ArrayList<UsersInfo> usersAllInfo() throws Exception {
        String sql = "select id,name,gender,age,college,classname from users";
        return executeQuery(UsersInfo.class, sql);
    }

    @Override
    public int deleteStudentInfo(Integer id) throws Exception {
        String sql = "delete from users where id=?";
        return executeUpdate(sql,id);
    }

    @Override
    public int addStudentInfo(String name,String gender,Integer age,String college,String classname) throws Exception {
        String sql = "insert into users (name,gender,age,college,classname) value(?,?,?,?,?)";
        return executeUpdate(sql,name,gender,age,college,classname);
    }

    @Override
    public int updateStudentInfo(Integer id, String name, String gender, Integer age, String college, String classname) throws Exception {
        String sql = """
                UPDATE users
                SET name = ?,
                    gender = ?,
                    age = ?,
                    college = ?,
                    classname = ?
                WHERE id = ?
                """;
        return executeUpdate(sql,name,gender,age,college,classname,id);
    }

    @Override
    public ArrayList<UsersInfo> getStudentInfo(String name) throws Exception {
        String sql = "select id,name,gender,age,college,classname from users where name = ?";
        return executeQuery(UsersInfo.class,sql,name);
    }
}
