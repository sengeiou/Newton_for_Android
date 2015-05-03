package com.leleliu008.newton.framework.net;

import java.util.concurrent.ExecutorService;

import com.leleliu008.newton.base.ThreadPoolManager;
import com.leleliu008.newton.business.UploadUncaughtException;

/**
 * 请求服务端的管理类
 * 
 * @author 792793182@qq.com 2014-11-02
 * 
 */
public final class RequestServerManager {
	
	private RequestServerManager() { }
	
	/**
	 * 同步请求网络接口
	 * @param request   请求接口
	 */
	public static <R extends RequestResult> R syncRequest(final IRequest<R> request) {
		if (request == null) {
			return null;
		}
		
		R result = null;
		//失败后重试，重试次数不超过5次
		int retryCount = 0;
		do {
			result = request.request();
		} while (!result.isSuccessful() && result.getHttpStatusCode() == 200 && retryCount++ <= 5);
		
		//如果HTTP状态码不是200，或者有异常抛出，就保存异常。
		if (result.getHttpStatusCode() != 200 || result.getHttpException() != null) {
			UploadUncaughtException.saveHttpException(result.getHttpProcess());
		}
		
		return result;
	}
	
	/**
	 * 异步请求网络接口
	 * @param requestCode  请求码，便于一一对应
	 * @param request      请求接口
	 * @param callback     完成后的回调
	 */
	public static <R extends RequestResult> void asyncRequest(final int requestCode,
			                                                  final IRequest<R> request,
			                                                  final RequestFinishCallback<R> callback) {
		asyncRequest(ThreadPoolManager.EXECUTOR, requestCode, request, callback);
	}
	
	/**
	 * 异步请求网络接口
	 * @param requestCode  请求码，便于一一对应
	 * @param request      请求接口
	 * @param callback     完成后的回调
	 */
	public static <R extends RequestResult> void asyncRequest(ExecutorService executor, final int requestCode,
			                                                  final IRequest<R> request,
			                                                  final RequestFinishCallback<R> callback) {
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				R result = syncRequest(request);
				result.setRequestCode(requestCode);
				
				if (callback != null) {
					callback.onFinish(result);
				}
			}
		});
	}
}
