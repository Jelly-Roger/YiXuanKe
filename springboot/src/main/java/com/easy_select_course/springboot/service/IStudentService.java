package com.easy_select_course.springboot.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy_select_course.springboot.common.Result;
import com.easy_select_course.springboot.controller.dto.StudentDTO;
import com.easy_select_course.springboot.entity.Manager;
import com.easy_select_course.springboot.entity.Student;
import  com.easy_select_course.springboot.service.impl.StudentServiceImpl;

import javax.mail.MessagingException;

public interface IStudentService extends IService<Student> {
    // 邮箱验证
    StudentDTO loginEmail(StudentDTO userDTO);

    void sendEmailCode(String email, Integer type,String username) throws MessagingException;
}
