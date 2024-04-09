package com.lk77.server.service;

import com.github.pagehelper.PageInfo;
import com.lk77.server.domain.entity.Question;

import java.util.List;

public interface QuestionService {

    void delete(Integer id);

    void insert(Question record);

    Question selectById(Integer id);

    void updateById(Question record);

    List<Question> selectByQuestion(String type);

    PageInfo<Question> selectByKeys(String keys, Integer pageNum);
}
