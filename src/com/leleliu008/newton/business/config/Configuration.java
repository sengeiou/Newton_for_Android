package com.leleliu008.newton.business.config;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 
 * 服务端返回的配置
 * 
 * @author 792793182@qq.com 2014-10-29
 *
 */
public final class Configuration extends RequestResult {

	private static final String TAG = Configuration.class.getSimpleName();
	
	/** 渠道号，打渠道包时需要替换此值 */
	public static final String CHANNEL_ID = "100000";
	
	public static final String DB_NAME = "dbv1";
	
	private int withdrawalQuota = 5;

	private String azure = "";
	
	public int getWithdrawalQuota() {
		return withdrawalQuota;
	}

	public void setWithdrawalQuota(int withdrawalQuota) {
		this.withdrawalQuota = withdrawalQuota;
	}
	
	public String getAzureConf() {
		return azure;
	}

	@Override
	public Configuration parse(String jsonStr) {
		super.parse(jsonStr);
		
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			withdrawalQuota = jsonObject.getInt("withdrawalQuota");
			azure = jsonObject.getString("azure_key");
			setIsSuccessful(true);
			description = "";
		} catch (Exception e) {
			DebugLog.e(TAG, "parse()", e);
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return "Configuration [withdrawalQuota=" + withdrawalQuota
				 + super.toString();
	}
}
