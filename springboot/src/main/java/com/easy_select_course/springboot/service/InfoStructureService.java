package com.easy_select_course.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.easy_select_course.springboot.entity.InfoStructure;
import com.easy_select_course.springboot.mapper.InfoStructureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InfoStructureService {

    @Autowired
    private InfoStructureMapper infoStructureMapper;

    public List<InfoStructure> selectAllInfo()
    {
        List<InfoStructure> res;
        try{
            res = infoStructureMapper.selectAllInfo();
        }catch (Exception e){
            System.out.println(e);
            return Collections.emptyList();
        }
        return res;
    }

    public int insertOneInfo(InfoStructure infoStructure)
    {
        if(StrUtil.isBlank(infoStructure.getInfoTar()))
            return 0;
        if(StrUtil.isBlank(infoStructure.getInfoTarId()))
            return 0;
        if(StrUtil.isBlank(infoStructure.getInfoTitle()))
            return 0;
        if(StrUtil.isBlank(infoStructure.getInfoContent()))
            infoStructure.setInfoContent(null);
        if(StrUtil.isBlank(infoStructure.getReadStatus()))
            return 0;
        int res = 0;
        try {
            res = infoStructureMapper.insertOneInfo(infoStructure);
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public int updateReadStatus(int infoId, String readStatus)
    {
        if(StrUtil.isBlank(readStatus))
            return 0;
        if(infoId <= 0)
            return 0;
        if(!readStatus.equals("已查阅") && !readStatus.equals("未查阅"))
            return 0;
        int res = 0;
        try{
            res = infoStructureMapper.updateReadStatus(infoId, readStatus);
        }catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public List<InfoStructure> findInfoByIdPage(int pageStart, int pageNum, String infoTarId)
    {
        if(pageStart < 0)
            return Collections.emptyList();
        if(pageNum < 0)
            return Collections.emptyList();
        if(StrUtil.isBlank(infoTarId))
            return Collections.emptyList();

        List<InfoStructure> res;
        try{
            res = infoStructureMapper.findInfoByIdPage(pageStart,pageNum,infoTarId);
        }catch (Exception e)
        {
            System.out.println(e);
            return Collections.emptyList();
        }
        return res;

    }

    public InfoStructure findInfoByInfoId(int infoId)
    {
        if(infoId < 0)
            return new InfoStructure();
        InfoStructure res;
        try{
            res = infoStructureMapper.findInfoByInfoId(infoId);
        }catch (Exception e)
        {
            System.out.println(e);
            return new InfoStructure();
        }
        return res;
    }

    public int findInfoCount(String infoTarId)
    {
        if(StrUtil.isBlank(infoTarId))
            return 0;
        int res = 0;
        try{
            res = infoStructureMapper.findInfoCount(infoTarId);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }

    public int findUnreadInfo(String infoTarId)
    {
        if(StrUtil.isBlank(infoTarId))
            return 0;
        int res = 0;
        try{
            res = infoStructureMapper.findUnreadInfo(infoTarId);
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
        return res;
    }
}
