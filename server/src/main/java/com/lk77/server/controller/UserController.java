package com.lk77.server.controller;

import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import com.lk77.server.dao.OrderDao;
import com.lk77.server.dao.UserDao;
import com.lk77.server.domain.entity.Expert;
import com.lk77.server.domain.entity.Order;
import com.lk77.server.domain.entity.PasswordParam;
import com.lk77.server.domain.entity.User;
import com.lk77.server.service.impl.JwtUserDetailsServiceImpl;
import com.lk77.server.util.JwtTokenUtil;
import com.lk77.server.service.ExpertService;
import com.lk77.server.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "用户模块接口")
public class UserController {
    private final OrderDao orderDao;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final UserDao userDao;
    private final ExpertService expertService;

    public UserController(OrderDao orderDao, UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, JwtUserDetailsServiceImpl jwtUserDetailsService, UserDao userDao, ExpertService expertService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.userDao = userDao;
        this.expertService = expertService;
    }

    @ApiOperation(value = "用户登录之后，修改用户密码")
    @PostMapping("/loginUpdatePassword")
    public HttpResult<String> loginUpdatePassword(@Validated @RequestBody PasswordParam passwordParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuffer = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "密码修改失败", s);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        User user = userService.selectByUserName(username);
        String oldDataBasePassword = user.getPassword();
        String oldPassword = passwordParam.getOldPassword();
        if (!passwordEncoder.matches(oldPassword, oldDataBasePassword)) {
            return new HttpResult<String>(false, Constant.ERROR, "原密码输入错误", "原密码输入错误");
        }
        user.setUserName(username);
        user.setPassword(passwordParam.getNewPassword());
        userService.update(user);
        return new HttpResult<String>(true, Constant.OK, "修改成功");
    }

    @ApiOperation(value = "用户登录之后，根据用户名展示个人信息")
    @GetMapping("/loginSelectByUsername")
    public HttpResult<User> loginSelectByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        User user = userService.selectByUserName(username);
        return new HttpResult<User>(true, Constant.OK, "查询成功", user);
    }

    @ApiOperation(value = "用户登录之后，根据用户名修改个人基本信息")
    @PostMapping("/loginUpdateByUsername")
    public HttpResult<String> loginUpdateByUsername(@Validated @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder stringBuffer = new StringBuilder();
            for (ObjectError error : allErrors) {
                stringBuffer.append(error.getDefaultMessage()).append("; ");
            }
            String errorInfo = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "修改失败", errorInfo);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        user.setUserName(username);
        userService.loginUpdateByUsername(user);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        return new HttpResult<String>(true, Constant.OK, "修改成功", token);
    }

    @ApiOperation(value = "查询所有用户")
    @GetMapping
    public HttpResult<List<User>> selectAll() {
        List<User> users = userService.selectAll();
        return new HttpResult<List<User>>(true, Constant.OK, "查询成功", users);
    }

    @ApiOperation(value = "增加用户")
    @PostMapping("/add")
    public HttpResult<String> add(@Valid @RequestBody User user, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder stringBuffer = new StringBuilder();
            for (ObjectError error : allErrors) {
                stringBuffer.append(error.getDefaultMessage()).append("; ");
            }
            String errorInfo = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "注册失败", errorInfo);
        }
        String userName = user.getUserName();
        User user1 = userDao.selectByPrimaryKey(userName);
        if (user1 != null) {
            return new HttpResult<String>(false, Constant.ERROR, "注册失败", "用户名已被注册，请重新输入");
        }
        userService.add(user);
        return new HttpResult(true, Constant.OK, "注册成功");
    }

    @ApiOperation(value = "根据用户名修改用户信息")
    @PutMapping(value = "/{userName}")
    public HttpResult<String> update(@Validated @RequestBody User user, BindingResult bindingResult, @PathVariable("userName") String userName) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuffer = new StringBuilder();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            return new HttpResult<String>(false, Constant.ERROR, "信息修改失败", s);
        }
        user.setUserName(userName);
        userService.update(user);
        return new HttpResult<String>(true, Constant.OK, "信息修改成功", "修改成功");
    }

    @ApiOperation(value = "根据用户名删除用户")
    @DeleteMapping("/{userName}")
    public HttpResult<String> deletes(@PathVariable("userName") String userName) {
        Order order = new Order();
        order.setOwnName(userName);
        List<Order> orders = orderDao.selectByExample(order);
        if (!orders.isEmpty()) {
            return new HttpResult<String>(false, Constant.ERROR, "删除失败", "该用户有发布的订单，暂不能删除");
        }
        userService.delete(userName);
        expertService.delete(userName);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation(value = "根据用户名查询用户")
    @GetMapping("/{userName}")
    public HttpResult selectByUserName(@PathVariable("userName") String userName) {
        User user = userService.selectByUserName(userName);
        return new HttpResult(true, Constant.OK, "查询成功", user);
    }

    @ApiOperation("分页查询所有用户")
    @GetMapping("/search/{pageNum}")
    public HttpResult<PageInfo> findPage(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<User> pageInfo = userService.findPage(pageNum);
        return new HttpResult<>(true, Constant.OK, "分页查询成功", pageInfo);
    }

    @ApiOperation("分页条件查询用户")
    @PostMapping("/search/{pageNum}/{pageSize}")
    public HttpResult<PageInfo> findPage(@RequestBody User user,
                                         @PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize) {
        PageInfo<User> pageInfo = userService.findPage(user, pageNum, pageSize);
        return new HttpResult<>(true, Constant.OK, "查询成功", pageInfo);
    }

    @ApiOperation("查询专家详情")
    @GetMapping("/searchexpert")
    public HttpResult searchExpert() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Expert expert = expertService.selectById(name);
        return new HttpResult(true, Constant.OK, "查询成功", expert);
    }

    @ApiOperation("查询所有专家详情")
    @GetMapping("/searchallexpert")
    public HttpResult searchAllExpert() {
        List<Expert> experts = expertService.selectAllExpert();
        return new HttpResult(true, Constant.OK, "查询成功", experts);
    }

    @ApiOperation("删除专家详情")
    @DeleteMapping("/deleteexpert/{userName}")
    public HttpResult deleteExpert(@PathVariable("userName") String userName) {
        expertService.delete(userName);
        return new HttpResult(true, Constant.OK, "删除成功");
    }

    @ApiOperation("更新专家详情")
    @PutMapping("/updateexpert")
    public HttpResult updateExpert(@RequestBody Expert expert) {
        expertService.updateById(expert);
        return new HttpResult(true, Constant.OK, "更新成功");
    }

    @ApiOperation("增加专家详情")
    @PostMapping("/addexpert")
    public HttpResult addExpert(@RequestBody Expert expert) {
        expertService.insert(expert);
        return new HttpResult(true, Constant.OK, "增加成功");
    }

    @ApiOperation("增加或修改专家详情")
    @PostMapping("/addUpdexpert")
    public HttpResult addUpdexpert(@RequestBody Expert expert) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Expert expertExist = expertService.selectById(name);
        expert.setUserName(name);
        if (null != expertExist) {
            expertService.updateById(expert);
        } else {
            expertService.insert(expert);
        }
        return new HttpResult(true, Constant.OK, "操作成功");
    }
}
