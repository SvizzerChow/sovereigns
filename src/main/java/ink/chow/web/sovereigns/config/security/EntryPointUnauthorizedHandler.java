package ink.chow.web.sovereigns.config.security;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ink.chow.web.sovereigns.common.HttpContentTypeEnum;
import ink.chow.web.sovereigns.ctrl.BaseController;
import ink.chow.web.sovereigns.domain.dto.DtoResult;

/**
 * Description: 自定义401返回值
 *
 * @author ZhouS
 * @date 2018/11/29 10:05
 */
@Component
public class EntryPointUnauthorizedHandler extends BaseController implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(EntryPointUnauthorizedHandler.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        if(e instanceof BadCredentialsException){
            handlerBadCredentialsException(httpServletResponse, e);
        } else if (e instanceof InsufficientAuthenticationException){
            handlerInsufficientAuthenticationException(httpServletResponse, httpServletRequest, e);
        } else {
            writeMessage(httpServletResponse, new DtoResult<>("", 401, "验证不通过"));
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
