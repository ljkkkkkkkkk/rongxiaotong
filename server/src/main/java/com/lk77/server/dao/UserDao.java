package com.lk77.server.dao;

import com.lk77.server.domain.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface UserDao {

    List<User> selectAll();

    int insertSelective(User user);

    int updateByPrimaryKeySelective(User user);

    int deleteByPrimaryKey(String userName);

    User selectByPrimaryKey(String userName);

    String selectAddressByName(String ownName);
}
