package com.lk77.server.protocal;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class HttpResult<T> implements Serializable {
    private final boolean flag;
    @Getter
    private T data;
    private final Integer code;
    @Getter
    private String message;

    public HttpResult(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public HttpResult(boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
