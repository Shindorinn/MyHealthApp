package com.jcheed06.myhealthapp.measurement.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;

public class ViewMeasurementsActivity extends Activity {
	
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugLogger.log_i("ViewMeasurementsActivity", "in Oncreate");
		this.initializeActivity();
	}

	private void initializeActivity() {
		DebugLogger.log_i("ViewMeasurementsActivity", "in initializeActivity");
		setContentView(R.layout.activity_view_measurements);
		DebugLogger.log_i("ViewMeasurementsActivity", "setContentView done");
		
		TabHost tabHost = (TabHost) this.findViewById(R.id.viewMeasurementsTabhost);
		
		DebugLogger.log_i("ViewMeasurementsActivity", "found TabHost");
		
		TabHost.TabSpec spec;
		Intent intent;
		String indicator;
  
		intent = new Intent().setClass(ViewMeasurementsActivity.this, ViewPulseMeasurementsActivity.class);
		
		DebugLogger.log_i("ViewMeasurementsActivity", "intent : " + intent);
		
		indicator = getResources().getString(R.string.indicator_viewPulseMeasurements);
		
		DebugLogger.log_i("ViewMeasurementsActivity", "indicator : " + indicator);
		/*
		tabHost.addTab(tabHost.newTabSpec(Registry.TAB_PULSE).setIndicator(indicator).setContent(intent));

		intent = new Intent().setClass(ViewMeasurementsActivity.this, ViewPressureMeasurementsActivity.class);
		indicator = getResources().getString(R.string.indicator_viewPressureMeasurements);
		tabHost.addTab(tabHost.newTabSpec(Registry.TAB_PRESSURE).setIndicator(indicator).setContent(intent));

		intent = new Intent().setClass(ViewMeasurementsActivity.this, ViewECGMeasurementsActivity.class);
		indicator = getResources().getString(R.string.indicator_viewECGMeasurements);
		tabHost.addTab(tabHost.newTabSpec(Registry.TAB_ECG).setIndicator(indicator).setContent(intent));
		*/
		
	}
	
	
}
