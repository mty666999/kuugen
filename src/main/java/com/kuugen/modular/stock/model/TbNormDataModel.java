package com.kuugen.modular.stock.model;

public class TbNormDataModel {
	
	private String id;
	private String norm_id;
	private String stock_code;
	private String state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNorm_id() {
		return norm_id;
	}
	public void setNorm_id(String norm_id) {
		this.norm_id = norm_id;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
