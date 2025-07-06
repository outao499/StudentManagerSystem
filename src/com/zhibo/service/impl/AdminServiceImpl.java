package com.zhibo.service.impl;

import com.zhibo.dao.AdminDao;
import com.zhibo.dao.impl.AdminDaoImpl;
import com.zhibo.pojo.AdminInfo;
import com.zhibo.service.AdminService;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 18:25
 */
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao = new AdminDaoImpl();
    @Override
    public AdminInfo loginWithPassword(String username, String password) throws Exception {
        return adminDao.loginWithPassword(username,password);
    }
}
