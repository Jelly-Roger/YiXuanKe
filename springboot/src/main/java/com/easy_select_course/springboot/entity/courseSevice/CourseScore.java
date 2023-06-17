package com.easy_select_course.springboot.entity.courseSevice;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseScore {
    @JsonProperty("Sno")
    private String Sno;

    @JsonProperty("Cid")
    private Integer Cid;

    @JsonProperty("CName")
    private String CName;

    @JsonProperty("procScore")
    private Float procScore;

    @JsonProperty("examScore")
    private Float examScore;

    @JsonProperty("finalScore")
    private Float finalScore;

    @JsonProperty("scoreRank")
    private String scoreRank;

}
