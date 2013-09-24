package com.jcheed06.myhealthapp.tasks;

import org.json.JSONException;
import org.json.JSONObject;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.login.RestClient;

import android.os.AsyncTask;
import android.os.Debug;
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
		JSONObject resultData = restClient.login(username, password, this);
		if(resultData != null){
			// TODO : Add data into somewhere
			System.out.println("UserLoginTask: Login has result!");
			try{
				if(resultData.getString("status") != null) {
					return false;
				}else if(resultData.getString("status").equals("0")){
					return false;
				}else if(resultData.getString("status").equals("1")){
					return true;
				}
			
			Log.e("UserLoginTask", resultData.toString());
			return true;
			}catch(JSONException jsex){
				DebugLogger.log_e("UserLoginTask", "JSONException : " + jsex.getMessage());
				return false;				
			}
		}else{
			System.out.println("UserLoginTask: Login failed!");
			return false;
		}
	}
		
}
