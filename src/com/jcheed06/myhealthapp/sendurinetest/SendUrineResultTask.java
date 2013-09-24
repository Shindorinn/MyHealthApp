package com.jcheed06.myhealthapp.sendurinetest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.jcheed06.myhealthapp.MainActivity;
import com.jcheed06.myhealthapp.Registry;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Represents an asynchronous login/registration task used to authenticate the
 * user.
 */
public class SendUrineResultTask extends AsyncTask<String, Integer, Boolean> {
	private Context context;
	
	public SendUrineResultTask(Context context){
        this.context = context;

    }
	
	@Override
	protected Boolean doInBackground(String... parameters) {
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Registry.BASE_API_URL
				+ Registry.SEND_URINE_TEST);
		try {

			Log.e("RestClient", "Adding data");
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

			nameValuePairs.add(new BasicNameValuePair("username",
					"cristianhalman"));
			nameValuePairs.add(new BasicNameValuePair("password", "password"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			Log.e("RestClient", "HttpClient.execute()");
			HttpResponse response = httpclient.execute(httppost);

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));


		} catch (Exception e) {
			Log.e("Exception", e.getMessage());
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		if(success){
			Intent intent = new Intent(this.context, MainActivity.class);
			this.context.startActivity(intent);
		}else{
			
		}
		
	}
}