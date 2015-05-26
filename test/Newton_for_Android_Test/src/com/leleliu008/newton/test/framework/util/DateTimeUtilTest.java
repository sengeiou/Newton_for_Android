package com.leleliu008.newton.test.framework.util;

import android.test.AndroidTestCase;

import com.leleliu008.newton.framework.util.DateTimeUtil;

public class DateTimeUtilTest extends AndroidTestCase {

	public void testIsYestoday() throws Exception {
		assertEquals(true, DateTimeUtil.isYestoday(System.currentTimeMillis() - DateTimeUtil.ONE_DAY));
		assertEquals(false, DateTimeUtil.isYestoday(System.currentTimeMillis() + DateTimeUtil.ONE_DAY));
		assertEquals(false, DateTimeUtil.isYestoday(System.currentTimeMillis()));
	}
	
	public void testIsToday() throws Exception {
		assertEquals(true, DateTimeUtil.isToday(System.currentTimeMillis()));
		assertEquals(false, DateTimeUtil.isToday(System.currentTimeMillis() - DateTimeUtil.ONE_DAY - 1));
		assertEquals(false, DateTimeUtil.isToday(System.currentTimeMillis() - DateTimeUtil.ONE_DAY - 1));
		assertEquals(false, DateTimeUtil.isToday(System.currentTimeMillis() + DateTimeUtil.ONE_DAY + 1));
		assertEquals(false, DateTimeUtil.isToday(System.currentTimeMillis() + DateTimeUtil.ONE_DAY + 1));
		assertEquals(false, DateTimeUtil.isToday(System.currentTimeMillis() + DateTimeUtil.ONE_DAY));
	}
	
	public void testTomorrow() throws Exception {
		assertEquals(true, DateTimeUtil.isTomorrow(System.currentTimeMillis() + DateTimeUtil.ONE_DAY));
		assertEquals(false, DateTimeUtil.isTomorrow(System.currentTimeMillis() - DateTimeUtil.ONE_DAY));
		assertEquals(false, DateTimeUtil.isTomorrow(System.currentTimeMillis() - DateTimeUtil.ONE_DAY - 1));
	}
}
