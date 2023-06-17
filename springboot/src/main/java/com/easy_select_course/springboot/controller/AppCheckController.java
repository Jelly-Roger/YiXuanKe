package com.easy_select_course.springboot.controller;

import com.easy_select_course.springboot.entity.AppCheck;
import com.easy_select_course.springboot.entity.AppForGrade;
import com.easy_select_course.springboot.service.AppCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appcheck")
public class AppCheckController {

    @Autowired
    private AppCheckService appCheckService;

    @PostMapping("")
    public boolean add(@RequestBody AppCheck appCheck) {
        return appCheckService.add(appCheck);
    }

    @PutMapping("")
    public boolean update(@RequestBody AppCheck appCheck) {
        return appCheckService.update(appCheck);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable int id) {
        return appCheckService.deleteById(id);
    }

    @GetMapping("/{id}")
    public AppCheck findById(@PathVariable int id) {
        return appCheckService.findById(id);
    }

    @GetMapping("")
    public List<AppCheck> findAll() {
        return appCheckService.findAll();
    }

    @GetMapping("/aid/{aid}")
    public List<AppCheck> findByAid(@PathVariable int aid) {
        return appCheckService.findByAid(aid);
    }

    @GetMapping("/mid/{mid}")
    public List<AppCheck> findByMid(@PathVariable int mid) {
        return appCheckService.findByMid(mid);
    }

    @PutMapping("/{id}/approve")
    public boolean updateStatusToApproved(@PathVariable int id, @RequestParam("auditTime") Timestamp auditTime) {
        return appCheckService.updateStatusToApproved(id, auditTime);
    }

    @GetMapping("/updateStatus")
    public boolean updateStatusAndApproval(@RequestParam("id") int id,
                                           @RequestParam("type") String type,
                                           @RequestParam("aid") int aid,
                                           @RequestParam("mid") int mid,
                                           @RequestParam("isApproved") int isApproved) {
        return appCheckService.updateStatusAndApproval(id, type, aid, mid, isApproved);
    }


    @GetMapping("/getByPage")
    public Map<String, Object> getCheckByPage(@RequestParam("mid") int mid, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
//        System.out.println("debug");
//        System.out.println("\n\n\n\n\n\n");
//        System.out.println(mid);
//        System.out.println(pageNum);
//        System.out.println(pageSize);
//        System.out.println("\n\n\n\n\n\n");
        return appCheckService.findByPage(mid, pageNum , pageSize);
    }

    @GetMapping("/getDetailAppCheck")
    public AppForGrade getDetailAppCheck(@RequestParam int appID)
    {
        return appCheckService.getDetailAppCheck(appID);
    }
}