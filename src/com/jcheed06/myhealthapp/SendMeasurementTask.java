package com.jcheed06.myhealthapp;

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

import android.content.SharedPreferences;
import android.os.AsyncTask;

public class SendMeasurementTask extends BaseActivity {
	public SharedPreferences sp;

	public SendMeasurementTask(Object measurement) {
		this.sp = super.sp;
		new SendTask().execute(measurement);
	}

	class SendTask extends AsyncTask<Object, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Object... params) {
			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(Registry.BASE_API_URL
					+ Registry.SEND_MEASUREMENT_COMMAND);
			try {
				String type = "";
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						10);
				nameValuePairs.add(new BasicNameValuePair("username", sp
						.getString("username", "default")));

				if (params[0] instanceof PressureMeasurement) {
					PressureMeasurement pm = (PressureMeasurement) params[0];
					type = "0";
					nameValuePairs.add(new BasicNameValuePair("hypertension",
							"" + pm.getHyperTension()));
					nameValuePairs.add(new BasicNameValuePair("hypotension", ""
							+ pm.getHypoTension()));

				} else if (params[0] instanceof PulseMeasurement) {
					PulseMeasurement psm = (PulseMeasurement) params[0];
				} else if (params[0] instanceof ECGMeasurement) {
					ECGMeasurement ecgm = (ECGMeasurement) params[0];
				}

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String r = "";

				while ((r = buffer.readLine()) != null) {
					sb.append(r);
				}

				JSONObject content = new JSONObject(sb.toString());
				if (content.get("status").equals("1")) {
					return true;
				}

				buffer.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}

	}
}
