package com.leleliu008.newton.business.account;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.account.login.LoginResult;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostNameValuePair;

/**
 * 请求更新Token
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
final class RequestRefreshToken extends RequestPostNameValuePair<LoginResult> {

	private static String client_id = "BBCBAF84617E491E9797FA2CC10B31CF";
	private static String client_secret = "DATATANG-CLIENT01";

	@Override
	public LoginResult request() {
		DebugLog.d(getTag(), "request()");

		LoginResult loginResult = UserManager.getInstance().getLogin().getLoginResult();
		String userddirective = loginResult.getRefreshToken();

		// 如果是Post提交可以将参数封装到集合中传递
		List<BasicNameValuePair> dataList = new ArrayList<BasicNameValuePair>();
		dataList.add(new BasicNameValuePair("grant_type", "refresh_token"));
		dataList.add(new BasicNameValuePair("refresh_token", userddirective));
		dataList.add(new BasicNameValuePair("client_id", client_id));
		dataList.add(new BasicNameValuePair("client_secret", client_secret));

		return post(UrlConfig.loginUrl, dataList);
	}
}
