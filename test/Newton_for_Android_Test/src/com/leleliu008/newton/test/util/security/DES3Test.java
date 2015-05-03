package com.leleliu008.newton.test.util.security;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.security.DES3;

/**
 * 三重DES加密解密测试
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public class DES3Test extends AndroidTestCase {

	public void testDES3() throws Exception {
		//DES3加密的key必须是8x3=24个字节
		String key = "ABCDEFGHABCDEFGHABCDEFGH";
		String source = "I Love you";
		
		//加密
		byte[] encrpt = DES3.encrypt(source.getBytes(), key.getBytes());
		assertEquals(true, encrpt != null);
		//解密
		byte[] decrpt = DES3.decrypt(encrpt, key.getBytes());
		assertEquals(true, decrpt != null);
		assertEquals(source, new String(decrpt));
	}
}
