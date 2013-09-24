package com.jcheed06.myhealthapp;

import android.app.Activity;
import android.content.Context;

public final class Registry extends Activity{

	public static final String BASE_API_URL = "http://myhealth.omninous.com/app";
	public static final String URL_KEY = "com.jcheed06.myhealthapp.URL_KEY";
	
	public static final String CHARSET = "UTF-8";
	
	public static final String USERNAME_KEY = "com.jcheed06.Registry.USERNAME_KEY";
	
	public static final String LOGIN_COMMAND = "/login";
	public static final String LOGIN_BOOLEAN = "com.jcheed06.MainActivity.LOGIN_BOOLEAN";

	public static final String REST_CALL_SUCCESS = "success";
		
	public static final String SHARED_DATA_NAME = "com.jcheed06.myhealthapp.Registry.SHARED_DATA_NAME";
	
	
	public static final int SHARED_DATA_CONTEXT = Context.MODE_PRIVATE;	
	
	public static final int TASK_LOGIN_REQUEST 		   = 0;
	public static final int TASK_LOGIN_REQUEST_SUCCESS = 1;
	public static final int TASK_LOGIN_REQUEST_FAILED  = 2;
		
	private Registry(){	}
	
	
}
