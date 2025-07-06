package com.zhibo.controller;

import com.zhibo.common.ApiResponse;
import com.zhibo.common.GlobalExceptionHandler;
import com.zhibo.pojo.AdminInfo;
import com.zhibo.service.AdminService;
import com.zhibo.service.impl.AdminServiceImpl;
import com.zhibo.util.WebUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 19:12
 */
@WebServlet("/login")
public class AdminLoginController extends HttpServlet {
    private final AdminService adminService = new AdminServiceImpl();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        AdminInfo json = WebUtil.readJson(request, AdminInfo.class);
        try {
            AdminInfo info = adminService.loginWithPassword(json.getUsername(), json.getPassword());
            log("登录请求：" + info);
            if (info != null) {
                WebUtil.writeJson(response, ApiResponse.success("登录成功"));
            } else {
                WebUtil.writeJson(response, ApiResponse.error("登录失败"));
            }
        }catch (Exception e) {
            GlobalExceptionHandler.handleException(e, response);
        }

    }
}
