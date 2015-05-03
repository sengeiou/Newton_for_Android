package com.leleliu008.newton.framework.net;

import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.leleliu008.newton.base.DebugLog;

/**
 * 以POST方式向服务器传送键值对
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public abstract class RequestPostNameValuePair<R extends RequestResult> extends Request<R> {

	/**
	 * 以POST方式请求资源
	 * @param url            资源地址
	 * @param dataList       请求体中的键值对数据
	 * @param requestResult  请求结果
	 */
	protected final R post(String url, List<BasicNameValuePair> dataList) {
		String tag = getTag();
		
		DebugLog.d(tag, "post()");
		
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		try {
			// UrlEncodedFormEntity用于将集合转换为Entity对象
			httpPost.setEntity(new UrlEncodedFormEntity(dataList));
			return request(httpPost, "Basic QkJDQkFGODQ2MTdFNDkxRTk3OTdGQTJDQzEwQjMxQ0Y6REFUQVRBTkctQ0xJRU5UMDE=");
		} catch (Exception e) {
			DebugLog.e(tag, "post()", e);
			return ReflactUtil.newInstance(getClass(), tag);
		}
	}
}
