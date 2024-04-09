package com.lk77.server.dao;

import com.lk77.server.domain.entity.Reserve;
import java.util.List;

public interface ReserveDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Reserve record);

    Reserve selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reserve record);

    List<Reserve> selectByReserve(Reserve record);
}