package com.leleliu008.newton.test;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.leleliu008.newton.business.home.Home;

/**
 * UI效果的测试用例
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public class UITest extends ActivityInstrumentationTestCase2<Home> {

	/** UI效果的测试可以与特定Activity无关，这里使用Home */
	protected Home home;
	protected Instrumentation instrumentation;
	
	public UITest() {
		super(Home.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		home = getActivity();
		instrumentation = getInstrumentation();
		
		SystemClock.sleep(2000);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		SystemClock.sleep(5000);
		
		home = null;
		instrumentation = null;
	}
}
