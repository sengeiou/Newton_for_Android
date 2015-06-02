package com.leleliu008.newton.base.audio;

/**
 * MP3编码
 * 
 * @author 792793182@qq.com 2015-05-22
 *
 */
public final class MP3Encoder {

	static {
		System.loadLibrary("mp3lame");
	}

	/**
	 * 初始化录制参数
	 */
	public static void init(int inSamplerate, int outChannel,
			int outSamplerate, int outBitrate) {
		init(inSamplerate, outChannel, outSamplerate, outBitrate, 7);
	}

	/**
	 * 初始化录制参数 quality:0=很好很慢 9=很差很快
	 */
	public native static void init(int inSamplerate, int outChannel,
			int outSamplerate, int outBitrate, int quality);

	/**
	 * 音频数据编码(PCM左进,PCM右进,MP3输出)
	 */
	public native static int encode(short[] buffer_l, short[] buffer_r,
			int samples, byte[] mp3buf);

	/**
	 * 据说撸完之后要刷干净缓冲区,就是冲出来的那些东西要擦干净啦
	 */
	public native static int flush(byte[] mp3buf);

	/**
	 * 结束编码
	 */
	public native static void close();
}