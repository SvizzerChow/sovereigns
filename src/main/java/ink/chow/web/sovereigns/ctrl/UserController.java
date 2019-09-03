package ink.chow.web.sovereigns.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import ink.chow.web.sovereigns.common.UrlConstant;
import ink.chow.web.sovereigns.domain.UserWithAuthorities;
import ink.chow.web.sovereigns.domain.dto.DtoResult;
import ink.chow.web.sovereigns.domain.dto.LoginToken;
import ink.chow.web.sovereigns.domain.dto.user.DtoUser;
import ink.chow.web.sovereigns.service.IUserService;

/**
 * Description: 用户管理
 *
 * @author ZhouS
 * @date 2018/11/28 19:48
 */
@RestController
@RequestMapping(UrlConstant.USER_CONTROLLER)
public class UserController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @RequestMapping(value = UrlConstant.METHOD_LOGIN, method = {RequestMethod.POST, RequestMethod.GET})
    public DtoResult<LoginToken> login(@RequestParam String username, @RequestParam String password,
                                       HttpServletRequest request){
        var token = userService.login(username, password, request.getRemoteHost());
        return DtoResult.success(token);
    }
    @RequestMapping(UrlConstant.METHOD_USER_INFO)
    public DtoResult<DtoUser> getUserInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserWithAuthorities user = (UserWithAuthorities) authentication.getPrincipal();
        return DtoResult.success(new DtoUser(user.getUser()));
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
