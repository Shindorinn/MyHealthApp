package com.jcheed06.myhealthapp.measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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

import com.jcheed06.myhealthapp.BaseActivity;
import com.jcheed06.myhealthapp.Registry;
import com.jcheed06.myhealthapp.measurement.domain.ECGMeasurement;
import com.jcheed06.myhealthapp.measurement.domain.PressureMeasurement;
import com.jcheed06.myhealthapp.measurement.domain.PulseMeasurement;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;

public class SendMeasurementTask extends BaseActivity {
	public static SharedPreferences sp;

	public SendMeasurementTask(Object measurement) {
		this.sp = super.sp;
		new SendTask().execute(measurement);
	}

	class SendTask extends AsyncTask<Object, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Object... params) {
			int num = 0;
			if (params[0] instanceof PulseMeasurement) {
				num = 4;
			} else if (params[0] instanceof PressureMeasurement) {
				num = 5;
			} else if (params[0] instanceof ECGMeasurement) {
				num = 13;
			}

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(Registry.BASE_API_URL
					+ Registry.SEND_MEASUREMENT_COMMAND);
			try {
				String type = "";
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						num);
				nameValuePairs.add(new BasicNameValuePair("username",
						"cristianhalman"));

				java.util.Date dt = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				nameValuePairs.add(new BasicNameValuePair("timestamp", sdf
						.format(dt)));
				if (params[0] instanceof PressureMeasurement) {
					PressureMeasurement pm = (PressureMeasurement) params[0];
					type = "0";
					nameValuePairs.add(new BasicNameValuePair("hypertension",
							"" + pm.getHyperTension()));
					nameValuePairs.add(new BasicNameValuePair("hypotension", ""
							+ pm.getHypoTension()));

				} else if (params[0] instanceof PulseMeasurement) {
					PulseMeasurement psm = (PulseMeasurement) params[0];
					type = "1";
					nameValuePairs.add(new BasicNameValuePair("bpm", ""
							+ psm.getBPM()));

					Log.d("myhealth", "In pulse: " + psm.getBPM());

				} else if (params[0] instanceof ECGMeasurement) {
					ECGMeasurement ecgm = (ECGMeasurement) params[0];
					type = "2";
					nameValuePairs.add(new BasicNameValuePair("printerval", ""
							+ ecgm.getPrinterval()));
					nameValuePairs.add(new BasicNameValuePair("prsegment", ""
							+ ecgm.getPrsegment()));
					nameValuePairs.add(new BasicNameValuePair("qrscomplex", ""
							+ ecgm.getQrscomplex()));
					nameValuePairs.add(new BasicNameValuePair("stsegment", ""
							+ ecgm.getStsegment()));
					nameValuePairs.add(new BasicNameValuePair("qtinterval", ""
							+ ecgm.getQtinterval()));
					nameValuePairs.add(new BasicNameValuePair("qtrough", ""
							+ ecgm.getQtrough()));
					nameValuePairs.add(new BasicNameValuePair("rpeak", ""
							+ ecgm.getRpeak()));
					nameValuePairs.add(new BasicNameValuePair("strough", ""
							+ ecgm.getStrough()));
					nameValuePairs.add(new BasicNameValuePair("tpeak", ""
							+ ecgm.getTpeak()));
					nameValuePairs.add(new BasicNameValuePair("ppeak", ""
							+ ecgm.getPpeak()));
				}
				nameValuePairs.add(new BasicNameValuePair("type", "" + type));

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
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}

	}
}
