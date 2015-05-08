package com.leleliu008.newton.framework.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.leleliu008.newton.R;
import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.base.camera.CameraManager;
import com.leleliu008.newton.framework.ui.fragment.BaseFragment;
import com.leleliu008.newton.framework.util.BitmapUtil;

public final class TakePhotoFragment extends BaseFragment implements OnClickListener {

	private static final String TAG = TakePhotoFragment.class.getSimpleName();
	
	private CameraManager cameraManager = CameraManager.getInstance();
	
	
	@Override
	public RelativeLayout onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout rootView = super.onCreateView(inflater, container, savedInstanceState);
		addContentView(inflater.inflate(R.layout.take_photo_fragment, container, false));
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		view.findViewById(R.id.take_photo_fragment_take_photo_btn).setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		DebugLog.d(TAG, "onResume()");
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.take_photo_fragment_surface_view);
		cameraManager.initCamera(surfaceView, Environment.getInstance().getScreenWidth(), Environment.getInstance().getScreenHeight());
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		cameraManager.stopPreview();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		cameraManager.release();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.take_photo_fragment_take_photo_btn:
			cameraManager.takePhoto(new PictureCallback() {
				
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					Bitmap bitmap = cameraManager.toBitmap(data);
					BitmapUtil.saveBitmapToFile(bitmap, Environment.getInstance().getMyDir() + "/xx.jpg", CompressFormat.JPEG);
					
					Bundle bundle = new Bundle();
					bundle.putParcelable("bitmap", bitmap);
					setResult(bundle);
					finish();
				}
			});
			break;

		default:
			break;
		}
	}
}
