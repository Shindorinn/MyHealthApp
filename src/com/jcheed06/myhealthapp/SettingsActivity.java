package com.jcheed06.myhealthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.jcheed06.myhealthapp.login.LoginActivity;

public class SettingsActivity extends PreferenceActivity {
	SharedPreferences sp;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        sp = this.getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        
        if (sp.getString("isSuper", "0").equals("0")) {
        	PreferenceScreen screen = getPreferenceScreen();
            Preference pref = findPreference("pref_language");
            screen.removePreference(pref);
        }
    }
    
    protected void onResume() {
		super.onResume();
		
		if (sp.getBoolean("loggedIn", false)) {
//			Toast.makeText(getApplicationContext(), "Welcome back!", Toast.LENGTH_SHORT).show();
		} else if (sp.getBoolean("inLoginActivity", false)) {
			Toast.makeText(getApplicationContext(), "Login to continue", Toast.LENGTH_SHORT).show();
		} else {
			startActivity(new Intent(this, LoginActivity.class));
		}
	}
}