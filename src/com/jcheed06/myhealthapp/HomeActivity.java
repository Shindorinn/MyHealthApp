package com.jcheed06.myhealthapp;

import com.jcheed06.myhealthapp.login.LoginActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends BaseActivity {

	private Button takeMeasurementButton;
	private Button viewMeasurementsButton;
	private Button manageBluetoothDevicesButton;
	private Button takeAPictureButton;
	private Button chooseLanguageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initializeActivity();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(!super.sharedPreferences.getBoolean(Registry.LOGIN_BOOLEAN, false)){ // TODO
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
		case Registry.TASK_LOGIN_REQUEST :
			if(resultCode == Registry.TASK_LOGIN_REQUEST_FAILED){
				goToLoginScreen();
			}
			break;
		}
	}
	
	private void goToLoginScreen() {
		Intent loginScreenIntent = new Intent(this, LoginActivity.class);
		this.startActivityForResult(loginScreenIntent, Registry.TASK_LOGIN_REQUEST);
		
	}

	private void initializeActivity() {

		this.takeMeasurementButton = (Button) this.findViewById(R.id.button_take_measurement);
		this.viewMeasurementsButton = (Button) this.findViewById(R.id.button_view_measurements);
		this.manageBluetoothDevicesButton = (Button) this.findViewById(R.id.button_manage_bluetooth_devices);
		this.takeAPictureButton = (Button) this.findViewById(R.id.button_take_picture);
		this.chooseLanguageButton = (Button) this.findViewById(R.id.button_choose_language);
			
		this.takeMeasurementButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.viewMeasurementsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.manageBluetoothDevicesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.takeAPictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.chooseLanguageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.takeMeasurementButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
