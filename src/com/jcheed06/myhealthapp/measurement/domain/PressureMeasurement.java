package com.jcheed06.myhealthapp.measurement.domain;

public class PressureMeasurement extends Measurement {

	private Integer hypoTension;
	private Integer hyperTension;
	
	public PressureMeasurement(){
		super(null);
	}

	public PressureMeasurement(String id, Integer hypoTension, Integer hyperTension) {
		super(id);
		this.setHyperTension(hyperTension);
		this.setHypoTension(hypoTension);
	}
	
	@Override
	public String getMeasurementValues() {
		StringBuilder builder = new StringBuilder();
		builder.append("HyperTension : " + hyperTension);
		builder.append(", ");
		builder.append("HypoTension : " + hypoTension);
		return builder.toString();
	}
	
	@Override
	public String toString(){
		return this.getMeasurementValues();
	}

	public Integer getHypoTension() {
		return hypoTension;
	}
	
	public void setHypoTension(Integer hypoTension) {
		this.hypoTension = hypoTension;
	}
	
	public Integer getHyperTension() {
		return hyperTension;
	}
	
	public void setHyperTension(Integer hyperTension) {
		this.hyperTension = hyperTension;
	}
}