package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.service.ReserveService;
import com.lk77.server.domain.entity.Reserve;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "预约模块接口")
@RestController
@RequestMapping("/reserve")
@CrossOrigin
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    @ApiOperation("根据ID查询预约情况")
    @GetMapping("/selectId/{id}")
    public HttpResult selectById(@PathVariable("id") Integer id) {
        Reserve reserve = reserveService.selectById(id);
        return new HttpResult(true, Constant.OK, "查询成功", reserve);
    }

    @ApiOperation("添加预约情报")
    @PostMapping("/add")
    public HttpResult add(@RequestBody Reserve reserve, BindingResult bindingResult) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        reserve.setQuestioner(name);
        if (bindingResult.hasErrors()) {
            StringBuffer stringBuffer = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "添加失败", s);
        }
        reserveService.insert(reserve);
        return new HttpResult(true, Constant.OK, "添加成功");
    }

    @ApiOperation("根据id修改预约情报")
    @PutMapping("/update")
    public HttpResult update(@RequestBody Reserve reserve, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer stringBuffer = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "修改失败", s);
        }
        reserveService.updateById(reserve);
        return new HttpResult(true, Constant.OK, "修改成功");
    }

    @ApiOperation("根据id删除预约情报")
    @DeleteMapping("/delete/{id}")
    public HttpResult delete(@PathVariable("id") Integer id) {
        reserveService.delete(id);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("根据用户查询预约情况")
    @GetMapping("/selectByKind/{kind}")
    public HttpResult selectByKind(@PathVariable("kind") String kind) {
        List<Reserve> reserves = reserveService.selectByReserve(kind);
        return new HttpResult(true, Constant.OK, "查询成功", reserves);
    }
}
