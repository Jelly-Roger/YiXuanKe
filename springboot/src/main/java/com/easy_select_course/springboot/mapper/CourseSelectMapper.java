package com.easy_select_course.springboot.mapper;

import com.easy_select_course.springboot.entity.courseSevice.*;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface CourseSelectMapper {

    //查询某个学生的选课时间表[course+StuCourseSelect]
    @Select("SELECT c.Cday,c.timeStart,c.timeEnd " +
            "FROM Course as c , StuCourseSelect as cs " +
            "WHERE cs.Sno=#{Sno} AND c.Cid=cs.Cid " +
            "ORDER BY c.Cid ASC ")
    List<CourseSchedule> getCourseScheBySno(String Sno);


    //查询某个学生的选课记录（不含成绩）[course+StuCourseSelect+Teacher]
    @Select("SELECT c.Cid,c.CName,c.Ccredit,c.CourseDesc, " +
            "t.Tno,t.Tname,c.Cday,   c.timeStart,c.timeEnd, c.classroom, " +
            "(SELECT COUNT(*) FROM StuCourseSelect WHERE Cid = c.Cid) AS SNum  " +
            "FROM StuCourseSelect as cs, Course as c , Teacher as t " +
            "WHERE cs.Sno=#{Sno} AND cs.Cid=c.Cid AND c.Tno=t.Tno " +
            "ORDER BY c.Cid ASC  ")
    List<CourseInfoDisplayForSchedule> getCourseInfoForScheduleBySno(String Sno);

    //插入一个学生的选课信息（学号，课程id）(不含成绩)
    @Insert("INSERT into StuCourseSelect (Sno,Cid) VALUES(#{Sno},#{Cid})")
    int insertOneCourseSel(String Sno, Integer Cid);

    //删除某学生的某门课程的选课信息（学号，课程id）
    @Delete("DELETE FROM StuCourseSelect WHERE Sno=#{Sno} AND Cid=#{Cid}")
    int deleteOneCourseSel(String Sno, Integer Cid);

    @Select("select Cid from StuCourseSelect where Sno = #{Sno}")
    List<Integer> selectCidBySno(String Sno);


    /*下边是成绩处理部分*/


    //往成绩更新申请表中插入一条记录
//    @Insert("INSERT into AppForGrade" +
//            "(Tno,Sno,Cid,procScore,examScore,finalScore,ScoreRank,submit_time) " +
//            "VALUES(#{Tno},#{Sno},#{Cid},#{procScore},#{examScore},#{finalScore},#{ScoreRank},#{submit_time}) ")
    public int insertAppForGrade(AppScoreUpdate appScoreUpdate);

    //学生查询自己的绩点，学分，不及格门数，不及格学分
    //绩点：根据成绩等级（优：5，良：4，中：3，及格：2，不及格：0）计算，按学分加权平均
    //不及格门数：成绩等级为“不及格”的课程门数
    //不及格学分：成绩等级为“不及格”的课程学分之和
    @Select("SELECT "+
    " SUM((CASE CS.ScoreRank " +
            " WHEN '优' THEN 5 " +
            " WHEN '良' THEN 4 " +
            " WHEN '中' THEN 3 " +
            " WHEN '及格' THEN 2 " +
            " WHEN '不及格' THEN 0 " +
            " ELSE 0 " +
            " END) * C.Ccredit) / SUM(C.Ccredit) AS GPA, " +
    " SUM(C.Ccredit) AS credit, " +
    " SUM(CASE WHEN CS.scoreRank = '不及格' THEN 1 ELSE 0 END) AS failedNum, " +
    " SUM(CASE WHEN CS.scoreRank = '不及格' THEN C.Ccredit ELSE 0 END) AS failedCredit " +
    " FROM " +
    " StuCourseSelect CS " +
    " JOIN " +
    " Course C ON CS.Cid = C.Cid " +
    " WHERE " +
    " CS.Sno = #{Sno} " +
    " AND CS.scoreRank IS NOT NULL;")
    public StuScoreInfoDisplay getStuScoreInfo(String Sno);



    //录入（或者更新）某学生的某门课程的成绩
    @Update("UPDATE StuCourseSelect " +
            "SET procScore=#{procScore} ,examScore=#{examScore},finalScore=#{finalScore},ScoreRank=#{ScoreRank} " +
            "WHERE Sno=#{Sno} AND Cid=#{Cid}")
    int updateOneScore(String Sno, Integer Cid, Float procScore,Float examScore,Float finalScore,String ScoreRank);

    //查询某学生的某门课程的成绩
    //查询课Id、课名、学号、专业、姓名、年级、平时成绩、期末成绩、总评成绩、成绩等级
    @Select("SELECT c.Cid,c.CName,s.Sno,s.Sname,m.majorName,s.grade,cs.procScore,cs.examScore,cs.finalScore,cs.ScoreRank " +
            "FROM StuCourseSelect as cs, Student as s, Course as c ,Major as m " +
            "WHERE s.Sno=#{Sno} and m.MajorId = s.MajorId " +
            "AND cs.Cid=c.Cid AND cs.Sno=s.Sno ")
    List<StuCourseScoreDisplay> getAllScoreBySno(String Sno);


    //查询某课程的所有学生的成绩（应该在Course中实现？）
    //查询课Id、课名、学号、专业、姓名、年级、平时成绩、期末成绩、总评成绩、成绩等级
    @Select("SELECT c.Cid,c.CName,s.Sno,s.Sname,m.majorName,s.grade,cs.procScore,cs.examScore,cs.finalScore,cs.ScoreRank " +
            "FROM StuCourseSelect as cs, Student as s, Course as c ,Major as m " +
            "WHERE cs.Cid=#{Cid} and m.MajorId = s.MajorId " +
            "AND cs.Cid=c.Cid AND cs.Sno=s.Sno " +
            "limit #{pageStart}, #{pageSize};")
    List<StuCourseScoreDisplay> getAllScoreByCid_ByPage(Integer pageStart,Integer pageSize,Integer Cid);


    //老师 查询自己教授的课程的所有学生的成绩（根据Cid）
    //查询课Id、课名、学号、专业、姓名、年级、平时成绩、期末成绩、总评成绩、成绩等级
    @Select("SELECT c.Cid,c.CName,s.Sno,s.Sname,m.majorName,s.grade,cs.procScore,cs.examScore,cs.finalScore,cs.ScoreRank " +
            "FROM StuCourseSelect as cs, Student as s, Course as c ,Major as m,Teacher as t " +
            "WHERE c.CName like concat('%',#{CName},'%') and m.MajorId = s.MajorId and c.Tno = t.Tno and c.Tno=#{Tno} " +
            "AND cs.Cid=c.Cid AND cs.Sno=s.Sno " +
            "limit #{pageStart}, #{pageSize};")
    List<StuCourseScoreDisplay> getAllScoreByCNameAndTno_ByPage(int pageStart, int pageSize, String CName, String Tno);


    //插入一条审核记录
    @Insert("INSERT into AppCheck (type,aid,mid,submit_time)" +
            " VALUES(#{type},#{aid},#{mid},#{submit_time}) ")
    int insertAppCheck(String type, int aid, int mid, Date submit_time);

    @Select("SELECT cs.Sno,cs.Cid,c.CName,cs.procScore,cs.examScore,cs.finalScore,cs.ScoreRank " +
            "FROM StuCourseSelect as cs ,Course as c " +
            "WHERE cs.Cid=c.Cid AND cs.Sno=#{Sno} ")
    List<CourseScore> getStuScoreDetail(String Sno);
}
