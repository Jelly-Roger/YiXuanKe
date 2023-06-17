package com.easy_select_course.springboot.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@ToString
public class QuestionSquare {
    private int id;
    private String title;
    private String content;
    private int anonymous;
    private String name;
    private Integer thumbNum;
    private int commentId;
    private Timestamp time;

}