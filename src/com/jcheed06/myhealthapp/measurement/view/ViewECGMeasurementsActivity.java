package com.jcheed06.myhealthapp.measurement.view;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.jcheed06.myhealthapp.DebugLogger;
import com.jcheed06.myhealthapp.R;
import com.jcheed06.myhealthapp.Registry;
import com.jcheed06.myhealthapp.R.layout;
import com.jcheed06.myhealthapp.R.menu;
import com.jcheed06.myhealthapp.measurement.DeleteMeasurementsTask;
import com.jcheed06.myhealthapp.measurement.RetrieveMeasurementsTask;
import com.jcheed06.myhealthapp.measurement.domain.Measurement;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewECGMeasurementsActivity extends Activity {

	protected int position;
	
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
		this.ecgMeasurementsList = (ListView) this.findViewById(R.id.listView_ecgMeasurements);
		
		this.ecgListAdapter = new ArrayAdapter<Measurement>(this, android.R.layout.simple_list_item_1);
		this.ecgMeasurementsList.setAdapter(ecgListAdapter);
		
		this.createOnItemClickListener();
		
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
	
	private void createOnItemClickListener() {
		ecgMeasurementsList.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

		position = pos;

		new AlertDialog.Builder(ViewECGMeasurementsActivity.this).setMessage(
						getResources().getString(R.string.delete_confirmation))
						.setPositiveButton(getResources().getString(R.string.button_text_yes),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,	int which) {
								Measurement measurement = ecgListAdapter.getItem(position);
								Integer thisId = measurement.getId();
								String thisType = measurement.getType();

								DeleteMeasurementsTask deletemeasurement = new DeleteMeasurementsTask();
								deletemeasurement.execute(thisType,	thisId.toString());

								try {
									if (deletemeasurement.get()) {
										ecgListAdapter.remove(measurement);
									} else {
										Toast.makeText(
												getBaseContext(),
												getResources().getString(R.string.delete_error),
												Toast.LENGTH_SHORT);
									}
								} catch (InterruptedException e) {
									DebugLogger.log_e("ViewPressureMeasurementsActivity : ItemOnClickListener", e.getMessage());
								} catch (ExecutionException e) {
									DebugLogger.log_e("ViewPressureMeasurementsActivity : ItemOnClickListener", e.getMessage());
								}
							}
						}).setNegativeButton(getResources().getString(R.string.button_text_no),
								new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,	int which) {

							}
						}).show();
			}
		});
	}

	
	private ArrayList<Measurement> getECGMeasurements() throws InterruptedException, ExecutionException{
		RetrieveMeasurementsTask retriever = new RetrieveMeasurementsTask();
		retriever.execute(Registry.RETRIEVE_ECG_MEASUREMENTS, spData.getString("id", "3")); //TODO : Standard value needs to be replaced
		return retriever.get();
	}
}
