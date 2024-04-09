package com.lk77.server.dao;

import com.lk77.server.domain.entity.Purchase;
import com.lk77.server.domain.CustomPurchase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseDao {

    int insertSelective(Purchase record);

    Purchase selectNewPurchaseId(@Param("ownName")String ownName);

    List<CustomPurchase> selectByPurchase(String name);
}