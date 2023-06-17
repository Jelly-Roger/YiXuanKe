package com.easy_select_course.springboot.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Teacher;
import com.easy_select_course.springboot.entity.TeacherDisplay;
import com.easy_select_course.springboot.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {

    //与Mapper层交互
    @Autowired
    private TeacherMapper teacherMapper;

    //添加一位老师
    public int insertOneTeacher(Teacher teacher){
        //判断各字段是否为空
        if(StrUtil.isBlank(teacher.getTno()))
            return 0;
        if(teacher.getMajorId() <= 0)
            teacher.setMajorId(1);
        if(StrUtil.isBlank(teacher.getTname()))
            return 0;
        if(StrUtil.isBlank(teacher.getPassword()))
            return 0;
        if(StrUtil.isBlank(teacher.getSex()))
            teacher.setSex(null);
        if(StrUtil.isBlank(teacher.getTitle()))
            teacher.setTitle(null);
        if(StrUtil.isBlank(teacher.getPhone()))
            teacher.setPhone(null);

        int res=0;
        try {
            res = teacherMapper.insertOneTeacher(teacher);
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public int updateOneTeacher(Teacher teacher)
    {
        //判断各字段是否为空
        if(StrUtil.isBlank(teacher.getTno()))
            return 0;
        if(teacher.getMajorId() <= 0)
            teacher.setMajorId(1);
        if(StrUtil.isBlank(teacher.getTname()))
            return 0;
        if(StrUtil.isBlank(teacher.getPassword()))
            return 0;
        if(StrUtil.isBlank(teacher.getSex()))
            teacher.setSex(null);
        if(StrUtil.isBlank(teacher.getTitle()))
            teacher.setTitle(null);
        if(StrUtil.isBlank(teacher.getPhone()))
            teacher.setPhone(null);

        int res=0;
        try {
            res = teacherMapper.updateOneTeacher(teacher);
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public int deleteTeacherByNo(String Tno)
    {
        int res = 0;
        try {
            res = teacherMapper.deleteTeacherByNo(Tno);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    //分页查询老师
    public Map<String,Object> findTeacher_by_Page_Conditon(int pageNum, int pageSize, String tno, String tname, String title){
        List<TeacherDisplay> teaInfo;
        int pageStart = (pageNum-1)*pageSize;
        int total = 0;
        Map<String, Object> resMap = new HashMap<>();

        teaInfo = teacherMapper.findTeacher_By_Page_Condition(pageStart, pageSize, tno, tname, title);

        System.out.println("teaInfo");
        System.out.println(teaInfo);
        total = teacherMapper.findTotal_By_Condition(tno, tname, title);

        resMap.put("data", teaInfo);
        resMap.put("total", total);
        return resMap;

    }

    //根据专业名查找所有属于该专业的老师
    public List<TeacherDisplay> findAllTeacher_withAllMajor(){
        List<TeacherDisplay> res ;
        try {
            res = teacherMapper.findAllTeacher_withAllMajor();
        }
        catch (Exception e){
            System.out.println(e);
            return Collections.emptyList(); //返回空列表
        }
        return res;
    }



    //教师登录
    public boolean teaLogin(UserLoginDto userLoginDto){

        List<Teacher> res;
        try {
            res = teacherMapper.teaLogin(userLoginDto);
        }
        catch (Exception e){
            System.out.println("find the login teacher error!");
            return false;
        }

        if(res.size() == 0)
        {
            System.out.println("fail to find login teacher!");
            return false;
        }
        else
        {
            System.out.println("find the login teacher!");
            System.out.println(res.get(0));
            return true;
        }


    }

}
