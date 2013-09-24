package com.jcheed06.myhealthapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

/**
 * Represents an asynchronous login/registration task used to authenticate the
 * user.
 */
public class SendUrineResult extends AsyncTask<String, Integer, Boolean> {
	private String username,photo,message;
	
	private Context context;
	public SendUrineResult(Context context){
		this.context = context;
	}
	/**
	 * Parameter 0 = photo Parameter 1 = message Parameter 2 = username
	 */
	@Override
	protected Boolean doInBackground(String... parameters) {
		this.username=parameters[2];
		this.message=parameters[1];
		this.photo=parameters[0];
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Registry.BASE_API_URL
				+ Registry.SEND_URINE_TEST);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("picture", photo));
			nameValuePairs
					.add(new BasicNameValuePair("message", message));
			nameValuePairs
					.add(new BasicNameValuePair("username", username));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String r = "";
			
			while ((r = buffer.readLine()) != null) {
				sb.append(r);
			}
			
			JSONObject content = new JSONObject(sb.toString());
			
			if(content.get("status").equals("1")){
				return true;
			}
			
			buffer.close();
			
		} catch (JSONException e) {
			Log.e("JSONException", e.getMessage());
		} catch (ClientProtocolException e) {
			Log.e("ClientProtocolException ", e.getMessage());
		} catch (IOException e) {
			Log.e("IOException ", e.getMessage());
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		if (success) {
			Toast.makeText(context, photo + username + message, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(context, photo + username + message + "Error sending photo", Toast.LENGTH_LONG).show();
		}
	}
}