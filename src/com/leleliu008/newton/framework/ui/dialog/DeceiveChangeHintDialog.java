package com.leleliu008.newton.framework.ui.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.leleliu008.newton.MyApp;
import com.leleliu008.newton.R;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.business.config.SettingConfig;
import com.leleliu008.newton.framework.ui.drawable.StateList;

/***
 * 任务详情时的提示（执行任务过程中禁止更换设备）
 * 
 * @author wangzhichao@datatang.com 2015.2.9
 *
 */
public class DeceiveChangeHintDialog extends CustomDialog{

	private CheckBox checkBoxHint;
	
	public DeceiveChangeHintDialog(final Activity context) {
		super(context);
		
		//设置高度和宽度
		setWindowWidth(Environment.getInstance().getScreenWidth());
		
		View view = LayoutInflater.from(context).inflate(R.layout.deceive_hint_dialog, null);
		setContentView(view);
		checkBoxHint = (CheckBox) view.findViewById(R.id.checkBox_hint);
		Button ok = (Button) view.findViewById(R.id.dialog_ok);
		ok.setBackgroundDrawable(StateList.get());
		ok.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 是否不再提示
				if(checkBoxHint.isChecked()){
					MyApp.getApp().getSetting().setSetting(SettingConfig.KEY_CHANGE_DECEIVE_HINT, !checkBoxHint.isChecked());
				}
				DeceiveChangeHintDialog.this.dismiss();
			}
		});
		
	}

}
