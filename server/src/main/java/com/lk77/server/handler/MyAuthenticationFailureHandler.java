package com.lk77.server.handler;

import com.alibaba.fastjson.JSON;
import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String s = JSON.toJSONString(new HttpResult(false, Constant.ERROR, "账号或密码错误，请输入正确的的信息"));
        outputStream.write(s.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
