package com.leleliu008.newton.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.leleliu008.newton.base.DebugLog;

/**
 * 解析ini格式的内容
 * 
 * @author 792793182@qq.com 2014-9-22
 * 
 */
public final class IniParser {

	private static final String TAG = IniParser.class.getSimpleName();
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 2;

	/** 注释标记 */
	private static final String TAG_COMMENT = "#";

	/** 属性开始标记 */
	private static final String TAG_PROPERTY_START = "[";

	/** 属性结束标记 */
	private static final String TAG_PROPERTY_END = "]";

	/** 分隔标记 */
	private static final char TAG_SEPARATE = '=';

	private IniParser() { }

	public static Map<String, Properties> read(String iniFilePath) {
		try {
			InputStream is = new FileInputStream(iniFilePath);
			return parse(is);
		} catch (Exception e) {
			DebugLog.e(TAG, "", e);
		}
		return null;
	}
	
	public static Map<String, Properties> read(File iniFile) {
		try {
			InputStream is = new FileInputStream(iniFile);
			return parse(is);
		} catch (Exception e) {
			DebugLog.e(TAG, "", e);
		}
		return null;
	}
	
	public static Map<String, Properties> parse(InputStream is) {
		if (is == null) {
			return null;
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "utf-8"), DEFAULT_BUFFER_SIZE);
			
			Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
			Properties properties = null;
			
			String key = null;
			String value = null;
			
			String str = null;
			while ((str = br.readLine()) != null) {
				str = str.trim();
				if (str.startsWith(TAG_COMMENT)) {
					/* 如果是注释行，则读下一行 */
					continue;
				} else if (str.startsWith(TAG_PROPERTY_START)
						&& str.endsWith(TAG_PROPERTY_END)) {
					/* 如果是新的属性，则创建对象并加入列表 */
					key = str.substring(1, str.length() - 1);
					if (key.length() > 0) {
						properties = new Properties();
						propertiesMap.put(key, properties);
					}
				} else {
					/* 读取每个键值对 */
					int index = str.indexOf(TAG_SEPARATE);
					if (index > 0) {
						key = str.substring(0, index).trim();
						value = str.substring(index + 1).trim();
						if (key.length() > 0 && properties != null) {
							properties.put(key, value);
						}
					}
				}
			}
			
			return propertiesMap;
		} catch (IOException e) {
			DebugLog.e(TAG, "", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					DebugLog.e(TAG, "", e);
				}
			}
		}
		
		return null;
	}
}
