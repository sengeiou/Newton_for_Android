package com.leleliu008.newton.test.framework.security;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.security.SymmetricalEncryption;
import com.leleliu008.newton.framework.security.SymmetricalEncryption.Algorithm;

/**
 * AES加密界面测试
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public final class AESTest extends AndroidTestCase {

	public void testAES() throws Exception {
		// AES加密的key是16个字节
		String key = "ABCDEFGHIJKLMNOP";
		String source = "I Love you";

		// 加密
		byte[] encrpt = SymmetricalEncryption.encrypt(Algorithm.AES, source.getBytes(), key.getBytes());
		assertNotNull(encrpt);

		// 解密
		byte[] decrpt = SymmetricalEncryption.decrypt(Algorithm.AES, encrpt, key.getBytes());
		assertNotNull(decrpt);
		assertEquals(source, new String(decrpt));
	}
}
