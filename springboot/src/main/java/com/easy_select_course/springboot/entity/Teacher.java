package com.easy_select_course.springboot.entity;

//使用lombok插件，自动生成get、set、toString方法
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
public class Teacher {
    // 教师工号
    @JsonProperty("Tno")
    private String Tno;
    // 教师所属专业
    @JsonProperty("MajorId")
    private int MajorId;
    // 教师密码
    @JsonProperty("password")
    private String password;
    // 教师真实姓名
    @JsonProperty("Tname")
    private String Tname;
    // 教师性别
    @JsonProperty("sex")
    private String sex;
    // 教师职称
    @JsonProperty("title")
    private String title;
    // 教师电话
    @JsonProperty("phone")
    private String phone;
}
