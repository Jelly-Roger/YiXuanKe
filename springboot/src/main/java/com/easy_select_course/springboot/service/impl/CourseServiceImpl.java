//package com.easy_select_course.springboot.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.easy_select_course.springboot.entity.Course;
//import com.easy_select_course.springboot.mapper.CourseMapper;
//import com.easy_select_course.springboot.service.ICourseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
///**
// * <p>
// *  服务实现类
// * </p>
// *
// * @author marca
// * @since 2023-04-17
// */
//@Service
//
//public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
//    @Autowired
//    private CourseMapper courseMapper;
//    public boolean saveCourse(Course course){
//        if (!courseMapper.findById(course.getCid())){
//            return courseMapper.insert(course)>0;
//        }
//        else{
//            return courseMapper.update(course)>0;
//        }
//    }
//    public boolean findById(Integer id){
//        return courseMapper.findById(id);
//    }
//}
