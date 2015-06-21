package com.leleliu008.newton.business.account;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.HttpClientRequest;
import com.leleliu008.newton.framework.net.RequestCallback;

/**
 * 请求登录
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public final class RequestLogin {
	
	public static final void requestLogin(String userName, String password, RequestCallback<LoginResult> callback) {
		// 如果是Post提交可以将参数封装到集合中传递
		List<BasicNameValuePair> dataList = new ArrayList<BasicNameValuePair>();
		dataList.add(new BasicNameValuePair("grant_type", "password"));
		dataList.add(new BasicNameValuePair("password", password));
		dataList.add(new BasicNameValuePair("username", userName));
				
		HttpClientRequest.postForm(UrlConfig.loginUrl, null, dataList, LoginResult.class, callback);
	}
}
