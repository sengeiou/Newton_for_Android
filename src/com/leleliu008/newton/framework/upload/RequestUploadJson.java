package com.leleliu008.newton.framework.upload;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.UserManager;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostJson;

/**
 * 请求上传数据
 * 
 * @author 792793182@qq.com 2014-11-18
 *
 */
final class RequestUploadJson extends RequestPostJson<UploadResult> {

	private JSONObject jsonObject;
	
	public RequestUploadJson(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	@Override
	public UploadResult request() {
		try {
			LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			return post(UrlConfig.getUploadFileUrl, jsonObject, authorization);
		} catch (Exception e) {
			DebugLog.e(getTag(), "request()", e);
			return new UploadResult();
		}
	}
}
