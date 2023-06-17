package com.easy_select_course.springboot.service;

import cn.hutool.core.date.DateTime;
import com.easy_select_course.springboot.entity.Validation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzh
 * @since 2023-05-13
 */
public interface IValidationService extends IService<Validation> {
    public void saveCode(String email, String code, Integer type, DateTime expireDate);
}
