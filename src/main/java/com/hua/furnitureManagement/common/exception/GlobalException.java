package com.hua.furnitureManagement.common.exception;

/**
 * 全局异常
 */
public class GlobalException extends RuntimeException {
    public GlobalException() {
    }

    public GlobalException(String msg) {
        super(msg);
    }
}
