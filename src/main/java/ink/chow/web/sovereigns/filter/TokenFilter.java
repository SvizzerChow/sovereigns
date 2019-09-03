package ink.chow.web.sovereigns.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ink.chow.web.sovereigns.common.SecurityTokenHeaderEnum;
import ink.chow.web.sovereigns.domain.UserWithAuthorities;
import ink.chow.web.sovereigns.entity.User;
import ink.chow.web.sovereigns.util.JwtUtils;

/**
 * Description: token校验
 *
 * @author ZhouS
 * @date 2018/11/29 9:57
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> user = Optional.empty();
        String token = null;
        var cookies = httpServletRequest.getCookies();
        if (cookies !=null) {
            for (var cookie : cookies) {
                if (cookie.getName().equals(SecurityTokenHeaderEnum.COOKIE_TOKEN.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (StringUtils.isEmpty(token)) {
            String header = httpServletRequest.getHeader(SecurityTokenHeaderEnum.HEADER_TOKEN.getName());
            if (header != null && header.length() > SecurityTokenHeaderEnum.HEADER_TOKEN_HEAD.getName().length()) {
                token = header.substring(SecurityTokenHeaderEnum.HEADER_TOKEN_HEAD.getName().length());
            }
        }
        if (StringUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter(SecurityTokenHeaderEnum.PARAM_TOKEN.getName());
        }
        if (!StringUtils.isEmpty(token)) {
            try {
                user = JwtUtils.getUserFromToken(token);
            }catch (JWTVerificationException exception){
                logger.error("token 解析失败 {}", token, exception);
                throw new RuntimeException(exception);
            }
            if (user.isPresent()) {
                UserDetails userDetails = new UserWithAuthorities(
                        JSON.parseObject(user.get(), User.class), null);
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
