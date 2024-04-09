package com.lk77.server.service.impl;

import com.lk77.server.dao.UserDao;
import com.lk77.server.domain.entity.User;
import com.lk77.server.service.UserService;
import com.lk77.server.dao.AddressDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public void add(User user) {
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userDao.insertSelective(user);
    }

    @Override
    public void update(User user) {
        String password = user.getPassword();
        if (password!=null){
            String encode = passwordEncoder.encode(password);
            user.setPassword(encode);
        }
        user.setUpdateTime(new Date());
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(String userName) {
        userDao.deleteByPrimaryKey(userName);
    }

    @Override
    public User selectByUserName(String userName) {
        return userDao.selectByPrimaryKey(userName);
    }

    @Override
    public PageInfo<User> findPage(Integer pageNum) {
        int pageSize = 30;
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectAll();
        return new PageInfo<User>(users);
    }

    @Override
    public PageInfo<User> findPage(User user, Integer pageNum, Integer pageSize) {
        List<User> users = userDao.selectAll();
        return new PageInfo<User>(users);
    }

    @Override
    public void loginUpdateByUsername(User user) {
        user.setUpdateTime(new Date());
        userDao.updateByPrimaryKeySelective(user);
    }
}
