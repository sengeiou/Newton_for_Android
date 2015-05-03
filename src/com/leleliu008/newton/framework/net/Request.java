package com.leleliu008.newton.framework.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.leleliu008.newton.base.DebugLog;

/**
 * 请求的公共实现
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public abstract class Request<R extends RequestResult> implements IRequest<R> {

	/**
	 * 请求
	 * @param httpRequest    8种请求方法的基类
	 * @param authorization  验证用户，不需要验证的，传入空，即可
	 */
	public final R request(HttpRequestBase httpRequest, String authorization) {
		String tag = getTag();
		DebugLog.d(tag, "get()");
		
		R requestResult = ReflactUtil.newInstance(getClass(), tag);
		
		try {
			httpRequest.addHeader("Accept", "*/*");
			httpRequest.addHeader("Connection", "Keep-Alive");
			httpRequest.addHeader("User-Agent", "Android");
			httpRequest.addHeader("Referer", "http://crowd.datatang.com");
			
			if (!TextUtils.isEmpty(authorization)) {
				httpRequest.addHeader("Authorization", authorization);
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams httpParams = httpclient.getParams();
			
			//设置连接超时
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			//设置读取超时
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			
			//获取请求行、请求头、请求体
			String requestInfo = HTTPUtil.getRequestInfo(httpRequest);
			DebugLog.d(tag, "\n" + requestInfo);
			requestResult.setHttpRequest(requestInfo);
			
			//执行请求操作
			HttpResponse response = httpclient.execute(httpRequest);
			
			//获取状态行
			requestResult.setHttpStatusCode(response.getStatusLine().getStatusCode());
			
			//将HTTP响应体转换成字符串，实际返回的是JSON格式的数据
			String jsonResult = EntityUtils.toString(response.getEntity());
			requestResult.setRawData(jsonResult);
			
			//获取状态行、响应头、响应体
			String responseInfo = HTTPUtil.getResponseInfo(response, jsonResult);
			DebugLog.d(tag, "\n" + responseInfo);
			requestResult.setHttpRespond(responseInfo);
			
			//解析JSON
			requestResult.parse(jsonResult);
		} catch (Exception e) {
			DebugLog.e(tag, "get()", e);
			requestResult.setHttpException(e);
		} finally {
			httpRequest.abort();
			DebugLog.i(tag, "get() requestResult = " + requestResult);
		}
		
		return requestResult;
	}
	
	/**
	 * 获取Log的标记
	 */
	protected final String getTag() {
		return getClass().getSimpleName();
	}
}
