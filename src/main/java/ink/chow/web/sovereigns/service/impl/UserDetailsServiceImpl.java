package ink.chow.web.sovereigns.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ink.chow.web.sovereigns.domain.UserWithAuthorities;
import ink.chow.web.sovereigns.mapper.ex.UserMapperEx;

/**
 * Description: security框架根据用户名查询用户密码和权限的类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapperEx userMapperEx;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserWithAuthorities withAuthorities = new UserWithAuthorities(userMapperEx.selectByUserName(s), null);
        return withAuthorities;
    }
}
