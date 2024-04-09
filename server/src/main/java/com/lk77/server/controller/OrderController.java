package com.lk77.server.controller;

import com.github.pagehelper.PageInfo;
import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.domain.entity.Order;
import com.lk77.server.domain.entity.PurchaseDetail;
import com.lk77.server.domain.entity.SellPurchase;
import com.lk77.server.domain.CustomPurchase;
import com.lk77.server.service.OrderService;
import com.lk77.server.service.PurchaseDetailService;
import com.lk77.server.service.PurchaseService;
import com.lk77.server.service.SellPurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(tags = "订单模块接口")
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    private final PurchaseService purchaseService;

    private final PurchaseDetailService purchaseDetailService;

    private final SellPurchaseService sellPurchaseService;

    public OrderController(OrderService orderService, PurchaseService purchaseService, PurchaseDetailService purchaseDetailService, SellPurchaseService sellPurchaseService) {
        this.orderService = orderService;
        this.purchaseService = purchaseService;
        this.purchaseDetailService = purchaseDetailService;
        this.sellPurchaseService = sellPurchaseService;
    }

    @ApiOperation("查询所有商品")
    @GetMapping("/All/{pageNum}")
    public HttpResult<PageInfo> selectAll(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAll(pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("分页查询所有货源(商品)商品")
    @GetMapping("/goods/{pageNum}")
    public HttpResult<PageInfo> selectAllGoods(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllGoods(pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("分页查询所有需求")
    @GetMapping("/needs/{pageNum}")
    public HttpResult<PageInfo> selectAllNeeds(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllNeeds(pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("添加商品")
    @PostMapping
    public HttpResult<String> add(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuffer = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<>(false, Constant.ERROR, "添加失败", s);
        }
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        order.setOwnName(name);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.add(order);
        return new HttpResult(true, Constant.OK, "添加成功", null);
    }

    @ApiOperation("根据id修改商品")
    @PutMapping("/{id}")
    public HttpResult<String> update(@Validated @RequestBody Order order, BindingResult bindingResult,
                                     @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuffer = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "修改失败", s);
        }
        order.setUpdateTime(new Date());
        order.setOrderId(id);
        orderService.update(order);
        return new HttpResult(true, Constant.OK, "修改成功", null);
    }

    @ApiOperation("根据id删除商品")
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("根据用户名+订单类型查询商品")
    @GetMapping("/search/{type}/{pageNum}")
    public HttpResult<PageInfo> selectByType(@PathVariable("type") String type, @PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectByType(type, pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("根据id查询商品")
    @GetMapping("/selectById/{id}")
    public HttpResult<Order> selectById(@PathVariable("id") Integer id) {
        Order order = orderService.selectById(id);
        return new HttpResult<>(true, Constant.OK, "查询成功", order);
    }

    @ApiOperation("根据登录用户查询我买的订单")
    @GetMapping("/selectBuys")
    public HttpResult<List<CustomPurchase>> selectBuys() {
        List<CustomPurchase> purchase = purchaseService.selectByPurchaseType();
        return new HttpResult<>(true, Constant.OK, "查询成功", purchase);
    }

    @ApiOperation("根据登录用户查询我买的订单详情")
    @GetMapping("/selectBuysDetail/{id}")
    public HttpResult<List<PurchaseDetail>> selectBuysDetail(@PathVariable("id") Integer purchaseId) {
        List<PurchaseDetail> purchaseDetail = purchaseDetailService.selectByPurchaseId(purchaseId);
        return new HttpResult<>(true, Constant.OK, "查询成功", purchaseDetail);
    }

    @ApiOperation("根据登录用户查询我卖出的订单")
    @GetMapping("/selectSells")
    public HttpResult<List<SellPurchase>> selectSells() {
        List<SellPurchase> purchase = sellPurchaseService.selectByName();
        return new HttpResult<>(true, Constant.OK, "查询成功", purchase);
    }

    @ApiOperation("分页条件搜索商品（货源）订单")
    @GetMapping("/searchGoodsByKeys/{keys}/{pageNum}")
    public HttpResult<PageInfo> searchGoodsByKeys(@PathVariable("keys") String keys, @PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectGoodsByKeys(keys, pageNum, null);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("分页条件搜索需求商品")
    @GetMapping("/searchNeedsByKeys/{keys}/{pageNum}")
    public HttpResult<PageInfo> searchNeedsByKeys(@PathVariable("keys") String keys, @PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectNeedsByKeys(keys, pageNum, null);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("分页条件搜索所有商品")
    @GetMapping("/searchAllByKeys/{keys}/{pageNum}")
    public HttpResult<PageInfo> searchAllByKeys(@PathVariable("keys") String keys, @PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllByKeys(keys, pageNum);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("分页条件搜索需求商品")
    @GetMapping("/searchMyNeedsByKeys/{keys}/{pageNum}")
    public HttpResult<PageInfo> searchMyNeedsByKeys(@PathVariable("keys") String keys, @PathVariable("pageNum") Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        PageInfo<Order> orders = orderService.selectNeedsByKeys(keys, pageNum, name);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("分页条件搜索商品（货源）商品")
    @GetMapping("/searchMyGoodsByKeys/{keys}/{pageNum}")
    public HttpResult<PageInfo> searchMyGoodsByKeys(@PathVariable("keys") String keys, @PathVariable("pageNum") Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        PageInfo<Order> orders = orderService.selectGoodsByKeys(keys, pageNum, name);
        return new HttpResult<>(true, Constant.OK, "查询成功", orders);
    }

    @ApiOperation("商品下架")
    @PutMapping("/takeDownOrder/{orderId}")
    public HttpResult takeDownOrder(@PathVariable("orderId") String orderId) {
        orderService.takeDown(orderId);
        return new HttpResult<PageInfo>(true, Constant.OK, "下架成功");
    }

    @ApiOperation("商品上架")
    @PutMapping("/takeUpOrder/{orderId}")
    public HttpResult takeUpOrder(@PathVariable("orderId") String orderId) {
        orderService.takeUp(orderId);
        return new HttpResult<PageInfo>(true, Constant.OK, "上架成功");
    }
}
