package com.leleliu008.newton.business.account;

import java.util.Date;

import android.text.TextUtils;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestGet;
import com.leleliu008.newton.framework.net.RequestServerManager;

/**
 * 请求用户信息
 * 
 *  @author 792793182@qq.com 2014-11-09
 * 
 */
public final class RequestUserInfo extends RequestGet<UserInfo> {
	
	@Override
	public UserInfo request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");
		
		LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
		UserInfo userInfo = null;
		
		try {
			String authorization = UrlConfig.getAuthorization(loginResult.getAccessToken());
			userInfo = get(UrlConfig.getUserInfoUrl, authorization);
		} catch (Exception e) {
			DebugLog.e(tag, "request()", e);
			userInfo = new UserInfo();
		}
		
		if (userInfo.isSuccessful()) {
			String userName = loginResult.getUserName();
			if (!TextUtils.isEmpty(userName)) {
				userInfo.setUserName(userName);
			}
			
			String password = loginResult.getPassword();
			if (!TextUtils.isEmpty(password)) {
				userInfo.setPassword(password);
			}
			
			userInfo.setSignInTime(new Date().getTime());
			
			UserManager.getInstance().setUserInfo(userInfo);
			UserManager.getInstance().saveUserInfoInCache(userInfo);
			
			//请求配置
			RequestServerManager.syncRequest(new RequestConfiguration());
		}
		
		DebugLog.i(tag, "request() userInfo = " + userInfo);
		
		return userInfo;
	}
}
