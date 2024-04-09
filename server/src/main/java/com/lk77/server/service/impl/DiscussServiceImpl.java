package com.lk77.server.service.impl;

import com.lk77.server.dao.DiscussDao;
import com.lk77.server.domain.entity.Discuss;
import com.lk77.server.service.DiscussService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussServiceImpl implements DiscussService {
    private final DiscussDao discussDao;

    public DiscussServiceImpl(DiscussDao discussDao) {
        this.discussDao = discussDao;
    }

    @Override
    public void add(Discuss discuss) {
        discussDao.insertSelective(discuss);
    }

    @Override
    public List<Discuss> selectByKnowledgeId(Integer knowledgeId) {
        return discussDao.selectByKnowledgeId(knowledgeId);
    }

}
