package com.leleliu008.newton.test.base.audio;

import java.io.File;

import android.media.MediaRecorder.AudioSource;
import android.test.AndroidTestCase;

import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.base.audio.Record;

public final class RecordTest extends AndroidTestCase {

	private Record record;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		record = new Record(AudioSource.MIC);
	}
	
	public void testStart() throws Exception {
		File desFile = new File(Environment.getInstance().getMyDir(), "xx.pcm");
		record.start(desFile);
	}
}
