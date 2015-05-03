package com.leleliu008.newton.framework.upload;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 上传完成后返回的结果
 * 上传后返回的结果协议请参看：http://192.168.1.181:1107/Help/Api/POST-api-source-post
 * 
 * @author 792793182@qq.com 2014-9-22
 * 
 */
public class UploadResult extends RequestResult implements Parcelable {

	private static final String TAG = UploadResult.class.getSimpleName();
	
	private String fileSize;
	private String serverTime;
	private String content;

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
	public UploadResult parse(String jsonStr) {
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

	@Override
	public String toString() {
		return "UploadResult [fileSize=" + fileSize + ", serverTime="
				+ serverTime + ", content=" + content + ", uploadCount="
				+ uploadCount + super.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(requestCode);
		dest.writeString(isSuccessful ? "true" : "false");
		dest.writeInt(errorCode);
		dest.writeString(description);
		dest.writeInt(httpStatusCode);
		dest.writeString(httpRequest);
		dest.writeString(httpRespond);
		dest.writeSerializable(httpException);
		dest.writeString(fileSize);
		dest.writeString(serverTime);
		dest.writeString(content);
		dest.writeInt(uploadCount);
	}
	
	public static final Parcelable.Creator<UploadResult> CREATOR = new Creator<UploadResult>() {

		@Override
		public UploadResult createFromParcel(Parcel in) {
			UploadResult uploadResult = new UploadResult();
			
			uploadResult.requestCode = in.readInt();
			uploadResult.isSuccessful = Boolean.parseBoolean(in.readString());
			uploadResult.errorCode = in.readInt();
			uploadResult.description = in.readString();
			uploadResult.httpStatusCode = in.readInt();
			uploadResult.httpRequest = in.readString();
			uploadResult.httpRespond = in.readString();
			uploadResult.httpException = (Exception) in.readSerializable();
			
			uploadResult.fileSize = in.readString();
			uploadResult.serverTime = in.readString();
			uploadResult.content = in.readString();
			uploadResult.uploadCount = in.readInt();
			
			return uploadResult;
		}

		@Override
		public UploadResult[] newArray(int size) {
			return new UploadResult[size];
		}
	};
}
