package com.jcheed06.myhealthapp.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;

import com.jcheed06.myhealthapp.Registry;
import com.jcheed06.myhealthapp.tasks.UserLoginTask;

public final class RestClient {

	private SharedPreferences preferences; // TODO

	public RestClient() {
	}

	@SuppressLint("NewApi")
	public JSONObject login(String username, String password, UserLoginTask loginTask) {

		Log.e("RestClient", "Starting login process");

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Registry.BASE_API_URL + Registry.LOGIN_COMMAND);
		try {

			Log.e("RestClient", "Adding data");
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			nameValuePairs.add(new BasicNameValuePair("password", password));
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
			Log.e("RestClient",	"UnsupportedEncodingException " + ueex.getMessage());
			loginTask.cancel(true);
		} catch (IOException ioex) {
			Log.e("RestClient", "IOException " + ioex.getMessage());
			loginTask.cancel(true);
		} catch (Exception ex) {
			Log.e("RestClient", "Exception " + ex.getMessage());
			loginTask.cancel(true);
		}
		return null;
	}
}