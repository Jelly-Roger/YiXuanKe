package com.easy_select_course.springboot.service.impl;

import com.easy_select_course.springboot.entity.AppCheck;
import com.easy_select_course.springboot.entity.AppForGrade;
import com.easy_select_course.springboot.entity.InfoStructure;
import com.easy_select_course.springboot.entity.gradeElement;
import com.easy_select_course.springboot.mapper.AppCheckMapper;
import com.easy_select_course.springboot.mapper.InfoStructureMapper;
import com.easy_select_course.springboot.service.AppCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppCheckServiceImpl implements AppCheckService {

    @Autowired
    private AppCheckMapper appCheckMapper;
    @Autowired
    private InfoStructureMapper infoStructureMapper;


    @Override
    public boolean add(AppCheck appCheck) {
        int result = appCheckMapper.add(appCheck);
        return result > 0;
    }

    @Override
    public boolean update(AppCheck appCheck) {
        int result = appCheckMapper.update(appCheck);
        return result > 0;
    }

    @Override
    public boolean deleteById(int id) {
        int result = appCheckMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public AppCheck findById(int id) {
        return appCheckMapper.findById(id);
    }

    @Override
    public List<AppCheck> findAll() {
        return appCheckMapper.findAll();
    }

    @Override
    public List<AppCheck> findByAid(int aid) {
        return appCheckMapper.findByAid(aid);
    }

    @Override
    public List<AppCheck> findByMid(int mid) {
        return appCheckMapper.findByMid(mid);
    }

    @Override
    public boolean updateStatusToApproved(int id, Timestamp auditTime) {
        AppCheck appCheck = appCheckMapper.findById(id);
        if (appCheck != null && appCheck.getStatus().equals("待审核")) {
            appCheck.setStatus("已审核");
            appCheck.setAuditTime(auditTime);
            int result = appCheckMapper.update(appCheck);
            return result > 0;
        }
        return false;
    }

    @Override
    public boolean updateStatusAndApproval(int id,String type,int aid, int mid, int isApproved) {
        AppCheck appCheck = appCheckMapper.findById(id);
        System.out.println("\n\n\n now check ! \n\n\n");
        if (appCheck != null && appCheck.getMid() == mid) {
            appCheck.setStatus("已审核");
            appCheck.setIsApproved(isApproved);
            // 成绩修改，并且同意，更新到成绩表中
            if (1 == isApproved && "成绩修改".equals(type)) {
                // 首先找到对应的成绩修改申请，拿到四个成绩
                AppForGrade appForGrade = appCheckMapper.getAppForGradeById(aid);
                // 将四个成绩更新到Course中
                appCheckMapper.updateGrade(appForGrade);
            }
            int result = appCheckMapper.update(appCheck);
//            return result > 0;
            if(result <= 0)
                return false;
            else{
                gradeElement element = infoStructureMapper.findInfoElement(aid);
                InfoStructure info = new InfoStructure();
                String timeStr= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                info.setInfoDate(timeStr);
                info.setInfoTar("教师");
                info.setInfoTarId(element.getTno());
                info.setReadStatus("未查阅");
                if(isApproved != 0)
                {
                    String content = element.getCName() + "审核通过";
                    info.setInfoTitle(content);
                    content = element.getTno() + "老师你好！你的" + element.getCName()+"课程已经录入成功，请及时查收";
                    info.setInfoContent(content);
                }
                else{
                    String content = element.getCName() + "审核未通过";
                    info.setInfoTitle(content);
                    content = element.getTno() + "老师你好！你的" + element.getCName()+"课程已经录入失败，请检查非法录入操作";
                    info.setInfoContent(content);
                }
                infoStructureMapper.insertOneInfo(info);
                if(isApproved != 0)
                {
                    info.setInfoTar("学生");
                    info.setInfoTarId(element.getSno());
                    String content = element.getCName() + "成绩发布";
                    info.setInfoTitle(content);
                    content = element.getSno() + "学生你好！你的" + element.getCName()+"课程已经发布成绩，请及时查收";
                    info.setInfoContent(content);
                }
                infoStructureMapper.insertOneInfo(info);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AppCheck> getCheckInfoBypage(int mid,int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        System.out.println("debug222");
        System.out.println("\n\n\n\n\n\n");
        System.out.println(mid);
        System.out.println(pageNum);
        System.out.println(pageSize);
        System.out.println("\n\n\n\n\n\n");
        return appCheckMapper.getAppForGradeByPage(mid, pageSize, offset);
    }

    @Override
    public Map<String, Object> findByPage(int mid, int pageNum, int pageItemNum) {
        int offset = (pageNum - 1) * pageItemNum;
        Map<String, Object> res = new HashMap<>();
        List<AppCheck> appcheck = appCheckMapper.findByPage(mid, offset, pageItemNum);
        res.put("data", appcheck);
        int num = appCheckMapper.findPageCount(mid);
        res.put("total", num);
        return res;
    }

    @Override
    public AppForGrade getDetailAppCheck(int appID){
        if(appID <= 0)
            return new AppForGrade();
        return appCheckMapper.getDetailAppCheck(appID);
    }
}