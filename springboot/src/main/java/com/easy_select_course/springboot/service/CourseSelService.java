package com.easy_select_course.springboot.service;


import cn.hutool.core.util.StrUtil;
import com.easy_select_course.springboot.entity.CourseTemp;
import com.easy_select_course.springboot.entity.courseSevice.*;
import com.easy_select_course.springboot.mapper.CourseMapper;
import com.easy_select_course.springboot.mapper.CourseSelectMapper;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.util.*;

//@Transactional
@Service
public class CourseSelService {

    @Autowired
    private CourseSelectMapper cSelMapper;

    @Autowired
    private CourseMapper courseMapper;


    /*学生 提交一个学生选若干课的选课信息，
    * 选课信息包括：学号、课号列表
    * 插入（学号、课号）到选课表中,但是要先检查课程人数是否已满
    * 返回成功插入的课程ID列表
    * */
    @Transactional
    public Map<Integer,String> submitCourseSelect(CourseSelect courseSelect)
    {
        Map<Integer,String> res=new HashMap<>();

        //0、检查传入参数非空
        if(courseSelect == null)
            return res;

        String sno = courseSelect.getSno();
        for (Integer cid : courseSelect.getCidList()) {
            try {
                // 1、检查课程人数是否已满
                Pair<Integer, Integer> chosen = courseMapper.checkChosenNum(cid);
                if (chosen == null) {
                    res.put(cid, "选课失败，课程选择人数已经达到上限");
                    courseMapper.releaseLock(cid);
                    continue;
                }
                if(!courseMapper.checkTimeConflict(cid,sno).isEmpty()){
                    res.put(cid, "选课失败，课程时间冲突或已选过");
                    courseMapper.releaseLock(cid);
                    continue;
                }

                // 2、执行选课与更新
                if (insertOneCourseSel(courseSelect.getSno(), cid) == 0) {
                    res.put(cid, "选课失败，其他错误");
                    courseMapper.releaseLock(cid);
                    continue;
                }

                // 3、更新课程人数
                Integer newChosenNum = chosen.getSecond() + 1;
                if (courseMapper.updateChosenNum(cid, newChosenNum) == 1) {
                    res.put(cid, "选课成功");
                } else {
                    // 更新选课人数失败
                    res.put(cid, "选课失败，更新选课人数时发生错误");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    courseMapper.releaseLock(cid);
                    // 回滚

                }
            } catch (Exception e) {
                // 处理异常情况
                res.put(cid, "选课失败，发生异常：" + e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                courseMapper.releaseLock(cid);
            }
        }

        return res;
    }

    @Transactional
    public Map<Integer,String> delCourseSelect(CourseSelect courseSelect)
    {
        Map<Integer,String> res=new HashMap<>();

        //0、检查传入参数非空
        if(courseSelect == null)
            return res;

        for (Integer cid : courseSelect.getCidList()) {
            try {
            //1、拿到课程人数
                int chosenNum = courseMapper.getChosenNum(cid);
                if(chosenNum <= 0)
                {
                    res.put(cid,"退课失败，选课人数为0");
                    courseMapper.releaseLock(cid);
                    continue;
                }
                //2、执行退课操作
                if (deleteOneCourseSel(courseSelect.getSno(),cid)==0)
                {
                    res.put(cid,"退课失败，删除数据库数据时发生错误");
                    courseMapper.releaseLock(cid);
                    continue;
                }
                //3、更新课程人数
                Integer newChosenNum = chosenNum - 1;
                if(courseMapper.updateChosenNum(cid,newChosenNum) == 1)//如果更新选课人数成功
                {
                    res.put(cid,"退课成功");
                }
                else {
                    //更新选课人数失败
                    res.put(cid,"退课失败，更新选课人数时发生错误");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    courseMapper.releaseLock(cid);
                    //回滚

                }

            //TODO:已经插入了的课程，如果更新选课人数失败，应该？
            }
            catch (Exception e) {
                // 处理异常情况
                res.put(cid, "选课失败，发生异常：" + e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                courseMapper.releaseLock(cid);
            }
        }


        return res;
    }

    //根据课号和课程名字分页查询查询所有课程的选课信息（模糊匹配）
//    public List<CourseTemp> getCourseInfoSelByCidAndCname_PageCondition(Integer pageNum, Integer pageSize, String CName, String Cday, String Tno)
//    {
//        List<CourseTemp> courseInfoList;
//
//        try{
//            courseInfoList = courseService.findCourse_by_Page_Conditon(pageNum,pageSize,CName,Cday,Tno);
//        }catch (Exception e){
//            System.out.println(e);
//            return List.of();
//        }
//        return courseInfoList;
//    }





    //？？？ 插入一条选课信息
    public int insertOneCourseSel(String Sno, Integer Cid)
    {
        if(Sno == null || Cid == null)
            return 0;

        int res = 0;
        try{
            res = cSelMapper.insertOneCourseSel(Sno, Cid);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    //？？？ 删除一条选课信息
    public int deleteOneCourseSel(String Sno, Integer Cid)
    {
        if(Sno == null || Cid == null)
            return 0;

        int res = 0;
        try{
            res = cSelMapper.deleteOneCourseSel(Sno, Cid);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }

        return res;
    }

    public List<Integer> findCidBySno(String Sno)
    {
        if(StrUtil.isBlank(Sno))
            return Collections.emptyList();
        List<Integer> res;
        try{
            res = cSelMapper.selectCidBySno(Sno);
        }catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
        return res;
    }


    //学生 查询自己所有课程的成绩
    public List<StuCourseScoreDisplay> getAllScoreBySno(String Sno) {

        List<StuCourseScoreDisplay> CScoreInfo;

        try{
            CScoreInfo = cSelMapper.getAllScoreBySno(Sno);
        }catch (Exception e){
            System.out.println(e);
            return List.of();
        }
        return CScoreInfo;
    }

    //查询选某个课所有学生的成绩
    public List<StuCourseScoreDisplay> getAllStuScoreByCid(int pageNum, int pageSize,Integer Cid) {

        List<StuCourseScoreDisplay> CScoreInfo;

        int pageStart = (pageNum-1)*pageSize;
//        int total = 0;

        try{
            CScoreInfo = cSelMapper.getAllScoreByCid_ByPage(pageStart,pageSize,Cid);
        }catch (Exception e){
            System.out.println(e);
            return List.of();
        }

        return CScoreInfo;

    }



    //老师 根据自己的教师号查询自己教授的所有课程的成绩
    public List<StuCourseScoreDisplay> getAllStuScoreByCNameAndTno_ByPage(int pageNum, int pageSize,String CName,String Tno) {

        List<StuCourseScoreDisplay> CScoreInfo;

        int pageStart = (pageNum-1)*pageSize;
//        int total = 0;

        try{
            CScoreInfo = cSelMapper.getAllScoreByCNameAndTno_ByPage(pageStart,pageSize,CName,Tno);
        }catch (Exception e){
            System.out.println(e);
            return List.of();
        }

        return CScoreInfo;

    }


    //老师 提交一组成绩更新的申请（含清空）
    public int appUpdateCourseScores(List<AppScoreUpdate> courseScoreList)
    {

        for (AppScoreUpdate appScoreUpdate : courseScoreList) {


            //获取Tno,Sno, Cid, Score
            String Tno = appScoreUpdate.getTno();
            String Sno = appScoreUpdate.getSno();
            Integer Cid = appScoreUpdate.getCid();

            if(Tno==null||Sno == null || Cid == null)
                continue;

//            Float procScore = appScoreUpdate.getProcScore();
//            Float examScore = appScoreUpdate.getExamScore();
//            Float finalScore = appScoreUpdate.getFinalScore();
//            String scoreRank = appScoreUpdate.getScoreRank();


            appScoreUpdate.setSubmit_time(new java.sql.Date(new java.util.Date().getTime()));
            Date submit_time = appScoreUpdate.getSubmit_time();
            //更新一个学生一门课的成绩
            int res = 0;
            try{
                res = cSelMapper.insertAppForGrade(appScoreUpdate);
                int aid = appScoreUpdate.getId();
                System.out.println(aid);
                //插入到AppCheck(教务没有默认值。。。先写1)
                //TODO: 获取当前登录管理员的mid
                res=cSelMapper.insertAppCheck("成绩修改",aid, 1, submit_time);

            }catch (Exception e){
                System.out.println(e);
                System.out.println("exception");
                return 0;
            }

            return res;
        }

        return 0;
    }


    //真正更新一组成绩的接口
    public int updateCourseScores(List<CourseScore> courseScoreList)
    {
        //迭代访问List<CourseSelect>
        for (CourseScore courseScore : courseScoreList) {

            //获取Sno, Cid, Score
            String Sno = courseScore.getSno();
            Integer Cid = courseScore.getCid();

            if(Sno == null || Cid == null)
                continue;

            Float procScore = courseScore.getProcScore();
            Float examScore = courseScore.getExamScore();
            Float finalScore = courseScore.getFinalScore();
            String scoreRank = courseScore.getScoreRank();

            //更新一个学生一门课的成绩
            int res = 0;
            try{
                res = cSelMapper.updateOneScore(Sno, Cid, procScore,examScore,finalScore,scoreRank);
            }catch (Exception e){
                System.out.println(e);
                return 0;
            }

            return res;
        }

        return 0;
    }

    //学生 查询自己所有课程的选课记录 返回课程表日历和课程信息列表
    public StuCourseSelDisplay getCourseScheduleBySno(String Sno) {

        StuCourseSelDisplay stuCourseSelDisplay = new StuCourseSelDisplay();

        //获取课程信息列表
        List<CourseInfoDisplayForSchedule> courseInfoList;
        try {
            courseInfoList = cSelMapper.getCourseInfoForScheduleBySno(Sno);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }


        //获取矩阵
        List<CourseSchedule> courseScheduleList;
        try {
            courseScheduleList = cSelMapper.getCourseScheBySno(Sno);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }


        List<List<Integer>> courseScheMat=new ArrayList<>();
        //初始化矩阵，7*12
        for(int i=0;i<7;i++)
        {
            List<Integer> temp=new ArrayList<>();
            for(int j=0;j<12;j++)
            {
                temp.add(-1);
            }
            courseScheMat.add(temp);
        }

        //创建周一-周日映射到0-6
        Map<String,Integer> dayMap = new HashMap<>();
        dayMap.put("周一",0);
        dayMap.put("周二",1);
        dayMap.put("周三",2);
        dayMap.put("周四",3);
        dayMap.put("周五",4);
        dayMap.put("周六",5);
        dayMap.put("周日",6);

        //填充矩阵,遍历课程表
        for (int i = 0; i < courseScheduleList.size() ; i++) {
            //获取Cid, Day, Time
            CourseSchedule courseSchedule = courseScheduleList.get(i);
            Integer Cid = dayMap.get(courseSchedule.getCday());
            Integer startSec = courseSchedule.getTimeStart();
            Integer endSec = courseSchedule.getTimeEnd();
            for (int j = startSec; j <= endSec; j++) {
                courseScheMat.get(Cid).set(j-1,i);//1表示有课
            }
        }

        stuCourseSelDisplay.setCourseInfoList(courseInfoList);
        stuCourseSelDisplay.setCourseScheMat(courseScheMat);

        return stuCourseSelDisplay;
    }

    /*学生成绩相关*/
    //学生查询自己的绩点，学分，不及格门数，不及格学分
    //绩点：根据成绩等级（优：5，良：4，中：3，及格：2，不及格：0）计算，按学分加权平均
    //不及格门数：成绩等级为“不及格”的课程门数
    //不及格学分：成绩等级为“不及格”的课程学分之和
    public StuScoreInfoDisplay getStuScoreInfo(String sno) {

            StuScoreInfoDisplay stuScoreInfoDisplay = new StuScoreInfoDisplay();

            //获取所有信息
            try {
                stuScoreInfoDisplay = cSelMapper.getStuScoreInfo(sno);
            }
            catch (Exception e){
                System.out.println(e);
                return null;
            }


            return stuScoreInfoDisplay;
    }

    //学生获取详细的成绩信息
    public List<CourseScore> getStuScoreDetail(String sno) {

            List<CourseScore> courseScoreList = new ArrayList<>();

            //获取所有信息
            try {
                courseScoreList = cSelMapper.getStuScoreDetail(sno);
            }
            catch (Exception e){
                System.out.println(e);
                return null;
            }

            return courseScoreList;
    }
}
