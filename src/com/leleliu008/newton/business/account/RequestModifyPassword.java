package com.leleliu008.newton.business.account;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.HttpClientRequest;
import com.leleliu008.newton.framework.net.RequestCallback;

/**
 * 请求修改密码
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
final class RequestModifyPassword {

	private static final String TAG = RequestModifyPassword.class.getSimpleName();
	
	public static void requestModifyPassword(String oldPassword, String newPassword, RequestCallback<String> callback) {
		JSONObject jsonObject = new JSONObject();
		try {
			// 能进行修改密码，一定是登录成功了，所以必然有缓存的用户信息
			UserInfo userInfo = UserManager.getInstance().getUserInfo();
			jsonObject.put("account", userInfo.getUserName());
			jsonObject.put("oldPassword", oldPassword);
			jsonObject.put("newPassword", newPassword);
		} catch (Exception e) {
			DebugLog.e(TAG, "request()", e);
		}
		
		try {
			LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			HttpClientRequest.postJson(UrlConfig.changePasswordUrl, authorization, jsonObject.toString(), String.class, callback);
		} catch (Exception e) {
			DebugLog.e(TAG, "request()", e);
		}
	}
}
