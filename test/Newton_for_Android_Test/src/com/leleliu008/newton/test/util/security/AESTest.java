package com.leleliu008.newton.test.util.security;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.security.AES;

/**
 * AES加密界面测试
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public class AESTest extends AndroidTestCase {

	public void testAES() throws Exception {
		//AES加密的key必须是16个字节
		String key = "ABCDEFGHIJKLMNOP";
		String source = "I Love you";
		
		//加密
		byte[] encrpt = AES.encrypt(source.getBytes(), key.getBytes());
		assertEquals(true, encrpt != null);
		//解密
		byte[] decrpt = AES.decrypt(encrpt, key.getBytes());
		assertEquals(true, decrpt != null);
		assertEquals(source, new String(decrpt));
	}
}
