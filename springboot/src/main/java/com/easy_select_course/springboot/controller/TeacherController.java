package com.easy_select_course.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.entity.Teacher;
import com.easy_select_course.springboot.entity.TeacherDisplay;
import com.easy_select_course.springboot.service.TeacherService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    //调用service层
    @Resource
    private TeacherService teacherService;

    //插入一条教师信息
    @PostMapping("/insert_one_teacher")
    public int insertOneTeacher(@RequestBody Teacher teacher)
    {
        return teacherService.insertOneTeacher(teacher);
    }

    @PostMapping("/update_one_teacher")
    public int updateOneTeacher(@RequestBody Teacher teacher)
    {
        return teacherService.updateOneTeacher(teacher);
    }

    @DeleteMapping("/{Tno}")
    public int deleteTeacherByNo(@PathVariable String Tno)
    {
        return teacherService.deleteTeacherByNo(Tno);
    }
    //分页查询教师信息
    @GetMapping("/find_by_page_condition")
    public Map<String,Object> findTeacher_By_Page_Condition(@RequestParam int pageNum,
                                                            @RequestParam int pageSize,
                                                            @RequestParam(defaultValue = "") String tno,
                                                            @RequestParam(defaultValue = "") String tname,
                                                            @RequestParam(defaultValue = "") String title)
    {
        if(pageNum < 1 || pageSize < 1)
        {
            Map<String,Object> res = new HashMap<>();
            res.put("data", Collections.emptyList());
            res.put("total", 0);
            return res;
        }
        return teacherService.findTeacher_by_Page_Conditon(pageNum,pageSize,tno,tname,title);
    }


    //根据所在专业查询教师信息
    @GetMapping("/find_with_major")
    public List<TeacherDisplay> findAllTeacher_withAllMajor()
    {
        return teacherService.findAllTeacher_withAllMajor();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception
    {
        List<TeacherDisplay> list = teacherService.findAllTeacher_withAllMajor();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;cjarset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    //教师登录
    @PostMapping("/login")
    public boolean login(@RequestBody UserLoginDto userLoginDto)
    {
        String tno = userLoginDto.getNo();
        String password = userLoginDto.getPassword();
        if(StrUtil.isBlank(tno) || StrUtil.isBlank(password))
        {
            System.out.println("no or password is null");
            return false;
        }

        System.out.println("print the info at controller layer");
        System.out.println(userLoginDto);
        return teacherService.teaLogin(userLoginDto);
    }

    @PostMapping("/import")
    public String InfoImport(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Teacher> list = reader.readAll(Teacher.class);
        int totalFile = list.size();
        int res = 0;
        int cnt = 0;
        String info = "总共识别到" + totalFile + "条数据 ";
        for(Teacher element: list)
        {
            res = 0;
            try{
                res = teacherService.insertOneTeacher(element);
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
