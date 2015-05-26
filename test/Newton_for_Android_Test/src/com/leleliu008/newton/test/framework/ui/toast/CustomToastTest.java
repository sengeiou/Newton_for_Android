package com.leleliu008.newton.test.framework.ui.toast;

import android.test.UiThreadTest;

import com.leleliu008.newton.framework.ui.toast.CustomToast;
import com.leleliu008.newton.test.UITest;

/**
 * 自定义Toast测试用例
 * 
 * @author 792793182@qq.com 2014-11-04
 *
 */
public class CustomToastTest extends UITest {

	@UiThreadTest
	public void test1() throws Exception {
		String text = "dddddddddddddddddd";
		CustomToast.makeText(home, text, 5000).show();
	}
	
	@UiThreadTest
	public void test2() throws Exception {
		String text = "dddddddddddddddddddddddd" +
				      "eeeeeeeeeeeeeeeeeeeeeeee" +
				      "ffffffffffffffffffffffff";
		CustomToast.makeText(home, text, 5000).show();
	}
	
	@UiThreadTest
	public void test3() throws Exception {
		String text = "";
		CustomToast.makeText(home, text, 5000).show();
	}
	
	@UiThreadTest
	public void test4() throws Exception {
		String text = null;
		CustomToast.makeText(home, text, 5000).show();
	}
}
