package com.easy_select_course.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AppForGrade {
    private int id;
    private String tno;
    private String sno;
    private int cid;
    private Float procScore;
    private Float examScore;
    private Float finalScore;
    private String scoreRank;
}