package com.easy_select_course.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 用于数据库取出数据与最后呈现数据的过渡
@Data
@Setter
@Getter
@ToString
public class CourseTemp {

    @JsonProperty("Cid")
    private Integer Cid;

    @JsonProperty("CName")
    private String CName;

    @JsonProperty("Ccredit")
    private float Ccredit;

    @JsonProperty("Cday")
    private String Cday;

    @JsonProperty("timeStart")
    private int timeStart;

    @JsonProperty("timeEnd")
    private int timeEnd;

    private String college;

    private String type;

    private int chosenNum;

    private int totalNum;

    @JsonProperty("CourseDesc")
    private String CourseDesc;

    @JsonProperty("Tno")
    private String Tno;

    @JsonProperty("Tname")
    private String Tname;

    @JsonProperty("classroom")
    private String classroom;

}
