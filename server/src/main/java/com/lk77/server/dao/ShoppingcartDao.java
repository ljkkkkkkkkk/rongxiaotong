package com.lk77.server.dao;

import com.lk77.server.domain.entity.Shoppingcart;
import com.lk77.server.domain.ShoppingInfo;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public interface ShoppingcartDao {

    int deleteByPrimaryKey(Integer shoppingId);

    int insertSelective(Shoppingcart shoppingcart);

    List<ShoppingInfo> selectByShopping(Shoppingcart shoppingcart);

    int updateByPrimaryKeySelective(Shoppingcart shoppingcart);

}