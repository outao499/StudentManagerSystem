package com.zhibo.test;

import com.zhibo.dao.AdminDao;
import com.zhibo.dao.UsersDao;
import com.zhibo.dao.impl.AdminDaoImpl;
import com.zhibo.dao.impl.UsersDaoImpl;
import com.zhibo.pojo.AdminInfo;
import com.zhibo.pojo.UsersInfo;
import org.junit.Test;

import java.util.List;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 18:55
 */
public class testDemo {
    @Test
    public void test() throws Exception {
//        AdminDao adminDao = new AdminDaoImpl();
//        AdminInfo info = adminDao.loginWithPassword("outao4991", "123456");
//        System.out.println(info);

        UsersDao usersDao = new UsersDaoImpl();
//        List<UsersInfo> usersInfos = usersDao.usersAllInfo();
//        for (UsersInfo usersInfo : usersInfos) {
//            System.out.println(usersInfo);
//        }

        List<UsersInfo> info1 = usersDao.getStudentInfo("孙权");
        for (UsersInfo usersInfo : info1) {
            System.out.println(usersInfo);
        }

    }
}
