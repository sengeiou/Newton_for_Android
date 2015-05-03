package com.leleliu008.newton.business.update;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestGet;

/**
 * 请求服务器上的版本号
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public final class RequestVersionInfo extends RequestGet<UpdateResult> {

	@Override
	public UpdateResult request() {
		DebugLog.d(getTag(), "request()");
		
		return get(UrlConfig.UPDATE_VERSION, null);
	}
}
