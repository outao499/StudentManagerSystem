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
 * @date 2025/7/6 17:51
 */
@WebServlet("/info")
public class GetStudentInfoController extends HttpServlet {
    private final UsersService usersService = new UsersServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersInfo usersInfo = WebUtil.readJson(req, UsersInfo.class);
        try {
            List<UsersInfo> info = usersService.getStudentInfo(usersInfo.getName());
            if (info != null && !info.isEmpty()){
                WebUtil.writeJson(resp, ApiResponse.success(info));
            }else {
                WebUtil.writeJson(resp,ApiResponse.error("查询失败"));
            }
        } catch (Exception e) {
            GlobalExceptionHandler.handleException(e,resp);
        }
    }
}
