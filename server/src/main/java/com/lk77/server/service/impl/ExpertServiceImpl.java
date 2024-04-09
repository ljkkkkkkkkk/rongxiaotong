package com.lk77.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lk77.server.domain.entity.Expert;
import com.lk77.server.service.ExpertService;
import com.lk77.server.dao.ExpertDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertDao expertDao;

    private final Integer pageSize = 30;

    public ExpertServiceImpl(ExpertDao expertDao) {
        this.expertDao = expertDao;
    }

    @Override
    public Expert selectById(String name) {

        return expertDao.selectByPrimaryKey(name);
    }

    @Override
    public List<Expert> selectAllExpert() {

        return expertDao.selectAllExpert();
    }

    @Override
    public void delete(String name) {
        expertDao.deleteByPrimaryKey(name);
    }

    @Override
    public void insert(Expert expert) {
        expertDao.insertSelective(expert);
    }

    @Override
    public void updateById(Expert expert) {
        expertDao.updateByPrimaryKeySelective(expert);
    }

    @Override
    public PageInfo<Expert> findPage(Integer pageNum){
        PageHelper.startPage(pageNum, pageSize);
        List<Expert> experts = expertDao.selectAllExpert();
        return new PageInfo<>(experts);
    }

    @Override
    public PageInfo<Expert> findPageByKeys(String keys, Integer pageNum){
        PageHelper.startPage(pageNum, pageSize);
        List<Expert> experts = expertDao.selectAllByKeys(keys);
        return new PageInfo<>(experts);
    }
}
