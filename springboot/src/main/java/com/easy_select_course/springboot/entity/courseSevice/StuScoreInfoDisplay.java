package com.easy_select_course.springboot.entity.courseSevice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;


//展示绩点、学分、不及格门数、不及格学分
@Data
@ToString
public class StuScoreInfoDisplay {

        @JsonProperty("gpa")
        private Float GPA;

        @JsonProperty("credit")
        private Float credit;

        @JsonProperty("failedNum")
        private Integer failedNum;

        @JsonProperty("failedCredit")
        private Float failedCredit;

}
