package com.lk77.server.service.impl;

import com.lk77.server.dao.BankDao;
import com.lk77.server.domain.entity.Bank;
import com.lk77.server.service.BankService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    private final BankDao bankDao;

    public BankServiceImpl(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    @Override
    public List<Bank> selectAllBank() {

        return bankDao.selectAllBank();
    }

}
