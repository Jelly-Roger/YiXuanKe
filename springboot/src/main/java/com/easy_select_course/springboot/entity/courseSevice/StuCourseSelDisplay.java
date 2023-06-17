package com.easy_select_course.springboot.entity.courseSevice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class StuCourseSelDisplay {

    @JsonProperty("courseInfoList")
    private List<CourseInfoDisplayForSchedule> courseInfoList; //课程信息列表

    @JsonProperty("courseScheMat")
    private List<List<Integer>> courseScheMat; //课表矩阵
}
