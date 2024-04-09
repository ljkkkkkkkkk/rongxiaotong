package com.lk77.server.service;

import com.github.pagehelper.PageInfo;
import com.lk77.server.domain.entity.Expert;

import java.util.List;

public interface ExpertService {

    void delete(String type);

    void insert(Expert record);

    Expert selectById(String type);

    void updateById(Expert record);

    List<Expert> selectAllExpert();

    PageInfo<Expert> findPage(Integer pageNum);

    PageInfo<Expert> findPageByKeys(String keys, Integer pageNum);

}
