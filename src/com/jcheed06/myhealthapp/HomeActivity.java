package com.jcheed06.myhealthapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Button;

public class HomeActivity extends MainActivity {

	public static final int LOGIN_REQUEST 		  = 0;
	public static final int LOGIN_REQUEST_SUCCESS = 1;
	public static final int LOGIN_REQUEST_FAILED  = 2;
	
	
	private Button takeMeasurementButton;
	private Button viewMeasurementsButton;
	private Button manageBluetoothDevicesButton;
	private Button takeAPictureButton;
	private Button chooseLanguageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		this.takeMeasurementButton = (Button) this.findViewById(R.id.button_take_measurement);
		this.viewMeasurementsButton = (Button) this.findViewById(R.id.button_view_measurements);
		this.manageBluetoothDevicesButton = (Button) this.findViewById(R.id.button_manage_bluetooth_devices);
		this.takeAPictureButton = (Button) this.findViewById(R.id.button_take_picture);
		this.chooseLanguageButton = (Button) this.findViewById(R.id.button_choose_language);
	
		if(!super.sharedPreferences.getBoolean(MainActivity.LOGIN_BOOLEAN, false)){
			goToLoginScreen();
		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		case HomeActivity.LOGIN_REQUEST :
			if(resultCode == HomeActivity.LOGIN_REQUEST_SUCCESS){
				
			}else if(resultCode == HomeActivity.LOGIN_REQUEST_FAILED){
				goToLoginScreen();
			}
		
		}
	}
	
	private void goToLoginScreen() {
		Intent loginScreenIntent = new Intent(this, LoginActivity.class);
		this.startActivity(loginScreenIntent);
		
	}

}
