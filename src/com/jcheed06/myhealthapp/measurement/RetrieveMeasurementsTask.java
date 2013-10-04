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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.Registry;
import com.jcheed06.myhealthapp.measurement.domain.ECGMeasurement;
import com.jcheed06.myhealthapp.measurement.domain.Measurement;
import com.jcheed06.myhealthapp.measurement.domain.PressureMeasurement;
import com.jcheed06.myhealthapp.measurement.domain.PulseMeasurement;

public class RetrieveMeasurementsTask extends AsyncTask<String, Void, ArrayList<Measurement>> {

	@Override
	protected ArrayList<Measurement> doInBackground(String... contactInfo) {
		
		ArrayList<Measurement> toReturn = new ArrayList<Measurement>();
		String urlToUse;
		
		String measurementType = contactInfo[0];
		
		if(measurementType.equals(Registry.RETRIEVE_PULSE_MEASUREMENTS)){
			urlToUse = Registry.BASE_API_URL + Registry.RETRIEVE_PULSE_MEASUREMENTS;
		}else if(measurementType.equals(Registry.RETRIEVE_PRESSURE_MEASUREMENTS)){
			urlToUse = Registry.BASE_API_URL + Registry.RETRIEVE_PRESSURE_MEASUREMENTS;
		}else if(measurementType.equals(Registry.RETRIEVE_ECG_MEASUREMENTS)){
			urlToUse = Registry.BASE_API_URL + Registry.RETRIEVE_ECG_MEASUREMENTS;
		}else{
			throw new IllegalArgumentException("Wrong retrieval command!");
		}
				
		String id = contactInfo[1];
		
		DebugLogger.log_i("RetrieveMeasurementsTask", "id : " + id);
		
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpPost httppost = new HttpPost(urlToUse);

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("user_id", id));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        HttpResponse response = httpclient.execute(httppost);
	        
	        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8")); 
		    StringBuilder sb = new StringBuilder();
		    String string = "";
		    while ((string = buffer.readLine()) != null) {
		    	sb.append(string);
		    }
		    
		    DebugLogger.log_d("RetrieveMeasurementsTask", "Message : " + sb.toString());
		    
		    JSONObject content = new JSONObject(sb.toString());
	        buffer.close();	        
	        
	       	if (content.get("status").toString().equals("1")) {
	        	
	        	JSONArray responses = content.getJSONArray("id");
	        	inflateResponse(toReturn, responses, measurementType);
	
	        	
	        } else if (content.get("status").toString().equals("0")) {
	        	DebugLogger.log_i("RetrieveMeasurementsTask", "No measurements retrieved");
	        }       	
	    }catch(JSONException ex){
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
	    } catch (UnsupportedEncodingException ex) {
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
		} catch (IllegalStateException ex) {
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
		} catch (IOException ex) {
	    	DebugLogger.log_e("RetrieveMeasurementsTask", ex.getMessage());
		}
	    
	    DebugLogger.log_i("RetrieveMeasurementsTask", "returning, ArrayList<Measurement> :" + toReturn.toString());
	    
		return toReturn;
	}

	private void inflateResponse(ArrayList<Measurement> toReturn, JSONArray responses, String measurementType) {
		if(measurementType.equals(Registry.RETRIEVE_PULSE_MEASUREMENTS)){
			inflatePulseMeasurements(toReturn, responses);
		}else if(measurementType.equals(Registry.RETRIEVE_PRESSURE_MEASUREMENTS)){
			inflatePressureMeasurements(toReturn, responses);
		}else if(measurementType.equals(Registry.RETRIEVE_ECG_MEASUREMENTS)){
			inflateECGMeasurements(toReturn, responses);
		}else{
			throw new UnsupportedOperationException("Unknown Measurement Type!");
		}
	}

	private ArrayList<Measurement> inflatePulseMeasurements(ArrayList<Measurement> toReturn, JSONArray responses) {
		for(int index = 0; index < responses.length(); index++){
			try {
				JSONObject container = new JSONObject(responses.getString(index));
				JSONObject measurement = new JSONObject(container.getString(container.names().getString(0)));
				Log.i("RetrieveMeasurementTask", "inflatePulseMeasurement -> JSON : " + measurement);
				toReturn.add(new PulseMeasurement(
							measurement.get("id").toString(), 
							measurement.getInt("bpm")));
			} catch (JSONException e) {
				DebugLogger.log_e("RetrieveMeasurementsTask", e.getMessage());
			}
		}
		return toReturn;
	}

	private ArrayList<Measurement> inflatePressureMeasurements(ArrayList<Measurement> toReturn, JSONArray responses) {
		for(int index = 0; index < responses.length(); index++){
			try {
				JSONObject container = new JSONObject(responses.getString(index));
				JSONObject measurement = new JSONObject(container.getString(container.names().getString(0)));
				Log.i("RetrieveMeasurementTask", "inflatePressureMeasurement -> JSON : " + measurement);
				toReturn.add(new PressureMeasurement(
							measurement.get("id").toString(),
							measurement.getInt("hypotension"),
							measurement.getInt("hypertension")));
			} catch (JSONException e) {
				DebugLogger.log_e("RetrieveMeasurementsTask", e.getMessage());
			}
		}
		return toReturn;
	}

	private ArrayList<Measurement> inflateECGMeasurements(ArrayList<Measurement> toReturn, JSONArray responses) {
		for(int index = 0; index < responses.length(); index++){
			try {
				JSONObject container = new JSONObject(responses.getString(index));
				JSONObject measurement = new JSONObject(container.getString(container.names().getString(0)));
				Log.i("RetrieveMeasurementTask", "inflateECGMeasurement -> JSON : " + measurement);
				toReturn.add(new ECGMeasurement(
							measurement.get("id").toString(),
							measurement.getInt("printerval"),
							measurement.getInt("prsegment"),
							measurement.getInt("qrscomplex"),
							measurement.getInt("stsegment"),
							measurement.getInt("qtinterval"),
							measurement.getInt("qtrough"),
							measurement.getInt("rpeak"),
							measurement.getInt("strough"),
							measurement.getInt("tpeak"),
							measurement.getInt("ppeak")));
			} catch (JSONException e) {
				DebugLogger.log_e("RetrieveMeasurementsTask", e.getMessage());
			}
		}
		return toReturn;
	}

}
