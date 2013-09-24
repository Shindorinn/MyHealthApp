package com.jcheed06.myhealthapp.sendurinetest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.jcheed06.myhealthapp.HomeActivity;
import com.jcheed06.myhealthapp.Registry;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.JsonReader;
import android.util.Log;

/**
 * Represents an asynchronous login/registration task used to authenticate the
 * user.
 */
public class JSONReceiver extends AsyncTask<HashMap<String,String>, Integer, Boolean> {

	@Override
	protected Boolean doInBackground(HashMap<String, String>... parameters) {
		HashMap<String, String>[] hm = parameters;
		HashMap<String,String> urlmap = hm[0];
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(urlmap.get("url"));
		try {

			Log.e("RestClient", "Adding data");
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			
			HashMap<String, String> namevaluemap = hm[1];
			for (Map.Entry<String, String> entry : namevaluemap.entrySet()){
		        nameValuePairs.add(new BasicNameValuePair(entry.getKey(),
		        		entry.getValue()));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			Log.e("RestClient", "HttpClient.execute()");
			HttpResponse response = httpclient.execute(httppost);

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			JsonReader reader = new JsonReader(buffer);
			StringBuilder object = new StringBuilder();
			reader.beginObject();
			String name;
			while ((name= reader.nextName())!=null){
				
			}
			reader.close();

		} catch (Exception e) {
			Log.e("Exception", e.getMessage());
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		if(success){
			Intent intent = new Intent(this.context, HomeActivity.class);
			this.context.startActivity(intent);
		}else{
			
		}
		
	}
}