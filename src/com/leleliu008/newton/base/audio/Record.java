package com.leleliu008.newton.base.audio;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import android.media.AudioFormat;
import android.media.AudioRecord;

/**
 * 音频录制
 * 
 * @author 792793182@qq.com 2014-9-22
 *
 */
public final class Record {

	private AudioRecord audioRecord;
	
	private int bufferSizeInBytes;
	
	private AtomicBoolean isRecording;
	
	private File desFile;
	
	/**
	 * 
	 * @param audioSource @see MediaRecorder.AudioSource 音频来源
	 */
	public Record(int audioSource) {
		isRecording = new AtomicBoolean(false);
		
		int sampleRateInHz = 16000;
		int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
		int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
		bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
		audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
	}

	public void start(File desFile) throws FileNotFoundException {
		this.desFile = desFile;
		
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(desFile)));
		
		short[] audioData = new short[bufferSizeInBytes];
		
		audioRecord.startRecording();
		
		isRecording.set(true);
		
		try {
			while (isRecording.get()) {
				int result = audioRecord.read(audioData, 0, bufferSizeInBytes);
				for (int i = 0; i < result; i++) {
					dos.writeShort(audioData[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void pause() {
		audioRecord.stop();
		isRecording.set(false);
	}
	
	public void resume() {
		try {
			start(desFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			desFile.getParentFile().mkdirs();
			try {
				desFile.createNewFile();
				resume();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void stop() {
		audioRecord.stop();
		audioRecord.release();
		isRecording.set(false);
	}
}
