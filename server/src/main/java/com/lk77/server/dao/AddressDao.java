package com.lk77.server.dao;

import com.lk77.server.domain.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AddressDao{
    int insertSelective(Address address);

    List<Address> selectByExample(@Param("name")String name);

    Address selectOneByExample(String name,String isDef);

    int updateByPrimaryKey(Address address);

    Address selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);
}
