package com.easy_select_course.springboot.entity.courseSevice;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
//课Id、课名、学号、专业、姓名、年级、平时成绩、期末成绩、总评成绩、成绩等级
public class StuCourseScoreDisplay {
    @JsonProperty("cid")
    private Integer Cid;
    @JsonProperty("cname")
    private String Cname;
    @JsonProperty("sno")
    private String Sno;
    @JsonProperty("sname")
    private String Sname;
    @JsonProperty("majorName")
    private String MajorName;
    @JsonProperty("grade")
    private String Grade;

    @JsonProperty("procScore")
    private Float procScore;
    @JsonProperty("examScore")
    private Float examScore;
    @JsonProperty("finalScore")
    private Float finalScore;
    @JsonProperty("scoreRank")
    private String scoreRank;
}
