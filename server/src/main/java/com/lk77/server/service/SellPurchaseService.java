package com.lk77.server.service;

import com.lk77.server.domain.entity.SellPurchase;

import java.util.List;

public interface SellPurchaseService {

    void add(SellPurchase purchase);

    List<SellPurchase> selectByName();

}
