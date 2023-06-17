package com.easy_select_course.springboot.controller.dto;

import com.easy_select_course.springboot.entity.Menu;
import lombok.Data;
import java.util.List;
import java.awt.*;

//import java.awt.*;

@Data
public class StudentDTO {
    private Integer id;
    private String username;
    private String email;
    private String code;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
