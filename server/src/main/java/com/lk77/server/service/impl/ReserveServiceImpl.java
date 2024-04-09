package com.lk77.server.service.impl;

import com.lk77.server.dao.ReserveDao;
import com.lk77.server.service.ReserveService;
import com.lk77.server.domain.entity.Reserve;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {
    private final ReserveDao reserveDao;

    public ReserveServiceImpl(ReserveDao reserveDao) {
        this.reserveDao = reserveDao;
    }

    @Override
    public Reserve selectById(Integer id) {

        return reserveDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Reserve> selectByReserve(String type) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Reserve reserve = new Reserve();
        if ("questioner".equals(type)){
            reserve.setQuestioner(name);
        } else {
            reserve.setExpertName(name);
        }

        return reserveDao.selectByReserve(reserve);
    }

    @Override
    public void delete(Integer id) {
        reserveDao.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Reserve reserve) {
        reserveDao.insertSelective(reserve);
    }

    @Override
    public void updateById(Reserve reserve) {
        reserveDao.updateByPrimaryKeySelective(reserve);
    }
}
