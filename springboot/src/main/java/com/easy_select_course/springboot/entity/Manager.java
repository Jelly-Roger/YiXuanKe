package com.easy_select_course.springboot.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author marca
 * @since 2023-04-15
 */
@Data
@ToString
@Setter
@Getter
public class Manager{

    private int id;

    private String name;

    private String password;

    private String sex;

    private String phone;

}
