package com.jcheed06.myhealthapp;

import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BluetoothActivity extends Activity {
	
	private final static int REQUEST_ENABLE_BT = 1;
	BluetoothAdapter btAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if(btAdapter == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothActivity.this);
			builder.setMessage("Your device doesn't support bluetooth.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					finish();
				}
			});
			builder.setCancelable(false);
			builder.create();
			builder.show();
		} 
		
		if(!btAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
	}
	
	private void checkConnections() {
		
		TextView pairedDevicesView = new TextView(this);
		
		ArrayAdapter arrayAdapter = new ArrayAdapter(this, pairedDevicesView.getId());
		
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if(pairedDevices.size() > 0) {
			for(BluetoothDevice device : pairedDevices) {
				
			}
		}
	}

}
