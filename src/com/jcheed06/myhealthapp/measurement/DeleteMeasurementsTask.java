package com.jcheed06.myhealthapp.measurement;

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
import org.json.JSONObject;

import android.os.AsyncTask;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.Registry;

public class DeleteMeasurementsTask extends AsyncTask<String, Void, Boolean> {

	@Override
	protected Boolean doInBackground(String... values) {
		String type = values[0];
		String id   = values[1];
		
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();	    
	    HttpPost httppost 	  = new HttpPost(Registry.BASE_API_URL + Registry.DELETE_MEASUREMENT_COMMAND);

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("type", type));
	        nameValuePairs.add(new BasicNameValuePair("id", id));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        HttpResponse response = httpclient.execute(httppost);
	        
	        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8")); 
		    StringBuilder sb = new StringBuilder();
		    String string = "";
		    while ((string = buffer.readLine()) != null) {
		    	sb.append(string);
		    }
		    
		    JSONObject content = new JSONObject(sb.toString());
	        buffer.close();
	        
	       	if (content.get("status").toString().equals("1")) {
	        	return true;
	        }
	    } catch(Exception ex){
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
	    }
	    
		return false;
	}
}