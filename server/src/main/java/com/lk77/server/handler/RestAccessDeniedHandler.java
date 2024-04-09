package com.lk77.server.handler;

import com.alibaba.fastjson.JSON;
import com.lk77.server.protocal.HttpResult;
import com.lk77.server.constant.Constant;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        System.out.println("权限校验处理器");
        ServletOutputStream outputStream = response.getOutputStream();
        String s = JSON.toJSONString(new HttpResult(false, Constant.ERROR, "您没有权限"));
        outputStream.write(s.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
