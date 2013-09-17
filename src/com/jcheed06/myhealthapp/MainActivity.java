package com.jcheed06.myhealthapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity {

	public static final String REST_CALL_SUCCESS = "success";

	public static final int LOGIN_REQUEST 		  = 0;
	public static final int LOGIN_REQUEST_SUCCESS = 1;
	public static final int LOGIN_REQUEST_FAILED  = 2;
	
	public static final String LOGIN_BOOLEAN = "com.jcheed06.MainActivity.LOGIN_BOOLEAN";
	
	private SharedPreferences sharedPreferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		this.sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
		
		if(!this.sharedPreferences.getBoolean(MainActivity.LOGIN_BOOLEAN, false)){
			// Start login
			Intent loginIntent = new Intent(this, LoginActivity.class);
			this.startActivityForResult(loginIntent, MainActivity.LOGIN_REQUEST);
		}else{
			// moveToHomeScreen
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		case MainActivity.LOGIN_REQUEST :
			if(resultCode == MainActivity.LOGIN_REQUEST_SUCCESS){
				
			}else if(resultCode == MainActivity.LOGIN_REQUEST_FAILED){
				
			}
		
		}
	}

}
