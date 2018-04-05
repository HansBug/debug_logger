package com.hansbug.arguments.exceptions;

/**
 * 非法参数项设置异常类（抽象类）
 */
public abstract class InvalidArgumentInfo extends ArgumentsException {
    /**
     * 非法参数想设置异常类初始化
     *
     * @param msg 初始化字符串信息
     */
    InvalidArgumentInfo(String msg) {
        super(msg);
    }
    
}
