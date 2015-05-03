package com.leleliu008.newton.framework.net;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;

/**
 * 以POST方式向服务器传送Json数据
 * 
 * @author 792793182@qq.com 2014-11-09
 *
 */
public abstract class RequestPostJson<R extends RequestResult> extends Request<R> {
	
	/**
	 * 以POST方式请求资源
	 * @param url            资源地址
	 * @param jsonObject     请求体中的JSON数据
	 * @param authorization  验证用户，不需要验证的，传入空，即可
	 */
	protected final R post(String url, JSONObject jsonObject, String authorization) {
		String tag = getTag();
		DebugLog.d(tag, "post()");
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		
		try {
			StringEntity stringEntity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			
			return request(httpPost, authorization);
		} catch (Exception e) {
			DebugLog.e(tag, "post()", e);
			return ReflactUtil.newInstance(getClass(), tag);
		}
	}
}
