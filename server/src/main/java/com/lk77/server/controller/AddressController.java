package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.domain.entity.Address;
import com.lk77.server.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Api(tags = "地址模块接口")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @ApiOperation(value = "添加地址 ")
    @PostMapping("/add")
    public HttpResult addAddress(@RequestBody Address newAddress, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                errorMessages.append(objectError.getDefaultMessage()).append("; ");
            }
            String errorMessage = errorMessages.toString();
            return new HttpResult<String>(false, Constant.ERROR, "添加失败", errorMessage);
        }
        addressService.add(newAddress);
        return new HttpResult(true, Constant.OK, "添加成功");
    }

    @ApiOperation(value = "根据登录用户名查询所有地址")
    @GetMapping("/selectByOwnName")
    public HttpResult<List<Address>> getAddressListByOwnName() {
        List<Address> addresses = addressService.selectByOwnName();
        return new HttpResult<List<Address>>(true, Constant.OK, "查询成功", addresses);
    }

    @ApiOperation(value = "根据登录用户名查询默认地址")
    @GetMapping("/selectDefaultByOwnName")
    public HttpResult<Address> getDefaultAddressByOwnName() {
        Address defaultAddress = addressService.selectDefaultByOwnName();

        if (defaultAddress == null) {
            List<Address> addresses = addressService.selectByOwnName();
            if (addresses != null && !addresses.isEmpty()) {
                defaultAddress = addresses.get(0);
            }
        }
        return new HttpResult<Address>(true, Constant.OK, "查询成功", defaultAddress);
    }

    @ApiOperation(value = "默认地址信息更新操作")
    @PostMapping("/defaultAddressInfoUpdate")
    public HttpResult<String> updateDefaultAddressInfo(@Validated @RequestBody Address updatedAddress, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                errorMessages.append(objectError.getDefaultMessage()).append("; ");
            }
            String errorMessage = errorMessages.toString();
            return new HttpResult<String>(false, Constant.ERROR, "地址信息更新失败", errorMessage);
        }
        addressService.defaultAddressInfoUpdate(updatedAddress);
        return new HttpResult<String>(true, Constant.OK, "修改成功");
    }

    @ApiOperation(value = "根据id修改地址")
    @PostMapping("/update/{id}")
    public HttpResult updateAddressById(@PathVariable("id") Integer addressId, @RequestBody Address updatedAddress) {
        updatedAddress.setId(addressId);
        addressService.update(updatedAddress);
        return new HttpResult(true, Constant.OK, "修改成功");
    }

    @ApiOperation(value = "根据id删除地址")
    @DeleteMapping("/delete/{id}")
    public HttpResult deleteAddressById(@PathVariable("id") Integer addressId) {
        boolean deleted = addressService.delete(addressId);
        if (!deleted) {
            return new HttpResult(false, Constant.ERROR, "该地址为默认地址不能删除");
        } else {
            return new HttpResult(true, Constant.OK, "删除成功");
        }
    }
}
