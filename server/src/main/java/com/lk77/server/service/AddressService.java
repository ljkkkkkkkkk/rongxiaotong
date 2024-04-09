package com.lk77.server.service;

import com.lk77.server.domain.entity.Address;

import java.util.List;

public interface AddressService {
    void add(Address address);

    List<Address> selectByOwnName();

    Address selectDefaultByOwnName();


    void defaultAddressInfoUpdate(Address address);

    void update(Address address);

    boolean delete(Integer id);
}
