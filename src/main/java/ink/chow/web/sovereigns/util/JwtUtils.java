package ink.chow.web.sovereigns.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import ink.chow.web.sovereigns.domain.UserWithAuthorities;
import ink.chow.web.sovereigns.domain.dto.LoginToken;
import org.springframework.util.StringUtils;

/**
 * Description: jwt工具类
 *
 * @author ZhouS
 * @date 2018/11/28 17:31
 */
public class JwtUtils {
    private static String secret = "freedom";

    private static String issuer = JwtUtils.class.getName();

    private static int expiredTime = 120;// 过期时间


    public static String createJwt(UserWithAuthorities userDetails){

        return createJwt(userDetails, expiredTime);
    }

    public static String createJwt(UserWithAuthorities userDetails, int expired){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create().withIssuer(issuer)
                //.withClaim("userId", userDetails.getUser().getUserId())
                //.withClaim("userName", userDetails.getUsername())
                //.withClaim("password", userDetails.getPassword())
                .withClaim("user", JSON.toJSONString(userDetails.getUser()))
                //.withClaim("auth", JSON.toJSONString(userDetails.getAuthorities()))
                .withExpiresAt(Date.from(new Date().toInstant().plus(expired, ChronoUnit.MINUTES)))
                .sign(algorithm);
        return token;
    }

    public static LoginToken createLoginToken(UserWithAuthorities userDetails){
        LoginToken token = new LoginToken();
        token.setToken(createJwt(userDetails));
        token.setRefreshToken(createJwt(userDetails, expiredTime*100));
        return token;
    }


    public static Map<String, Claim> parseJWT(String token) throws JWTVerificationException {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaims();
    }

    public static Optional<String> getUserIdFromToken(String token){
        Map<String, Claim> claimMap = parseJWT(token);
        if (claimMap != null){
            Claim claim = claimMap.get("userId");
            return Optional.ofNullable(claim!=null?claim.asString():null);
        }
        return Optional.empty();
    }

    public static Optional<String> getUserNameFromToken(String token){
        Map<String, Claim> claimMap = parseJWT(token);
        if (claimMap != null){
            Claim claim = claimMap.get("userName");
            return Optional.ofNullable(claim!=null?claim.asString():null);
        }
        return Optional.empty();
    }

    public static Optional<String> getUserFromToken(String token){
        Map<String, Claim> claimMap = parseJWT(token);
        if (claimMap != null){
            Claim claim = claimMap.get("user");
            return Optional.ofNullable(claim!=null?claim.asString():null);
        }
        return Optional.empty();
    }
}
