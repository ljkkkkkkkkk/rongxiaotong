package com.lk77.server.service.impl;

import com.lk77.server.dao.OrderDao;
import com.lk77.server.dao.UserDao;
import com.lk77.server.domain.entity.Order;
import com.lk77.server.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final Integer pageSize = 30;

    private final OrderDao orderDao;
    private final UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    @Override
    public PageInfo<Order> selectAll(Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> goods = orderDao.selectAll();
        return new PageInfo<>(goods);
    }

    @Override
    public PageInfo<Order> selectAllGoods(Integer pageNum) {
        Order order = new Order();
        order.setType("goods");
        PageHelper.startPage(pageNum, pageSize);
        List<Order> goods = orderDao.selectAllGoods(order);
        return new PageInfo<>(goods);
    }

    @Override
    public PageInfo<Order> selectAllNeeds(Integer pageNum) {
        Order order = new Order();
        order.setType("needs");

        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            for (Object obj : principal.getAuthorities()) {
                String role = obj.toString();
                if (!role.equals("admin")) {
                    order.setOrderStatus(0);
                    break;
                }
            }
        }catch (Exception ignored){

        }

        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderDao.selectAllNeeds(order);
        return new PageInfo<>(orders);
    }

    @Override
    public void add(Order order) {
        String address = userDao.selectAddressByName(order.getOwnName());
        if(!StringUtils.isEmpty(address)){
            order.setAddress(address);
        }
        orderDao.insertSelective(order);
    }

    @Override
    public void update(Order order) {
       orderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public void delete(Integer id) {
        orderDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Order> selectByType(String type,Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Order order = new Order();
        for (Object obj : principal.getAuthorities()) {
            String role = obj.toString();
            if (!role.equals("admin")) {
                order.setOwnName(name);
                order.setOrderStatus(0);
                break;
            }
        }
        order.setType(type);

        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderDao.selectByExample(order);
        return new PageInfo<>(orders);
    }


    @Override
    public Order selectById(Integer id) {
        return orderDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Order> selectGoodsByKeys(String keys,Integer pageNum,String name) {
        Order order = new Order();
        order.setType("goods");
        order.setContent(keys);

        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("========="+principal);
            Iterator<? extends GrantedAuthority> it = principal.getAuthorities().iterator();
            do {
                if (!it.hasNext()) break;
                Object obj = it.next();
                String role = obj.toString();
                if (!role.equals("admin")) {
                    order.setOwnName(name);
                    order.setOrderStatus(0);
                    break;
                }
            } while (true);
        }catch (Exception ignored){

        }


        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderDao.selectByKeys(order);
        System.out.println(orders);

        return new PageInfo<>(orders);
    }

    @Override
    public PageInfo<Order> selectNeedsByKeys(String keys,Integer pageNum,String name) {
        Order order = new Order();
        order.setType("needs");
        order.setContent(keys);

        try{
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            for (Object obj : principal.getAuthorities()) {
                String role = obj.toString();
                if (!role.equals("admin")) {
                    order.setOwnName(name);
                    order.setOrderStatus(0);
                    break;
                }
            }
        }catch (Exception ignored){

        }


        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderDao.selectByKeys(order);
        System.out.println(orders);

        return new PageInfo<>(orders);
    }

    @Override
    public PageInfo<Order> selectAllByKeys(String keys,Integer pageNum) {
        Order order = new Order();
        order.setContent(keys);
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderDao.selectByKeys(order);

        return new PageInfo<>(orders);
    }

    @Override
    public void takeDown(String orderId) {
        orderDao.takeDown(orderId);
    }

    @Override
    public void takeUp(String orderId) {
        orderDao.takeUp(orderId);
    }
}
