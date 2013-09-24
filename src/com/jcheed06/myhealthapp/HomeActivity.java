package com.jcheed06.myhealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.jcheed06.myhealthapp.login.LoginActivity;

public class HomeActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button takeMeasurementsButton = (Button) findViewById(R.id.button_take_measurement);
		
		takeMeasurementsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, BluetoothActivity.class);
				startActivity(intent);
			}
		});

//		if(!super.sharedPreferences.getBoolean(BaseActivity.LOGIN_BOOLEAN, false)){ // TODO
//			goToLoginScreen();
//		}
		
		
		
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
			if(resultCode == Registry.TASK_LOGIN_REQUEST_SUCCESS){
				
			}else if(resultCode == Registry.TASK_LOGIN_REQUEST_FAILED){
				goToLoginScreen();
			}
		
		}
	}
	
	private void goToLoginScreen() {
		Intent loginScreenIntent = new Intent(this, LoginActivity.class);
		this.startActivityForResult(loginScreenIntent, Registry.TASK_LOGIN_REQUEST);
		
	}

}
