package com.easy_select_course.springboot;

import com.easy_select_course.springboot.controller.StudentController;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.entity.StudentDisplay;
import com.easy_select_course.springboot.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootApplicationTests {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserLoginDto userLoginDto;
    @Autowired
    private StudentController studentController;
    @Test
    void contextLoads() {
    }
    @Test
    void TestMybatis()
    {
        int pageNum = 2;
        int pageSize = 5;
        String sno = "";
        String sname = "";
        String grade = "大";
        Student student = new Student();
        student.setSno("2134332");
        student.setMajorId(-1);
        student.setSname("陈马鸣");
        student.setPassword("chenmaming");
        int res = studentController.insertOneStudent(student);
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setNo("2050244");
//        userLoginDto.setPassword("Chenzhizhi");
//        Boolean res = studentController.login(userLoginDto);
//        List<StudentDisplay> res = studentController.findAllStudent_withMajor();
//        Map<String,Object> res = new HashMap<>();
//        res = studentController.findStudent_By_Page_Condition(pageNum,pageSize,sno,sname,grade);
        System.out.println(res);
//        List<Student> search = studentMapper.stuLogin(userLoginDto);
//        System.out.println(search);
//        List<Student> res = studentMapper.findAllStudent();
//        System.out.println(res);
    }
}
