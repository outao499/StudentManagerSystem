package com.zhibo.controller;

import com.zhibo.common.ApiResponse;
import com.zhibo.common.GlobalExceptionHandler;
import com.zhibo.pojo.UsersInfo;
import com.zhibo.service.UsersService;
import com.zhibo.service.impl.UsersServiceImpl;
import com.zhibo.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/5 12:00
 */
@WebServlet("/users")
public class UsersAllInfoController extends HttpServlet {
    private final UsersService usersService = new UsersServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UsersInfo> usersInfos = null;
        try {
            usersInfos = usersService.usersAllInfo();
        } catch (Exception e) {
            GlobalExceptionHandler.handleException(e, resp);
        }
        WebUtil.writeJson(resp, ApiResponse.success(usersInfos));
    }
}
