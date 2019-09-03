package ink.chow.web.sovereigns.service;

import java.util.Optional;

import ink.chow.web.sovereigns.domain.UserWithAuthorities;
import ink.chow.web.sovereigns.domain.dto.LoginToken;
import ink.chow.web.sovereigns.entity.User;

/**
 * Description: 用户相关
 *
 * @author ZhouS
 * @date 2018/11/28 15:31
 */
public interface IUserService {
    /**
     * 登录，返回token
     * @param username
     * @param password
     * @return
     */
    LoginToken login(String username, String password, String ip);

    UserWithAuthorities getUserDetailByUserId(String userId);
    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    Optional<User> getUser(String userId);
    /**
     * 注册，返回失败信息
     * @param username
     * @param password
     * @return
     */
    String register(String username, String password);

}
