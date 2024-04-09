package com.lk77.server.service.impl;

import com.lk77.server.dao.FinanceDao;
import com.lk77.server.domain.entity.*;
import com.lk77.server.service.FinanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    private final FinanceDao financeDao;

    public FinanceServiceImpl(FinanceDao financeDao) {
        this.financeDao = financeDao;
    }

    @Override
    public void add(Finance finance) {
        finance.setStatus(0);
        finance.setCreateTime(new Date());
        finance.setUpdateTime(new Date());

        financeDao.insertSelective(finance);
    }

    @Override
    public List<Finance> selectByFinance(Finance finance) {

        return financeDao.selectByFinance(finance);
    }

    @Override
    public Finance selectByFinanceId(Integer id){

        return financeDao.selectByPrimaryKey(id);
    }

    @Override
    public void deleteByFinanceId(Integer id){

        financeDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateByFinance(Finance finance){

        finance.setUpdateTime(new Date());
        financeDao.updateByPrimaryKeySelective(finance);

    }

    @Override
    public List<Intention> selectIntentionByName(String name) {
        return financeDao.selectIntentionByName(name);
    }

    @Override
    public void updateIntentionByName(Intention intention) {
        intention.setUpdateTime(new Date());
        financeDao.updateIntentionByName(intention);
    }

    @Override
    public void insertIntentionByName(Intention intention) {
        intention.setCreateTime(new Date());
        intention.setUpdateTime(new Date());
        financeDao.insertIntentionByName(intention);
    }

    @Override
    public void deleteIntentionByName(String name) {
        financeDao.deleteIntentionByName(name);
    }



    @Override
    public List<Recommend> selectRecommend(String name) {
        Intention intention = financeDao.selectIntention(name);
        if(StringUtils.isEmpty(intention)){
            List<Recommend> list = financeDao.selectWithNoneIntention(name);
            System.out.println("无意向："+list);
            return list;
        }else {
            List<Recommend> list = financeDao.selectAllRecommend(name);
            System.out.println(list);
            return list;
        }


    }

    @Override
    public void AuthorizationtoUser(String name, String userName) {
        financeDao.AuthorizationtoUser(name,userName);
    }

    @Override
    public FinaceUserDetails selectFinaceUser(String name, String bankId) {
        FinaceUserDetails user = new FinaceUserDetails();
        Bank bank = financeDao.selectBankById(bankId);
        user.setRate(bank.getRate());
        user.setIntroduce(bank.getIntroduce());
        user.setBankName(bank.getBankName());
        System.out.println("user:"+user.toString());
        return user;
    }

    @Override
    public boolean selectIfApply(String name) {
        Finance finance = financeDao.selectIfApply(name);
        if(StringUtils.isEmpty(finance)){
            return false;
        }
        return true;
    }


    @Override
    public void addMulti(Finance finance) {
        finance.setCreateTime(new Date());
        finance.setUpdateTime(new Date());
        finance.setStatus(0);
        financeDao.insertMulti(finance);
    }

}
