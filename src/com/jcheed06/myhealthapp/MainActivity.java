package com.jcheed06.myhealthapp;

import com.jcheed06.myhealthapp.measurement.BluetoothActivity;
import com.jcheed06.myhealthapp.measurement.view.ViewMeasurementsActivity;
import com.jcheed06.myhealthapp.urinetest.TakePictureActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.action_about:
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void logout(View view) {
		spEdit.putBoolean("loggedIn", false);
		spEdit.commit();

		startActivity(new Intent(this, LoginActivity.class));
	}

	public void takePicture(View view) {
		startActivityForResult(new Intent(this, TakePictureActivity.class),
				Registry.TAKE_PICTURE_REQUEST);

	}

	public void manageBluetooth(View view) {
		startActivity(new Intent(this, BluetoothActivity.class));

	}

	public void viewMeasurements(View view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				getString(R.string.view_local_or_online_measurements))
				.setPositiveButton(
						getString(R.string.button_text_option_online),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								startActivityForResult(new Intent(
										MainActivity.this,
										ViewMeasurementsActivity.class),
										Registry.VIEW_MEASUREMENTS_REQUEST);
							}
						})
				.setNegativeButton(
						getString(R.string.button_text_option_local),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).setCancelable(true).show();

	}
}