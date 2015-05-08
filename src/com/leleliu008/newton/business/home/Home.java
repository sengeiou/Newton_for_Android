package com.leleliu008.newton.business.home;

import android.os.Bundle;

import com.leleliu008.newton.framework.bitmap.TakePhotoFragment;
import com.leleliu008.newton.framework.ui.fragment.BaseFragmentActivity;

public class Home extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentMediator().addFragment(this, new TakePhotoFragment());
	}
}
