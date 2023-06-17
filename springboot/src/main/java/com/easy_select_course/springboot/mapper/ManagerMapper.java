
package com.easy_select_course.springboot.mapper;

import com.easy_select_course.springboot.controller.dto.UserLoginDto;
import com.easy_select_course.springboot.entity.Manager;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author marca
 * @since 2023-04-15
 */
@Mapper
public interface ManagerMapper{
    @Select("select * from Manager where id = #{no} and password = #{password}")
    List<Manager> managerLogin(UserLoginDto userLoginDto);
}

