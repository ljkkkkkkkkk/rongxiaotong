package com.lk77.server.service.impl;

import com.lk77.server.domain.entity.Knowledge;
import com.lk77.server.service.KnowledgeService;
import com.lk77.server.dao.KnowledgeDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    private static final Integer pageSize = 30;
    private final KnowledgeDao knowledgeDao;

    public KnowledgeServiceImpl(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    @Override
    public PageInfo<Knowledge> findPage(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Knowledge> knowledges = knowledgeDao.selectAll();
        return new PageInfo<>(knowledges);
    }

    @Override
    public void add(Knowledge knowledge) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        knowledge.setOwnName(name);
        knowledge.setCreateTime(new Date());
        knowledge.setUpdateTime(new Date());
        knowledgeDao.insertSelective(knowledge);
    }

    @Override
    public void update(Knowledge knowledge,Integer id) {
        knowledge.setKnowledgeId(id);
        knowledgeDao.updateByPrimaryKeySelective(knowledge);
    }

    @Override
    public void delete(Integer id) {
        knowledgeDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Knowledge> selectByUsername(String name) {
        return knowledgeDao.selectByExample(name);
    }

    @Override
    public Knowledge selectById(Integer id) {
        return knowledgeDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Knowledge> findPageByKeys(String keys, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Knowledge> knowledges = knowledgeDao.selectAllByKeys(keys);
        return new PageInfo<>(knowledges);
    }
}
