package com.jcheed06.myhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.jcheed06.myhealthapp.login.LoginActivity;
import com.jcheed06.myhealthapp.picture.TakePictureActivity;

public class HomeActivity extends BaseActivity {

	Button takeMeasurementsButton;
	Button viewMeasurementsButton;
	Button manageBluetoothDevicesButton;
	Button takePictureButton;
	Button chooseLanguageButton;
		
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
		
		takeMeasurementsButton = (Button) findViewById(R.id.button_take_measurement);
		
		takeMeasurementsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		viewMeasurementsButton = (Button) findViewById(R.id.button_view_measurements);
		
		viewMeasurementsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		manageBluetoothDevicesButton = (Button) findViewById(R.id.button_manage_bluetooth_devices);
		
		manageBluetoothDevicesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, BluetoothActivity.class);
				startActivity(intent);
			}
		});
		
		takePictureButton = (Button) findViewById(R.id.button_take_picture);
		
		takePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent takePictureIntent = new Intent(HomeActivity.this, TakePictureActivity.class);
				startActivityForResult(takePictureIntent, Registry.TAKE_PICTURE_REQUEST);				
			}
		});
	}
}
