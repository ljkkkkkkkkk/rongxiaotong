package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.domain.entity.*;
import com.lk77.server.service.BankService;
import com.lk77.server.service.FinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "融资模块接口")
@RestController
@RequestMapping("/finance")
@CrossOrigin
public class FinanceController {
    private final FinanceService financeService;
    private final BankService bankService;

    public FinanceController(FinanceService financeService, BankService bankService) {
        this.financeService = financeService;
        this.bankService = bankService;
    }

    @ApiOperation("查询融资意向")
    @GetMapping("/selectIntentionByName")
    public HttpResult selectIntentionByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Intention> intentions = financeService.selectIntentionByName(name);
        return new HttpResult(true, Constant.OK, "查询成功", intentions);
    }

    @ApiOperation("添加融资意向")
    @PostMapping("/insertIntentionByName")
    public HttpResult insertIntentionByName(@RequestBody Intention intention) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        intention.setUserName(name);
        financeService.insertIntentionByName(intention);
        return new HttpResult(true, Constant.OK, "添加成功");
    }

    @ApiOperation("修改融资意向")
    @PutMapping("/updateIntentionByName")
    public HttpResult updateIntentionByName(@RequestBody Intention intention) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        intention.setUserName(name);
        financeService.updateIntentionByName(intention);
        return new HttpResult(true, Constant.OK, "修改成功");
    }

    @ApiOperation("删除融资意向")
    @DeleteMapping("/deleteIntentionByName")
    public HttpResult deleteIntentionByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        financeService.deleteIntentionByName(name);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("查询推荐融资人")
    @GetMapping("/selectRecommendByName")
    public HttpResult selectRecommendByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Recommend> list = financeService.selectRecommend(name);
        return new HttpResult(true, Constant.OK, "查询成功", list);
    }

    @ApiOperation("单人贷款")
    @GetMapping("/selectFinaceUser/{bankId}")
    public HttpResult selectFinaceUser(@PathVariable("bankId") String bankId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        FinaceUserDetails finaceUserDetails = financeService.selectFinaceUser(name, bankId);
        return new HttpResult(true, Constant.OK, null, finaceUserDetails);
    }

    @ApiOperation("查询是否已经贷款")
    @GetMapping("/selectIfApply")
    public HttpResult selectIfApply() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        boolean b = financeService.selectIfApply(name);
        return new HttpResult(true, Constant.OK, "添加成功", b);
    }

    @ApiOperation("添加单人贷款")
    @PostMapping("/add")
    public HttpResult add(@RequestBody Finance finance) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        finance.setOwnName(name);
        financeService.add(finance);
        return new HttpResult(true, Constant.OK, "申请成功");
    }

    @ApiOperation("添加组合贷款")
    @PostMapping("/addFinanceMulti")
    public HttpResult addFinanceMulti(@RequestBody Finance finance) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        finance.setOwnName(name);
        financeService.addMulti(finance);
        return new HttpResult(true, Constant.OK, "申请成功");
    }

    @ApiOperation("查询融资情况")
    @GetMapping("/selectByName")
    public HttpResult selectByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Finance finance = new Finance();
        finance.setOwnName(name);
        List<Finance> finances = financeService.selectByFinance(finance);
        return new HttpResult(true, Constant.OK, "查询成功", finances);
    }

    @ApiOperation("根据id修改融资情报")
    @PutMapping("/update")
    public HttpResult update(@RequestBody Finance finance) {
        financeService.updateByFinance(finance);
        return new HttpResult(true, Constant.OK, "修改成功");
    }

    @ApiOperation("根据id删除融资情报")
    @DeleteMapping("/delete/{id}")
    public HttpResult delete(@PathVariable("id") Integer id) {
        financeService.deleteByFinanceId(id);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("根据id查询融资情报")
    @GetMapping("/selectById/{id}")
    public HttpResult selectById(@PathVariable("id") Integer id) {
        Finance finance = financeService.selectByFinanceId(id);
        return new HttpResult(true, Constant.OK, "查询成功", finance);
    }

    @ApiOperation("查询银行情报")
    @GetMapping("/selectbank")
    public HttpResult selectbank() {
        List<Bank> banks = bankService.selectAllBank();
        return new HttpResult(true, Constant.OK, "查询成功", banks);
    }
}
