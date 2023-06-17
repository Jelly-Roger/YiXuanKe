package com.easy_select_course.springboot.controller;

import com.easy_select_course.springboot.entity.QuestionSquare;
import com.easy_select_course.springboot.entity.SquareComment;
import com.easy_select_course.springboot.service.QuestionSquareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question-square")
public class QuestionSquareController {
    @Autowired
    private QuestionSquareService questionSquareService;

    @PostMapping("/insert")
    public Boolean addQuestionSquare(@RequestBody QuestionSquare questionSquare) {
        System.out.println(questionSquare);
        return questionSquareService.addQuestionSquare(questionSquare);

    }
    @PostMapping("/insertComment")
    public Boolean updateQuestionSquare(@RequestBody SquareComment comment) {
        return questionSquareService.addComment(comment);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionSquareById(@PathVariable int id) {
        boolean result = questionSquareService.deleteQuestionSquareById(id);
        if (result) {
            return ResponseEntity.ok("Delete question square success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete question square failed");
        }
    }



    @GetMapping("/")
    public ResponseEntity<List<QuestionSquare>> getQuestionSquaresByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        List<QuestionSquare> questionSquares = questionSquareService.getQuestionSquaresByPage(pageNum,pageSize);
        return ResponseEntity.ok(questionSquares);
    }

    @GetMapping("/getByPage")
    public ResponseEntity<Map<String, Object>> getQuestionSquaresAndTotalByPage(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize) {
        System.out.println(pageNum);
        System.out.println(pageSize);

        Map<String, Object> resultMap = questionSquareService.getQuestionSquaresAndTotalByPage(pageNum, pageSize);
        return ResponseEntity.ok(resultMap);
    }
    @GetMapping("/getCommentByQid")
    public ResponseEntity<Map<String, Object>> getCommentById(@RequestParam(name = "id", defaultValue = "1") int id) {


        Map<String, Object> resultMap = questionSquareService.getCommentById(id);
        return ResponseEntity.ok(resultMap);
    }
}