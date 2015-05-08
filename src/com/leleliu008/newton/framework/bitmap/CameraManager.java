package com.leleliu008.newton.framework.bitmap;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public final class CameraManager {
	
	private Camera camera;
	
	/** 摄像头是否已经在预览状态. true:预览状态 ;false:没有预览状态 */
	private boolean isPreview;
	
	public void initCamera(final SurfaceView surfaceView, final int width, final int height) {
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(new Callback() {

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// 打开并初始化摄像头
				openCamera(surfaceView, width, height);
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// 如果camera不为null，释放摄像头
				if (camera != null) {
					if (isPreview) {
						camera.stopPreview();
						camera.release();
						camera = null;
					}
				}
			}
		});
		
		// 设置SurfaceView自己不维护缓冲区
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	/**
	 * 初始化摄像头
	 */
	private void openCamera(SurfaceView surfaceView, int width, int height) {
		if (!isPreview) {
			camera = Camera.open();
		}
		if (camera != null && !isPreview) {
			try {
				Parameters parameters = camera.getParameters();
				// 设置闪光灯为自动状态
				parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
				camera.setParameters(parameters);
				// 设置预览照片的大小
				parameters.setPreviewSize(width, height);
				// 设置每秒显示4帧
				parameters.setPreviewFrameRate(4);
				// 设置图片格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				// 设置JPG照片的质量
				parameters.set("jpeg-quality", 100);
				// 设置照片大小
				parameters.setPictureSize(width, height);
				// 通过SurfaceView显示取景画面
				camera.setPreviewDisplay(surfaceView.getHolder());
				// 如果是竖屏
				if (surfaceView.getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
					camera.setDisplayOrientation(90);
				} else {
					camera.setDisplayOrientation(0);
				}
				// 开始预览
				camera.startPreview();
				// 自动对焦
				camera.autoFocus(null);
				
				isPreview = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void takePhoto(PictureCallback jpeg) {
		if (camera != null) {
			camera.takePicture(null, null, jpeg);
		}
	}

	public Bitmap toBitmap(byte[] data) {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	public void stopPreview() {
		if (camera != null) {
			camera.stopPreview();
		}
	}

	public void release() {
		if (camera != null) {
			camera.release();
		}
	}
}
