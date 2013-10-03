package com.jcheed06.myhealthapp.urinetest;

import java.io.BufferedReader;
import java.io.IOException;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.jcheed06.myhealthapp.Registry;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * An asynchronous task to send the result of a processed urine test
 */
public class SendUrineResult extends AsyncTask<UrineTestData, Integer, Boolean> {
	
	private Context context;
//	private String picture;
	
	public SendUrineResult(Context context){
		this.context = context;
	}
	
	/**
	 * Parameter 0 = photo Parameter 1 = message Parameter 2 = username
	 */
	@Override
	protected Boolean doInBackground(UrineTestData... parameters) {
		
		UrineTestData result = parameters[0];
		
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Registry.BASE_API_URL
				+ Registry.SEND_URINE_TEST);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			
			nameValuePairs
			.add(new BasicNameValuePair("picture", result.getPhoto()));
			nameValuePairs
					.add(new BasicNameValuePair("message", result.getMessage()));
			nameValuePairs
					.add(new BasicNameValuePair("username", result.getUsername()));

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		if (success) {
			Toast.makeText(context, "succes", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(context, "Error sending photo", Toast.LENGTH_LONG).show();
		}
	}

}