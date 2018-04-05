package com.hansbug.debug.exceptions;

import com.hansbug.arguments.exceptions.ArgumentsException;

/**
 * DebugHelper命令行参数错误
 */
public class DebugArgumentsException extends DebugHelperException {
    /**
     * 参数错误异常对象
     */
    private ArgumentsException arguments_exception;
    
    /**
     * DebugHelper命令行参数错误对象初始化
     *
     * @param arguments_exception 参数错误异常对象
     */
    public DebugArgumentsException(ArgumentsException arguments_exception) {
        super(arguments_exception.getMessage());
        this.arguments_exception = arguments_exception;
    }
    
    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return this.arguments_exception.getMessage();
    }
}
