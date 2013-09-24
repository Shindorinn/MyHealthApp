package com.jcheed06.myhealthapp.sendurinetest;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class SendUrineResultTask extends AsyncTask<String, Void, Boolean> {
	
	@Override
	protected Boolean doInBackground(String... parameters) {
		
		
		
		SendUrineTestRestClient restClient = new SendUrineTestRestClient();
		JSONObject resultData = restClient.sendTest(null, null);
		
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
