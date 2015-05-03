package com.leleliu008.newton.business.account;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.leleliu008.newton.R;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.framework.net.RequestFinishCallback;
import com.leleliu008.newton.framework.net.RequestResult;
import com.leleliu008.newton.framework.net.RequestServerManager;
import com.leleliu008.newton.framework.ui.drawable.StateList;
import com.leleliu008.newton.framework.ui.fragment.BaseFragment;

/**
 * 修改密码界面
 * 
 * @author 792793182@qq.com 2014-10-20
 * 
 */
public class ModifyPasswordFragment extends BaseFragment implements OnClickListener {

	/** 旧密码输入框 */
	private EditText oldPasswordET;

	/** 新密码输入框 */
	private EditText newPasswordET;

	/** 新密码确认输入框 */
	private EditText newPasswordComfirmET;

	@Override
	public RelativeLayout onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout rootView = super.onCreateView(inflater, container, savedInstanceState);
		addContentView(inflater.inflate(R.layout.change_password, rootView, false));
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		setTitleText(R.string.modifyPasswordFragment_title);

		oldPasswordET = (EditText) view.findViewById(R.id.old_password);
		newPasswordET = (EditText) view.findViewById(R.id.new_password);
		newPasswordComfirmET = (EditText) view.findViewById(R.id.new2_password);

		Button modifyButton = (Button) findViewById(R.id.changePassword_bt);
		modifyButton.setBackgroundDrawable(StateList.get());
		modifyButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.changePassword_bt:
			String oldPassword = oldPasswordET.getText().toString().trim();
			String newPassword = newPasswordET.getText().toString().trim();
			String newPasswordComfirm = newPasswordComfirmET.getText().toString().trim();

			if (TextUtils.isEmpty(oldPassword)) {
				showToast(R.string.modifyPasswordFragment_oldpassword_isEntity);
				return;
			}

			if (TextUtils.isEmpty(newPassword)) {
				showToast(R.string.modifyPasswordFragment_newpassword_isEntity);
				return;
			}

			if (TextUtils.isEmpty(newPasswordComfirm)) {
				showToast(R.string.retype_password);
				return;
			}

			if (!newPassword.equals(newPasswordComfirm)) {
				showToast(R.string.modifyPasswordFragment_password_not_match);
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
			
			// 请求修改密码
			RequestServerManager.asyncRequest(0, new RequestModifyPassword(oldPassword, newPassword), new RequestFinishCallback<RequestResult>() {

				@Override
				public void onFinish(RequestResult result) {
					if (result.isSuccessful()) {
						postShowToast(R.string.modifyPasswordFragment_succeed);
						postFinish();
					} else {
						String text = getResources().getString(R.string.modifyPasswordFragment_fail);
						String discription = result.getDiscription();
						if (!TextUtils.isEmpty(discription)) {
							text = text + " : " + discription;
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
}
