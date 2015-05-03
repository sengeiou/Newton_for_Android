package com.leleliu008.newton.test;

import android.text.TextUtils;
import android.util.Log;

import com.leleliu008.newton.test.util.Util;

/**
 * Home界面的测试用例
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public class HomeTest extends UITest {

	public void test1() throws Exception {
		String result = Util.readAssetsFile(instrumentation.getContext(), "request_task_list_result_success.txt");
		assertEquals(false, TextUtils.isEmpty(result));
	}
}
