package com.jcheed06.myhealthapp.measurement.domain;

public abstract class Measurement {

	protected Integer id;
	
	protected Measurement(String id){
		this.id = this.id;
	}
	
	public abstract String getMeasurementValues();
	
}
