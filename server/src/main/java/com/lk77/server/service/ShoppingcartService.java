package com.lk77.server.service;

import com.lk77.server.domain.entity.Shoppingcart;
import com.lk77.server.domain.ShoppingInfo;

import java.util.List;

public interface ShoppingcartService {

    void add(Shoppingcart shoppingcart);

    void update(Shoppingcart shoppingcart);

    void delete(Integer id);

    List<ShoppingInfo> selectByUsername();

    List<ShoppingInfo> selectByUserOrderId(Integer orderId);

}
