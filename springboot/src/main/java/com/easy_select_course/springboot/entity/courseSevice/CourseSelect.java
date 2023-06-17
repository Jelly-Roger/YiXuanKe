package com.easy_select_course.springboot.entity.courseSevice;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CourseSelect
{
    @JsonProperty("sno")
    private String Sno;

    @JsonProperty("cidList")
    private List<Integer> CidList;


}
