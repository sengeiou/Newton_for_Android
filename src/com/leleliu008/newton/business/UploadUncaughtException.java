package com.leleliu008.newton.business;

import java.io.File;

import android.text.TextUtils;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.framework.net.RequestFinishCallback;
import com.leleliu008.newton.framework.net.RequestResult;
import com.leleliu008.newton.framework.net.RequestServerManager;
import com.leleliu008.newton.framework.util.IOUtil;

public final class UploadUncaughtException {

	/**
	 * 上传奔溃日志和网络异常日志
	 */
	public static void uploadUncaughtException() {
		//如果是有网络，并且是wifi，才上传
		Environment environment = Environment.getInstance();
		if (environment.isNetworkAvailable() && environment.isWifi()) {
			String imei = environment.getIMEI();
			
			final File uncaughtExceptionFile = DebugLog.getUncaughtExceptionFile();
			final File httpExceptionFile = DebugLog.getHttpExceptionFile();
			
			boolean uncaughtExceptionFileExist = uncaughtExceptionFile.exists();
			boolean httpExceptionFileExist = httpExceptionFile.exists();
			
			if (uncaughtExceptionFileExist || httpExceptionFileExist) {
				String uncaughtException = "";
				if (uncaughtExceptionFileExist) {
					uncaughtException = IOUtil.readTextFile(uncaughtExceptionFile);
				}
				
				String httpException = "";
				if (httpExceptionFileExist) {
					httpException = IOUtil.readTextFile(httpExceptionFile);
				}
				
				if (TextUtils.isEmpty(uncaughtException) && TextUtils.isEmpty(httpException)) {
					return;
				}
				
				RequestServerManager.asyncRequest(0, new RequestPostUncaughtException(imei, uncaughtException + httpException), 
						                             new UploadFinishCallback(uncaughtExceptionFile, httpExceptionFile, uncaughtException, httpException));
			}
		}
	}
	
	private static class UploadFinishCallback implements RequestFinishCallback<RequestResult> {

		private File uncaughtExceptionFile = DebugLog.getUncaughtExceptionFile();
		private File httpExceptionFile = DebugLog.getHttpExceptionFile();
		
		private String uncaughtException = "";
		private String httpException = "";
		
		private UploadFinishCallback(File uncaughtExceptionFile, File httpExceptionFile,
				                     String uncaughtException, String httpException) {
			this.uncaughtExceptionFile = uncaughtExceptionFile;
			this.httpExceptionFile = httpExceptionFile;
			this.uncaughtException = uncaughtException;
			this.httpException = httpException;
		}
		
		@Override
		public void onFinish(RequestResult result) {
			if (result.isSuccessful()) {
				File uploadedUncaughtExceptionFile = new File(uncaughtExceptionFile.getAbsoluteFile() + ".uploaded");
				if (uploadedUncaughtExceptionFile.exists()) {
					uncaughtExceptionFile.delete();
					IOUtil.writeFile(uploadedUncaughtExceptionFile, uncaughtException, true);
				} else {
					uncaughtExceptionFile.renameTo(uploadedUncaughtExceptionFile);
				}
				
				File uploadedHttpExceptionFile = new File(httpExceptionFile.getAbsoluteFile() + ".uploaded");
				if (uploadedHttpExceptionFile.exists()) {
					httpExceptionFile.delete();
					IOUtil.writeFile(uploadedHttpExceptionFile, httpException, true);
				} else {
					httpExceptionFile.renameTo(uploadedHttpExceptionFile);
				}
			}
		}
	}
	
	public static void saveHttpException(String requestResult) {
		String content = DebugLog.getBaseInfo().append(requestResult).append("\n").toString();
		DebugLog.syncSaveFile(DebugLog.FILE_HTTP_EXCEPTION_LOG, content);
	}
}
