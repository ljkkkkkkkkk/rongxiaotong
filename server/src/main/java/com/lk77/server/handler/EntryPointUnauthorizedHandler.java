package com.lk77.server.handler;

import com.alibaba.fastjson.JSON;
import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ServletOutputStream outputStream = response.getOutputStream();
        String s = JSON.toJSONString(new HttpResult(false, Constant.ERROR, "您未登录，请先登录"));
        outputStream.write(s.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
