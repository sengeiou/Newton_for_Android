package com.leleliu008.newton.test.framework.security;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.security.Base64;

public class Base64Test extends AndroidTestCase {

	public void testBase64() throws Exception {
		String string = "I Love you";
		
		//先编码
		String result = Base64.encode(string.getBytes());
		assertNotNull(result);
		
		//再解码
		byte[] result2 = Base64.decode(result.getBytes());
		assertNotNull(result2);
		assertEquals(string, new String(result2));
	}
}
