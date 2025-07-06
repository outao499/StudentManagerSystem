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

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/5 14:56
 */
@WebServlet("/delete")
public class DeleteStudentInfoController extends HttpServlet {
    private final UsersService usersService = new UsersServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersInfo usersInfo = WebUtil.readJson(req, UsersInfo.class);
        try {
            int info = usersService.deleteStudentInfo(usersInfo.getId());
            if (info>0){
                WebUtil.writeJson(resp,ApiResponse.success("删除成功"));
            }else {
                WebUtil.writeJson(resp,ApiResponse.error("删除失败"));
            }
        } catch (Exception e) {
            GlobalExceptionHandler.handleException(e, resp);
        }
    }
}
