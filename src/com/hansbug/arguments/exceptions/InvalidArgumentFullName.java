package com.hansbug.arguments.exceptions;

/**
 * 非法参数全名异常类
 */
public class InvalidArgumentFullName extends InvalidArgumentInfo {
    /**
     * 非法参数全名初始化
     *
     * @param full_name 全名
     */
    public InvalidArgumentFullName(String full_name) {
        super(String.format("Invalid full name - %s", full_name));
    }
}
