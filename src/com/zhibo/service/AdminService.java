package com.zhibo.service;

import com.zhibo.pojo.AdminInfo;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 18:22
 */
public interface AdminService {
    /**
     * 根据管理员用户名和密码进行登录验证
     *
     * @param username 管理员用户名
     * @param password 密码
     * @return 登录成功返回管理员信息，失败返回null
     */
    AdminInfo loginWithPassword(String username, String password) throws Exception;
}
