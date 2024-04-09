package com.lk77.server.dao;

import com.lk77.server.domain.entity.Bank;

import java.util.List;

public interface BankDao {

    List<Bank> selectAllBank();

}