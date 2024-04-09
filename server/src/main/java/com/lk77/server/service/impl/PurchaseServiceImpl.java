package com.lk77.server.service.impl;

import com.lk77.server.dao.PurchaseDao;
import com.lk77.server.domain.entity.Purchase;
import com.lk77.server.service.PurchaseService;
import com.lk77.server.domain.CustomPurchase;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseDao purchaseDao;

    public PurchaseServiceImpl(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }

    @Override
    public void add(Purchase purchase) {
        purchaseDao.insertSelective(purchase);
    }

    @Override
    public List<CustomPurchase> selectByPurchaseType() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        System.out.println("查询订单：");
        List<CustomPurchase> purchases = purchaseDao.selectByPurchase(name);
        System.out.println(purchases);

        return purchases;
    }

    @Override
    public Purchase selectNewPurchaseId(String ownName) {
        return purchaseDao.selectNewPurchaseId(ownName);
    }
}
