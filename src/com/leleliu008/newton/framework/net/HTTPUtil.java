package com.leleliu008.newton.framework.net;

import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.leleliu008.newton.base.DebugLog;

/**
 * HTTP帮助类
 * 
 * @author 792793182@qq.com 2014-11-15
 *
 */
public final class HTTPUtil {

	private static final String TAG = HTTPUtil.class.getSimpleName();
	
	private HTTPUtil() {}
	
	/**
	 * 获取请求信息
	 * @param httpRequest  HTTP请求
	 * @return             HTTP协议内容
	 */
	public static String getRequestInfo(HttpRequest httpRequest) {
		StringBuilder builder = new StringBuilder();
		
		//请求行
		builder.append(httpRequest.getRequestLine()).append('\n');
		
		//请求头
		for (Header header : httpRequest.getAllHeaders()) {
			builder.append(header.getName()).append(':').append(header.getValue()).append('\n');
		}
		
		if (httpRequest instanceof HttpPost) {
			//空行
			builder.append('\n');
			
			//请求体
			HttpEntity httpEntity = ((HttpPost)httpRequest).getEntity();
			if (httpEntity instanceof MultipartEntity) {
				MultipartEntity multipartEntity = (MultipartEntity) httpEntity;
				builder.append(multipartEntity.toString());
			} else {
				try {
					builder.append(EntityUtils.toString(httpEntity));
				} catch (Exception e) {
					DebugLog.e(TAG, "getRequestInfo()", e);
				}
			}
			
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * 获取响应信息
	 * @param response 响应
	 * @return         HTTP协议内容
	 */
	public static String getResponseInfo(HttpResponse response, String entity) {
		StringBuilder builder = new StringBuilder();
		
		//状态行
		builder.append(response.getStatusLine()).append('\n');
		
		//响应头
		for (Header header : response.getAllHeaders()) {
			builder.append(header.getName()).append(':').append(header.getValue()).append('\n');
		}
		
		//空行
		builder.append('\n');
		
		//响应体
		builder.append(entity);
		
		builder.append('\n');
		
		return builder.toString();
	}
	
	/**
	 * 获取请求信息
	 * @param connection   HTTP连接
	 * @return             HTTP协议内容
	 */
	public static String getRequestInfo(URLConnection connection) {
		StringBuilder builder = new StringBuilder();
		
		//获取头信息，用于打印Log
		Map<String, List<String>> headers = connection.getRequestProperties();
		for (String key : headers.keySet()) {
			if (TextUtils.isEmpty(key)) {
				builder.append(headers.get(null).get(0)).append('\n');
			} else {
				List<String> values = headers.get(key);
				for (String value : values) {
					builder.append(key).append(':').append(value).append('\n');
				}
			}
		}
		builder.append('\n');
		return builder.toString();
	}
	
	/**
	 * 获取响应信息
	 * @param connection   HTTP连接
	 * @return             HTTP协议内容
	 */
	public static String getResponseInfo(URLConnection connection) {
		StringBuilder builder = new StringBuilder();
		
		//获取头信息，用于打印Log
		Map<String, List<String>> headers = connection.getHeaderFields();
		for (String key : headers.keySet()) {
			if (TextUtils.isEmpty(key)) {
				builder.append(headers.get(null).get(0)).append('\n');
			} else {
				List<String> values = headers.get(key);
				for (String value : values) {
					builder.append(key + ":" + value).append('\n');
				}
			}
		}
		builder.append('\n');
		return builder.toString();
	}
}
