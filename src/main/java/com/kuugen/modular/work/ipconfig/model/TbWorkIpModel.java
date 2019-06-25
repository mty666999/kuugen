package com.kuugen.modular.work.ipconfig.model;


import java.util.Map;

 


public class TbWorkIpModel {
	 private String id;
	 private String ip_addr;//ip地址 61.135.217.7(例)
	 private String port;
	 private String is_usable;//是否可用 0 是 1否
	 private String add_time;
	 private String unable_time;
	 private String remarks;
	 private String password;
	 private String type;//类型
	 private String user_name;
	
	 public  TbWorkIpModel() {
		// TODO Auto-generated method stub

	}
	 
	public TbWorkIpModel(Map ip) {
		// TODO Auto-generated constructor stub
		this.ip_addr = (String) ip.get("ip");
		this.password =(String) ip.get("password");
		this.port =(String) ip.get("port");
		this.type=(String) ip.get("type");
		this.user_name=(String) ip.get("userName");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp_addr() {
		return ip_addr;
	}
	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getIs_usable() {
		return is_usable;
	}
	public void setIs_usable(String is_usable) {
		this.is_usable = is_usable;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getUnable_time() {
		return unable_time;
	}
	public void setUnable_time(String unable_time) {
		this.unable_time = unable_time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	 
}
