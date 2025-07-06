package com.zhibo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param
 * @param
 * @author 19349
 * @description
 * @date 2025/7/2 18:09
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersInfo {
    private int id;
    private String name;
    private String gender;
    private Integer age;
    private String college;
    private String classname;
}
