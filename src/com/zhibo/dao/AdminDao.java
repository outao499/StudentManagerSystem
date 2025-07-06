package com.zhibo.dao;

import com.zhibo.pojo.AdminInfo;

import java.util.List;

/**
 * @author 19349
 * &#064;description
 * &#064;date  2025/7/2 18:29
 */
public interface AdminDao {
    /**
     * 根据管理员用户名和密码进行登录验证
     *
     * @param username 管理员用户名
     * @param password 密码
     * @return 登录成功返回管理员信息，失败返回null
     */
    AdminInfo loginWithPassword(String username, String password) throws Exception;


}
