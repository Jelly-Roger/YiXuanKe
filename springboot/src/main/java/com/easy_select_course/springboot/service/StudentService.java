package com.easy_select_course.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.entity.StudentDisplay;
import com.easy_select_course.springboot.entity.TeacherDisplay;
import com.easy_select_course.springboot.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public int insertOneStudent(Student student)
    {
        if(StrUtil.isBlank(student.getSno()))
            return 0;
        if(student.getMajorId() <= 0)
            student.setMajorId(1);
        if(StrUtil.isBlank(student.getSname()))
            return 0;
        if(StrUtil.isBlank(student.getPassword()))
            return 0;
        if(StrUtil.isBlank(student.getEmail()))
            return 0;
        if(StrUtil.isBlank(student.getSex()))
            student.setSex(null);
        if(StrUtil.isBlank(student.getGrade()))
            student.setGrade(null);

        int res = 0;
        try{
            res = studentMapper.insertOneStudent(student);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }

        return res;
    }

    public int updateOneStudent(Student student)
    {
        if(StrUtil.isBlank(student.getSno()))
            return 0;
        if(student.getMajorId() <= 0)
            student.setMajorId(1);
        if(StrUtil.isBlank(student.getSname()))
            return 0;
        if(StrUtil.isBlank(student.getPassword()))
            return 0;
        if(StrUtil.isBlank(student.getSex()))
            student.setSex(null);
        if(StrUtil.isBlank(student.getGrade()))
            student.setGrade(null);
        if(StrUtil.isBlank(student.getEmail()))
            return 0;

        int res = 0;
        try{
            res = studentMapper.updateOneStudent(student);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }

        return res;
    }

    public int deleteOneStudentByNo(String sno)
    {
        if(StrUtil.isBlank(sno))
            return 0;
        int res = 0;
        try {
            res = studentMapper.deleteStudentByNo(sno);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public Map<String, Object> findStudent_by_Page_Conditon(int pageNum, int pageSize, String sno, String sname, String grade)
    {
        Map<String, Object> res = new HashMap<>();
        List<StudentDisplay> info;
        int total = 0;
        int pageStart = (pageNum-1)*pageSize;
        info = studentMapper.findStudent_By_Page_Condition(pageStart, pageSize, sno, sname, grade);
        System.out.println("info");
        System.out.println(info);
        total = studentMapper.findTotal_By_Condition(sno, sname, grade);
        res.put("data", info);
        res.put("total", total);
        return res;
    }
    public Student findByEmail(String email){
        Student res ;

        res = studentMapper.findByEmail(email);


        return res;
    }

    public List<StudentDisplay> findAllStudent_withMajor()
    {
        List<StudentDisplay> res;
        try{
            res = studentMapper.findAllStudent_withMajor();
        }catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
        return res;
    }
    public boolean stuLogin(UserLoginDto userLoginDto)
    {
        List<Student> res;
//        res = studentMapper.stuLogin(userLoginDto);
        try{
            res = studentMapper.stuLogin(userLoginDto);
        }catch(Exception e){
            System.out.println("find the login student error!");
            return false;
        }

        if(res.size() != 0)
        {
            System.out.println("find the login student");
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
