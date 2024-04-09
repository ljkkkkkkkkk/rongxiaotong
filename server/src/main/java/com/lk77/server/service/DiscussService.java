package com.lk77.server.service;

import com.lk77.server.domain.entity.Discuss;

import java.util.List;

public interface DiscussService {

    void add(Discuss discuss);

    List<Discuss> selectByKnowledgeId(Integer knowledgeId);

}
