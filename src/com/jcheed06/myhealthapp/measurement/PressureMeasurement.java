package com.jcheed06.myhealthapp.measurement;

public class PressureMeasurement extends Measurement {

	public PressureMeasurement(String id, Integer hypoTension, Integer hyperTension) {
		super(id);
		this.setHyperTension(hyperTension);
		this.setHypoTension(hypoTension);
	}

	private Integer hypoTension;
	private Integer hyperTension;
	
	
	public Integer getHypoTension() {
		return hypoTension;
	}
	
	@Override
	public String getMeasurementValues() {
		StringBuilder builder = new StringBuilder();
		builder.append("HyperTension : " + hyperTension);
		builder.append("HypoTension : " + hypoTension);
		return builder.toString();
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