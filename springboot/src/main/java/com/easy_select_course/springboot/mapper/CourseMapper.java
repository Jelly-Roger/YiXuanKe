package com.easy_select_course.springboot.mapper;

import com.easy_select_course.springboot.entity.Course;


import com.easy_select_course.springboot.entity.CourseDisplay;


import com.easy_select_course.springboot.entity.CourseTemp;
import org.apache.commons.math3.util.Pair;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper{


    //检查选课人数是否已满【加锁】
    @Select("select Cid,chosenNum from Course where Cid = #{Cid} " +
            "and chosenNum < totalNum FOR UPDATE ")
    Pair<Integer,Integer> checkChosenNum(int Cid);


    //检查选课时间冲突
    @Select("SELECT C2.Cid  " +
            "FROM Course C1 " +
            "JOIN Course C2 JOIN StuCourseSelect CS " +
            "ON C1.Cid = #{Cid} AND CS.Sno=#{Sno} AND C2.Cid=CS.Cid " +
            "AND C2.Cday = C1.Cday AND C2.timeStart < C1.timeEnd AND C2.timeEnd > C1.timeStart; ")
    List<Integer> checkTimeConflict(int Cid, String Sno);

    //释放锁【使用没有实际意义的更新】
    @Update("UPDATE Course SET Cday = Cday WHERE Cid = #{Cid}")
    void releaseLock(int Cid);

    @Select("select chosenNum from Course where Cid = #{Cid} FOR UPDATE ")
    int getChosenNum(int Cid);



    @Select("select Course.*, Teacher.Tname from Course inner join Teacher on Course.Tno = Teacher.Tno;")
    List<CourseTemp> findAllCourse();

    @Select("select Course.*, Teacher.Tname from Course inner join Teacher on Course.Tno = Teacher.Tno where Cid = #{Cid}")
    CourseTemp findAllCourseById(int Cid);

    @Select("select Course.*, Teacher.Tname from Course inner join Teacher on Course.Tno = Teacher.Tno " +
            "where CName like concat('%',#{CName},'%') and " +
            "Cday like concat('%',#{Cday},'%') and Course.Tno like concat('%',#{Tno},'%') limit #{pageNum}, #{pageSize};")
    List<CourseTemp> findCourse_By_Page_Condition(int pageNum, int pageSize, String CName, String Cday, String Tno);

    @Select("select Course.*, Teacher.Tname from Course inner join Teacher on Course.Tno = Teacher.Tno where Cid = #{Cid}")
    CourseTemp findCourseByCid(int Cid);

    @Select("select Course.*, Teacher.Tname from Course inner join Teacher on Course.Tno = Teacher.Tno " +
            "where college like concat('%', #{college}, '%') and type like concat('%', #{type}, '%')")
    List<CourseTemp> findCourse_By_College_Type(String college, String type);

    @Select("select Course.*, Teacher.Tname from Course inner join Teacher on Course.Tno = Teacher.Tno where CName like concat('%', #{CName}, '%')")
    List<CourseTemp> findCourse_By_CName(String CName);


    @Select("select count(*) from Course where CName like concat('%',#{CName},'%') and Cday like concat('%',#{Cday},'%') and Course.Tno like concat('%',#{Tno},'%');")
    int findTotal_By_Condition(String CName, String Cday, String Tno);

    @Insert("insert into Course values(null, #{CName}, #{Ccredit}, #{Cday}, #{timeStart}, #{timeEnd}, #{college}, #{type}, #{chosenNum}, #{totalNum}, #{CourseDesc}, #{Tno}, #{classroom})")
    int insertOneCourse(Course course);

    @Update("update Course set CName = #{CName}, Ccredit = #{Ccredit}, Cday = #{Cday}, timeStart = #{timeStart}, timeEnd = #{timeEnd}, " +
            "college = #{college}, type = #{type}, chosenNum = #{chosenNum}, totalNum = #{totalNum}, CourseDesc = #{CourseDesc}, Tno = #{Tno}, classroom = #{classroom} where Cid = #{Cid}")
    int updateOneCourse(Course course);

    @Update("update Course set chosenNum = #{chosenNum} where Cid = #{Cid}")
    int updateChosenNum(int Cid, int chosenNum);

    @Delete("delete from Course where Cid = #{Cid}")
    int deleteCourseByNo(int Cid);

}

