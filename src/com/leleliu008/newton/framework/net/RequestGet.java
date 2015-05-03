package com.leleliu008.newton.framework.net;

import org.apache.http.client.methods.HttpGet;

/**
 * GET请求
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public abstract class RequestGet<R extends RequestResult> extends Request<R> {

	/**
	 * GET请求
	 * @param url            要请求资源的URL
	 * @param authorization  验证用户，不需要验证的，传入空，即可
	 */
	public final R get(String url, String authorization) {
		return request(new HttpGet(url), authorization);
	}
}
