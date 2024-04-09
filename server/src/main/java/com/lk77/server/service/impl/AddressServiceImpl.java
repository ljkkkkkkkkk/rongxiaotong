package com.lk77.server.service.impl;

import com.lk77.server.domain.entity.Address;
import com.lk77.server.service.AddressService;
import com.lk77.server.dao.AddressDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public void add(Address address) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        address.setOwnName(name);
        if (address.getIsDefault()) {
            setZero();
        }
        addressDao.insertSelective(address);
    }

    @Override
    public List<Address> selectByOwnName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        return addressDao.selectByExample(ownName);
    }

    @Override
    public Address selectDefaultByOwnName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        String isDef = "1";

        return addressDao.selectOneByExample(name,isDef);
    }

    @Override
    public void defaultAddressInfoUpdate(Address address) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        address.setOwnName(name);
        if (address.getIsDefault()) {
            setZero();
        }
        update(address);
    }

    @Override
    public void update(Address address) {
        addressDao.updateByPrimaryKey(address);
    }

    @Override
    public boolean delete(Integer id) {
        Address address = addressDao.selectByPrimaryKey(id);
        if (address.getIsDefault()){
            return false;
        }else {
            addressDao.deleteByPrimaryKey(id);
            return true;
        }
    }


    public void setZero() {
        Address address = selectDefaultByOwnName();
        if (null != address)
        {
            address.setIsDefault(false);
            update(address);
        }
    }

}
