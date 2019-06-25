package com.kuugen.modular.stock.model;



 



public class TbStockMainModel {
	private String stock_id;   //id    
	private String stock_code; //股票代码
	private String stock_type;     //类型 sz深圳sh上海
	private String stock_name;     //股票名称
	private String stock_name_en;  //股票简称
	private String now_price;      //当前价格
	private String volume;         //成交量
	private String flow_stocks;    //流通股本
	private String tol_stocks;     //总股本
	private String pb;             //市净率
	private String pe;             //市盈率
	private String net_assets;     //美股净资产
	private String tol_val;        //总市值
	private String remarks;	//备注
	
	//查询用
	private String ave_price;//13均线数据
	
	
	public String getAve_price() {
		return ave_price;
	}
	public void setAve_price(String ave_price) {
		this.ave_price = ave_price;
	}
	public String getStock_id() {
		return stock_id;
	}
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	public String getStock_type() {
		return stock_type;
	}
	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public String getStock_name_en() {
		return stock_name_en;
	}
	public void setStock_name_en(String stock_name_en) {
		this.stock_name_en = stock_name_en;
	}
	public String getNow_price() {
		return now_price;
	}
	public void setNow_price(String now_price) {
		this.now_price = now_price;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getFlow_stocks() {
		return flow_stocks;
	}
	public void setFlow_stocks(String flow_stocks) {
		this.flow_stocks = flow_stocks;
	}
	public String getTol_stocks() {
		return tol_stocks;
	}
	public void setTol_stocks(String tol_stocks) {
		this.tol_stocks = tol_stocks;
	}
	public String getPb() {
		return pb;
	}
	public void setPb(String pb) {
		this.pb = pb;
	}
	public String getPe() {
		return pe;
	}
	public void setPe(String pe) {
		this.pe = pe;
	}
	public String getNet_assets() {
		return net_assets;
	}
	public void setNet_assets(String net_assets) {
		this.net_assets = net_assets;
	}
	public String getTol_val() {
		return tol_val;
	}
	public void setTol_val(String tol_val) {
		this.tol_val = tol_val;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}        

	
	 
}
