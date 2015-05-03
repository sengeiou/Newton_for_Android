package com.leleliu008.newton.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.leleliu008.newton.base.DebugLog;

/**
 * 
 * 图片处理工具类
 * 
 * @author 792793182@qq.com 2014-9-28 
 *
 */
public final class BitmapUtil {

	private static final String TAG = BitmapUtil.class.getSimpleName();
	
	private BitmapUtil() { }
	
	/**
	 * 图片缩放
	 * @param bitmap    目标图片
	 * @param desWidth  目标宽度
	 * @param desHeight 目标高度
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int desWidth, int desHeight) {
		return zoomBitmap(bitmap, (float) desWidth / bitmap.getWidth(), 
				                  (float) desHeight / bitmap.getHeight());
	}
	
	/**
	 * 图片缩放
	 * @param bitmap    目标图片
	 * @param desWidth  目标宽度
	 * @param desHeight 目标高度
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, float sx, float sy) {
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				                                          bitmap.getHeight(), matrix, true);
	}
	
	public static byte[] compress(Bitmap bitmap, Bitmap.CompressFormat format) {
		return compress(bitmap, 75, format);
	}
	
	public static byte[] compress(Bitmap bitmap, int quality, Bitmap.CompressFormat format) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(format, quality, baos);
		return baos.toByteArray();
	}
	
	public static boolean saveBitmapToFile(Bitmap bitmap, String filePath, Bitmap.CompressFormat format) {
		return saveBitmapToFile(bitmap, filePath, 75, format);
	}
	
	/**
	 * 保存Bitmap到文件
	 * @param bitmap    位图
	 * @param fileName  文件名，路径自己选择
	 * @return          完整的路径
	 */
	public static boolean saveBitmapToFile(Bitmap bitmap, String filePath, int quality, Bitmap.CompressFormat format) {
		FileOutputStream fos = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(compress(bitmap, quality, format));
			return true;
		} catch (Exception e) {
			DebugLog.e(TAG, "saveBitmapToFile()", e);
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					DebugLog.e(TAG, "saveBitmapToFile()", e);
				}
			}
		}
	}
}
