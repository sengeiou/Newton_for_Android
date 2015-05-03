package com.leleliu008.newton.framework.net;

import org.json.JSONObject;

import android.text.TextUtils;

import com.leleliu008.newton.MyApp;
import com.leleliu008.newton.R;
import com.leleliu008.newton.base.DebugLog;

/**
 * 请求后返回的结果
 * 
 * @author 792793182@qq.com 2014-10-18
 * 
 */
public class RequestResult {

	private static final String TAG = RequestResult.class.getSimpleName();
	
	/** 请求码，便于与请求一一对应 */
	protected int requestCode;
	
	/** 是否成功 */
	protected boolean isSuccessful;

	/** 错误码 */
	protected int errorCode;

	/** 描述 */
	protected String description = "";
	
	/** HTTP的状态码 */
	protected int httpStatusCode;
	
	/** HTTP的异常，如果发生异常的话 */
	protected Exception httpException;
	
	/** HTTP请求信息 */
	protected String httpRequest;
	
	/** HTTP响应信息 */
	protected String httpRespond;
	
	/** HTTP响应体 */
	protected String rawData;
	
	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}
	
	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDiscription() {
		return description;
	}
	
	public void setDiscription(String discription) {
		this.description = discription;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public Exception getHttpException() {
		return httpException;
	}

	public void setHttpException(Exception httpException) {
		this.httpException = httpException;
	}
	
	public String getHttpRequest() {
		return httpRequest;
	}

	public String getHttpRespond() {
		return httpRespond;
	}

	public void setHttpRequest(String httpRequest) {
		this.httpRequest = httpRequest;
	}

	public void setHttpRespond(String httpRespond) {
		this.httpRespond = httpRespond;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public RequestResult parse(String jsonStr) {
		if (TextUtils.isEmpty(jsonStr)) {
			jsonStr = "{\"errorCode\":1,\"description\":\"未知错误!\",\"hasError\":'true'}";
		}
		
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			if (jsonObject.has("hasError")) {
				isSuccessful = !(jsonObject.getBoolean("hasError"));
			}
			
			if (jsonObject.has("errorCode")) {
				String errorCodeStr = jsonObject.getString("errorCode");
				try {
					errorCode = Integer.parseInt(errorCodeStr);
				} catch (Exception e) {
					DebugLog.e(TAG, "parse()");
				}
			}
			
			if (jsonObject.has("description")) {
				description = jsonObject.getString("description");
			} else if (jsonObject.has("error")) {
				description = jsonObject.getString("error");
			} else {
				if (!isSuccessful) {
					description = MyApp.getApp().getResources().getString(R.string.net_disconnected);
				}
			}
		} catch (Exception e) {
			DebugLog.e(TAG, "parse()", e);
		}
		
		return this;
	}

	@Override
	public String toString() {
		return "RequestResult [requestCode=" + requestCode + ", isSuccessful="
				+ isSuccessful + ", errorCode=" + errorCode + ", description="
				+ description + ", httpStatusCode=" + httpStatusCode
				+ ", httpException=" + httpException + ", httpRequest="
				+ "########" + ", httpRespond=" + "*******" + "]";
	}
	
	/**
	 * 获取HTTP请求过程，用于记录日志
	 */
	public final String getHttpProcess() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(httpRequest);
		stringBuilder.append('\n');
		if (httpException == null) {
			stringBuilder.append(httpRespond);
		} else {
			stringBuilder.append(DebugLog.getExceptionTrace(httpException));
		}
		
		stringBuilder.append('\n');
		stringBuilder.append("--------------------------------------------------------------");
		stringBuilder.append('\n');
		
		return stringBuilder.toString();
	}
}
