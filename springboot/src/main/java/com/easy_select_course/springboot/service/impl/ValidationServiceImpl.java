package com.easy_select_course.springboot.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.easy_select_course.springboot.entity.Validation;
import com.easy_select_course.springboot.mapper.ValidationMapper;
import com.easy_select_course.springboot.service.IValidationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hzh
 * @since 2023-05-13
 */
@Service
public class ValidationServiceImpl extends ServiceImpl<ValidationMapper, Validation> implements IValidationService {

    @Transactional
    @Override
    public void saveCode(String email, String code, Integer type, DateTime expireDate) {

        Validation validation = new Validation();
        validation.setEmail(email);
        validation.setCode(code);
        validation.setType(type);
        validation.setTime(expireDate);

        // 删除同类型的验证
        UpdateWrapper<Validation> validationUpdateWrapper = new UpdateWrapper<>();
        validationUpdateWrapper.eq("email", email);
        validationUpdateWrapper.eq("type", type);
        remove(validationUpdateWrapper);

        // 再插入新的code
        save(validation);
    }
}
