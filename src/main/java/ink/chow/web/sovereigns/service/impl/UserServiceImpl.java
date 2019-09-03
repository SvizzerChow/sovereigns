package ink.chow.web.sovereigns.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import ink.chow.web.sovereigns.common.LoginTypeEnum;
import ink.chow.web.sovereigns.domain.UserWithAuthorities;
import ink.chow.web.sovereigns.domain.dto.LoginToken;
import ink.chow.web.sovereigns.entity.LoginInfo;
import ink.chow.web.sovereigns.entity.User;
import ink.chow.web.sovereigns.mapper.UserMapper;
import ink.chow.web.sovereigns.service.ILoginInfoService;
import ink.chow.web.sovereigns.service.IUserService;
import ink.chow.web.sovereigns.util.JwtUtils;
import org.springframework.util.StringUtils;

/**
 * Description: 用户管理
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ILoginInfoService loginInfoService;

    @Override
    public LoginToken login(String username, String password, String ip) {
        UserWithAuthorities authorities = null;
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authorities = (UserWithAuthorities) authentication.getPrincipal();
        }finally {
            this.addLoginInfo(authorities, ip, username);
        }
        return JwtUtils.createLoginToken(authorities);
    }

    private void addLoginInfo(UserWithAuthorities authorities, String ip, String username){
        LoginInfo info = new LoginInfo();
        info.setIp(ip);
        if (authorities == null){
            info.setType(LoginTypeEnum.FAIL.getType());
            info.setInfo("登录失败"+username);
            info.setUserId(username);
        }else {
            info.setType(LoginTypeEnum.SUCCESS.getType());
            info.setInfo("用户名："+username);
            info.setUserId(authorities.getUser().getUserId());
        }
        loginInfoService.addLoginInfo(info);
    }

    @Override
    public UserWithAuthorities getUserDetailByUserId(String userId) {
        return null;
    }

    @Override
    public Optional<User> getUser(String userId) {
        if (!StringUtils.isEmpty(userId)){
            User user = userMapper.selectByPrimaryKey(userId);
            return Optional.ofNullable(user);
        }
        return Optional.empty();
    }

    @Override
    public String register(String username, String password) {
        return null;
    }
}
