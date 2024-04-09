package com.lk77.server.service.impl;

import com.lk77.server.service.PurchaseDetailService;
import com.lk77.server.dao.PurchaseDetailDao;
import com.lk77.server.domain.entity.PurchaseDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    private final PurchaseDetailDao purchaseDetailDao;

    public PurchaseDetailServiceImpl(PurchaseDetailDao purchaseDetailDao) {
        this.purchaseDetailDao = purchaseDetailDao;
    }

    @Override
    public void add(PurchaseDetail purchaseDetail) {
        purchaseDetailDao.insertSelective(purchaseDetail);
    }

    @Override
    public List<PurchaseDetail> selectByPurchaseId(Integer purchaseId) {
        return purchaseDetailDao.selectByPurchaseId(purchaseId);
    }

}
