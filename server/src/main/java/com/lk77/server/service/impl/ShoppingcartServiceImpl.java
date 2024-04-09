package com.lk77.server.service.impl;

import com.lk77.server.dao.ShoppingcartDao;
import com.lk77.server.domain.entity.Shoppingcart;
import com.lk77.server.service.ShoppingcartService;
import com.lk77.server.domain.ShoppingInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingcartServiceImpl implements ShoppingcartService {
    private final ShoppingcartDao shoppingcartDao;

    public ShoppingcartServiceImpl(ShoppingcartDao shoppingcartDao) {
        this.shoppingcartDao = shoppingcartDao;
    }

    @Override
    public void add(Shoppingcart shoppingcart) {
        shoppingcartDao.insertSelective(shoppingcart);
    }

    @Override
    public void update(Shoppingcart shoppingcart) {
        shoppingcartDao.updateByPrimaryKeySelective(shoppingcart);
    }

    @Override
    public void delete(Integer id) {
        shoppingcartDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<ShoppingInfo> selectByUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Shoppingcart shoppingcart = new Shoppingcart();
        shoppingcart.setOwnName(name);
        return shoppingcartDao.selectByShopping(shoppingcart);
    }

    @Override
    public List<ShoppingInfo> selectByUserOrderId(Integer orderId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        Shoppingcart shoppingcart = new Shoppingcart();
        shoppingcart.setOwnName(name);
        shoppingcart.setOrderId(orderId);

        return shoppingcartDao.selectByShopping(shoppingcart);
    }

}
