package com.lk77.server.dao;

import com.lk77.server.domain.entity.Bank;
import com.lk77.server.domain.entity.Finance;
import com.lk77.server.domain.entity.Intention;
import com.lk77.server.domain.entity.Recommend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceDao {

    int deleteByPrimaryKey(Integer financeId);

    int insertSelective(Finance record);

    Finance selectByPrimaryKey(Integer financeId);

    List<Finance> selectByFinance(Finance finance);

    int updateByPrimaryKeySelective(Finance record);

    List<Intention> selectIntentionByName(String name);

    void updateIntentionByName(Intention intention);

    void insertIntentionByName(Intention intention);

    void deleteIntentionByName(String name);

    void AuthorizationtoUser(@Param("name") String name,@Param("userName") String userName);

    Bank selectBankById(String bankId);

    Intention selectIntention(String name);


    Finance selectIfApply(String name);

    void insertMulti(Finance finance);

    List<Recommend> selectAllRecommend(String name);

    List<Recommend> selectWithNoneIntention(String name);
}