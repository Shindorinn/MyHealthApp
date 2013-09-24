package com.jcheed06.myhealthapp;

import android.util.Log;

public class DebugLogger {

	private static boolean DEBUG_MODE = true;
	
	public static void log_e(String tag, String message){
		if(DEBUG_MODE){
			Log.e(tag, message);
		}
	}
	
	public static void log_i(String tag, String message){
		if(DEBUG_MODE){
			Log.i(tag, message);
		}
	}
	
	public static void log_d(String tag, String message){
		if(DEBUG_MODE){
			Log.d(tag, message);
		}
	}
	
	public static void log_wtf(String tag, String message){
		if(DEBUG_MODE){
			Log.wtf(tag, message);
		}
	}
	
	public static void log_v(String tag, String message){
		if(DEBUG_MODE){
			Log.v(tag, message);
		}
	}
}
