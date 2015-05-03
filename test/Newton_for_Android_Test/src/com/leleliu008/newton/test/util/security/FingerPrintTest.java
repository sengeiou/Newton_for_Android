package com.leleliu008.newton.test.util.security;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.security.FingerPrint;
import com.leleliu008.newton.framework.security.FingerPrint.Algorithm;

/**
 * 信息摘要编码测试
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public class FingerPrintTest extends AndroidTestCase {

	public void testMD5() throws Exception {
		String result = FingerPrint.encode(Algorithm.MD5, "hh");
		assertEquals(true, result != null);
		assertEquals(32, result.length());
	}
	
	public void testSHA1() throws Exception {
		String result = FingerPrint.encode(Algorithm.SHA_1, "hh");
		assertEquals(true, result != null);
		assertEquals(40, result.length());
	}
	
	public void testSHA256() throws Exception {
		String result = FingerPrint.encode(Algorithm.SHA_256, "hh");
		assertEquals(true, result != null);
		assertEquals(64, result.length());
	}
}
