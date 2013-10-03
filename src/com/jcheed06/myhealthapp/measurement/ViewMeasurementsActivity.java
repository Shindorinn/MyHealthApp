package com.jcheed06.myhealthapp.measurement;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;

public class ViewMeasurementsActivity extends Activity {

	ArrayAdapter<Measurement> listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initializeActivity();
	}

	private void initializeActivity() {
		setContentView(R.layout.activity_view_measurements);
		try{
		
			this.listAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_list_item_1);
			for(Measurement measurement : getMeasurements()){
				this.listAdapter.add(measurement);
			}
		}catch(InterruptedException iex){
			DebugLogger.log_e("ViewMeasurementsActivity", iex.getMessage());
		}catch(ExecutionException ex){
			DebugLogger.log_e("ViewMeasurementsActivity", ex.getMessage());			
		}
	}

	private ArrayList<Measurement> getMeasurements() throws InterruptedException, ExecutionException {
		SharedPreferences spData = this.getSharedPreferences(Registry.SHARED_DATA_NAME, Registry.SHARED_DATA_CONTEXT);
		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
		retriever.execute(spData.getString("id", "3")); //TODO : Standard value needs to be replaced
		return retriever.get();
	}

}
