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
public class SquareComment {
    private int id;

    private String content;

    private String name;

    private Timestamp time;

    private int qid;
}