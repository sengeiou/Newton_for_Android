package com.leleliu008.newton.base.audio;

/**
 * 音频处理
 * 
 * @author 792793182@qq.com 2014-9-22
 *
 */
public final class AudioManager {

	private static final class InstanceHolder {
		private static AudioManager instance = new AudioManager();
	}
	
	public static AudioManager getInstance() {
		return InstanceHolder.instance;
	}
}
