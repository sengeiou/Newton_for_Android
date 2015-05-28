package com.leleliu008.newton.test.base.audio;

import java.io.File;

import android.test.AndroidTestCase;

import com.leleliu008.newton.base.Environment;
import com.leleliu008.newton.base.audio.record.IRecord;

public final class RecordTest extends AndroidTestCase {

	private IRecord record;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		
	}
	
	public void testStart() throws Exception {
		File desFile = new File(Environment.getInstance().getMyDir(), "xx.pcm");
		
	}
}
