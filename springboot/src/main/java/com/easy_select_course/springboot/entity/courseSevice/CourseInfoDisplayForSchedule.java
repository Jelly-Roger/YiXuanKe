package com.easy_select_course.springboot.entity.courseSevice;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


//展示选课信息(学生学号，姓名，课程名称，课程id，教师工号，教师名称)
@Data
public class CourseInfoDisplayForSchedule {
    //通过这个注释可以不把这部分传给前端
    //@JsonIgnore
    //
    @JsonProperty("Cid")
    private int Cid;
    //
    @JsonProperty("CName")
    private String CName;
    //
    @JsonProperty("Ccredit")
    private Float Ccredit;
    //
    @JsonProperty("CourseDesc")
    private String CourseDesc;
    //
    //private String type;
    //
    @JsonProperty("Tno")
    private String Tno;

    @JsonProperty("Tname")
    private String Tname;
    //
    @JsonProperty("Cday")
    private String Cday;

//    private String college;
//    private String chosenNum;
//    private String totalNum;

    //    private String CPlace; //待会再说
    @JsonProperty("timeStart")
    private int timeStart ;
    @JsonProperty("timeEnd")
    private int timeEnd ;

    @JsonProperty("classroom")
    private String classroom;

    //处理字段使其符合前端要求
//    public String getNewField() {
//        // 处理 field1, field2, field3 三个字段，返回一个新的字段
//        // 例如：将三个字段拼接起来作为新的字段返回
//        return field1 + field2 + field3;
//    }

}
