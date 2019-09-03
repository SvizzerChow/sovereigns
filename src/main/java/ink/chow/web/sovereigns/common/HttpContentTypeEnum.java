package ink.chow.web.sovereigns.common;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2018/12/3 11:00
 */
public enum  HttpContentTypeEnum {
    JSON("application/json;charset=utf-8");

    private String value;

    HttpContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
