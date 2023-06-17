package com.easy_select_course.springboot.mapper;

import com.easy_select_course.springboot.entity.AppCheck;
import com.easy_select_course.springboot.entity.AppForGrade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppCheckMapper {
    // 添加审核信息
    @Insert("INSERT INTO AppCheck(type, aid, mid, status, is_approved, submit_time, audit_time) " +
            "VALUES(#{type}, #{aid}, #{mid}, #{status}, #{isApproved}, #{submitTime}, #{auditTime})")
    int add(AppCheck appCheck);

    // 更新审核信息
    @Update("UPDATE AppCheck SET type=#{type}, aid=#{aid}, mid=#{mid}, status=#{status}, is_approved=#{isApproved}, " +
            "submit_time=#{submitTime}, audit_time=#{auditTime} WHERE id=#{id}")
    int update(AppCheck appCheck);

    // 根据ID删除审核信息
    @Delete("DELETE FROM AppCheck WHERE id=#{id}")
    int deleteById(int id);

    // 根据ID查询审核信息
    @Select("SELECT * FROM AppCheck WHERE id=#{id}")
    AppCheck findById(int id);

    // 查询所有审核信息
    @Select("SELECT * FROM AppCheck")
    List<AppCheck> findAll();

    // 根据申请编号查询审核信息
    @Select("SELECT * FROM AppCheck WHERE aid=#{aid}")
    List<AppCheck> findByAid(int aid);

    // 根据管理员编号查询审核信息
    @Select("SELECT * FROM AppCheck WHERE mid=#{mid}")
    List<AppCheck> findByMid(int mid);

    // 根据
    @Update("UPDATE AppCheck SET status=#{status}, is_approved=#{isApproved} WHERE id=#{id} AND mid=#{mid}")
    int updateStatusAndApproval(int id, int mid, int isApproved, String status);

    @Select("SELECT id, Tno, Sno, Cid, procScore, examScore, finalScore, scoreRank" +
            " FROM AppForGrade WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "Tno", property = "tno"),
            @Result(column = "Sno", property = "sno"),
            @Result(column = "Cid", property = "cid"),
            @Result(column = "procScore", property = "procScore"),
            @Result(column = "examScore", property = "examScore"),
            @Result(column = "finalScore", property = "finalScore"),
            @Result(column = "scoreRank", property = "scoreRank")
    })
    AppForGrade getAppForGradeById(int id);

    @Select("SELECT * FROM AppCheck LIMIT #{pageItemNum} OFFSET #{offset} WHERE mid=#{mid}")
    List<AppCheck> getAppForGradeByPage(int mid,int pageItemNum, int offset);


    @Update("UPDATE StuCourseSelect SET procScore = #{procScore}, examScore = #{examScore}, finalScore = #{finalScore}, scoreRank = #{scoreRank} WHERE Cid = #{cid} AND Sno = #{sno}")
    boolean updateGrade(AppForGrade appForGrade);

    @Select("SELECT * FROM AppCheck WHERE mid = #{mid} LIMIT #{pageItemNum} OFFSET #{pageNum}")
    List<AppCheck> findByPage(@Param("mid") int mid, @Param("pageNum") int pageNum, @Param("pageItemNum") int pageItemNum);

    @Select("SELECT count(*) FROM AppCheck WHERE mid = #{mid}")
    int findPageCount(@Param("mid") int mid);

    @Select("select AppForGrade.* from AppCheck inner join  AppForGrade on AppCheck.aid = AppForGrade.id where AppCheck.id = #{appID}")
    AppForGrade getDetailAppCheck(int appID);
}