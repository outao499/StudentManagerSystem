package com.zhibo.dao.impl;

import com.zhibo.common.ApiResponse;
import com.zhibo.dao.AdminDao;
import com.zhibo.pojo.AdminInfo;
import com.zhibo.util.BaseDao;
import com.zhibo.util.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 18:30
 */
public class AdminDaoImpl extends BaseDao implements AdminDao {
    @Override
    public AdminInfo loginWithPassword(String username, String password) throws Exception {
        String sql = "select username,password from admin where username=? and password=?";
        return executeQueryBean(AdminInfo.class,sql,username,password);
    }
}
