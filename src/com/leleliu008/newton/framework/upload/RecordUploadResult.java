package com.leleliu008.newton.framework.upload;

import org.json.JSONObject;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 上传完成后返回的结果
 * 
 * @author wangzhichao@datatang.com 2014-9-22
 * 
 */
public class RecordUploadResult extends RequestResult {
	
	private static final String TAG = RecordUploadResult.class.getSimpleName();
	
	private String fileSize;
	private String serverTime;
	private String content;
	private String fileName;

	private int uploadCount;
	
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(int uploadCount) {
		this.uploadCount = uploadCount;
	}
	
	
	
	@Override
	public RecordUploadResult parse(String jsonStr) {
		super.parse(jsonStr);
		
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			fileSize = jsonObject.getString("FileSize");
			serverTime = jsonObject.getString("ServerTime");
			content = jsonObject.getString("Content");
			if (jsonObject.has("UploadCount")) {
				uploadCount = jsonObject.getInt("UploadCount");
			}
		} catch (Exception e) {
			DebugLog.e(TAG, "parse()", e);
		}
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "RecordUploadResult [fileSize=" + fileSize + ", serverTime="
				+ serverTime + ", content=" + content + ", fileName="
				+ fileName + ", uploadCount=" + uploadCount + "]";
	}

	
	
}
