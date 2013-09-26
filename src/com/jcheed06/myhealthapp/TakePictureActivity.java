package com.jcheed06.myhealthapp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.R.layout;
import com.jcheed06.myhealthapp.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

/**
 * Based on the tutorial :
 * http://developer.android.com/training/camera/photobasics.html
 */

public class TakePictureActivity extends BaseActivity {

	Bitmap imageBitmap;
	ImageView takePictureImageView;
	File imageFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_take_picture);
		takePictureImageView = (ImageView) this
				.findViewById(R.id.takePictureImageView);
		dispatchTakePictureIntent(Registry.TAKE_PICTURE_REQUEST, getImageFile());

	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Registry.TAKE_PICTURE_REQUEST:
			if (resultCode == Activity.RESULT_OK) {
				dispatchTakePictureIntent(Registry.TAKE_PICTURE_REQUEST,
						imageFile);
				setPic(imageFile);
				finish();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				DebugLogger.log_wtf("TakePictureActivity",
						"Activity result CANCELLED!");
				this.finish();
			}
			break;
		}
	}

	private void handleSmallCameraPhoto(Intent intent) {
		Bundle extras = intent.getExtras();
		Log.d("DATA", " " + extras.get("data"));
		imageBitmap = (Bitmap) extras.get("data");
		takePictureImageView.setImageBitmap(imageBitmap);
	}

	private void dispatchTakePictureIntent(int requestCode, File imageFile) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
				.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {
			Log.d("requestCode", ""+requestCode);
			this.startActivityForResult(takePictureIntent, requestCode);
		} else {
			this.setResult(Registry.TAKE_PICTURE_NOT_AVAILABLE);
			this.finish();
		}
	}

	private void setPic(File file){

//		int targetW = this.takePictureImageView.getWidth();
//		int targetH = this.takePictureImageView.getHeight();

		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
//		int photoW = bmOptions.outWidth;
//		int photoH = bmOptions.outHeight;
//
//		int scaleFactor = 1;
//		if ((targetW > 0) || (targetH > 0)) {
//			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//		}
//
//		bmOptions.inJustDecodeBounds = false;
//		bmOptions.inSampleSize = scaleFactor;
//		bmOptions.inPurgeable = true;
		
		imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
		new SendUrineResult(this).execute(new UrineTestData(imageBitmap,
				"Text", super.sp));
		
		takePictureImageView.setImageBitmap(imageBitmap);
		takePictureImageView.setVisibility(View.VISIBLE);
		takePictureImageView.setVisibility(View.INVISIBLE);
	}

	private File getImageFile() {
		  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		  String imageFileName = "IMG_" + timeStamp + "_";
		  File albumF = Environment.get;
		  File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		
		
		if (imageFile == null) {
				imageFile = File.createTempFile("IMG_tempfile" +  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), ".jpeg", "tempMyHealth");

		}
		return imageFile;
	}
}
