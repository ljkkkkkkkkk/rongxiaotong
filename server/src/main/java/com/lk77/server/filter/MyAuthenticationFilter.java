package com.lk77.server.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lk77.server.domain.entity.AuthenticationBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (isJsonContentType(request)) {
            return attemptJsonAuthentication(request);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    private boolean isJsonContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        return MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType) || MediaType.APPLICATION_JSON_VALUE.equals(contentType);
    }

    private Authentication attemptJsonAuthentication(HttpServletRequest request) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = request.getInputStream()) {
            AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    authenticationBean.getUsername(), authenticationBean.getPassword());
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return new UsernamePasswordAuthenticationToken("", "");
        }
    }
}
