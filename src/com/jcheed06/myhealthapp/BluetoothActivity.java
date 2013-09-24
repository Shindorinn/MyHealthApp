package com.jcheed06.myhealthapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.view.Menu;

public class BluetoothActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		if(btAdapter == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Your device doesn't support bluetooth.");
			builder.create();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Your device does support bluetooth.");
			builder.create();
		}
		
	}

}
