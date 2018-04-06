package com.hansbug.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 日志管理器
 */
public class LogHelper {
    /**
     * 日志类型
     */
    private enum LogType {
        SYSTEM, USER
    }
    
    /**
     * 单条日志对象
     */
    private class Log {
        /**
         * 日志类型
         */
        private LogType type;
        
        /**
         * 日志信息
         */
        private String information;
        
        /**
         * 日志时间戳
         */
        private long timestamp;
        
        /**
         * 初始化日志（默认为用户日志）
         *
         * @param information 日志信息
         */
        Log(String information) {
            this(LogType.USER, information);
        }
        
        /**
         * 自定义类型初始化日志
         *
         * @param type        日志类型
         * @param information 日志信息
         */
        Log(LogType type, String information) {
            this.type = type;
            this.information = information;
            this.timestamp = System.currentTimeMillis();
        }
        
        /**
         * 获取时间戳
         *
         * @return 字符串时间戳
         */
        private String getFormatTimestamp() {
            Date d = new Date(this.timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.format(d);
        }
        
        /**
         * 导出为文本
         *
         * @return 输出文本
         */
        @Override
        public String toString() {
            return String.format("[%s,%s] %s", this.type.toString(), this.getFormatTimestamp(), this.information);
        }
    }
    
    /**
     * 相关默认值
     */
    private static final int DEFAULT_MAX_CACHE_LINES = 0;
    private static final int DEFAULT_SYNC_TIMESPAN = 100;
    
    /**
     * 文件名
     */
    private String filename;
    
    /**
     * 缓存区域
     */
    private ArrayList<Log> cache_lines;
    
    /**
     * 缓存区域容量
     */
    private int max_cache_lines = DEFAULT_MAX_CACHE_LINES;
    
    /**
     * 最后同步时间戳
     */
    private long last_sync;
    
    /**
     * 同步最大时间间隔（毫秒）
     */
    private long sync_timespan = DEFAULT_SYNC_TIMESPAN;
    
    
    /**
     * 根据文件名初始化对象
     *
     * @param filename 文件名
     */
    public LogHelper(String filename) {
        this(filename, DEFAULT_MAX_CACHE_LINES, DEFAULT_SYNC_TIMESPAN);
    }
    
    /**
     * 根据文件名，最大缓存行数和最大同步时间间隔初始化对象
     *
     * @param filename        文件名
     * @param max_cache_lines 最大缓存行数
     * @param sync_timespan   最大时间间隔
     */
    private LogHelper(String filename, int max_cache_lines, int sync_timespan) {
        this.filename = new File(filename).getAbsolutePath();
        this.max_cache_lines = max_cache_lines;
        this.last_sync = System.currentTimeMillis() - sync_timespan - 1;
        this.cache_lines = new ArrayList<>();
        this.sync_timespan = sync_timespan;
    }
    
    /**
     * 获取文件名
     *
     * @return 文件名（绝对路径名）
     */
    public String getFilename() {
        return this.filename;
    }
    
    /**
     * 写日志
     *
     * @param log 日志字符串
     */
    public void logger(String log) {
        this.cache_lines.add(new Log(log));
        try {
            if (dumpable()) dump();
        } catch (Exception e) {
            this.cache_lines.add(new Log(LogType.SYSTEM, String.format("[%s] %s", e.getClass().getName(), e.getMessage())));
        }
    }
    
    /**
     * 判断是否应该同步日志
     *
     * @return 是否应该同步日志
     */
    private boolean dumpable() {
        return (this.cache_lines.size() > this.max_cache_lines) || (this.sync_timespan + this.last_sync < System.currentTimeMillis());
    }
    
    /**
     * 导出缓存
     *
     * @throws IOException IO错误
     */
    private void dump() throws IOException {
        FileWriter fw = null;
        IOException exception = null;
        try {
            fw = new FileWriter(new File(this.filename), true);
            this.dumpCache(fw);
        } catch (IOException e) {
            exception = e;
        } finally {
            if (fw != null) fw.close();
            this.last_sync = System.currentTimeMillis();
            if (exception != null) throw exception;
        }
    }
    
    /**
     * 导出缓存行（原子）
     *
     * @param fw filewriter
     * @throws IOException IO错误
     */
    private synchronized void dumpCache(FileWriter fw) throws IOException {
        while (this.cache_lines.size() > 0) {
            Log log = this.cache_lines.get(0);
            String line = log.toString();
            fw.write(line + System.getProperty("line.separator"));
            this.cache_lines.remove(0);
        }
    }
}
