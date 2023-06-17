package com.easy_select_course.springboot.entity.courseSevice;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;


//课的天数、开始时间、结束时间（原始数据）
@Data
@ToString
public class CourseSchedule {
    @JsonProperty("Cday")
    private String Cday;

    @JsonProperty("timeStart")
    private Integer timeStart;

    @JsonProperty("timeEnd")
    private Integer timeEnd;
}
