package com.leleliu008.newton.base.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.leleliu008.newton.base.DebugLog;

/**
 * 音频处理
 * 
 * @author 792793182@qq.com 2014-9-22
 *
 */
public final class AudioManager {

	private static final String TAG = AudioManager.class.getSimpleName();

	private static final class InstanceHolder {
		private static AudioManager instance = new AudioManager();
	}

	public static AudioManager getInstance() {
		return InstanceHolder.instance;
	}

	/**
	 * 16位的WAV文件头，插入这些信息就可以得到可以播放的文件。
	 * 
	 * @param totalAudioLen
	 * @param totalDataLen
	 * @param longSampleRate
	 * @param channels
	 * @param byteRate
	 * @return
	 */
	public byte[] getWAVHeader(long totalAudioLen, long totalDataLen,
			long longSampleRate, int channels, long byteRate) {
		byte[] header = new byte[44];
		header[0] = 'R'; // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f'; // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16; // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1; // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (16 / 8);// (2 * 16 / 8); // block align
		header[33] = 0;
		header[34] = 16; // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		return header;
	}

	/**
	 * 将PCM文件转换成WAV文件
	 * @param pcmFile          PCM文件
	 * @param wavFile          WAV文件
	 * @param longSampleRate   采样率
	 * @param channels         通道数量
	 */
	private void convertPCM2WAV(File pcmFile, File wavFile, long sampleRate, int channels) {
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {
			in = new FileInputStream(pcmFile);
			out = new FileOutputStream(wavFile);

			long totalAudioLen = in.getChannel().size();
			long totalDataLen = totalAudioLen + 36;

			long byteRate = 16 * sampleRate * channels / 8;
			
			// 写入头
			out.write(getWAVHeader(totalAudioLen, totalDataLen, sampleRate,
					channels, byteRate));

			byte[] data = new byte[1024];
			// 写入PCM数据
			while (in.read(data) != -1) {
				out.write(data);
			}
		} catch (Exception e) {
			DebugLog.e(TAG, "convertPCM2WAV()", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					DebugLog.e(TAG, "convertPCM2WAV()", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					DebugLog.e(TAG, "convertPCM2WAV()", e);
				}
			}
		}
	}
}
