package com.leleliu008.newton.framework.upload;

import java.util.List;

import org.apache.http.entity.mime.FormBodyPart;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestPostMultipart;

/**
 * 请求上传数据
 * 
 * @author 792793182@qq.com 2014-11-18
 *
 */
final class RequestUploadMultipart extends RequestPostMultipart<UploadResult> {

	/** Multipart的part列表 */
	private List<FormBodyPart> formBodyParts;
	
	/** 认证信息 */
	private String authorization = "";
	
	public RequestUploadMultipart(List<FormBodyPart> formBodyParts) {
		this(formBodyParts, "");
	}
	
	public RequestUploadMultipart(List<FormBodyPart> formBodyParts, String authorization) {
		this.formBodyParts = formBodyParts;
		this.authorization = authorization;
	}
	
	@Override
	public UploadResult request() {
		try {
			return post(UrlConfig.getUploadFileUrl2, formBodyParts, authorization);
		} catch (Exception e) {
			DebugLog.e(getTag(), "request()", e);
			return new UploadResult();
		}
	}
}
