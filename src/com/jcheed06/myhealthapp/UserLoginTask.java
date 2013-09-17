package com.jcheed06.myhealthapp;

import android.os.AsyncTask;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<String, String, Boolean> {
	
	@Override
	protected Boolean doInBackground(String... parameters) {

		String username = parameters[0];
		String password = parameters[1];
				
		RestClient connection = new RestClient(username, password);
		
		return connection.login();
	}
}
