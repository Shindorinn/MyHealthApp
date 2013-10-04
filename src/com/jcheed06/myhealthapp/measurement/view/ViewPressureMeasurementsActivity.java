package com.jcheed06.myhealthapp.measurement.view;

import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.R.layout;
import com.jcheed06.myhealthapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewPressureMeasurementsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pressure_measurements);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_pressure_measurements, menu);
		return true;
	}

}
