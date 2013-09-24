package com.jcheed06.myhealthapp.sendurinetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.jcheed06.myhealthapp.Registry;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;

public final class SendUrineTestRestClient {

	private SharedPreferences preferences; // TODO

	public SendUrineTestRestClient() {
	}

	@SuppressLint("NewApi")
	public JSONObject sendTest(String... parameters) {

		Log.e("RestClient", "Starting login process");

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Registry.BASE_API_URL
				+ Registry.LOGIN_COMMAND);
		try {

			Log.e("RestClient", "Adding data");
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			
			nameValuePairs.add(new BasicNameValuePair("username", "cristianhalman"));
			nameValuePairs.add(new BasicNameValuePair("password", "password"));
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			Log.e("RestClient", "HttpClient.execute()");
			HttpResponse response = httpclient.execute(httppost);
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			JsonReader reader = new JsonReader(buffer);
			JSONObject object = new JSONObject();
			reader.beginObject();
			
			while(reader.hasNext()){
				String name = reader.nextName();
				if(name.equals("status")){
					object.put("status", ""+reader.nextInt());
				}else if(name.equals("username")){
					object.put("username",reader.nextString());
				}else if(name.equals("id")){
					object.put("id",""+reader.nextInt());
				}
			}
			reader.close();
			return object;
		} catch (UnsupportedEncodingException ueex) {
			Log.e("RestClient",
					"UnsupportedEncodingException " + ueex.getMessage());
		} catch (IOException ioex) {
			Log.e("RestClient", "IOException " + ioex.getMessage());
		} catch (Exception ex) {
			Log.e("RestClient", "Exception " + ex.getMessage());
		}
		return null;
	}

	private JSONObject inputStreamToJSON(InputStream is) {
		String string = "";

		Log.e("RestClient inputStreamToJSON", is.toString());
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder response = new StringBuilder();

			while ((string = buffer.readLine()) != null) {
				response.append(string);
			}
			
			Log.d("response",response.toString());
			return new JSONObject(response.toString());

		} catch (Exception e) {
			Log.e("RestClient inputStreamToJSON", "Exception " + e.getMessage());
		}

		return null;
	}
}
