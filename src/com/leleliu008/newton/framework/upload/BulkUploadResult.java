package com.leleliu008.newton.framework.upload;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.leleliu008.newton.framework.net.RequestResult;

/**
 * 批量上传的返回结果
 * 
 * @author 792793182@qq.com 2014-11-06
 * 
 */
public class BulkUploadResult extends RequestResult implements Parcelable {
	
	private List<UploadResult> uploadResults;

	public List<UploadResult> getUploadResults() {
		return uploadResults;
	}

	public void setUploadResults(List<UploadResult> uploadResults) {
		this.uploadResults = uploadResults;
	}

	@Override
	public BulkUploadResult parse(String jsonStr) {
		super.parse(jsonStr);
		return this;
	}
	
	@Override
	public String toString() {
		return "BulkUploadResult [uploadResults=" + uploadResults + super.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(uploadResults);
	}
	
	public static final Parcelable.Creator<BulkUploadResult> CREATOR = new Creator<BulkUploadResult>() {

		@Override
		public BulkUploadResult createFromParcel(Parcel in) {
			BulkUploadResult bulkUploadResult = new BulkUploadResult();
			bulkUploadResult.uploadResults = in.readArrayList(UploadResult.class.getClassLoader());
			
			return bulkUploadResult;
		}

		@Override
		public BulkUploadResult[] newArray(int size) {
			return new BulkUploadResult[size];
		}
	};
}
