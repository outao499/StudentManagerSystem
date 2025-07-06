package com.zhibo.common;

import com.zhibo.util.WebUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/4 22:42
 */
public class GlobalExceptionHandler {
    public static void handleException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        if (e instanceof SQLException || e instanceof RuntimeException) {
            // 数据库相关错误
            WebUtil.writeJson(response, ApiResponse.error("数据库连接失败，请稍后再试"));
        } else if (e instanceof IOException) {
            // IO 错误，比如请求体读取失败
            WebUtil.writeJson(response, ApiResponse.error("请求数据格式错误"));
        } else {
            // 其他未知错误
            WebUtil.writeJson(response, ApiResponse.error("服务器内部错误"));
        }
    }
}
