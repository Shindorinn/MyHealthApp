package com.jcheed06.myhealthapp.measurement.domain;

public class PulseMeasurement extends Measurement {

	private Integer bpm;

	public PulseMeasurement() {
		super(null);
	}

	public PulseMeasurement(String id, Integer bpm) {
		super(id);
		this.setBPM(bpm);
	}

	@Override
	public String getMeasurementValues() {
		StringBuilder builder = new StringBuilder();
		builder.append("BPM : " + bpm);
		return builder.toString();
	}
	
	@Override
	public String toString(){
		return this.getMeasurementValues();
	}
	
	public void setBPM(Integer bpm){
		this.bpm = bpm;
	}
	
	public Integer getBPM(){
		return this.bpm;
	}
	
}
