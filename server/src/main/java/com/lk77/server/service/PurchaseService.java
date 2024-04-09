package com.lk77.server.service;

import com.lk77.server.domain.entity.Purchase;
import com.lk77.server.domain.CustomPurchase;

import java.util.List;

public interface PurchaseService {

    void add(Purchase purchase);

    List<CustomPurchase> selectByPurchaseType();

    Purchase selectNewPurchaseId(String ownName);

}
