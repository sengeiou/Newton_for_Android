package com.leleliu008.newton.business.account.login;


import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.leleliu008.newton.MyApp;
import com.leleliu008.newton.R;
import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.base.ThreadPoolManager;
import com.leleliu008.newton.framework.net.RequestGet;
import com.leleliu008.newton.framework.net.RequestResult;
import com.leleliu008.newton.framework.ui.toast.CustomToast;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * 微信登录
 * 
 * @author 792793182@qq.com 2015-03-03
 *
 */
final class WeixinLogin implements ILogin {

	private static final String TAG = WeixinLogin.class.getSimpleName();
	
	private LoginResult loginResult = new LoginResult();
	
	private boolean isLogined;
	
	private LoginCallback callback;
	
	@Override
	public void login(final Activity activity, Bundle pararms, final LoginCallback callback) {
		DebugLog.d(TAG, "login()");
		
		this.callback = callback;
		
		IWXAPI wxapi = MyApp.getApp().getWXAPI();
		if (!wxapi.isWXAppInstalled()) {
            //提醒用户没有安装微信
			String uninstalled = activity.getResources().getString(R.string.WeixinLogin_uninstalled);
			CustomToast.makeText(activity,uninstalled, 2000).show();
            return;
        }
		
		 SendAuth.Req req = new SendAuth.Req();
	     req.scope = "snsapi_userinfo";
	     req.state = "wechat_sdk_demo";
	     wxapi.sendReq(req);
	}

	@Override
	public void logout(Context context) {
		DebugLog.d(TAG, "logout()");
		
		isLogined = false;
	}

	@Override
	public boolean isLogin() {
		return isLogined;
	}
	
	@Override
	public LoginResult getLoginResult() {
		return loginResult;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		DebugLog.d(TAG, "onActivityResult()");
		
		final SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
		
		//用户同意
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
        	ThreadPoolManager.EXECUTOR.execute(new Runnable() {
				
				@Override
				public void run() {
					//获取accessToken
		        	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + MyApp.getApp().b() + "&secret=" + MyApp.getApp().c() + "&code=" + resp.code + "&grant_type=authorization_code";
		        	RequestAccessTokenResult requestAccessTokenResult = new RequestAccessToken(url).request();
		        	DebugLog.d(TAG, "requestAccessTokenResult = " + requestAccessTokenResult);
		        	
		        	if (requestAccessTokenResult.isSuccessful()) {
		        		//获取用户信息
			        	url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + requestAccessTokenResult.access_token + "&openid=" + requestAccessTokenResult.openid;
			        	WeiXinUserInfo weiXinUserInfo = new RequestWeiXinUserInfo(url).request();
			        	DebugLog.d(TAG, "weiXinUserInfo = " + weiXinUserInfo);
			        	
			        	if (weiXinUserInfo.isSuccessful()) {
			        		LoginResult loginResult = new RequestThirdPartLogin(3, weiXinUserInfo.openid, requestAccessTokenResult.access_token, weiXinUserInfo.sex == 1 ? "男" : "女", weiXinUserInfo.nickname, weiXinUserInfo.headimgurl).request();
			        		if (loginResult.isSuccessful()) {
								isLogined = true;
								WeixinLogin.this.loginResult = loginResult;
								
								if (callback != null) {
									loginResult.setLoginType(LoginType.WeiXin);
									callback.onLoginSuccess(loginResult);
								}
							} else {
								if (callback != null) {
									loginResult.setIsSuccessful(false);
									loginResult.setErrorCode(LoginErrorCode.LOGIN_FAIL);
									loginResult.setDiscription("");
									loginResult.setLoginType(LoginType.WeiXin);
									callback.onLoginFail(loginResult);
								}
							}
			        	} else {
			        		if (callback != null) {
								loginResult.setIsSuccessful(false);
								loginResult.setErrorCode(LoginErrorCode.LOGIN_FAIL);
								loginResult.setDiscription("");
								loginResult.setLoginType(LoginType.WeiXin);
								callback.onLoginFail(loginResult);
							}
						}
		        	} else {
		        		if (callback != null) {
							loginResult.setIsSuccessful(false);
							loginResult.setErrorCode(LoginErrorCode.LOGIN_FAIL);
							loginResult.setDiscription("");
							loginResult.setLoginType(LoginType.WeiXin);
							callback.onLoginFail(loginResult);
						}
					}
				}
			});
        }
	}
	
	private static final class RequestAccessToken extends RequestGet<RequestAccessTokenResult> {

		private String url;
		
		private RequestAccessToken(String url) {
			this.url = url;
		}
		
		@Override
		public RequestAccessTokenResult request() {
			return get(url, "");
		}
	}
	
	/**
	 * 获取accessToken
	 *
	 */
	private static final class RequestAccessTokenResult extends RequestResult {
		
		private String access_token = "";
		private long expires_in;
		private String refresh_token = "";
		private String openid = "";
		private String scope = "";
		private String unionid = "";
		
		@Override
		public RequestAccessTokenResult parse(String jsonStr) {
			super.parse(jsonStr);
			
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				if (jsonObject.has("access_token")) {
					access_token = jsonObject.getString("access_token");
					setIsSuccessful(true);
				}
				if (jsonObject.has("expires_in")) {
					expires_in = jsonObject.getLong("expires_in");
				}
				if (jsonObject.has("refresh_token")) {
					refresh_token = jsonObject.getString("refresh_token");
				}
				if (jsonObject.has("openid")) {
					openid = jsonObject.getString("openid");
				}
				if (jsonObject.has("scope")) {
					scope = jsonObject.getString("scope");
				}
				if (jsonObject.has("unionid")) {
					unionid = jsonObject.getString("unionid");
				}
			} catch (Exception e) {
				DebugLog.e(TAG, "parse()", e);
			}
			
			return this;
		}

		@Override
		public String toString() {
			return "RequestAccessTokenResult [access_token=" + access_token
					+ ", expires_in=" + expires_in + ", refresh_token="
					+ refresh_token + ", openid=" + openid + ", scope=" + scope
					+ ", unionid=" + unionid + ", " + super.toString();
		}
	}
	
	private static final class RequestWeiXinUserInfo extends RequestGet<WeiXinUserInfo> {

		private String url;
		
		private RequestWeiXinUserInfo(String url) {
			this.url = url;
		}
		
		@Override
		public WeiXinUserInfo request() {
			return get(url, "");
		}
	}
	
	private static final class WeiXinUserInfo extends RequestResult {

		//普通用户的标识，对当前开发者帐号唯一
		private String openid;
		
		//普通用户昵称
		private String nickname;
		
		//普通用户性别，1为男性，2为女性
		private int sex;
		
		//普通用户个人资料填写的省份
		private String province;
		
		//普通用户个人资料填写的城市
		private String city;
		
		//国家，如中国为CN
		private String country;
		
		//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
		private String headimgurl;
		
		//用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
		private String privilege;
		
		//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
		private String unionid;
		
		@Override
		public WeiXinUserInfo parse(String jsonStr) {
			super.parse(jsonStr);
			
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				if (jsonObject.has("openid")) {
					openid = jsonObject.getString("openid");
					setIsSuccessful(true);
				}
				if (jsonObject.has("nickname")) {
					nickname = jsonObject.getString("nickname");
				}
				if (jsonObject.has("sex")) {
					sex = jsonObject.getInt("sex");
				}
				if (jsonObject.has("province")) {
					province = jsonObject.getString("province");
				}
				if (jsonObject.has("city")) {
					city = jsonObject.getString("city");
				}
				if (jsonObject.has("country")) {
					country = jsonObject.getString("country");
				}
				if (jsonObject.has("headimgurl")) {
					headimgurl = jsonObject.getString("headimgurl");
				}
				if (jsonObject.has("privilege")) {
					privilege = jsonObject.getString("privilege");
				}
				if (jsonObject.has("unionid")) {
					unionid = jsonObject.getString("unionid");
				}
			} catch (Exception e) {
				DebugLog.e(TAG, "parse()", e);
			}
			
			return this;
		}

		@Override
		public String toString() {
			return "WeiXinUserInfo [openid=" + openid + ", nickname="
					+ nickname + ", sex=" + sex + ", province=" + province
					+ ", city=" + city + ", country=" + country
					+ ", headimgurl=" + headimgurl + ", privilege=" + privilege
					+ ", unionid=" + unionid + ", " + super.toString();
		}
	}
}