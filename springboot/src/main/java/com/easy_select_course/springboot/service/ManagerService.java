package com.easy_select_course.springboot.service;

import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Manager;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    public boolean managerLogin(UserLoginDto userLoginDto)
    {
        List<Manager> res;
        try{
            res = managerMapper.managerLogin(userLoginDto);
        }catch(Exception e){
            System.out.println("find the login student error!");
            return false;
        }

        if(res.size() != 0)
        {
            System.out.println("find the login manager");
            System.out.println(res.get(0));
            return true;
        }
        else
        {
            System.out.println("fail to find login student");
            return false;
        }

    }
}
