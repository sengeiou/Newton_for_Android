package com.leleliu008.newton.business.update;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 版本更新
 * 
 * @author 792793182@qq.com 2014-9-22
 * 
 */
public class UpdateResult extends RequestResult {

	private static final String TAG = UpdateResult.class.getSimpleName();
	
	private int versionCodeOnServer;

	public int getVersionCodeOnServer() {
		return versionCodeOnServer;
	}

	@Override
	public String toString() {
		return "UpdateResult [versionCodeOnServer=" + versionCodeOnServer + super.toString();
	}
	
	@Override
	public UpdateResult parse(String jsonStr) {
		super.parse(jsonStr);
		
		try {
			JSONObject json = new JSONObject(jsonStr);
			versionCodeOnServer = json.getInt("versioncode");
			
			if (versionCodeOnServer > 0) {
				setIsSuccessful(true);
				description = "";
			}
		} catch (Exception e) {
			DebugLog.e(TAG, "parse()", e);
		}
		
		return this;
	}
}
