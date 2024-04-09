package com.lk77.server.service;

import com.lk77.server.domain.entity.PurchaseDetail;

import java.util.List;

public interface PurchaseDetailService {

    void add(PurchaseDetail purchaseDetail);

    List<PurchaseDetail> selectByPurchaseId(Integer purchaseId);

}
