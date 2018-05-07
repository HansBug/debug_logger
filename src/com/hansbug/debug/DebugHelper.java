package com.hansbug.debug;

import com.hansbug.arguments.Arguments;
import com.hansbug.arguments.exceptions.ArgumentsException;
import com.hansbug.arguments.exceptions.InvalidArgumentInfo;
import com.hansbug.debug.exceptions.DebugArgumentsException;
import com.hansbug.debug.exceptions.DebugHelperException;
import com.hansbug.debug.exceptions.InvalidDebugLevel;
import com.hansbug.log.LogHelper;
import com.hansbug.models.HashDefaultMap;

import java.util.regex.Pattern;

/**
 * debug信息输出帮助类
 * 使用说明：
 * -D <level>, --debug <level>              设置输出debug信息的最大级别
 * --debug_package_name <package_name>      限定输出debug信息的包名（完整包名，支持正则表达式）
 * --debug_file_name <file_name>            限定输出debug信息的文件名（无路径，支持正则表达式）
 * --debug_class_name <class_name>          限定输出debug信息的类名（不包含包名的类名，支持正则表达式）
 * --debug_method_name <method_name>        限定输出的debug信息的方法名（支持正则表达式）
 * --debug_include_children                 输出限定范围内的所有子调用的debug信息（不加此命令时仅输出限定范围内当前层的debug信息）
 */
public abstract class DebugHelper {
    /**
     * value constants
     */
    private static final int MIN_DEBUG_LEVEL = 1;
    private static final int MAX_DEBUG_LEVEL = 5;
    
    /**
     * argument constants
     */
    private static final String ARG_SHORT_DEBUG = "D";
    private static final String ARG_FULL_DEBUG = "debug";
    private static final String ARG_FULL_DEBUG_SHOW_THREAD = "debug_show_thread";
    private static final String ARG_FULL_DEBUG_INCLUDE_CHILDREN = "debug_include_children";
    private static final String ARG_FULL_DEBUG_PACKAGE_NAME = "debug_package_name";
    private static final String ARG_FULL_DEBUG_FILE_NAME = "debug_file_name";
    private static final String ARG_FULL_DEBUG_CLASS_NAME = "debug_class_name";
    private static final String ARG_FULL_DEBUG_METHOD_NAME = "debug_method_name";
    private static final String DEBUG_LOG_FILE = "debug.log";
    
    /**
     * helper value
     */
    private static boolean enable_debug = false;
    private static int debug_level = MIN_DEBUG_LEVEL;
    private static boolean range_include_children = false;
    private static boolean show_thread = false;
    private static String package_name_regex = null;
    private static String file_name_regex = null;
    private static String class_name_regex = null;
    private static String method_name_regex = null;
    private static LogHelper log_helper = new LogHelper(DEBUG_LOG_FILE);
    
    /**
     * 设置是否启用debug
     *
     * @param enable_debug 启用debug
     */
    private static void setEnableDebug(boolean enable_debug) {
        DebugHelper.enable_debug = enable_debug;
    }
    
    /**
     * 设置是否显示线程信息
     *
     * @param show_thread 是否显示线程信息
     */
    private static void setShowThread(boolean show_thread) {
        DebugHelper.show_thread = show_thread;
    }
    
    /**
     * 设置debug level
     *
     * @param debug_level 新的debug level
     * @throws InvalidDebugLevel 非法的debug level抛出异常
     */
    private static void setDebugLevel(int debug_level) throws InvalidDebugLevel {
        if ((debug_level <= MAX_DEBUG_LEVEL) && (debug_level >= MIN_DEBUG_LEVEL)) {
            DebugHelper.debug_level = debug_level;
        } else {
            throw new InvalidDebugLevel(debug_level);
        }
    }
    
    
    /**
     * 设置debug信息输出范围是否包含子调用
     *
     * @param include_children 是否包含子调用
     */
    private static void setRangeIncludeChildren(boolean include_children) {
        range_include_children = include_children;
    }
    
    /**
     * 设置包名正则筛选
     *
     * @param regex 正则表达式
     */
    private static void setPackageNameRegex(String regex) {
        package_name_regex = regex;
    }
    
    /**
     * 设置文件名正则筛选
     *
     * @param regex 正则表达式
     */
    private static void setFileNameRegex(String regex) {
        file_name_regex = regex;
    }
    
    /**
     * 设置类名正则筛选
     *
     * @param regex 正则表达式
     */
    private static void setClassNameRegex(String regex) {
        class_name_regex = regex;
    }
    
    /**
     * 设置方法正则筛选
     *
     * @param regex 正则表达式
     */
    private static void setMethodNameRegex(String regex) {
        method_name_regex = regex;
    }
    
    
    /**
     * 为程序命令行添加相关的读取参数
     *
     * @param arguments 命令行对象
     * @return 添加完读取参数的命令行对象
     * @throws InvalidArgumentInfo 非法命令行异常
     */
    private static Arguments setArguments(Arguments arguments) throws InvalidArgumentInfo {
        arguments.addArgs(ARG_SHORT_DEBUG, ARG_FULL_DEBUG, true);
        arguments.addArgs(null, ARG_FULL_DEBUG_INCLUDE_CHILDREN, false);
        arguments.addArgs(null, ARG_FULL_DEBUG_SHOW_THREAD, false);
        arguments.addArgs(null, ARG_FULL_DEBUG_PACKAGE_NAME, true);
        arguments.addArgs(null, ARG_FULL_DEBUG_FILE_NAME, true);
        arguments.addArgs(null, ARG_FULL_DEBUG_CLASS_NAME, true);
        arguments.addArgs(null, ARG_FULL_DEBUG_METHOD_NAME, true);
        return arguments;
    }
    
    /**
     * 根据程序命令行进行DebugHelper初始化
     *
     * @param arguments 程序命令行参数解析结果
     * @throws InvalidDebugLevel DebugLevel非法
     */
    private static void setSettingsFromArguments(HashDefaultMap<String, String> arguments) throws InvalidDebugLevel {
        DebugHelper.setEnableDebug(arguments.containsKey(ARG_FULL_DEBUG));
        if (enable_debug) {
            DebugHelper.setDebugLevel(Integer.valueOf(arguments.get(ARG_FULL_DEBUG)));
            DebugHelper.setRangeIncludeChildren(arguments.containsKey(ARG_FULL_DEBUG_INCLUDE_CHILDREN));
            DebugHelper.setShowThread(arguments.containsKey(ARG_FULL_DEBUG_SHOW_THREAD));
            DebugHelper.setPackageNameRegex(arguments.get(ARG_FULL_DEBUG_PACKAGE_NAME));
            DebugHelper.setFileNameRegex(arguments.get(ARG_FULL_DEBUG_FILE_NAME));
            DebugHelper.setClassNameRegex(arguments.get(ARG_FULL_DEBUG_CLASS_NAME));
            DebugHelper.setMethodNameRegex(arguments.get(ARG_FULL_DEBUG_METHOD_NAME));
            
            logger("=====================================DebugHelper initialized!=====================================");
            String settings = String.format("[Configuration]debug_level: %s, include_chilren: %s", debug_level, range_include_children);
            if (package_name_regex != null) settings += String.format(", package_name: %s", package_name_regex);
            if (file_name_regex != null) settings += String.format(", file_name: %s", file_name_regex);
            if (class_name_regex != null) settings += String.format(", class_name: %s", class_name_regex);
            if (method_name_regex != null) settings += String.format(", method_name: %s", method_name_regex);
            logger(settings);
        }
    }
    
    /**
     * 打印文件日志
     *
     * @param log 日志行
     */
    private static void logger(String log) {
        if (enable_debug) log_helper.logger(log);
    }
    
    /**
     * 根据String[] args配置DebugHelper设置
     *
     * @param args 系统传入的命令行参数
     * @throws DebugHelperException DebugHelper异常类
     */
    public static void setSettingsFromArguments(String[] args) throws DebugHelperException {
        Arguments arguments = new Arguments();
        try {
            arguments = setArguments(arguments);
        } catch (ArgumentsException e) {
            throw new DebugArgumentsException(e);
        }
        setSettingsFromArguments(arguments.parseArguments(args));
    }
    
    /**
     * 判断debug level是否需要打印
     *
     * @param debug_level debug level
     * @return 是否需要打印
     */
    private static boolean isLevelValid(int debug_level) {
        return enable_debug && (debug_level <= DebugHelper.debug_level);
    }
    
    /**
     * 正则表达式是否不匹配
     *
     * @param regex 正则表达式字符串（null则一定匹配）
     * @param str   待匹配字符串
     * @return 是否不匹配
     */
    private static boolean regexMismatch(String regex, String str) {
        return (regex != null) && (!Pattern.matches(regex, str));
    }
    
    /**
     * 判断栈信息是否合法
     *
     * @param trace 栈信息
     * @return 栈信息是否合法
     */
    private static boolean isTraceValid(StackTraceElement trace) {
        try {
            Class cls = Class.forName(trace.getClassName());
            String package_name = (cls.getPackage() != null) ? cls.getPackage().getName() : "";
            boolean package_name_mismatch = regexMismatch(package_name_regex, package_name);
            boolean file_name_mismatch = regexMismatch(file_name_regex, trace.getFileName());
            boolean class_name_mismatch = regexMismatch(class_name_regex, cls.getSimpleName());
            boolean method_name_mismatch = regexMismatch(method_name_regex, trace.getMethodName());
            return !(package_name_mismatch || file_name_mismatch || class_name_mismatch || method_name_mismatch);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    /**
     * 判断栈范围是否合法
     *
     * @return 栈范围是否合法
     */
    private static boolean isStackValid(StackTraceElement[] trace_list) {
        for (int i = 1; i < trace_list.length; i++) {
            StackTraceElement trace = trace_list[i];
            if (isTraceValid(trace)) return true;
        }
        return false;
    }
    
    /**
     * 判断限制范围是否合法
     *
     * @return 限制范围是否合法
     */
    private static boolean isRangeValid(StackTraceElement[] trace_list, StackTraceElement trace) {
        if (range_include_children)
            return isStackValid(trace_list);
        else
            return isTraceValid(trace);
    }
    
    
    /**
     * debug信息输出
     *
     * @param debug_level debug level
     * @param debug_info  debug信息
     */
    public static void dPrintln(int debug_level, String debug_info) {
        StackTraceElement[] trace_list = new Throwable().getStackTrace();
        StackTraceElement trace = trace_list[1];
        String class_method;
        int trace_len = trace_list.length;
        try {
            Class cls = Class.forName(trace.getClassName());
            class_method = String.format("%s.%s", cls.getSimpleName(), trace.getMethodName());
        } catch (ClassNotFoundException e) {
            class_method = trace.getMethodName();
        }
        String debug_location = String.format("[%s:DEPTH-%d:%s:%s %s]", trace.getFileName(), trace_len - 1, trace.getClassName(), trace.getLineNumber(), class_method);
        String thread_information = String.format("[%s]", Thread.currentThread().getName());
        String debug_level_str = String.format("[DEBUG-%s]", debug_level);
        if (isLevelValid(debug_level) && isRangeValid(trace_list, trace)) {
            String output = String.join("", new String[]{
                    debug_level_str, (show_thread ? thread_information : ""), debug_location, " " + debug_info
            });
            System.out.println(output);
            logger(String.format("[DEBUG OUTPUT] %s", output));
        } else {
            logger(String.format("[DEBUG HIDDEN] Location : %s", debug_location));
        }
    }
    
    /**
     * debug信息输出
     *
     * @param debug_level  debug level
     * @param debug_info   debug信息
     * @param print_switch print开关，当不需要输出时可以立即关掉
     */
    public static void dPrintln(int debug_number, int print_switch, String debug_info) {
        StackTraceElement[] trace_list = new Throwable().getStackTrace();
        StackTraceElement trace = trace_list[1];
        String class_method;
        int trace_len = trace_list.length;
        try {
            Class cls = Class.forName(trace.getClassName());
            class_method = String.format("%s.%s", cls.getSimpleName(), trace.getMethodName());
        } catch (ClassNotFoundException e) {
            class_method = trace.getMethodName();
        }
        String debug_location = String.format("[%s:DEPTH-%d:%s:%s %s]", trace.getFileName(), trace_len - 1, trace.getClassName(), trace.getLineNumber(), class_method);
        String thread_information = String.format("[%s]", Thread.currentThread().getName());
        String debug_level_str = String.format("[DEBUG-%s]", debug_number);
        if (isRangeValid(trace_list, trace)) {
            String output = String.join("", new String[]{
                    debug_level_str, (show_thread ? thread_information : ""), debug_location, " " + debug_info
            });
            if (print_switch == 0) {
                System.out.println(output);
            }
            logger(String.format("[DEBUG OUTPUT] %s", output));
        } else {
            logger(String.format("[DEBUG HIDDEN] Location : %s", debug_location));
        }
    }
}

