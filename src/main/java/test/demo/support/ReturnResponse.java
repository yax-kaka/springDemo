package test.demo.support;

import java.io.Serializable;

public class ReturnResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public static <T> ReturnResponse<T> makeOkMessage(T data) {
        return new ReturnResponse<T>().setCode(200).setMessage("成功").setData(data);
    }

    public static <T> ReturnResponse<T> makeOkMessage() {
        return new ReturnResponse<T>().setCode(200).setMessage("成功");
    }

    public static <T> ReturnResponse<T> makeFailMessage(String message) {
        return new ReturnResponse<T>().setCode(400).setMessage(message);
    }
    public ReturnResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }
    public ReturnResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    public ReturnResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }

}
