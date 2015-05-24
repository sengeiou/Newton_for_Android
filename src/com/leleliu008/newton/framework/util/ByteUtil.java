package com.leleliu008.newton.framework.util;

/**
 * 字节操作工具类
 * 
 * @author 792793182@qq.com 2015-05-24
 *
 */
public final class ByteUtil {

	private ByteUtil() { }

	/**
	 * 将字节数组转换成整数
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 整数
	 */
	public static int toInt(byte[] bytes) {
		return ((bytes[3] << 24) + (bytes[2] << 16) + (bytes[1] << 8) + (bytes[0] << 0));
	}

	/**
	 * 将字节数组转换成短整数
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 短整数
	 */
	public static short toShort(byte[] bytes) {
		return (short) ((bytes[1] << 8) + (bytes[0] << 0));
	}
}