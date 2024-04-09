package com.lk77.server.service.impl;

import com.lk77.server.domain.entity.SellPurchase;
import com.lk77.server.service.SellPurchaseService;
import com.lk77.server.dao.SellPurchaseDao;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SellPurchaseServiceImpl implements SellPurchaseService {
    private final SellPurchaseDao sellPurchaseDao;

    public SellPurchaseServiceImpl(SellPurchaseDao sellPurchaseDao) {
        this.sellPurchaseDao = sellPurchaseDao;
    }

    @Override
    public void add(SellPurchase sellPurchase) {
        sellPurchaseDao.insertSelective(sellPurchase);
    }

    @Override
    public List<SellPurchase> selectByName(){
        //获取用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        return sellPurchaseDao.selectByName(name);
    }
}
