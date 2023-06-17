package com.easy_select_course.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
public class StudentDisplay {

    // 学生学号
    private String sno;
    // 学生专业
    private String majorName;
    // 学生真实姓名
    private String sname;
    // 学生密码
    private String password;
    // 学生性别
    private String sex;
    // 学生年级
    private String grade;

    private String email;
}
