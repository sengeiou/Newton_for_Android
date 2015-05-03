package com.leleliu008.newton.business.account;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 请求绑定手机号
 * 
 * @author 792793182@qq.com 2014-11-09
 *
 */
final class RequestBindPhoneNumber extends RequestPostJson<RequestResult> {
	
	/** 要绑定的手机号 */
	private String phoneNumber;
	
	/** 用户名 */
	private String userName;
	
	public RequestBindPhoneNumber(String phoneNumber, String userName) {
		this.phoneNumber = phoneNumber;
		this.userName = userName;
	}
	
	@Override
	public RequestResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("account", userName);
			jsonObject.put("phoneNumber", phoneNumber);
			DebugLog.d(getTag(), jsonObject.toString());
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
		}
		
		try {
			LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			return post(UrlConfig.getBindPhoneUrl, jsonObject, authorization);
		} catch (Exception e) {
			DebugLog.e(getTag(), "request()", e);
			
			return new RequestResult();
		}
	}
}
