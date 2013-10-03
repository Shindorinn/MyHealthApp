package com.jcheed06.myhealthapp.measurement;

public abstract class Measurement {

	protected Integer id;
	
	protected Measurement(String id){
		this.id = this.id;
	}
	
	public abstract String getMeasurementValues();
	
}
