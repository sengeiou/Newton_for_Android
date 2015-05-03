package com.leleliu008.newton.framework.net;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.protocol.HTTP;

import com.leleliu008.newton.base.DebugLog;

/**
 * 以POST方式向服务器传送表单数据
 * 
 * @author 792793182@qq.com 2014-11-09
 *
 */
public abstract class RequestPostMultipart<R extends RequestResult> extends Request<R> {
	
	/**
	 * 以POST方式请求资源
	 * @param url            资源地址
	 * @param formBodyParts  请求体中表单数据
	 * @param authorization  验证用户，HTTP请求头Authorization
	 * @param requestResult  请求结果
	 */
	protected final R post(String url, List<FormBodyPart> formBodyParts, String authorization) {
		String tag = getTag();
		
		DebugLog.d(tag, "post()");
		
		if (formBodyParts == null) {
			return ReflactUtil.newInstance(getClass(), tag);
		}
		
		HttpPost httpPost = new HttpPost(url);
		
		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName(HTTP.UTF_8));
		for (FormBodyPart formBodyPart : formBodyParts) {
			DebugLog.d(tag, formBodyPart.getHeader().toString());
			multipartEntity.addPart(formBodyPart);
		}
		
		httpPost.setEntity(multipartEntity);
		
		return request(httpPost, authorization);
	}
}
