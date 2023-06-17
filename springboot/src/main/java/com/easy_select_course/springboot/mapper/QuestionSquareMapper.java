package com.easy_select_course.springboot.mapper;

import com.easy_select_course.springboot.entity.QuestionSquare;
import com.easy_select_course.springboot.entity.SquareComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionSquareMapper {
    @Insert("INSERT INTO QuestionSquare(title, content, name, thumb_num, comment_id) " +
            "VALUES(#{title}, #{content},  #{name}, #{thumbNum}, #{commentId})")
    int insert(QuestionSquare questionSquare);

    @Insert("INSERT INTO SquareComment (content, name,qid) " +
            "VALUES( #{content},  #{name},#{qid})")
    int insertComment(SquareComment comment);

    @Delete("DELETE FROM QuestionSquare WHERE id = #{id}")
    boolean deleteById(int id);

    @Update("UPDATE QuestionSquare SET title=#{title}, content=#{content}, " +
            "name=#{name}, thumb_num=#{thumbNum}, comment_id=#{commentId}, time=#{time} WHERE id=#{id}")
    boolean update(QuestionSquare questionSquare);

    @Select("SELECT * FROM QuestionSquare WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "anonymous", property = "anonymous"),
            @Result(column = "name", property = "name"),
            @Result(column = "thumb_num", property = "thumbNum"),
            @Result(column = "comment_id", property = "commentId"),
            @Result(column = "time", property = "time")
    })
    List<QuestionSquare> findAll();

    @Select("SELECT * FROM QuestionSquare LIMIT #{pageItemNum} OFFSET #{offset}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "anonymous", property = "anonymous"),
            @Result(column = "name", property = "name"),
            @Result(column = "thumb_num", property = "thumbNum"),
            @Result(column = "comment_id", property = "commentId"),
            @Result(column = "time", property = "time")
    })
    List<QuestionSquare> findCommentsByPage(@Param("pageItemNum") int pageItemNum, @Param("offset") int offset);

    @Select("SELECT SquareComment.* FROM SquareComment, QuestionSquare where QuestionSquare.id=#{id} and QuestionSquare.id=SquareComment.qid")
    List<SquareComment> getCommentById(int id);

    @Select("SELECT COUNT(*) FROM QuestionSquare")
    int getTotalCount();
}