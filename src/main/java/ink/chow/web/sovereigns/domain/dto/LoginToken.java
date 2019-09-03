package ink.chow.web.sovereigns.domain.dto;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2018/11/29 14:19
 */
public class LoginToken {
    private String token;
    private String refreshToken;

    public LoginToken() {
    }

    public LoginToken(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
