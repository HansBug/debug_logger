package com.hansbug.debug.exceptions;

/**
 * DebugHelper异常类
 */
public abstract class DebugHelperException extends Exception {
    /**
     * 初始化DebugHelper异常
     *
     * @param msg 异常信息
     */
    public DebugHelperException(String msg) {
        super(msg);
    }
}
