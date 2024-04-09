package com.lk77.server.dao;

import com.lk77.server.domain.entity.Discuss;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiscussDao {

    int insertSelective(Discuss record);

    List<Discuss> selectByKnowledgeId(@Param("knowledgeId") Integer knowledgeId);

}