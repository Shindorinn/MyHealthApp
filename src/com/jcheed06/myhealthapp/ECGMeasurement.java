package com.jcheed06.myhealthapp;

public class ECGMeasurement {
	
	private Integer printerval;
	private Integer prsegment;
	private Integer qrscomplex;
	private Integer stsegment;
	private Integer qtinterval;
	private Integer qtrough;
	private Integer rpeak;
	private Integer strough	;
	private Integer tpeak;
	private Integer ppeak;
	
	public Integer getPpeak() {
		return ppeak;
	}
	public void setPpeak(Integer ppeak) {
		this.ppeak = ppeak;
	}
	public Integer getQtrough() {
		return qtrough;
	}
	public void setQtrough(Integer qtrough) {
		this.qtrough = qtrough;
	}
	public Integer getRpeak() {
		return rpeak;
	}
	public void setRpeak(Integer rpeak) {
		this.rpeak = rpeak;
	}
	public Integer getStrough() {
		return strough;
	}
	public void setStrough(Integer strough) {
		this.strough = strough;
	}
	public Integer getTpeak() {
		return tpeak;
	}
	public void setTpeak(Integer tpeak) {
		this.tpeak = tpeak;
	}

	public Integer getPrinterval() {
		return printerval;
	}
	public void setPrinterval(Integer printerval) {
		this.printerval = printerval;
	}
	public Integer getPrsegment() {
		return prsegment;
	}
	public void setPrsegment(Integer prsegment) {
		this.prsegment = prsegment;
	}
	public Integer getQrscomplex() {
		return qrscomplex;
	}
	public void setQrscomplex(Integer qrscomplex) {
		this.qrscomplex = qrscomplex;
	}
	public Integer getStsegment() {
		return stsegment;
	}
	public void setStsegment(Integer stsegment) {
		this.stsegment = stsegment;
	}
	public Integer getQtinterval() {
		return qtinterval;
	}
	public void setQtinterval(Integer qtinterval) {
		this.qtinterval = qtinterval;
	}
}
