package com.jcheed06.myhealthapp.measurement.view;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;

public class ViewMeasurementsActivity extends TabActivity {
	
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugLogger.log_i("ViewMeasurementsActivity", "in Oncreate");
		this.initializeActivity();
	}

	private void initializeActivity() {
		setContentView(R.layout.activity_view_measurements);
		
		TabHost tabHost = super.getTabHost();
		
		TabHost.TabSpec spec;
		Intent intent;
		String indicator;
  
		intent = new Intent().setClass(ViewMeasurementsActivity.this, ViewPulseMeasurementsActivity.class);
		indicator = getResources().getString(R.string.indicator_viewPulseMeasurements);
		tabHost.addTab(tabHost.newTabSpec(Registry.TAB_PULSE).setIndicator(indicator).setContent(intent));

		intent = new Intent().setClass(ViewMeasurementsActivity.this, ViewPressureMeasurementsActivity.class);
		indicator = getResources().getString(R.string.indicator_viewPressureMeasurements);
		tabHost.addTab(tabHost.newTabSpec(Registry.TAB_PRESSURE).setIndicator(indicator).setContent(intent));

		intent = new Intent().setClass(ViewMeasurementsActivity.this, ViewECGMeasurementsActivity.class);
		indicator = getResources().getString(R.string.indicator_viewECGMeasurements);
		tabHost.addTab(tabHost.newTabSpec(Registry.TAB_ECG).setIndicator(indicator).setContent(intent));
		
		
	}
	
	
}
