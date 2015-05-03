package com.leleliu008.newton.business.account.login;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;

/**
 * 请求第三方登录
 * 
 * @author 792793182@qq.com 2015-03-09
 * 
 */
final class RequestThirdPartLogin extends RequestPostJson<LoginResult> {

	private int type;
	
	private String openId = "";
	
	private String accessToken = "";
	
	private String gender = "";
	
	private String nickname = "";
	
	/** 头像URL */
	private String avatar = "";

	public RequestThirdPartLogin(int type, String openId, String accessToken, String gender, String nickname, String avatar) {
		this.type = type;
		this.openId = openId;
		this.accessToken = accessToken;
		this.gender = gender;
		this.nickname = nickname;
		this.avatar = avatar;
	}

	@Override
	public LoginResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("type", type);
			jsonObject.put("openId", openId);
			jsonObject.put("accessToken", accessToken);
			jsonObject.put("gender", gender);
			jsonObject.put("nickname", nickname);
			jsonObject.put("avatar", avatar);
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
		}

		try {
			return post(UrlConfig.thirdPartLoginUrl, jsonObject, "");
		} catch (Exception e) {
			DebugLog.e(getTag(), "request()", e);
			return new LoginResult();
		}
	}
}
