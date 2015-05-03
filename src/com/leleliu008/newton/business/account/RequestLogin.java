package com.leleliu008.newton.business.account;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostNameValuePair;

/**
 * 请求登录
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public class RequestLogin extends RequestPostNameValuePair<LoginResult> {
	
	/** 用户名 */
	private String userName;
	
	/** 密码 */
	private String password;

	public RequestLogin(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public LoginResult request() {
		String tag = getTag();
		
		DebugLog.d(tag, "request()");

		// 如果是Post提交可以将参数封装到集合中传递
		List<BasicNameValuePair> dataList = new ArrayList<BasicNameValuePair>();
		dataList.add(new BasicNameValuePair("grant_type", "password"));
		dataList.add(new BasicNameValuePair("password", password));
		dataList.add(new BasicNameValuePair("username", userName));

		LoginResult loginResult = post(UrlConfig.loginUrl, dataList);
		
		loginResult.setUserName(userName);
		loginResult.setPassword(password);

		DebugLog.d(tag, "request() LoginResult = " + loginResult);
		
		return loginResult;
	}
}
