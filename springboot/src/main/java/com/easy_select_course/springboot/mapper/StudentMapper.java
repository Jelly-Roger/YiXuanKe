package com.easy_select_course.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Manager;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.entity.StudentDisplay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper  extends BaseMapper<Student> {

    @Select("select * from Student where Sno = #{no} and password = #{password}")
    List<Student> stuLogin(UserLoginDto userLoginDto);

//    @Select("select Sno, Sname, password, sex, grade from student")
//    List<Student> findAllStudent_withoutMajor();

    @Select("select Student.Sno, Major.majorName,Student.Sname, Student.password,Student.sex,Student.grade, Student.email from Student inner join Major on Student.MajorId = Major.MajorId;")
    List<StudentDisplay> findAllStudent_withMajor();

    @Select("select Student.Sno, Major.majorName,Student.Sname, Student.password,Student.sex,Student.grade, Student.email " +
            "from Student inner join Major on Student.MajorId = Major.MajorId where Sno like concat('%',#{sno},'%') and " +
            "Sname like concat('%',#{sname},'%') and grade like concat('%',#{grade},'%') limit #{pageNum}, #{pageSize};")
    List<StudentDisplay> findStudent_By_Page_Condition(int pageNum, int pageSize, String sno, String sname, String grade);

    @Select("select count(*) from Student where Sno like concat('%',#{sno},'%') and Sname like concat('%',#{sname},'%') and grade like concat('%',#{grade},'%');")
    int findTotal_By_Condition(String sno, String sname, String grade);


    @Select("select * from Student where email = #{email};")
    Student findByEmail(String email);

    @Insert("insert into Student values(#{sno}, #{MajorId}, #{sname}, #{password}, #{sex}, #{grade}, #{email})")
    int insertOneStudent(Student student);

    @Update("update Student set MajorId = #{MajorId}, Sname = #{sname}, password = #{password}, sex = #{sex}, grade = #{grade}, email = #{email} where Sno = #{sno}")
    int updateOneStudent(Student student);

    @Delete("delete from Student where Sno = #{sno}")
    int deleteStudentByNo(String sno);
}
