package com.jcheed06.myhealthapp.measurement;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;

public class ViewMeasurementsActivity extends Activity {

	TabHost tabHost;
	
	SharedPreferences spData;
	
	ListView pulseMeasurementsList;
	ListView pressureMeasurementsList;
	ListView ecgMeasurementsList;
	
	ArrayAdapter<Measurement> pulseListAdapter;
	ArrayAdapter<Measurement> pressureListAdapter;
	ArrayAdapter<Measurement> ecgListAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initializeActivity();
	}

	private void initializeActivity() {
		setContentView(R.layout.activity_view_measurements);

		this.spData = this.getSharedPreferences(Registry.SHARED_DATA_NAME, Registry.SHARED_DATA_CONTEXT);
		
		this.tabHost = (TabHost) this.findViewById(R.id.ViewMeasurementsTabhost);
		
		this.tabHost.addTab( this.tabHost.newTabSpec("pulse").setIndicator("Pulse").setContent(R.id.pulseTab));
		this.tabHost.addTab( this.tabHost.newTabSpec("ECG").setIndicator("ECG").setContent(R.id.ecgTab));
		this.tabHost.addTab( this.tabHost.newTabSpec("pressure").setIndicator("Pressure").setContent(R.id.pressureTab));
		
				
		this.pulseListAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_list_item_1);
		this.pressureListAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_list_item_1);
		this.ecgListAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_list_item_1);
		
		try{
					
			for(Measurement measurement : getPulseMeasurements()){
				this.pulseListAdapter.add(measurement);
			}
			
			for(Measurement measurement : getPressureMeasurements()){
				this.pressureListAdapter.add(measurement);
			}
			
			for(Measurement measurement : getECGMeasurements()){
				this.ecgListAdapter.add(measurement);
			}
						
			
		}catch(InterruptedException iex){
			DebugLogger.log_e("ViewMeasurementsActivity", iex.getMessage());
		}catch(ExecutionException ex){
			DebugLogger.log_e("ViewMeasurementsActivity", ex.getMessage());			
		}
	}

	private ArrayList<Measurement> getPulseMeasurements() throws InterruptedException, ExecutionException{
		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
		retriever.execute(Registry.RETRIEVE_PULSE_MEASUREMENTS, spData.getString("id", "3")); //TODO : Standard value needs to be replaced
		return retriever.get();
	}
	
	private ArrayList<Measurement> getPressureMeasurements() throws InterruptedException, ExecutionException{
		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
		retriever.execute(Registry.RETRIEVE_PRESSURE_MEASUREMENTS, spData.getString("id", "3")); //TODO : Standard value needs to be replaced
		return retriever.get();
	}
	
	private ArrayList<Measurement> getECGMeasurements() throws InterruptedException, ExecutionException{
		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
		retriever.execute(Registry.RETRIEVE_ECG_MEASUREMENTS, spData.getString("id", "3")); //TODO : Standard value needs to be replaced
		return retriever.get();
	}

//	private ArrayList<Measurement> getMeasurements() throws InterruptedException, ExecutionException {
//		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
//		retriever.execute(Registry.RETRIEVE_PULSE_MEASUREMENTS, spData.getString("id", "3")); //TODO : Standard value needs to be replaced
//		return retriever.get();
//	}

	
	
}
