package com.leleliu008.newton.test.framework.security;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.security.SymmetricalEncryption;
import com.leleliu008.newton.framework.security.SymmetricalEncryption.Algorithm;

/**
 * 三重DES加密解密测试
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public final class DES3Test extends AndroidTestCase {

	public void testDES3() throws Exception {
		// DES3加密的key必须是8x3=24个字节
		String key = "ABCDEFGHABCDEFGHABCDEFGH";
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
