package com.hua.furnitureManagement.common.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回结果封装
 *
 * @param <T>
 * @Author 曲冠华
 * @Date 2025/2/17
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public int code;

    private String msg;

    private T data;

    private final Map<String, Object> mapdata = new HashMap<String, Object>();

    public void Result() {
        this.code = ResultsCode.SUCCESS.code;
        this.msg = ResultsCode.SUCCESS.message;
    }

    public Result<T> setCode(ResultsCode resultsCode) {
        this.code = resultsCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Result<T> putDataValue(String key, Object value) {
        mapdata.put(key, value);
        this.data = (T) mapdata;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
