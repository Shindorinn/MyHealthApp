package com.jcheed06.myhealthapp;

import java.io.ByteArrayOutputStream;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class UrineTestData {

	private Bitmap photo;
	private String message,username;
	
	public UrineTestData(Bitmap photo, String message, SharedPreferences pref){
		this.photo = photo;
		this.message = message;
		this.username = pref.getString("username", "default");
	}
	
	public String getPhoto() {         
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.photo.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr,Base64.DEFAULT);
		return image_str;
	}

	public String getMessage() {
		return this.message;
	}

	public String getUsername() {
		return this.username;
	}

}
