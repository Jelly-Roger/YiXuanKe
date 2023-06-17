package com.easy_select_course.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class CourseDisplay {

    @JsonProperty("Cid")
    private Integer Cid;

    @JsonProperty("CName")
    private String CName;

    @JsonProperty("Ccredit")
    private float Ccredit;

    @JsonProperty("schedule")
    private String schedule;

    @JsonProperty("college")
    private String college;

    @JsonProperty("type")
    private String type;

    @JsonProperty("chosenCon")
    private String chosenCon;

    @JsonProperty("CourseDesc")
    private String CourseDesc;

    @JsonProperty("Tno")
    private String Tno;

    @JsonProperty("Tname")
    private String Tname;

    @JsonProperty("classroom")
    private String classroom;
}
