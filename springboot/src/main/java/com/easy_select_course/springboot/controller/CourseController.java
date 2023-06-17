package com.easy_select_course.springboot.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.easy_select_course.springboot.entity.Course;
import com.easy_select_course.springboot.entity.CourseDisplay;

import com.easy_select_course.springboot.service.CourseService;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping("/find_all_course")
    public List<CourseDisplay> findAllCourse()
    {
        return courseService.findAllCourse();
    }

    @GetMapping("/find_all_course_by_id")
    public CourseDisplay findAllCourseById(@RequestParam int Cid)
    {
        return courseService.findAllCourseById(Cid);
    }

    @GetMapping("/find_course_by_college_type")
    public List<CourseDisplay> findCourseByCollegeType(@RequestParam(defaultValue = "") String college,
                                                       @RequestParam(defaultValue = "") String type)
    {
        return courseService.findCourse_By_College_Type(college, type);
    }

    @GetMapping("/find_course_by_cname")
    public List<CourseDisplay> findCourseByCName(@RequestParam(defaultValue = "") String CName)
    {
        return courseService.findCourse_By_CName(CName);
    }


    @PostMapping("/insert_one_course")
    public int insertOneCourse(@RequestBody Course course)
    {
        return courseService.insertOneCourse(course);
    }

    @PostMapping("/update_one_course")
    public int updateOneCourse(@RequestBody Course course)
    {
        return courseService.updateOneCourse(course);
    }

    @DeleteMapping("/{Cid}")
    public int deleteOneCourseByNo(@PathVariable int Cid)
    {
        return courseService.deleteOneCourseByNo(Cid);
    }

    @GetMapping("/find_by_page_condition")
    public Map<String,Object> findCourse_By_Page_Condition(@RequestParam int pageNum,
                                                            @RequestParam int pageSize,
                                                            @RequestParam(defaultValue = "") String CName,
                                                            @RequestParam(defaultValue = "") String Cday,
                                                            @RequestParam(defaultValue = "") String Tno)
    {
        if(pageNum < 1 || pageSize < 1)
        {
            Map<String,Object> res = new HashMap<>();
            res.put("data", Collections.emptyList());
            res.put("total", 0);
            return res;
        }
        return courseService.findCourse_by_Page_Conditon(pageNum,pageSize,CName,Cday,Tno);
    }


    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception
    {
        List<CourseDisplay> list = courseService.findAllCourse();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;cjarset=utf-8");
        String fileName = URLEncoder.encode("课程信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public String InfoImport(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Course> list = reader.readAll(Course.class);
        int totalFile = list.size();
        int res = 0;
        int cnt = 0;
        String info = "总共识别到" + totalFile + "条数据 ";
        for(Course element: list)
        {
            res = 0;
            try{
                res = courseService.insertOneCourse(element);
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
