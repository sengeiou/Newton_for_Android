package com.leleliu008.newton.framework.net;

/**
 * 请求网络接口
 * 
 * @author 792793182@qq.com 2014-11-09
 *
 */
public interface IRequest<R extends RequestResult> {

	/**
	 * 请求网络接口
	 * @return 应答返回值
	 */
	R request();
}
