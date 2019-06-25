package com.kuugen.modular.stock.model;

public class TbStockAvgrageModel {
	private String id;
	private String stock_code;
	private String day_num;
	private String date;
	private String update_time;
	private String ave_price;
	
	public String getAve_price() {
		return ave_price;
	}
	public void setAve_price(String ave_price) {
		this.ave_price = ave_price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	public String getDay_num() {
		return day_num;
	}
	public void setDay_num(String day_num) {
		this.day_num = day_num;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	} 
	
}
