package com.easy_select_course.springboot.service;


import cn.hutool.core.util.StrUtil;

import com.easy_select_course.springboot.entity.Course;
import com.easy_select_course.springboot.entity.CourseDisplay;

import com.easy_select_course.springboot.entity.CourseTemp;
import com.easy_select_course.springboot.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CourseService {


    @Autowired
    private CourseMapper courseMapper;

    public int insertOneCourse(Course course)
    {
        if(StrUtil.isBlank(course.getCName()))
            return 0;
        if(course.getCcredit() < 0)
            return 0;
        if(StrUtil.isBlank(course.getCday()))
            return 0;
        if(StrUtil.isBlank(course.getTno()))
            return 0;
        if(StrUtil.isBlank(course.getClassroom()))
            return 0;
        if(StrUtil.isBlank(course.getCourseDesc()))
            course.setCourseDesc(null);

        int res = 0;
        try{
            res = courseMapper.insertOneCourse(course);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }

        return res;
    }

    public int updateOneCourse(Course course)
    {
        if(StrUtil.isBlank(course.getCName()))
            return 0;
        if(course.getCcredit() < 0)
            return 0;
        if(StrUtil.isBlank(course.getCday()))
            return 0;
        if(StrUtil.isBlank(course.getTno()))
            return 0;
        if(StrUtil.isBlank(course.getClassroom()))
            return 0;
        if(StrUtil.isBlank(course.getCourseDesc()))
            course.setCourseDesc(null);

        int res = 0;
        try{
            res = courseMapper.updateOneCourse(course);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }

        return res;
    }

    public int updateChosenNum(int Cid, int chosenNum)
    {
        if(Cid < 0 || chosenNum < 0)
            return 0;
        int res = 0;
        try{
            res = courseMapper.updateChosenNum(Cid, chosenNum);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public int deleteOneCourseByNo(int Cid)
    {
        if(Cid <= 0)
            return 0;
        int res = 0;
        try {
            res = courseMapper.deleteCourseByNo(Cid);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public CourseDisplay convertFormat(CourseTemp element)
    {
        CourseDisplay temp = new CourseDisplay();
        temp.setCid(element.getCid());
        temp.setCName(element.getCName());
        temp.setCcredit(element.getCcredit());
        temp.setSchedule(element.getCday() + " " + element.getTimeStart() +"到"+element.getTimeEnd()+"节");
        temp.setCollege(element.getCollege());
        temp.setType(element.getType());
        temp.setChosenCon(element.getChosenNum()+"/"+ element.getTotalNum());
        temp.setCourseDesc(element.getCourseDesc());
        temp.setTno(element.getTno());
        temp.setTname(element.getTname());
        temp.setClassroom(element.getClassroom());
        return temp;
    }

    public Map<String, Object> findCourse_by_Page_Conditon(int pageNum, int pageSize, String CName, String Cday, String Tno)
    {
        Map<String, Object> res = new HashMap<>();
        List<CourseDisplay> info = new ArrayList<>();
        List<CourseTemp> convert;

        int total = 0;
        int pageStart = (pageNum-1)*pageSize;
        convert = courseMapper.findCourse_By_Page_Condition(pageStart, pageSize, CName, Cday, Tno);
//        System.out.println("info");
//        System.out.println(info);
        for(CourseTemp element: convert)
        {
            CourseDisplay temp = convertFormat(element);
            info.add(temp);
        }
        total = courseMapper.findTotal_By_Condition(CName, Cday, Tno);
        res.put("data", info);
        res.put("total", total);
        return res;
    }
    public List<CourseDisplay> findCourseByCid(List<Integer> Cid)
    {
        List<CourseDisplay> res = new ArrayList<>();
        List<CourseTemp> convert = new ArrayList<>();
        for(int index :Cid)
        {
            CourseTemp tmp = courseMapper.findCourseByCid(index);
            convert.add(tmp);
        }
        for(CourseTemp element: convert)
        {
            CourseDisplay temp = convertFormat(element);
            res.add(temp);
        }
        return res;

    }
    public List<CourseDisplay> findAllCourse()
    {
        List<CourseDisplay> res = new ArrayList<>();
        List<CourseTemp> convert;

        try{
            convert = courseMapper.findAllCourse();
        }catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
        for(CourseTemp element: convert)
        {
            CourseDisplay temp = convertFormat(element);
            res.add(temp);
        }
        return res;
    }

    public CourseDisplay findAllCourseById(int Cid)
    {
        CourseDisplay res = new CourseDisplay();
        CourseTemp element;
        if(Cid <= 0)
            return res;
        try{
            element = courseMapper.findAllCourseById(Cid);
        }catch (Exception e){
            System.out.println(e);
            return res;
        }
        res = convertFormat(element);

        return res;
    }

    public List<CourseDisplay> findCourse_By_College_Type(String college, String type)
    {
        List<CourseDisplay> res = new ArrayList<>();
        List<CourseTemp> convert;

        try{
            convert = courseMapper.findCourse_By_College_Type(college, type);
        }catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
        for(CourseTemp element: convert)
        {
            CourseDisplay temp = convertFormat(element);
            res.add(temp);
        }
        return res;
    }

    public List<CourseDisplay> findCourse_By_CName(String CName)
    {
        List<CourseDisplay> res = new ArrayList<>();
        List<CourseTemp> convert;

        try{
            convert = courseMapper.findCourse_By_CName(CName);
        }catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
        for(CourseTemp element: convert)
        {
            CourseDisplay temp = convertFormat(element);
            res.add(temp);
        }
        return res;
    }

}
