package com.jcheed06.myhealthapp.measurement;

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
import org.json.JSONException;
import org.json.JSONObject;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.Registry;

import android.os.AsyncTask;
import android.util.Log;

public class RetrieveMeasurementsTask extends AsyncTask<String, Void, ArrayList<Measurement>> {

	@Override
	protected ArrayList<Measurement> doInBackground(String... contactInfo) {
		
		ArrayList<Measurement> toReturn = new ArrayList<Measurement>();
		String id = contactInfo[0];
		
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpPost httppost = new HttpPost(Registry.BASE_API_URL + Registry.RETRIEVE_MEASUREMENTS_COMMAND);

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
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
	        Log.e("H2:", "content: " + content.toString());
	        
//	        if (content.get("status").toString().equals("1")) {
//	        	
//	        } else if (content.get("status").toString().equals("1")) {
//	        	spEdit.putBoolean("loggedIn", true);
//	        	spEdit.putString("id", (String) content.get("id"));
//	        	spEdit.putString("username", username);
//	        	spEdit.commit();
//	        	incorrectLogins = 0;
//	        	Log.e("L1:", "Login ok");
//	        	return true;
//	        } else if (content.get("status").toString().equals("0")) {
//	        	Log.e("L2:", "Login NOT ok");
//	        	if (incorrectLogins < 2) {
//	        		incorrectLogins++;
//	        		Log.e("L3:", "incorrectLogins: " + incorrectLogins);
//				} else {
//					Log.e("L4:", "Block user");
//					// block user
//					httppost = new HttpPost(Registry.BASE_API_URL + "/block");
//					nameValuePairs = new ArrayList<NameValuePair>(2);
//			        nameValuePairs.add(new BasicNameValuePair("username", username));
//			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			        httpclient.execute(httppost);
//			        Log.e("L5:", "User has been blocked!");
//			        incorrectLogins = 0;
//				}
//	        }
		// TODO Auto-generated method stub
	    }catch(JSONException ex){
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
	    } catch (UnsupportedEncodingException ex) {
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
		} catch (IllegalStateException ex) {
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
		} catch (IOException ex) {
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
		}
		return toReturn;
	}

}