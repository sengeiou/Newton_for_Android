package com.leleliu008.newton.framework.net;

/**
 * 请求完成后的回调
 * 
 * @author 792793182@qq.com 2014-10-18
 *
 */
public interface RequestFinishCallback<R extends RequestResult> {

	void onFinish(R result);
}
