package com.jcheed06.myhealthapp.measurement.view;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;
import com.jcheed06.myhealthapp.R.layout;
import com.jcheed06.myhealthapp.R.menu;
import com.jcheed06.myhealthapp.measurement.RetrieveMeasurementsTask;
import com.jcheed06.myhealthapp.measurement.domain.Measurement;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewECGMeasurementsActivity extends Activity {


	SharedPreferences spData;
	
	ListView ecgMeasurementsList;
	ArrayAdapter<Measurement> ecgListAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initializeActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_ecgmeasurements, menu);
		return true;
	}

	private void initializeActivity(){
		this.setContentView(R.layout.activity_view_ecgmeasurements);
		
		this.spData = this.getSharedPreferences(Registry.SHARED_DATA_NAME, Registry.SHARED_DATA_CONTEXT);
		
		this.ecgListAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_list_item_1);
		
		try{
			for(Measurement measurement : getECGMeasurements()){
				this.ecgListAdapter.add(measurement);
			}
						
			
		}catch(InterruptedException iex){
			DebugLogger.log_e("ViewMeasurementsActivity", iex.getMessage());
		}catch(ExecutionException ex){
			DebugLogger.log_e("ViewMeasurementsActivity", ex.getMessage());			
		}
		
	}
	
	private ArrayList<Measurement> getECGMeasurements() throws InterruptedException, ExecutionException{
		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
		retriever.execute(Registry.RETRIEVE_ECG_MEASUREMENTS, spData.getString("id", "3")); //TODO : Standard value needs to be replaced
		return retriever.get();
	}
}
