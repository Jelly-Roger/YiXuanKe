package com.easy_select_course.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;


@Data
@Setter
@Getter
@ToString
public class AppCheck {
    private int id;
    private String type;
    private int aid;
    private int mid;
    private String status;
    private int isApproved;
//    private String submitTime;
//    private String auditTime;
    private Timestamp submitTime;
    private Timestamp auditTime;
    // 省略getter和setter方法
}