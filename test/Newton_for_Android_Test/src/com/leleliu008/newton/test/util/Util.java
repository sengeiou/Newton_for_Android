package com.leleliu008.newton.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * 
 * @author 792793182@qq.com 2014-11-04
 * 
 */
public final class Util {
	
	private Util() { }
	
	/**
     * 从测试工程的Assert中读取文本文件
     * @param context   测试工程的上下文
     * @param fileName  文件名
     */
//    public static String readAssetsFile(Context context, String fileName) {
//    	InputStream is = null;
//    	
//        try {
//            is = context.getAssets().open(fileName);
//            if (null != is && is.available() > 0) {
//                byte[] buffer = new byte[is.available()];
//                is.read(buffer);
//                return new String(buffer);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        	if (is != null) {
//        		try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//        }
//        return "";
//    }
    
    public static String readAssetsFile(Context context, String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bReader = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			bReader = new BufferedReader(new InputStreamReader(is));
			String lineStr = null;
			while ((lineStr = bReader.readLine()) != null) {
				stringBuilder.append(lineStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}
}
