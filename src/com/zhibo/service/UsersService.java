package com.zhibo.service;

import com.zhibo.pojo.UsersInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 18:23
 */
public interface UsersService {
    /**
     * 查询所有学生
     * @return 学生所有信息
     */
    List<UsersInfo> usersAllInfo() throws Exception;

    /**
     * 删除学生信息
     * @param id 根据id删除学生信息
     * @return 受影响行数
     */
    int deleteStudentInfo(Integer id) throws Exception;

    /**
     * 添加学生信息
     * @param college 添加字段
     * @return 受影响行数
     */
    int addStudentInfo(String name,String gender,Integer age,String college,String classname) throws Exception;

    /**
     * 根据id修改学生信息
     * @param id id
     * @param name 姓名
     * @param gender 性别
     * @param age 年龄
     * @param college 学院
     * @param classname 班级
     * @return 受影响行数
     */
    int updateStudentInfo(Integer id ,String name,String gender,Integer age,String college,String classname) throws Exception;

    /**
     * 查询指定学生信息
     * @param name 学生姓名
     * @return List集合或null
     */
    List<UsersInfo> getStudentInfo(String name) throws Exception;
}
