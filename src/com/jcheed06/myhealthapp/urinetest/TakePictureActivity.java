package com.jcheed06.myhealthapp.urinetest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.jcheed06.myhealthapp.BaseActivity;
import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;

//import org.opencv.android.OpenCVLoader;
//import org.opencv.android.Utils;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;

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
	String imageLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_take_picture);

		// if (!OpenCVLoader.initDebug()) {
		// // Handle initialization error
		// } else {
		// Log.e("health", "Error loading openCV!");
		// }

		takePictureImageView = (ImageView) this
				.findViewById(R.id.takePictureImageView);
		dispatchTakePictureIntent(Registry.TAKE_PICTURE_REQUEST);
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
		Log.d("RESULT", " " + resultCode);
		switch (requestCode) {
		case Registry.TAKE_PICTURE_REQUEST:
			if (resultCode == Activity.RESULT_OK) {
				handleSmallCameraPhoto(data);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				DebugLogger.log_wtf("TakePictureActivity",
						"Activity result CANCELLED!");
			}
			break;
		}

	}

	private void handleSmallCameraPhoto(Intent intent) {
		try {
			InputStream stream = getContentResolver().openInputStream(
					intent.getData());
			Bitmap bitmap = BitmapFactory.decodeStream(stream);

			Bitmap alreadyHere = BitmapFactory.decodeResource(getBaseContext()
					.getResources(), R.drawable.strip);
			//
			// // Mat imgToCompareWith =
			// // Highgui.imread(Uri.parse("R.drawable.strip").getPath(),
			// // Highgui.CV_LOAD_IMAGE_GRAYSCALE);
			//
			// Mat imgToCompareWith = new Mat(alreadyHere.getWidth(),
			// alreadyHere.getHeight(), CvType.CV_8UC1);
			// Utils.bitmapToMat(alreadyHere, imgToCompareWith);
			//
			// Mat imgToCompare = new Mat(bitmap.getWidth(), bitmap.getHeight(),
			// CvType.CV_8UC1);
			// Utils.bitmapToMat(bitmap, imgToCompare);
			// Mat result = new Mat();
			//
			// Imgproc.cvtColor(imgToCompareWith, imgToCompareWith,
			// Imgproc.COLOR_RGB2GRAY);
			// Imgproc.cvtColor(imgToCompare, imgToCompare,
			// Imgproc.COLOR_RGB2GRAY);
			//
			// // Core.absdiff(imgToCompare, imgToCompareWith, result);
			//
			// Core.compare(imgToCompare, imgToCompareWith, result,
			// Core.CMP_NE);
			//
			// int val = Core.countNonZero(result);
			//
			// if (val == 0) {
			// Log.e("health", "Photo is exactly the same");
			// } else if (val <= 4050000) {
			// Log.e("health", "Photo is about the same");
			// } else if (val > 4050000) {
			UrineTestData result1 = new UrineTestData(bitmap, "message",
					super.sp);
			new SendUrineResult(this).execute(result1);
			takePictureImageView.setImageBitmap(bitmap);
			// new SendUrineResult(this).execute(result1);
			// Log.e("health", "Photo is different");
			// }
			// Log.e("health", "Value: " + val);
			//
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("health", "IOException!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dispatchTakePictureIntent(int requestCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {
			this.startActivityForResult(takePictureIntent, requestCode);
		} else {
			this.setResult(Registry.TAKE_PICTURE_NOT_AVAILABLE);
			this.finish();
		}
	}
}
