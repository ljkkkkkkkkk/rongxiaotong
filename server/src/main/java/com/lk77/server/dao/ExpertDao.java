package com.lk77.server.dao;

import com.lk77.server.domain.entity.Expert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpertDao {

    int deleteByPrimaryKey(String userName);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(Expert record);

    List<Expert> selectAllExpert();

    List<Expert> selectAllByKeys(@Param("keys")String keys);

}