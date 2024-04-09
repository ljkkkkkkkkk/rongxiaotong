package com.lk77.server.dao;

import com.lk77.server.domain.entity.SellPurchase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellPurchaseDao {

    int insertSelective(SellPurchase record);

    List<SellPurchase> selectByName(@Param("ownName")String ownName);

}