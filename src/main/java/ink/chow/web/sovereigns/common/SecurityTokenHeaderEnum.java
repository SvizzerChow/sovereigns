package ink.chow.web.sovereigns.common;

/**
 * Description: token请求头以及参数名
 *
 * @author ZhouS
 * @date 2018/11/29 10:18
 */
public enum SecurityTokenHeaderEnum {
    COOKIE_TOKEN("securityToken"),
    HEADER_TOKEN("Authorization"),
    HEADER_TOKEN_HEAD("ST-"),
    PARAM_TOKEN("securityToken");

    private String name;

    SecurityTokenHeaderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
