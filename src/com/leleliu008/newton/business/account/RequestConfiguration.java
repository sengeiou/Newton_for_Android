package com.leleliu008.newton.business.account;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.business.config.Configuration;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.net.RequestGet;

/**
 * 请求配置信息
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public class RequestConfiguration extends RequestGet<Configuration> {

	@Override
	public Configuration request() {
		DebugLog.d(getTag(), "request()");

		Configuration configuration = get(UrlConfig.GET_CONFIGURATION, null);

		if (configuration.isSuccessful()) {
			Environment.getInstance().setConfiguration(configuration);
		}

		return configuration;
	}
}
