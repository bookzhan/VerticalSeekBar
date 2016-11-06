package com.chenzhanyang.layouttest;

import com.chenzhanyang.layouttest.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		VerticalSeekBar mVsk = (VerticalSeekBar) findViewById(R.id.vsk);
		mVsk.setProgress(50);
	}
}
