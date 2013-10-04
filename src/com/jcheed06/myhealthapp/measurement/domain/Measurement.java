package com.jcheed06.myhealthapp.measurement.domain;

public abstract class Measurement {

	protected Integer id;
	
	protected Measurement(Integer id){
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public abstract String getType();
	
	public abstract String getMeasurementValues();
	
}
