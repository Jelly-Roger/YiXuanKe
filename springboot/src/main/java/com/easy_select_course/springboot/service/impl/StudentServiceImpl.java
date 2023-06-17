package com.easy_select_course.springboot.service.impl;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy_select_course.springboot.common.ValidationEnum;
import com.easy_select_course.springboot.controller.dto.StudentDTO;
import com.easy_select_course.springboot.entity.Menu;
import com.easy_select_course.springboot.entity.Student;
import com.easy_select_course.springboot.entity.Validation;
import com.easy_select_course.springboot.exception.ServiceException;
import com.easy_select_course.springboot.mapper.StudentMapper;
import com.easy_select_course.springboot.service.IStudentService;

import com.easy_select_course.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    // 不要忘记导入相关的依赖和配置
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private ValidationServiceImpl validationService;
//    @Autowired
//    private TokenUtils.;

    @Value("${spring.mail.username}")
    private String from;


    // 邮箱验证
    @Override
    public StudentDTO loginEmail(StudentDTO userDTO) {
        String email = userDTO.getEmail();
        String code = userDTO.getCode();

        // 先查询 邮箱验证的表，看看之前有没有发送过  邮箱code，如果不存在，就重新获取
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email", email);
        validationQueryWrapper.eq("code", code);
        validationQueryWrapper.ge("time", new Date());  // 查询数据库没过期的code, where time >= new Date()
        Validation one = validationService.getOne(validationQueryWrapper);
        if (one == null) {
            throw new ServiceException("-1", "验证码过期，请重新获取");
        }

        // 如果验证通过了，就查询要不过户的信息
        QueryWrapper<Student> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);  //存根据email查询用户信息
        Student user = getOne(userQueryWrapper);

        if (user == null) {
            throw new ServiceException("-1", "未找到用户");
        }

        BeanUtil.copyProperties(user, userDTO, true);
        // 设置token
        String token = TokenUtils.genToken(user.getSno().toString(), user.getPassword());
        userDTO.setToken(token);

//        String role = user.getRole();
//        // 设置用户的菜单列表
//        List<Menu> roleMenus = getRoleMenus(role);
//        userDTO.setMenus(roleMenus);
        return userDTO;
    }



    // 发送邮箱验证码
    @Override
    public void sendEmailCode(String email, Integer type,String username) throws MessagingException {
        Date now = new Date();
        // 如果验证通过了，就查询要不过户的信息
        QueryWrapper<Student> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("sno", "sname", "sex", "grade", "email")
                .eq("email", email);  //存根据email查询用户信息
        Student user = getOne(userQueryWrapper);

        if (user == null) {
            throw new ServiceException("-1", "未找到用户");
        }
        if(!Objects.equals(user.getSno(), username)){
            throw new ServiceException("-1", "账户邮箱不对应");
        }
        // 先查询同类型code
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email", email);
        validationQueryWrapper.eq("type", type);
        validationQueryWrapper.ge("time", now);  // 查询数据库没过期的code
        Validation validation = validationService.getOne(validationQueryWrapper);
        if (validation != null) {
            throw new ServiceException("-1", "当前您的验证码仍然有效，请不要重复发送");
        }




        String code = RandomUtil.randomNumbers(4); // 随机一个 4位长度的验证码
        validationService.saveCode(email, code, type, DateUtil.offsetMinute(now, 5));
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("【易选课】忘记密码验证");
        helper.setFrom(from);  // 发送人
        helper.setTo(email);
        helper.setSentDate(now);  // 富文本
        String context = "<b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次忘记密码的验证码是：" +
                "<b color=\"'red'\">" + code + "</b><br>"
                + "，有效期5分钟。请妥善保管，切勿泄露";
        helper.setText(context, true);
        javaMailSender.send(message);
    }
//        if (ValidationEnum.LOGIN.getCode().equals(type)) {
//            SimpleMailMessage message=new SimpleMailMessage();
//            message.setFrom(from);  // 发送人
//            message.setTo(email);
//            message.setSentDate(now);
//            message.setSubject("【程序员青戈】登录邮箱验证");
//            message.setText("您本次登录的验证码是：" + code + "，有效期5分钟。请妥善保管，切勿泄露");
//            javaMailSender.send(message);
//        } else if (ValidationEnum.FORGET_PASS.getCode().equals(type)){
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper=new MimeMessageHelper(message);
//            helper.setSubject("【程序员青戈】忘记密码验证");
//            helper.setFrom(from);  // 发送人
//            helper.setTo(email);
//            helper.setSentDate(now);  // 富文本
//            String context="<b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次忘记密码的验证码是："+
//                    "<b color=\"'red'\">"  + code + "</b><br>"
//                    +"，有效期5分钟。请妥善保管，切勿泄露";
//            helper.setText(context, true);
//            javaMailSender.send(message);
//        }
//
//        // 发送成功之后，把验证码存到数据库
//        validationService.saveCode(email, code, type, DateUtil.offsetMinute(now, 5));
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom(from);  // 发送人
//        message.setTo(email);
//        message.setSentDate(now);
//        message.setSubject("【易选课】邮箱验证");
//        message.setText("您本次修改密码的验证码是：" + code + "，有效期5分钟。请妥善保管，切勿泄露");
//        javaMailSender.send(message);
//    }


}
