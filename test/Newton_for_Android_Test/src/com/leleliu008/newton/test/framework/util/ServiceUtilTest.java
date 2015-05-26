package com.leleliu008.newton.test.framework.util;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.util.ServiceUtil;

public final class ServiceUtilTest extends AndroidTestCase {

	public void testGetSystemService() throws Exception {
		Object audioService = ServiceUtil.getSystemService("audio");
		System.out.println(audioService);
		assertNotNull(audioService);
	}
	
	public void testGetSystemServiceMethods() throws Exception {
		ArrayList<String> methods = ServiceUtil.getSystemServiceMethods("audio");
		System.out.println(methods);
		assertNotNull(methods);
	}
	
	public void testListSystemServices() throws Exception {
		ArrayList<String> methods = ServiceUtil.listSystemServices();
		System.out.println(methods);
		assertNotNull(methods);
	}
}
