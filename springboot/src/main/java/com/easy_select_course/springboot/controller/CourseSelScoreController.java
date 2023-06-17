package com.easy_select_course.springboot.controller;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.easy_select_course.springboot.entity.CourseDisplay;
import com.easy_select_course.springboot.entity.CourseTemp;
import com.easy_select_course.springboot.entity.StudentDisplay;
import com.easy_select_course.springboot.entity.courseSevice.*;
import com.easy_select_course.springboot.service.CourseSelService;
import com.easy_select_course.springboot.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseSelScoreController {

    @Resource
    private CourseSelService courseSelService;
    @Resource
    private CourseService courseService;

    /*学生选课相关*/
    //提交选课信息
    /*@PostMapping("/submit_course_select")
    public int submitCourseSelect(@RequestBody List<CourseSe> courseScoreList)
    {
        if(courseScoreList == null || courseScoreList.size() == 0)
            return 0;
        return courseSelService.submitCourseSelect(courseScoreList);
    }*/



    //学生 提交选课信息,返回成功插入的课程ID列表
    @PostMapping("/submit_course_select")
    public Map<Integer,String> submitCourseSelect(@RequestBody CourseSelect courseScoreList)
    {
        if(courseScoreList == null || courseScoreList.getSno()==null ||courseScoreList.getCidList()== null ||courseScoreList.getCidList().size() == 0)
            return new HashMap<>();
        return courseSelService.submitCourseSelect(courseScoreList);
    }


    /*课程查看*/
    //根据课号和课程名字查询所有课程的选课信息 {data:[CourseTemp,],total:Integer}
    @PostMapping("/delete_course_select")
    public Map<Integer,String> delCourseSelect(@RequestBody CourseSelect courseScoreList)
    {
        if(courseScoreList == null || courseScoreList.getSno()==null ||courseScoreList.getCidList()== null ||courseScoreList.getCidList().size() == 0)
            return new HashMap<>();
        return courseSelService.delCourseSelect(courseScoreList);
    }


    //学生 根据课号和课程名字查询所有课程的选课信息 {data:[CourseTemp,],total:Integer}
    @GetMapping("/all_course_list_for_select")
    public Map<String,Object> getCourseInfoForSelByCidAndCname(@RequestParam(defaultValue = "") String Cday,
                                                             @RequestParam(defaultValue = "") String CName,
                                                             @RequestParam(defaultValue = "1") int pageNum,
                                                             @RequestParam(defaultValue = "100") int pageSize,
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

    @GetMapping("/find_course_by_cid")
    public List<CourseDisplay> findCourseByCid(@RequestParam List<Integer> Cid)
    {
        return courseService.findCourseByCid(Cid);
    }
//    @GetMapping("/course_select")
//    public List<CourseScore> getAllCourseSelect()
//    {
//        return courseSelService.getAllCourseSelect();
//    }


    //查询某学生的选课记录
    @GetMapping("/course_schedule")
    public StuCourseSelDisplay getCourseSelectByCid(@RequestParam String Sno)
    {
        if(Sno == null|| Sno.equals(""))
            return null;
        return courseSelService.getCourseScheduleBySno(Sno);
    }


    //分页查询学生选课的信息，准备录入成绩
    @GetMapping("/select_info_by_page")
    public List<StuCourseScoreDisplay> getAllStuScoreByCid(@RequestParam int pageNum,
                                                           @RequestParam int pageSize,
                                                           @RequestParam(defaultValue = "1") int cid)
    {

        if(pageNum < 1 || pageSize < 1)
        {
            return List.of();
        }
        return courseSelService.getAllStuScoreByCid(pageNum, pageSize, cid);
    }

    @GetMapping("/find_cid_by_sno")
    public List<Integer> findCidBySno(@RequestParam String Sno)
    {
        return courseSelService.findCidBySno(Sno);
    }

    /*学生成绩相关*/
    //学生查询自己的绩点，学分，不及格门数，不及格学分
    //绩点：根据成绩等级（优：5，良：4，中：3，及格：2，不及格：0）计算，按学分加权平均
    //不及格门数：成绩等级为“不及格”的课程门数
    //不及格学分：成绩等级为“不及格”的课程学分之和
    //课程成绩：List<CourseScore>
    @GetMapping("/score_info")
    public StuScoreInfoDisplay getStuScoreInfo(@RequestParam String Sno)
    {
        if(Sno == null || Sno.equals(""))
            return null;
        return courseSelService.getStuScoreInfo(Sno);
    }

    //学生获取详细的成绩信息
    @GetMapping("/score_detail")
    public List<CourseScore> getStuScoreDetail(@RequestParam String Sno)
    {
        if(Sno == null || Sno.equals(""))
            return null;
        return courseSelService.getStuScoreDetail(Sno);
    }

    @GetMapping("/export_personal_grade")
    public void export(@RequestParam String Sno, HttpServletResponse response) throws Exception
    {
        if(Sno == null || Sno.equals(""))
            return;
        System.out.println("Sno");
        System.out.println(Sno);
        List<CourseScore> list = courseSelService.getStuScoreDetail(Sno);

        System.out.println("List");
        System.out.println(list);
        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;cjarset=utf-8");
        String fileName = URLEncoder.encode("个人成绩", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }





    /*老师成绩相关*/
    //老师根据自己工号和课程名分页查询学生选课的信息，准备录入成绩
    @GetMapping("/select_info_by_page_tno")
    public List<StuCourseScoreDisplay> getAllStuScoreByCidAndTno(@RequestParam int pageNum,
                                                           @RequestParam int pageSize,
                                                           @RequestParam(defaultValue = "") String CName,
                                                           @RequestParam String Tno)
    {

        if(pageNum < 1 || pageSize < 1)
        {
            return List.of();
        }
        return courseSelService.getAllStuScoreByCNameAndTno_ByPage(pageNum, pageSize, CName, Tno);
    }

    //录入(更新)成绩的申请
    @PostMapping("/app_update_scores")
    public int appUpdateCourseScores(@RequestBody List<AppScoreUpdate> stuCourseScoreDisplays)
    {
        //插入消息表
        System.out.println(stuCourseScoreDisplays);
        if (stuCourseScoreDisplays == null || stuCourseScoreDisplays.size() == 0)
        {
            System.out.println("no scores to appUpdate!");
            return 0;
        }
        //TODO: 获取当前登录管理员的id
        return courseSelService.appUpdateCourseScores(stuCourseScoreDisplays);
    }


    //真正更新成绩
    @PostMapping("/update_scores")
    public int updateCourseScores(@RequestBody List<CourseScore> courseScoreUpdate) {
        if (courseScoreUpdate == null || courseScoreUpdate.size() == 0)
        {
            System.out.println("no scores to update!");
            return 0;
        }

        return courseSelService.updateCourseScores(courseScoreUpdate);
    }


}
