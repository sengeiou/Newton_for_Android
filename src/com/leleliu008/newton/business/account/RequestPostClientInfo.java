package com.leleliu008.newton.business.account;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.leleliu008.newton.MyApp;
import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.business.config.Configuration;
import com.leleliu008.newton.business.config.UrlConfig;
import com.leleliu008.newton.framework.lbs.LBS;
import com.leleliu008.newton.framework.lbs.XAddress;
import com.leleliu008.newton.framework.net.RequestPostJson;
import com.leleliu008.newton.framework.net.RequestResult;
import com.leleliu008.newton.framework.util.Helper;
import com.leleliu008.newton.framework.util.TestSetting;

/**
 * 向服务器发送客户端收集的信息
 * 
 * @author 792793182@qq.com 2014-11-09
 * 
 */
public class RequestPostClientInfo extends RequestPostJson<RequestResult> {

	@Override
	public RequestResult request() {
		DebugLog.d(getTag(), "request()");
		
		return post(UrlConfig.PostTraceMessageUrl, collectClientInfo(), null);
	}

	/**
	 * 收集客户端信息
	 */
	private JSONObject collectClientInfo() {
		JSONObject jsonObject = new JSONObject();

		try {
			Environment environment = Environment.getInstance();
			jsonObject.put("productCode", environment.getApplicationName());
			jsonObject.put("productVersion", environment.getMyVersionName());
			jsonObject.put("channelNo", "" + Configuration.CHANNEL_ID);
			jsonObject.put("activeType", "" + TestSetting.TraceSettings.TraceMessageType);

			jsonObject.put("deviceType", environment.getPhoneModel());
			jsonObject.put("deviceSerial", environment.getIMEI());
			jsonObject.put("osVersion", environment.getOSVersionName());
			jsonObject.put("network", Helper.isConnectNetwork(MyApp.getApp()));

			jsonObject.put("activeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
			jsonObject.put("clientIP", environment.getIPAddress());
			jsonObject.put("remark", "");

			XAddress address = LBS.getInstance().getAddress();
			if (address != null) {
				String lat = address.getLatitude();
				String lon = address.getLongtitude();
				String gps = "Lat:" + lat + "Long:" + lon;
				jsonObject.put("clientGPS", gps);
			}
		} catch (Exception e) {
			DebugLog.e(getTag(), "collectClientInfo()", e);
		}

		return jsonObject;
	}
}
