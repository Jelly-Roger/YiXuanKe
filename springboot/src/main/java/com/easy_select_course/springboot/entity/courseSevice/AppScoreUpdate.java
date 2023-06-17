package com.easy_select_course.springboot.entity.courseSevice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;


@Data
@ToString
public class AppScoreUpdate {
    @JsonIgnore
    private Integer id;//仅在后端有用？

    @JsonProperty("tno")
    private String Tno;

    @JsonProperty("sno")
    private String Sno;

    @JsonProperty("cid")
    private Integer Cid;

    @JsonProperty("procScore")
    private Float procScore;

    @JsonProperty("examScore")
    private Float examScore;

    @JsonProperty("finalScore")
    private Float finalScore;

    @JsonProperty("scoreRank")
    private String scoreRank;

    @JsonProperty("submit_time")
    private Date submit_time;
}
