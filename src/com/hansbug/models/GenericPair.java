package com.hansbug.models;

/**
 * 二元对（献给C++ STL大佬们）
 *
 * @param <X> 第一元类型
 * @param <Y> 第二元类型
 */
public class GenericPair<X, Y> {
    /**
     * 第一值
     */
    private X first_value;
    
    /**
     * 第二值
     */
    private Y second_value;
    
    /**
     * 获取第一值
     *
     * @return 第一值
     */
    public X getFirst() {
        return this.first_value;
    }
    
    /**
     * 设置第一值
     *
     * @param first 第一值
     */
    public void setFirst(X first) {
        this.first_value = first;
    }
    
    /**
     * 获取第二值
     *
     * @return 第二值
     */
    public Y getSecond() {
        return this.second_value;
    }
    
    /**
     * 设置第二值
     *
     * @param second 第二值
     */
    public void setSecond(Y second) {
        this.second_value = second;
    }
    
    /**
     * 初始化二元对
     *
     * @param first  第一值
     * @param second 第二值
     */
    public GenericPair(X first, Y second) {
        this.first_value = first;
        this.second_value = second;
    }
    
}
