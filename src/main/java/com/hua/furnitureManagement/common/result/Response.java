package com.hua.furnitureManagement.common.result;

import static com.hua.furnitureManagement.common.result.ResultsCode.AUTH_ERROR;

/**
 * 响应体数据封装
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
public class Response {
    /****
     * 成功响应Msg中的信息
     **/
    private final static String SUCCESS = "success";

    /****
     * 失败响应Msg中的信息
     **/
    private final static String FAILED = "failed";

    private final static String FORBIDDEN = "forbidden";


    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(ResultsCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    public static <T> Result<T> error(T data) {
        return new Result<T>().setCode(ResultsCode.FAIL).setMsg(FAILED).setData(data);
    }

    public static <T> Result<T> error(String data) {
        return new Result<T>().setCode(ResultsCode.FAIL).setMsg(FAILED).setData((T) data);
    }

    public static Result success() {
        return new Result().setCode(ResultsCode.SUCCESS).setMsg(SUCCESS);
    }

    public static Result forbidden() {
        return new Result().setCode(ResultsCode.FORBIDDEN).setMsg(FORBIDDEN);
    }

    public static Result authError() {
        return error(AUTH_ERROR);
    }

    public static <T> Result<T> error(ResultsCode resultsCode) {
        return new Result<T>().setCode(resultsCode.code).setMsg(resultsCode.message);
    }



    public static <T> Result<T> error(String mesage, ResultsCode resultsCode) {
        return new Result<T>().setMsg(mesage).setCode(resultsCode.code);
    }

    public static <T> Result<T> error() {
        return new Result<T>().setCode(ResultsCode.FAIL).setMsg(FAILED);
    }

    public static <T> Result<T> Forbidden() {
        return new Result<T>().setCode(ResultsCode.FORBIDDEN).setMsg(FORBIDDEN);
    }
}