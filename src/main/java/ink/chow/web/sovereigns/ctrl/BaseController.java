package ink.chow.web.sovereigns.ctrl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ink.chow.web.sovereigns.common.HttpContentTypeEnum;
import ink.chow.web.sovereigns.domain.dto.DtoResult;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2018/11/1 12:34
 */
public abstract class BaseController {

    /**
     * 400 处理
     * @param response
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handlerMissingServletRequestParameterException(HttpServletResponse response,
                                                               Exception e) throws IOException {
        getLogger().error("参数错误", e.getMessage());
        writeMessage(response, new DtoResult<>(e.getMessage(), 400, "参数错误"));
    }

    /**
     * 用户名或密码错误处理
     * @param response
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public void handlerBadCredentialsException(HttpServletResponse response,
                                                               Exception e) throws IOException {
        getLogger().error("用户名或密码错误", e);
        writeMessage(response, new DtoResult<>(e.getMessage(), 401, "用户名或密码错误"));
    }

    /**
     * 未登录处理
     * @param response
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public void handlerInsufficientAuthenticationException(HttpServletResponse response, HttpServletRequest request,
                                               Exception e) throws IOException {
        getLogger().error("未登录:{}{}", request.getContextPath(), request.getServletPath(), e);
        writeMessage(response, new DtoResult<>(e.getMessage(), 401, "未登录"));
    }

    /**
     * 异常统一处理
     * @param response
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    public void handlerException(HttpServletResponse response, Exception e) throws IOException {
        getLogger().error("未知异常", e);
        writeMessage(response, DtoResult.fail("", "服务器出错"));
    }

    protected void writeMessage(HttpServletResponse response, DtoResult result) throws IOException {
        response.setContentType(HttpContentTypeEnum.JSON.getValue());
        response.getWriter().write(JSON.toJSONString(result));
    }

    public abstract Logger getLogger();
}
