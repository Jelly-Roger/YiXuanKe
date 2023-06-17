package com.easy_select_course.springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.service.ManagerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author marca
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @PostMapping("/login")
    public boolean login(@RequestBody UserLoginDto userLoginDto)
    {
        String no = userLoginDto.getNo();
        String password = userLoginDto.getPassword();
        if(StrUtil.isBlank(no) || StrUtil.isBlank(password))
        {
            System.out.println("no or password is null");
            return false;
        }
        System.out.println("print the info at controller layer");
        System.out.println(userLoginDto);
        return managerService.managerLogin(userLoginDto);
    }

}

