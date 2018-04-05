package com.hansbug.arguments.exceptions;

/**
 * 非法参数短名异常类
 */
public class InvalidArgumentShortName extends InvalidArgumentInfo {
    /**
     * 非法参数短名异常类
     *
     * @param short_name 短名
     */
    public InvalidArgumentShortName(String short_name) {
        super(String.format("Invalid short name - %s", short_name));
    }
}
