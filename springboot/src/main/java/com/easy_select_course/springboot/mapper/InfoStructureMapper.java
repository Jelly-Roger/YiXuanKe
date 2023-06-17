package com.easy_select_course.springboot.mapper;

import com.easy_select_course.springboot.entity.InfoStructure;
import com.easy_select_course.springboot.entity.gradeElement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InfoStructureMapper {
    @Select("select * from InfoStructure")
    List<InfoStructure> selectAllInfo();

    @Insert("insert into InfoStructure values(null, #{infoTar}, #{infoTarId}, #{infoDate}, #{infoTitle}, #{infoContent}, #{readStatus})")
    int insertOneInfo(InfoStructure infoStructure);

    @Update("update InfoStructure set readStatus = #{readStatus} where infoId = #{infoId}")
    int updateReadStatus(int infoId, String readStatus);

    @Select("select * from InfoStructure where infoTarId = #{infoTarId} limit #{pageStart}, #{pageSize}")
    List<InfoStructure> findInfoByIdPage(int pageStart, int pageSize, String infoTarId);

    @Select("select * from InfoStructure where infoId = #{infoId}")
    InfoStructure findInfoByInfoId(int infoId);

    @Select("select count(*) from InfoStructure where infoTarId = #{infoTarId}")
    int findInfoCount(String infoTarId);

    @Select("select count(*) from InfoStructure where infoTarId = #{infoTarId} and readStatus = '未查阅' ")
    int findUnreadInfo(String infoTarId);

    @Select("select Course.Tno, Sno, CName from AppForGrade inner join Course on AppForGrade.Cid = Course.Cid where id = #{appID} ")
    gradeElement findInfoElement(int appID);
}
