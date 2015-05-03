package com.leleliu008.newton.test.framework.ui.dialog;

import android.graphics.Color;
import android.os.SystemClock;
import android.test.UiThreadTest;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.leleliu008.newton.framework.ui.dialog.CustomDialog;
import com.leleliu008.newton.test.UITest;

public class CustomDialogTest extends UITest {

	public void test1() throws Exception {
		instrumentation.runOnMainSync(new Runnable() {
			
			@Override
			public void run() {
				LinearLayout layout = new LinearLayout(home);
				layout.setBackgroundColor(Color.RED);
				layout.setLayoutParams(new LayoutParams(100, 100));
				
				CustomDialog customDialog = new CustomDialog(home);
				customDialog.setContentView(layout, new LayoutParams(100, 100));
				customDialog.show();
			}
		});
		
		SystemClock.sleep(10000);
	}
	
	public void test2() throws Exception {
		instrumentation.runOnMainSync(new Runnable() {
			
			@Override
			public void run() {
				LinearLayout layout = new LinearLayout(home);
				layout.setBackgroundColor(Color.RED);
				
				CustomDialog customDialog = new CustomDialog(home);
				customDialog.setTitle(null);
				customDialog.addContentView(layout, new LayoutParams(200, 200));
				customDialog.show(Gravity.LEFT | Gravity.TOP, 0, 0);
			}
		});
		
		SystemClock.sleep(10000);
	}
	
	public void test3() throws Exception {
		instrumentation.runOnMainSync(new Runnable() {
			
			@Override
			public void run() {
				LinearLayout layout = new LinearLayout(home);
				layout.setBackgroundColor(Color.RED);
				
				CustomDialog customDialog = new CustomDialog(home);
				customDialog.setTitle(null);
				customDialog.addContentView(layout, new LayoutParams(200, 200));
				customDialog.show(Gravity.LEFT | Gravity.TOP, 0, 0, 0);
			}
		});
		
		SystemClock.sleep(10000);
	}
	
	public void test4() throws Exception {
		instrumentation.runOnMainSync(new Runnable() {
			
			@Override
			public void run() {
				LinearLayout layout = new LinearLayout(home);
				layout.setBackgroundColor(Color.RED);
				
				CustomDialog customDialog = new CustomDialog(home);
				customDialog.setTitle(null);
				customDialog.setContentView(layout);
				customDialog.setWindowWidth(200);
				customDialog.setWindowHeight(600);
				customDialog.setDim(0.8f);
				customDialog.show(Gravity.LEFT | Gravity.TOP, 0, 0, 2000);
			}
		});
		
		SystemClock.sleep(10000);
	}
}
