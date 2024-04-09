package com.lk77.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lk77.server.dao.QuestionDao;
import com.lk77.server.service.QuestionService;
import com.lk77.server.domain.entity.Question;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question selectById(Integer id) {

        return questionDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Question> selectByQuestion(String type) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Question question = new Question();
        if ("questioner".equals(type)){
            question.setQuestioner(name);
        } else {
            question.setExpertName(name);
        }
        return questionDao.selectByQuestion(question);
    }

    @Override
    public void delete(Integer id) {
        questionDao.deleteByPrimaryKey(id);
    }
    @Override
    public void insert(Question question) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        question.setQuestioner(name);
        questionDao.insertSelective(question);
    }
    @Override
    public void updateById(Question question) {
        questionDao.updateByPrimaryKeySelective(question);
    }

    @Override
    public PageInfo<Question> selectByKeys(String keys, Integer pageNum){
        Integer pageSize = 30;
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionDao.selectAllByKeys(keys);
        return new PageInfo<>(questions);
    }
}
