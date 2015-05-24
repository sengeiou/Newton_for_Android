package com.leleliu008.newton.base.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.leleliu008.newton.base.DebugLog;
import com.leleliu008.newton.framework.util.ByteUtil;
import com.leleliu008.newton.framework.util.IOUtil;

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
	 * @param totalAudioLen  音频长度
	 * @param totalDataLen   总数据长度
	 * @param sampleRate     采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
	 * @param sampleBit      采样大小，Android支持16bit和8bit
	 * @param channels       通道数
	 * @return
	 */
	public byte[] getWAVHeader(int totalAudioLen, int totalDataLen,
			int sampleRate, int sampleBit, int channels) {
		
		long byteRate = getByteRate(sampleRate, sampleBit, channels);
		
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
		header[24] = (byte) (sampleRate & 0xff);
		header[25] = (byte) ((sampleRate >> 8) & 0xff);
		header[26] = (byte) ((sampleRate >> 16) & 0xff);
		header[27] = (byte) ((sampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (16 / 8);// (2 * 16 / 8); // block align
		header[33] = 0;
		header[34] = (byte) sampleBit; // bits per sample
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
	 * @param pcmFile      PCM文件
	 * @param wavFile      WAV文件
	 * @param sampleRate   采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
	 * @param sampleBit    采样大小，Android支持16bit和8bit
	 * @param channels     通道数量
	 */
	public void convertPCM2WAV(File pcmFile, File wavFile, int sampleRate, int sampleBit, int channels) {
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {
			in = new FileInputStream(pcmFile);
			out = new FileOutputStream(wavFile);

			int totalAudioLen = (int) in.getChannel().size();
			int totalDataLen = totalAudioLen + 36;
			
			// 写入头
			out.write(getWAVHeader(totalAudioLen, totalDataLen, sampleRate, sampleBit, channels));

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
	
	/**
	 * 获取数据速率，但是为KB/S
	 * @param sampleRate   采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
	 * @param sampleBit    采样大小，Android支持16bit和8bit
	 * @param channels     通道数量
	 */
	public int getByteRate(int sampleRate, int sampleBit, int channels) {
		return sampleRate * sampleBit * channels / 8;
	}
	
	/**
	 * 获取WAV文件的播放时长，单位为S
	 * @param totalAudioLen   音频数据长度，单位是KB
	 * @param byteRate        速率，单位是KB/S，可以通过getByteRate方法获取
	 */
	public int getWavDuration(int totalAudioLen, int byteRate) {
		return totalAudioLen / byteRate;
	}
	
	/**
	 * 获取WAV文件的播放时长，单位为S
	 * @param wavFile   WAV文件
	 */
	public int getWavDuration(File wavFile) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(wavFile, "r");
			byte[] head = IOUtil.read(randomAccessFile, 0, 44);

			byte[] sampleRateBytes = { head[24], head[25], head[26], head[27] };
			byte[] sampleBitBytes = { 0, 0, 0, head[34] };
			byte[] channelsBytes = { 0, 0, 0, head[22] };
			byte[] totalAudioLenBytes = { head[40], head[41], head[42], head[43] };
			
			int sampleRate = ByteUtil.toInt(sampleRateBytes);
			int sampleBit = ByteUtil.toInt(sampleBitBytes);
			int channels = ByteUtil.toInt(channelsBytes);
			int totalAudioLen = ByteUtil.toInt(totalAudioLenBytes);
			
			int byteRate = getByteRate(sampleRate, sampleBit, channels);
			
			return getWavDuration(totalAudioLen, byteRate);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
