package com.leleliu008.newton.business.account;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostNameValuePair;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 请求注册
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public class RequestRegister extends RequestPostNameValuePair<RequestResult> {

	/** 用户名 */
	private String userName;
	
	/** 密码 */
	private String password;
	
	/** 邮箱 */
	private String email;

	public RequestRegister(String userName, String password, String email) {
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	@Override
	public RequestResult request() {
		DebugLog.d(getTag(), "request()");

		// 如果是Post提交可以将参数封装到集合中传递
		List<BasicNameValuePair> dataList = new ArrayList<BasicNameValuePair>();
		dataList.add(new BasicNameValuePair("personfrom", "mobile"));
		dataList.add(new BasicNameValuePair("password", password));
		dataList.add(new BasicNameValuePair("account", userName));
		dataList.add(new BasicNameValuePair("email", email));
		
		return post(UrlConfig.registerUrl, dataList);
	}
}
