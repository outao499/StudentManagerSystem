package com.zhibo.service.impl;

import com.zhibo.dao.UsersDao;
import com.zhibo.dao.impl.UsersDaoImpl;
import com.zhibo.pojo.UsersInfo;
import com.zhibo.service.UsersService;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/5 11:38
 */
public class UsersServiceImpl implements UsersService {
    private final UsersDao usersDao = new UsersDaoImpl();
    @Override
    public List<UsersInfo> usersAllInfo() throws Exception {
        return usersDao.usersAllInfo();
    }

    @Override
    public int deleteStudentInfo(Integer id) throws Exception {
        return usersDao.deleteStudentInfo(id);
    }

    @Override
    public int addStudentInfo(String name,String gender,Integer age,String college,String classname) throws Exception {
        return usersDao.addStudentInfo(name,gender,age,college,classname);
    }

    @Override
    public int updateStudentInfo(Integer id, String name, String gender, Integer age, String college, String classname) throws Exception {
        return usersDao.updateStudentInfo(id, name, gender, age, college, classname);
    }

    @Override
    public List<UsersInfo> getStudentInfo(String name) throws Exception {
        return usersDao.getStudentInfo(name);
    }
}
