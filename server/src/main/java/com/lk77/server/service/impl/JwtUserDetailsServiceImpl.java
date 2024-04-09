package com.lk77.server.service.impl;


import com.lk77.server.domain.entity.User;
import com.lk77.server.service.UserService;
import com.lk77.server.domain.entity.JwtUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }

        String role = user.getRole();
        return new JwtUser(user.getUserName(),user.getNickName(), user.getPassword(), user.getAvatar(),AuthorityUtils.commaSeparatedStringToAuthorityList(role));
    }
}
