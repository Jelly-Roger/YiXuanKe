package com.easy_select_course.springboot.service;

import com.easy_select_course.springboot.entity.AppCheck;
import com.easy_select_course.springboot.entity.AppForGrade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface AppCheckService {
    boolean add(AppCheck appCheck);
    boolean update(AppCheck appCheck);
    boolean deleteById(int id);
    AppCheck findById(int id);
    List<AppCheck> findAll();
    List<AppCheck> findByAid(int aid);
    List<AppCheck> findByMid(int mid);
    boolean updateStatusToApproved(int id, Timestamp auditTime);
    boolean updateStatusAndApproval(int id,String type,int aid, int mid, int isApproved);
    List<AppCheck>  getCheckInfoBypage(int mid,int pageNum, int pageSize);
    Map<String, Object> findByPage(int mid, int pageNum, int pageItemNum);

    AppForGrade getDetailAppCheck(int appID);
}