package com.jcheed06.myhealthapp;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
	
	@Override
	protected Boolean doInBackground(String... parameters) {
		
		String username = parameters[0];
		String password = parameters[1];

		Log.e("UserLoginTask", "Username : " + username);
		Log.e("UserLoginTask", "Password : " + password);
		
		RestClient restClient = new RestClient();
		JSONObject resultData = restClient.login(username, password);
		
		if(resultData != null){
			// TODO : Add data into somewhere
			System.out.println("UserLoginTask: Login success!");
			Log.e("UserLoginTask", resultData.toString());
			return true;
		}else{
			System.out.println("UserLoginTask: Login failed!");
			return false;
		}
	}
}
