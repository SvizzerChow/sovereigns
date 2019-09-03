package ink.chow.web.sovereigns.common;

/**
 * Description: ${DESCRIPTION}
 *
 * @author ZhouS
 * @date 2018/11/28 19:54
 */
public enum ResultCodeEnum {
    SUCCESS(200, "success"),FAIL(500, "服务器异常");




    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
