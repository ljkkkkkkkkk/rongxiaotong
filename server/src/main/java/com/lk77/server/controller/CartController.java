package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.domain.entity.Purchase;
import com.lk77.server.domain.entity.SellPurchase;
import com.lk77.server.domain.entity.Shoppingcart;
import com.lk77.server.service.PurchaseDetailService;
import com.lk77.server.service.PurchaseService;
import com.lk77.server.service.SellPurchaseService;
import com.lk77.server.service.ShoppingcartService;
import com.lk77.server.domain.entity.PurchaseDetail;
import com.lk77.server.domain.ShoppingInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/cart")
@Api(tags = "购物车接口")
public class CartController {
    private final PurchaseService purchaseService;
    private final ShoppingcartService shoppingcartService;
    private final PurchaseDetailService purchaseDetailService;
    private final SellPurchaseService sellPurchaseService;

    public CartController(PurchaseService purchaseService, ShoppingcartService shoppingcartService, PurchaseDetailService purchaseDetailService, SellPurchaseService sellPurchaseService) {
        this.purchaseService = purchaseService;
        this.shoppingcartService = shoppingcartService;
        this.purchaseDetailService = purchaseDetailService;
        this.sellPurchaseService = sellPurchaseService;
    }

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add/{id}")
    public HttpResult add(@PathVariable("id") Integer id) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Shoppingcart shoppingcart =new Shoppingcart();
        shoppingcart.setOwnName(name);
        shoppingcart.setOrderId(id);
        shoppingcart.setCount(1);
        shoppingcart.setCreateTime(new Date());
        shoppingcart.setUpdateTime(new Date());

        List<ShoppingInfo> shoppingcarts = shoppingcartService.selectByUserOrderId(id);
        if (null!= shoppingcarts && !shoppingcarts.isEmpty()){
            shoppingcart.setCount(shoppingcarts.get(0).getCount() + 1);
            shoppingcart.setShoppingId(shoppingcarts.get(0).getShoppingId());
            shoppingcartService.update(shoppingcart);
        }
        else {
            shoppingcartService.add(shoppingcart);
        }
        return new HttpResult(true, Constant.OK, "添加商品到购物车成功");
    }

    @ApiOperation("从购物车删除商品")
    @DeleteMapping("/delete/{id}")
    public HttpResult delete(@PathVariable("id") Integer id) {
        shoppingcartService.delete(id);
        return new HttpResult(true, Constant.OK, "删除商品成功");
    }

    @ApiOperation("展示购物车列表")
    @GetMapping("/show")
    public HttpResult<List<ShoppingInfo>> show() {

        List<ShoppingInfo> shoppingInfo = shoppingcartService.selectByUsername();

        return new HttpResult<List<ShoppingInfo>>(true, Constant.OK, "查询成功", shoppingInfo);
    }

    @ApiOperation("更新购物车数量")
    @PutMapping("/update/{id}/{count}")
    public HttpResult update(@PathVariable("id") Integer id, @PathVariable("count") Integer count) {

        Shoppingcart shoppingcart =new Shoppingcart();
        shoppingcart.setShoppingId(id);
        shoppingcart.setCount(count);
        shoppingcart.setUpdateTime(new Date());
        shoppingcartService.update(shoppingcart);
        return new HttpResult(true, Constant.OK, "更新商品数量成功");
    }

    @ApiOperation("提交订单")
    @PostMapping("/commitOrder/{addId}/{tMoney}")
    public HttpResult commitOrder(@PathVariable("addId") Integer addId, @PathVariable("tMoney") String tMoney, @RequestBody List<ShoppingInfo> shoppingInfoList) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        Purchase purchase = new Purchase();
        purchase.setPurchaseType(1);
        purchase.setOwnName(name);
        purchase.setAddress(addId.toString());
        purchase.setPurchaseStatus(1);
        purchase.setTotalPrice(new BigDecimal(tMoney));
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());

        purchaseService.add(purchase);
        Purchase PurchaseGetId = purchaseService.selectNewPurchaseId(name);
        Integer purchaseId = PurchaseGetId.getPurchaseId();
        if (null != shoppingInfoList && !shoppingInfoList.isEmpty()){
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            SellPurchase sellPurchase = new SellPurchase();
            for (ShoppingInfo shoppingInfo : shoppingInfoList) {
                purchaseDetail.setCount(shoppingInfo.getCount());
                purchaseDetail.setOrderId(shoppingInfo.getOrderId());
                purchaseDetail.setPurchaseId(purchaseId);
                purchaseDetail.setSumPrice(BigDecimal.valueOf(Double.parseDouble(shoppingInfo.getPrice()) * shoppingInfo.getCount()));
                purchaseDetail.setUninPrice(new BigDecimal(shoppingInfo.getPrice()));
                purchaseDetailService.add(purchaseDetail);

                sellPurchase.setOrderId(shoppingInfo.getOrderId());
                sellPurchase.setAddress(addId.toString());
                sellPurchase.setOwnName(shoppingInfo.getOwnName());
                sellPurchase.setPurchaseId(purchaseId);
                sellPurchase.setPurchaseStatus(1);
                sellPurchase.setPurchaseType(2);
                sellPurchase.setSumPrice(BigDecimal.valueOf(Double.parseDouble(shoppingInfo.getPrice()) * shoppingInfo.getCount()));
                sellPurchase.setUninPricee(new BigDecimal(shoppingInfo.getPrice()));
                sellPurchase.setCreateTime(new Date());
                sellPurchase.setUpdateTime(new Date());
                sellPurchaseService.add(sellPurchase);

                shoppingcartService.delete(shoppingInfo.getShoppingId());
            }
        }

        return new HttpResult(true, Constant.OK, "提交成功");
    }
}