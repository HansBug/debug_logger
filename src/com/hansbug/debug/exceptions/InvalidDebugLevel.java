package com.hansbug.debug.exceptions;

/**
 * 非法debug level异常类
 */
public class InvalidDebugLevel extends DebugHelperException {
    /**
     * 初始化非法debug level异常类
     *
     * @param debug_level 非法的debug level
     */
    public InvalidDebugLevel(int debug_level) {
        this(String.valueOf(debug_level));
    }
    
    /**
     * 初始化非法debug level异常类
     *
     * @param debug_level 非法的debug level
     */
    public InvalidDebugLevel(String debug_level) {
        super(String.format("Invalid debug level - %s", debug_level));
    }
}
