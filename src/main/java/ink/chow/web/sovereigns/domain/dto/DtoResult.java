package ink.chow.web.sovereigns.domain.dto;

import ink.chow.web.sovereigns.common.ResultCodeEnum;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2018/11/28 19:51
 */
public class DtoResult<T> {
    private T data;
    private int code;
    private String msg;

    public DtoResult() {
    }

    public DtoResult(T data, ResultCodeEnum codeEnum){
        this.data = data;
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public DtoResult(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public static <T> DtoResult<T> success(T data){
        return new DtoResult<>(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> DtoResult<T> fail(T data){
        return new DtoResult<>(data, ResultCodeEnum.FAIL);
    }
    public static <T> DtoResult<T> fail(T data, String msg){
        return new DtoResult<>(data, ResultCodeEnum.FAIL.getCode(), msg);
    }
}
