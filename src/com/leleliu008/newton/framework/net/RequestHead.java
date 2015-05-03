package com.leleliu008.newton.framework.net;

import org.apache.http.client.methods.HttpHead;


/**
 * HEAD请求
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public abstract class RequestHead<R extends RequestResult> extends Request<R> {

	/**
	 * HEAD请求
	 * @param url            要请求资源的URL
	 * @param authorization  验证用户，不需要验证的，传入空，即可
	 */
	public final R get(String url, String authorization) {
		return request(new HttpHead(url), authorization);
	}
}
