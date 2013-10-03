package com.jcheed06.myhealthapp.urinetest;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.jcheed06.myhealthapp.BaseActivity;
import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;
import com.jcheed06.myhealthapp.R.id;
import com.jcheed06.myhealthapp.R.layout;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

/**
 * Based on the tutorial :
 * http://developer.android.com/training/camera/photobasics.html
 */

public class TakePictureActivity extends BaseActivity {

	Bitmap imageBitmap;
	ImageView takePictureImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_take_picture);
		takePictureImageView = (ImageView) this.findViewById(R.id.takePictureImageView);
		dispatchTakePictureIntent(Registry.TAKE_PICTURE_REQUEST);
	}

	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Log.d("RESULT", " "+resultCode);
		switch(requestCode){
		case Registry.TAKE_PICTURE_REQUEST :
			if(resultCode == Activity.RESULT_OK){
				handleSmallCameraPhoto(data);
			}else if(resultCode == Activity.RESULT_CANCELED){
				DebugLogger.log_wtf("TakePictureActivity", "Activity result CANCELLED!");
			}
		break;
		}
		
	}
	
	private void handleSmallCameraPhoto(Intent intent) {
	    try {
			InputStream stream = getContentResolver().openInputStream(intent.getData());
			Bitmap bitmap = BitmapFactory.decodeStream(stream);
			UrineTestData result = new UrineTestData(bitmap, "message", super.sp);
			new SendUrineResult(this).execute(result);
			takePictureImageView.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void dispatchTakePictureIntent(int requestCode) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if(isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)){
	    	this.startActivityForResult(takePictureIntent, requestCode);
	    }else{
	    	this.setResult(Registry.TAKE_PICTURE_NOT_AVAILABLE);
	    	this.finish();
	    }
	}
}
