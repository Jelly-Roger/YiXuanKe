package com.easy_select_course.springboot.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
public class UserLoginDto {

    private String no;
    private String password;
}
