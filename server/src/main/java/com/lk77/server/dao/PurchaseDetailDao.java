package com.lk77.server.dao;

import com.lk77.server.domain.entity.PurchaseDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseDetailDao {

    int insertSelective(PurchaseDetail record);

    List<PurchaseDetail> selectByPurchaseId(@Param("purchaseId")Integer purchaseId);

}