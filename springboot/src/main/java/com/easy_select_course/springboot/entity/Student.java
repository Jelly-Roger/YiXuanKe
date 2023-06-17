package com.easy_select_course.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
@TableName("Student")
public class Student {

    // 学生学号
    private String sno;
    // 学生专业
    @TableField("major_id")
    @JsonProperty("MajorId")
    private int MajorId;
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
