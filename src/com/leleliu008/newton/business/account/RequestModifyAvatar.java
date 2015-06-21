package com.leleliu008.newton.business.account;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.HttpClientRequest;
import com.leleliu008.newton.framework.net.RequestCallback;

/**
 * 请求上传头像
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
final class RequestModifyAvatar {
	
	private static final String TAG = RequestModifyAvatar.class.getSimpleName();

	public static final void requestModifyAvatar(String avatar, String account, RequestCallback<String> callback) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("avatar", avatar);
			jsonObject.put("account", account);
		} catch (Exception e) {
			DebugLog.e(TAG, "requestModifyAvatar()", e);
		}

		try {
			LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			
			HttpClientRequest.postJson(UrlConfig.getPostAvatarUrl, authorization, jsonObject.toString(), String.class, callback);
		} catch (Exception e) {
			DebugLog.e(TAG, "request()", e);
		}
	}
}
