package com.leleliu008.newton.business.account.login;

import java.util.Properties;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.leleliu008.newton.R;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.business.account.RequestPostClientInfo;
import com.leleliu008.newton.business.account.RequestRegister;
import com.leleliu008.newton.framework.net.RequestFinishCallback;
import com.leleliu008.newton.framework.net.RequestResult;
import com.leleliu008.newton.framework.net.RequestServerManager;
import com.leleliu008.newton.framework.ui.drawable.StateList;
import com.leleliu008.newton.framework.ui.fragment.BaseFragment;
import com.leleliu008.newton.framework.util.StringUtil;
import com.leleliu008.newton.framework.util.TestSetting;
import com.tencent.stat.StatService;

/**
 * 注册的第二个页面
 * 
 * @author 792793182@qq.com 2014-10-15
 *
 */
public class RegisterFragment2 extends BaseFragment implements OnClickListener {
	
	static final String KEY_PHONE_NUMBER = "phoneNumber";
	
	private CheckBox checkbox;
	
	private EditText phoneNumberEdit;
	
	private EditText passwordEdit;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//杀死后进入的不算
		if (savedInstanceState == null) {
			//统计分享功能
			Properties properties = new Properties();
			properties.put("os_version", Environment.getInstance().getOSVersionName());
			properties.put("phone_model", Environment.getInstance().getPhoneModel());
			properties.put("action", "enter_second_page");
			StatService.trackCustomKVEvent(getActivity(), "register", properties);
		}
	}
	
	@Override
	public RelativeLayout onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout rootView = super.onCreateView(inflater, container, savedInstanceState);
		addContentView(inflater.inflate(R.layout.user_register_verification, rootView, false));
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		setTitleText(R.string.register_phoneNum);
		
		phoneNumberEdit = (EditText) view.findViewById(R.id.phone_num_user);
		
		//手机号，从前一页传过来
		Bundle bundle = getArguments();
		if (bundle != null) {
			String phoneNumber = bundle.getString(KEY_PHONE_NUMBER);
			if (!TextUtils.isEmpty(phoneNumber)) {
				phoneNumberEdit.setText(phoneNumber);
			}
		}
		
		passwordEdit = (EditText) view.findViewById(R.id.user_pass_user);
		
		checkbox = (CheckBox) view.findViewById(R.id.checkBox_register);
		checkbox.setChecked(true);
		
		ImageButton rigesterBt = (ImageButton) view.findViewById(R.id.user_register_bt2);
		rigesterBt.setBackgroundDrawable(StateList.get());
		rigesterBt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_register_bt2:
			String phoneNumber = phoneNumberEdit.getText().toString().trim();
			
			if (TextUtils.isEmpty(phoneNumber)) {
				showToast(R.string.bindPhoneNumberFragment_phoneNum_isEmpty);
				return;
			}
			
			String password = passwordEdit.getText().toString().trim();
			
			if (TextUtils.isEmpty(password)) {
				showToast(R.string.bindPhoneNumberFragment_security_code_isEmpty);
				return;
			}
			
			if (!StringUtil.isPhomeNumber(phoneNumber)) {
				showToast(R.string.bindPhoneNumberFragment_phoneNum_error);
				return;
			}
			
			if(!checkbox.isChecked()){
				showToast(R.string.register_agreed_Confirm);
				return;
			}
			if (!Environment.getInstance().isNetworkAvailable()) {
				Resources resource = getResources();
				if(resource != null){
					showToast(resource.getString(R.string.net_disconnected));
				}else{
					showToast(R.string.net_disconnected);
				}
				return;
			}
			
			//请求注册
			RequestServerManager.asyncRequest(0, new RequestRegister(phoneNumber, password, "test@datatang.com"), new RequestFinishCallback<RequestResult>() {
				
				@Override
				public void onFinish(RequestResult result) {
					if (result.isSuccessful()) {
						TestSetting.TraceSettings.TraceMessageType = TestSetting.TraceSettings.TraceMessageType_REGISTER;
						RequestServerManager.asyncRequest(0, new RequestPostClientInfo(), null);
						
						//统计分享功能
						Properties properties = new Properties();
						properties.put("os_version", Environment.getInstance().getOSVersionName());
						properties.put("phone_model", Environment.getInstance().getPhoneModel());
						properties.put("action", "done");
						StatService.trackCustomKVEvent(getActivity(), "register", properties);
						
						postMessage(10, null);
						postFinish();
					} else {
						String text = getResources().getString(R.string.register_fail);
						String description = result.getDiscription();
						if (!TextUtils.isEmpty(description)) {
							text = text + " : " + description;
						}
						postShowToast(text);
					}
				}
			});
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case 10:
			showToast(R.string.register_succeed);
			addFragment(new LoginFragment());
			break;
		default:
			break;
		}
		
		return super.handleMessage(msg);
	}

}
