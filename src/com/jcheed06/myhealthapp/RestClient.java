package com.jcheed06.myhealthapp;

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

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;

public final class RestClient {

	private SharedPreferences preferences; // TODO

	public RestClient() {
	}

	@SuppressLint("NewApi")
	public JSONObject login(String username, String password) {

		// Working try
		// try {
		//
		// HttpClient httpclient = new DefaultHttpClient();
		//
		// HttpPost httppost = new HttpPost(Registry.BASE_API_URL
		// + Registry.LOGIN_COMMAND);
		//
		// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		// nameValuePairs.add(new BasicNameValuePair("username", username));
		// nameValuePairs.add(new BasicNameValuePair("password", password));
		//
		// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		//
		// HttpResponse response = httpclient.execute(httppost);
		//
		// HttpEntity entity = response.getEntity();
		//
		// InputStream is = entity.getContent();
		//
		// BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		//
		// JsonReader reader = new JsonReader(rd);
		//
		//
		// String success = "0";
		// int superuser = 0;
		// reader.beginObject();
		//
		// while (reader.hasNext()) {
		// String name = reader.nextName();
		// if (name.equals("status")) {
		// success = reader.nextString();
		// Log.e("Ezzence", "Hello! The value is: " + success);
		// } else if (name.equals("username")) {
		// // String message = reader.nextString();
		// // Log.e("Ezzence", "Hello! The value is: " + message);
		// } else if (name.equals("locked")) {
		// // locked = reader.nextString();
		// } else {
		// reader.skipValue();
		// }
		// }
		// reader.endObject();
		// reader.close();
		//
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// Try 2

		Log.e("RestClient", "Starting login process");

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Registry.BASE_API_URL
				+ Registry.LOGIN_COMMAND);
		try {

			Log.e("RestClient", "Adding data");
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			Log.e("RestClient", "HttpClient.execute()");
			HttpResponse response = httpclient.execute(httppost);
			Log.e("RestClient", response.getEntity().getContent().toString());

			Log.e("RestClient", "Recieved content");
			Log.e("RestClient", "Content : " + response.getEntity().getContent().toString() );
			
			
			
			JSONObject content = inputStreamToJSON(response.getEntity().getContent());
			return content;
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

			return new JSONObject(response.toString());

		} catch (Exception e) {
			Log.e("RestClient inputStreamToJSON", "Exception " + e.getMessage());
		}

		return null;
	}
}

//
// public final class RestClient {
// public static String API_URL = "http://myhealth.omninous.com/app";
// public static String CHARSET = "UTF-8";
//
// private String username;
// private String password;
//
// public RestClient(String username, String password){
// this.username = username;
// this.password = password;
// }
//
// public boolean login(){
// try{
// JSONObject result = this.put("/login");
//
// return result.getBoolean(MainActivity.REST_CALL_SUCCESS);
//
// }catch(JSONException ex){
// return false;
// }
//
// }
//
// public JSONObject get(String location) throws JSONException {
// try {
// HttpURLConnection httpConn = this.getHttpConnection(location);
// httpConn.connect();
// String json = this.convertStreamToString(httpConn.getInputStream());
// httpConn.disconnect();
// return (JSONObject) new JSONTokener(json).nextValue();
// }
// catch(Exception e) {
// return this.getJSONErrorObject();
// }
// }
//
// public JSONObject put(String location) throws JSONException {
// try {
// HttpURLConnection httpConn = this.getHttpConnection(location);
// httpConn.setRequestMethod("PUT");
// httpConn.connect();
// String json = this.convertStreamToString(httpConn.getInputStream());
// httpConn.disconnect();
// return (JSONObject) new JSONTokener(json).nextValue();
// }
// catch(Exception e) {
// return this.getJSONErrorObject();
// }
// }
//
// public JSONObject delete(String location) throws JSONException {
// try {
// HttpURLConnection httpConn = this.getHttpConnection(location);
// httpConn.setRequestMethod("DELETE");
// httpConn.connect();
// String json = this.convertStreamToString(httpConn.getInputStream());
// httpConn.disconnect();
// return (JSONObject) new JSONTokener(json).nextValue();
// }
// catch(Exception e) {
// return this.getJSONErrorObject();
// }
// }
//
// public JSONObject post(String location, ArrayList<NameValuePair> params)
// throws JSONException {
// try {
// HttpURLConnection httpConn = this.getHttpConnection(location);
// httpConn.setDoOutput(true);
// httpConn.setRequestProperty("Content-Type",
// "application/x-www-form-urlencoded;charset="+RestClient.CHARSET);
// httpConn.connect();
// this.writeParams(httpConn.getOutputStream(), params);
// String json = this.convertStreamToString(httpConn.getInputStream());
// httpConn.disconnect();
// return (JSONObject) new JSONTokener(json).nextValue();
// }
// catch(Exception e) {
// return this.getJSONErrorObject();
// }
// }
//
// private JSONObject getJSONErrorObject() throws JSONException{
// return new JSONObject("{success : false}");
// }
//
// private String getB64Auth(){
// String credentials = this.username+":"+this.password;
// String b64credentials = Base64.encodeToString(credentials.getBytes(),
// Base64.URL_SAFE|Base64.NO_WRAP);
// return "Basic "+b64credentials;
// }
//
// private void writeParams(OutputStream os, ArrayList<NameValuePair> params)
// throws IOException{
// StringBuilder sb = new StringBuilder();
// boolean first = true;
//
// for (NameValuePair pair : params){
// if (first){
// first = false;
// }else{
// sb.append("&");
// }
//
// sb.append(String.format("%s=%s",
// URLEncoder.encode(pair.getName(), RestClient.CHARSET),
// URLEncoder.encode(pair.getValue(), RestClient.CHARSET)));
//
// }
//
// os.write(sb.toString().getBytes(RestClient.CHARSET));
// os.close();
// }
//
// private String convertStreamToString(InputStream is) throws IOException {
// BufferedReader reader = new BufferedReader(new InputStreamReader(is));
// StringBuilder sb = new StringBuilder();
// String line = null;
//
// while ((line = reader.readLine()) != null) {
// sb.append(line);
// }
//
// is.close();
//
// return sb.toString();
// }
//
// private HttpURLConnection getHttpConnection(String location) throws
// IOException {
// URL url = new URL(RestClient.API_URL+location);
// HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
// httpConn.setChunkedStreamingMode(0);
// httpConn.addRequestProperty("Authorization", getB64Auth());
// return httpConn;
// }
// }
