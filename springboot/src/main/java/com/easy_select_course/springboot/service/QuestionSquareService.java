package com.easy_select_course.springboot.service;

import com.easy_select_course.springboot.entity.QuestionSquare;
import com.easy_select_course.springboot.entity.SquareComment;
import com.easy_select_course.springboot.mapper.QuestionSquareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionSquareService {
    @Autowired
    private QuestionSquareMapper questionSquareMapper;

    public boolean addQuestionSquare(QuestionSquare questionSquare) {
        return questionSquareMapper.insert(questionSquare) > 0;
    }
    public boolean addComment(SquareComment comment) {
        return questionSquareMapper.insertComment(comment) > 0;
    }
    public boolean deleteQuestionSquareById(int id) {
        return questionSquareMapper.deleteById(id);
    }

    public boolean updateQuestionSquare(QuestionSquare questionSquare) {
        return questionSquareMapper.update(questionSquare);
    }


    public List<QuestionSquare> getQuestionSquaresByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return questionSquareMapper.findCommentsByPage(pageSize, offset);
    }

    public Map<String, Object> getQuestionSquaresAndTotalByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<QuestionSquare> questionSquares = questionSquareMapper.findCommentsByPage(pageSize, offset);
        int total = questionSquareMapper.getTotalCount();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("datalist", questionSquares);
        resultMap.put("total", total);
        return resultMap;
    }
    public Map<String, Object> getCommentById(int id) {

        List<SquareComment> questionSquares = questionSquareMapper.getCommentById(id);
        int total = questionSquareMapper.getTotalCount();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("comment", questionSquares);
        resultMap.put("total", total);
        return resultMap;
    }
}