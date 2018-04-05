package com.hansbug.models;

import java.util.HashMap;

/**
 * 带默认值的魔改版HashMap（没办法ruby用惯了）
 *
 * @param <K> Key类型
 * @param <V> Value类型
 */
public class HashDefaultMap<K, V> extends HashMap<K, V> {
	/**
	 * 是否启用默认值（不启用时完全等价于HashMap）
	 */
	private boolean enable_default = true;
	
	/**
	 * 默认值
	 */
	private V default_value = null;
	
	/**
	 * 基本构造函数
	 *
	 * @param default_value  默认值
	 * @param enable_default 是否启用默认值
	 */
	public HashDefaultMap(V default_value, boolean enable_default) {
		this.default_value = default_value;
		this.enable_default = enable_default;
	}
	
	/**
	 * 基本构造函数
	 *
	 * @param default_value 默认值
	 */
	public HashDefaultMap(V default_value) {
		this(default_value, true);
	}
	
	/**
	 * 基本构造函数（默认值为null）
	 */
	public HashDefaultMap() {
		this(null);
	}
	
	/**
	 * 重载的get函数（当enable_default时，若key不存在则取默认值，否则按照HashMap的同等行为）
	 *
	 * @param key 需要取的key
	 * @return 取出来的值
	 */
	@Override
	public V get(Object key) {
		if (!super.containsKey(key) && this.enable_default) {
			return this.default_value;
		} else {
			return super.get(key);
		}
	}
	
	/**
	 * 获取是否启用默认值
	 *
	 * @return 是否启用默认值
	 */
	public boolean getEnableDefault() {
		return this.enable_default;
	}
	
	/**
	 * 设置是否启用默认值
	 *
	 * @param enable_default 是否启用默认值
	 */
	public void setEnableDefault(boolean enable_default) {
		this.enable_default = enable_default;
	}
	
	/**
	 * 获取默认值
	 *
	 * @return 默认值
	 */
	public Object getDefaultValue() {
		return this.default_value;
	}
	
	/**
	 * 设置默认值
	 *
	 * @param default_value 默认值
	 */
	public void setDefaultValue(V default_value) {
		this.default_value = default_value;
	}
}
