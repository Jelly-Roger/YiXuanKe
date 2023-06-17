package com.easy_select_course.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

@Data
@ToString
@Setter
@Getter
public class gradeElement {

    private String Tno;

    private String Sno;

    private String CName;
}
