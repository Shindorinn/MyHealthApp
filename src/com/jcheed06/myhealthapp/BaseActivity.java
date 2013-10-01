package com.jcheed06.myhealthapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class BaseActivity extends Activity {
	public static final String PREF_NAME = "com.jcheed06.myhealthapp";
	
	public SharedPreferences dsp;
	public SharedPreferences sp;
	public SharedPreferences.Editor spEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sp = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		spEdit = sp.edit();
		
		dsp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (sp.getBoolean("loggedIn", false)) {
//			Toast.makeText(getApplicationContext(), "Welcome back!", Toast.LENGTH_SHORT).show();
		} else if (sp.getBoolean("inLoginActivity", false)) {
			Toast.makeText(getApplicationContext(), "Login to continue", Toast.LENGTH_SHORT).show();
		} else {
//			startActivity(new Intent(this, LoginActivity.class));
		}
	}
}