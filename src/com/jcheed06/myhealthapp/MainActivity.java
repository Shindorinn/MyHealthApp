package com.jcheed06.myhealthapp;


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
    
    public void takePicture(View view){
    	startActivityForResult(new Intent(this, TakePictureActivity.class), Registry.TAKE_PICTURE_REQUEST);				

    }
    
    public void manageBluetooth(View view){
    	startActivity(new Intent(this, BluetoothActivity.class));

    }
    
}
