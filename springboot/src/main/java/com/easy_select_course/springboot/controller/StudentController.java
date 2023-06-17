package com.easy_select_course.springboot.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy_select_course.springboot.common.Constants;
import com.easy_select_course.springboot.common.Result;
import com.easy_select_course.springboot.config.AuthAccess;
import com.easy_select_course.springboot.controller.dto.StudentDTO;
import com.easy_select_course.springboot.controller.dto.StudentPasswordDTO;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.*;
import com.easy_select_course.springboot.exception.ServiceException;
import com.easy_select_course.springboot.service.StudentService;

import com.easy_select_course.springboot.service.impl.StudentServiceImpl;
import com.easy_select_course.springboot.service.impl.ValidationServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.net.URLEncoder;

import java.util.Date;

import java.util.Collections;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;
    @Resource
    private StudentServiceImpl istudentService;

    @Autowired
    private ValidationServiceImpl validationService;
    // 邮箱登录
//    @AuthAccess
//    @PostMapping("/loginEmail")
//    public Result loginEmail(@RequestBody StudentDTO userDTO) {
//        String email = userDTO.getEmail();
//        String code = userDTO.getCode();
//        if (StrUtil.isBlank(email) || StrUtil.isBlank(code)) {
//            return Result.error(Constants.CODE_400, "参数错误");
//        }
//        StudentDTO dto = studentService.loginEmail(userDTO);
//        return Result.success(dto);
//    }

//     忘记密码 | 重置密码
    @AuthAccess
    @PutMapping("/modify")
    public Result reset(@RequestBody StudentPasswordDTO userPasswordDTO) {
        System.out.println("ooooooooooooooo");
        System.out.println(userPasswordDTO.getPassword());
        System.out.println(userPasswordDTO.getEmail());
        System.out.println(userPasswordDTO.getCode());
//        SqlSessionFactory sqlSessionFactory = null;
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        sqlSession.close();
        if (StrUtil.isBlank(userPasswordDTO.getEmail()) || StrUtil.isBlank(userPasswordDTO.getCode())) {
            System.out.println("1");
            throw new ServiceException("-1", "参数异常");
        }
        // 先查询 邮箱验证的表，看看之前有没有发送过  邮箱code，如果不存在，就重新获取
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email", userPasswordDTO.getEmail());
        validationQueryWrapper.eq("code", userPasswordDTO.getCode());
        validationQueryWrapper.ge("time", new Date());  // 查询数据库没过期的code, where time >= new Date()
        Validation one = validationService.getOne(validationQueryWrapper);
        validationQueryWrapper.clear();
        if (one == null) {
            throw new ServiceException("-1", "验证码过期，请重新获取");
        }
        System.out.println("2");
        // 如果验证通过了，就查询要不过户的信息
//        QueryWrapper<Student> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq("email", userPasswordDTO.getEmail());  //存根据email查询用户信息
//        Student user = istudentService.getOne(userQueryWrapper);

        // 重置密码
//        user.setPassword("newPassword");
        Student stu = studentService.findByEmail(userPasswordDTO.getEmail());
        if (stu==null) {
            System.out.println("该邮箱未被绑定，修改失败");
            return Result.error();
        } else {
            System.out.println("List is not empty");
        }

        System.out.println(stu.getSname());
        stu.setPassword(userPasswordDTO.getPassword());
        studentService.updateOneStudent(stu);
        System.out.println("3");
        return Result.success();
    }

    // 发送邮箱验证码
    @AuthAccess
    @GetMapping("/modify/{email}/{type}/{username}")
    public Result sendEmailCode(@PathVariable String email, @PathVariable Integer type, @PathVariable String username) throws MessagingException {
        System.out.println();
        if(StrUtil.isBlank(email)) {
            throw new ServiceException(Constants.CODE_400, "参数错误");
        }
        if(type == null) {
            throw new ServiceException(Constants.CODE_400, "参数错误");
        }
        istudentService.sendEmailCode(email,2,username);
        return Result.success();
    }



    @PostMapping("/insert_one_student")
    public int insertOneStudent(@RequestBody Student student)
    {
        return studentService.insertOneStudent(student);
    }

    @PostMapping("/update_one_student")
    public int updateOneStudent(@RequestBody Student student)
    {
        return studentService.updateOneStudent(student);
    }

    @DeleteMapping("/{sno}")
    public int deleteOneStudentByNo(@PathVariable String sno)
    {
        return studentService.deleteOneStudentByNo(sno);
    }

    @GetMapping("/find_by_page_condition")
    public Map<String,Object> findStudent_By_Page_Condition(@RequestParam int pageNum,
                                                            @RequestParam int pageSize,
                                                            @RequestParam(defaultValue = "") String sno,
                                                            @RequestParam(defaultValue = "") String sname,
                                                            @RequestParam(defaultValue = "") String grade)
    {
        if(pageNum < 1 || pageSize < 1)
        {
            Map<String,Object> res = new HashMap<>();
            res.put("data", Collections.emptyList());
            res.put("total", 0);
            return res;
        }
        return studentService.findStudent_by_Page_Conditon(pageNum,pageSize,sno,sname,grade);
    }

    @GetMapping("/find_with_major")
    public List<StudentDisplay> findAllStudent_withMajor()
    {
        return studentService.findAllStudent_withMajor();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception
    {
        List<StudentDisplay> list = studentService.findAllStudent_withMajor();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;cjarset=utf-8");
        String fileName = URLEncoder.encode("学生信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

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
        return studentService.stuLogin(userLoginDto);
    }

    @PostMapping("/import")
    public String InfoImport(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Student> list = reader.readAll(Student.class);
        System.out.println(list);
        int totalFile = list.size();
        int res = 0;
        int cnt = 0;
        String info = "总共识别到" + totalFile + "条数据 ";
        for(Student element: list)
        {
            res = 0;
            System.out.println(element);
            try{
                res = studentService.insertOneStudent(element);
            }catch (Exception e){
                System.out.println(e);
                info = info + "成功导入"+ cnt + "条数据 请检查第"+ (cnt+1) + "条数据是否符合规范";
                return info;
            }
            if (res == 1)
            {
                cnt = cnt + 1;
            }
            else
            {
                info = info + "成功导入"+ cnt + "条数据 请检查第"+ (cnt+1) + "条数据是否符合规范";
                return info;
            }
        }
        return info + "成功导入所有数据";
    }
}
