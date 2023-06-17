package com.easy_select_course.springboot.mapper;


import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.entity.Teacher;
import com.easy_select_course.springboot.entity.TeacherDisplay;
import org.apache.ibatis.annotations.*;

import java.util.List;

//类似学生Mapper
@Mapper
public interface TeacherMapper {
    //教师登录
    @Select("select * from Teacher where Tno = #{no} and password = #{password}; ")
    List<Teacher> teaLogin(UserLoginDto userLoginDto);

    //找到所有某个专业的教师并显示专业名和其他信息
    @Select("select Teacher.Tno, Major.MajorName,Teacher.Tname, Teacher.password, Teacher.sex,Teacher.title,Teacher.phone " +
            "from Teacher, Major " +
            "where Teacher.MajorId = Major.MajorId; ")
    List<TeacherDisplay> findAllTeacher_withAllMajor();

    //分页查询教师，根据教师号，姓名，职称
    @Select(" select Teacher.Tno, Major.MajorName,Teacher.Tname,Teacher.password,Teacher.sex,Teacher.title,Teacher.phone " +
            " from Teacher inner join Major on Teacher.MajorId = Major.MajorId " +
            " where Tno like concat('%',#{tno},'%') and Tname like concat('%',#{tname},'%') and title like concat('%',#{title},'%') " +
            " limit #{pageNum}, #{pageSize}; ")
    List<TeacherDisplay> findTeacher_By_Page_Condition(int pageNum, int pageSize, String tno, String tname, String title);

    //添加教师
    @Insert("insert into Teacher values " +
            "(#{Tno}, #{MajorId}, #{Tname}, #{password}, #{sex}, #{title}, #{phone})")
    int insertOneTeacher(Teacher teacher);

    //条件查询教师
    @Select("select count(*) from Teacher where Tno like concat('%',#{tno},'%') and Tname like concat('%',#{tname},'%') and title like concat('%',#{title},'%');")
    int findTotal_By_Condition(String tno, String tname, String title);

    @Update("update Teacher set MajorId = #{MajorId}, Tname = #{Tname}, password = #{password}, sex = #{sex}, title = #{title}, phone = #{phone} where Tno = #{Tno}")
    int updateOneTeacher(Teacher teacher);

    @Delete("delete from Teacher where Tno = #{Tno}")
    int deleteTeacherByNo(String Tno);
    //回答问题
    //@Insert("insert into Answer values(#{tno},#{qno},#{answer});")


}
