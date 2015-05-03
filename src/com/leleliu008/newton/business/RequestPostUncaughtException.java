package com.leleliu008.newton.business;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 请求上传奔溃日志
 * 
 * @author 792793182@qq.com 2014-11-18
 *
 */
final class RequestPostUncaughtException extends RequestPostJson<RequestResult> {
	
	/** 手机设备号 */
	private String imei;
	
	/** 一条奔溃日志 */
	private String exception;
	
	public RequestPostUncaughtException(String imei, String exception) {
		this.imei = imei;
		this.exception = exception;
	}
	
	@Override
	public RequestResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("DeviceNumber", imei);
			jsonObject.put("Error", exception);
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
		}
		
		return post(UrlConfig.POST_UNCAUGHT_EXCEPTION, jsonObject, null);
	}
}
