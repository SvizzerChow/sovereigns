package ink.chow.web.sovereigns.config.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ink.chow.web.sovereigns.common.HttpContentTypeEnum;
import ink.chow.web.sovereigns.domain.dto.DtoResult;

/**
 * Description: 403返回
 *
 * @author ZhouS
 * @date 2018/11/29 10:09
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        logger.error("拒绝访问：{} -> {}",
                httpServletRequest.getRemoteHost(), httpServletRequest.getServletPath(), e);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType(HttpContentTypeEnum.JSON.getValue());
        httpServletResponse.getWriter().write(JSON.toJSONString(new DtoResult<>("", 403, "拒绝访问")));
    }
}
