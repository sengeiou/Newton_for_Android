package com.leleliu008.newton.business.account;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 请求获取验证码
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public final class RequestVerificationCode extends RequestPostJson<RequestResult> {

	/** 类型 - 用以区分这个验证码是用在哪儿 */
	private int type;
	
	/** 收取验证码的手机号 */
	private String phoneNumber;

	public RequestVerificationCode(int type, String phoneNumber) {
		this.type = type;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public RequestResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");

		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("type", type);
			jsonObject.put("recipient", phoneNumber);
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
		}

		return post(UrlConfig.registerGetVerificationUrl, jsonObject, null);
	}
}
