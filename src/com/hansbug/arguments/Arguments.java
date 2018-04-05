package com.hansbug.arguments;

import com.hansbug.arguments.exceptions.InvalidArgumentFullName;
import com.hansbug.arguments.exceptions.InvalidArgumentInfo;
import com.hansbug.arguments.exceptions.InvalidArgumentShortName;
import com.hansbug.arguments.exceptions.NoArgumentName;
import com.hansbug.models.HashDefaultMap;


/**
 * 参数解析器对象
 */
public class Arguments {
    /**
     * 单项设置项信息
     */
    private class ArgumentInfo {
        /**
         * 短名
         */
        private String short_name;
        
        /**
         * 全名
         */
        private String full_name;
        
        /**
         * 是否后面跟随值
         */
        private boolean value_required;
        
        /**
         * 参数默认值
         */
        private String default_value;
        
        /**
         * 基本初始化函数
         *
         * @param short_name     短名
         * @param full_name      长名
         * @param value_required 是否有值跟随
         * @param default_value  默认值
         * @throws InvalidArgumentInfo 非法参数项类
         */
        ArgumentInfo(String short_name, String full_name, boolean value_required, String default_value) throws InvalidArgumentInfo {
            if ((short_name != null) && (short_name.length() != 1)) {
                throw new InvalidArgumentShortName(short_name);
            }
            if ((full_name != null) && (full_name.length() <= 1)) {
                throw new InvalidArgumentFullName(full_name);
            }
            if ((full_name == null) && (short_name == null)) {
                throw new NoArgumentName();
            }
            this.short_name = short_name;
            this.full_name = full_name;
            this.value_required = value_required;
            this.default_value = default_value;
        }
        
        /**
         * 基本初始化函数（默认值null）
         *
         * @param short_name     短名
         * @param full_name      长名
         * @param value_required 是否有值跟随
         * @throws InvalidArgumentInfo 非法参数项类
         */
        public ArgumentInfo(String short_name, String full_name, boolean value_required) throws InvalidArgumentInfo {
            this(short_name, full_name, value_required, null);
        }
        
        /**
         * 获取短名
         *
         * @return 短名
         */
        String getShortName() {
            return this.short_name;
        }
        
        /**
         * 获取长名
         *
         * @return 长名
         */
        String getFullName() {
            return this.full_name;
        }
        
        /**
         * 获取是否需要值
         *
         * @return 是否需要值
         */
        boolean getValueRequired() {
            return this.value_required;
        }
        
        /**
         * 参数项默认值
         *
         * @return 默认值
         */
        String getDefaultValue() {
            return this.default_value;
        }
    }
    
    /**
     * 参数哈希类
     */
    private HashDefaultMap<String, ArgumentInfo> argument_hash = new HashDefaultMap<>(null);
    
    /**
     * 默认构造函数
     */
    public Arguments() {
    }
    
    /**
     * 添加参数项
     *
     * @param short_name     短名
     * @param full_name      长名
     * @param value_required 是否尾随数值
     * @throws InvalidArgumentInfo 非法参数项
     */
    public void addArgs(String short_name, String full_name, boolean value_required) throws InvalidArgumentInfo {
        addArgs(short_name, full_name, value_required, null);
    }
    
    /**
     * 添加参数项
     *
     * @param short_name     短名
     * @param full_name      长名
     * @param value_required 是否尾随数值
     * @param default_value  默认值
     * @throws InvalidArgumentInfo 非法参数项
     */
    public void addArgs(String short_name, String full_name, boolean value_required, String default_value) throws InvalidArgumentInfo {
        ArgumentInfo ai = new ArgumentInfo(short_name, full_name, value_required, default_value);
        if (ai.getShortName() != null) {
            argument_hash.put(ai.getShortName(), ai);
        }
        if (ai.getFullName() != null) {
            argument_hash.put(ai.getFullName(), ai);
        }
    }
    
    /**
     * 解析args数组
     *
     * @param args args数组
     * @return 参数表结果
     */
    public HashDefaultMap<String, String> parseArguments(String[] args) {
        HashDefaultMap<String, String> result = new HashDefaultMap<>(null);  // initialize default hash
        for (String key : argument_hash.keySet()) {  // set the default value
            String value = argument_hash.get(key).default_value;
            if (value != null) result.put(key, value);
        }
        
        for (int i = 0; i < args.length; i++) {
            String present_item = args[i];
            String name;
            if ((present_item.length() > 2) && present_item.substring(0, 2).equals("--")) {  // full name
                name = present_item.substring(2);
            } else if ((present_item.length() > 1) && present_item.substring(0, 1).equals("-")) {  // short name
                name = present_item.substring(1);
            } else {
                continue;
            }
            if (argument_hash.get(name) != null) {  // argument name exists
                ArgumentInfo ai = argument_hash.get(name);
                String value;
                if (ai.value_required && (i < (args.length - 1))) {
                    value = args[i + 1];
                } else {
                    value = "";
                }
                if (ai.getShortName() != null) {
                    result.put(ai.getShortName(), value);
                }
                if (ai.getFullName() != null) {
                    result.put(ai.getFullName(), value);
                }
            }
        }
        
        return result;
    }
}
