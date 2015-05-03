package com.leleliu008.newton.business.account;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 请求上传头像
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
final class RequestModifyAvatar extends RequestPostJson<RequestResult> {

	/** 头像数据，是经过base64编码的字符串 */
	private String avatar;

	/** 用户名 */
	private String account;

	public RequestModifyAvatar(String avatar, String account) {
		this.account = account;
		this.avatar = avatar;
	}

	@Override
	public RequestResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("avatar", avatar);
			jsonObject.put("account", account);
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
		}

		try {
			LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			return post(UrlConfig.getPostAvatarUrl, jsonObject, authorization);
		} catch (Exception e) {
			DebugLog.e(getTag(), "request()", e);
			return new RequestResult();
		}
	}
}
