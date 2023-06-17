package com.easy_select_course.springboot.controller;

import com.easy_select_course.springboot.entity.InfoStructure;
import com.easy_select_course.springboot.service.InfoStructureService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/InfoStructure")
public class InfoStructureController {
    @Resource
    private InfoStructureService infoStructureService;

    @GetMapping("/select_all_info")
    public List<InfoStructure> selectAllInfo()
    {
        return infoStructureService.selectAllInfo();
    }
    @PostMapping("/insert_one_info")
    public int insertOneInfo(@RequestBody InfoStructure infoStructure)
    {
        return infoStructureService.insertOneInfo(infoStructure);
    }

    @GetMapping("/find_info_count")
    public int findInfoCount(@RequestParam String infoTarId)
    {
        return infoStructureService.findInfoCount(infoTarId);
    }
    @GetMapping("/find_unread_info")
    public int findUnreadInfo(@RequestParam String infoTarId)
    {
        return infoStructureService.findUnreadInfo(infoTarId);
    }
    @GetMapping("/update_read_status")
    public int updateReadStatus(@RequestParam int upInfoId, @RequestParam String readStatus)
    {
        return infoStructureService.updateReadStatus(upInfoId, readStatus);
    }

    @GetMapping("/find_info_by_page_id")
    public List<InfoStructure> findInfoByPageId(@RequestParam int pageStart,
                                                @RequestParam int pageSize,
                                                @RequestParam String infoTarId)
    {
        return infoStructureService.findInfoByIdPage(pageStart, pageSize, infoTarId);
    }

    @GetMapping("/find_info_by_info_id")
    public InfoStructure findInfoByInfoId(@RequestParam int infoId)
    {
        return infoStructureService.findInfoByInfoId(infoId);
    }
}
