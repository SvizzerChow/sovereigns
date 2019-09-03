package ink.chow.web.sovereigns.common;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2018/12/3 14:36
 */
public enum LoginTypeEnum {
    FAIL(0),
    ILLEGAL(-1),
    SUCCESS(1);

    private int type;

    LoginTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
