package com.leleliu008.newton.business.account;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 请求修改密码
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
final class RequestModifyPassword extends RequestPostJson<RequestResult> {

	/** 旧密码 */
	private String oldPassword;
	
	/** 新密码 */
	private String newPassword;

	public RequestModifyPassword(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	@Override
	public RequestResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");

		JSONObject jsonObject = new JSONObject();
		try {
			// 能进行修改密码，一定是登录成功了，所以必然有缓存的用户信息
			UserInfo userInfo = UserManager.getInstance().getUserInfo();
			jsonObject.put("account", userInfo.getUserName());
			jsonObject.put("oldPassword", oldPassword);
			jsonObject.put("newPassword", newPassword);
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
		}
		
		try {
			LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			return post(UrlConfig.changePasswordUrl, jsonObject, authorization);
		} catch (Exception e) {
			DebugLog.e(getTag(), "request()", e);
			return new RequestResult();
		}
	}
}
