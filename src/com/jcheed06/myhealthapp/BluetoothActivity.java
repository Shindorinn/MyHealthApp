package com.jcheed06.myhealthapp;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
	
	private final static int REQUEST_ENABLE_BT = 1;
	BluetoothAdapter btAdapter;
	BluetoothDevice device;
	
	ArrayAdapter aa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		
		aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
		
		ListView devicesList = (ListView) findViewById(R.id.list_devices);
		devicesList.setAdapter(aa);
		
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
		
		final BroadcastReceiver Receiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		    	String action = intent.getAction();
		    	Toast.makeText(BluetoothActivity.this, "Hallo!", Toast.LENGTH_SHORT);
		    	if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		    		device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		    		aa.add(device.getName() + "\n" + device.getAddress());
		    	}
		    }
		};
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(Receiver, filter);
		
		discoverConnections();
		
	}
	
	private void discoverConnections() {
		Button discoverBtDevices = (Button) findViewById(R.id.button_discover_bt);
		discoverBtDevices.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("health", "Responding to button!");
				if(btAdapter.isDiscovering()) {
					btAdapter.cancelDiscovery();
					Toast.makeText(BluetoothActivity.this, "Stopped discovering.", Toast.LENGTH_LONG).show();
				} else {
					btAdapter.startDiscovery();
					Toast.makeText(BluetoothActivity.this, "Started discovering.", Toast.LENGTH_LONG).show();					
				}
			}
		});
	}
	
	private class ConnectThread extends Thread {
		private final BluetoothSocket socket;
		private final BluetoothDevice device;
		private final UUID deviceId = UUID.fromString("889a38c0-251b-11e3-8224-0800200c9a66");
		
		public ConnectThread(BluetoothDevice device) {
			BluetoothSocket tmp = null;
			this.device = device;
				try {
					tmp = device.createRfcommSocketToServiceRecord(deviceId);
				} catch (IOException e) { }
					socket = tmp;
		}
		
		public void run() {
			btAdapter.cancelDiscovery();
			
			try {
				socket.connect();
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					return;
				}
//				manageConnectedSocket(socket);
			}
			
		}
		
		public void cancel() {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
//	private void checkConnections() {
//		
//		TextView pairedDevicesView = new TextView(this);
//		
//		ArrayAdapter arrayAdapter = new ArrayAdapter(this, pairedDevicesView.getId());
//		
//		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
//		if(pairedDevices.size() > 0) {
//			for(BluetoothDevice device : pairedDevices) {
//				arrayAdapter.add(device.getName() + "\n" + device.getAddress());
//			}
//		}
//	}

}
