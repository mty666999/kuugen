package com.kuugen.modular.work.ipconfig.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Map;



@TableName("tb_work_ip")
public class TbWorkIpModel {
	 private String id;
	 @TableField(value = "ip_addr")
	 private String ipAddr;//ip地址 61.135.217.7(例)
	 private String port;
	 @TableField(value = "is_usable")
	 private String isUsable;//是否可用 0 是 1否
	 @TableField(value = "add_time")
	 private String addTime;
	 private String unable_time;
	 private String remarks;
	 private String password;
	 private String type;//类型

	 private String userName;
	
	 public  TbWorkIpModel() {
		// TODO Auto-generated method stub

	}
	 
	public TbWorkIpModel(Map ip) {
		// TODO Auto-generated constructor stub
		this.ipAddr = (String) ip.get("ip");
		this.password =(String) ip.get("password");
		this.port =(String) ip.get("port");
		this.type=(String) ip.get("type");
		this.userName=(String) ip.get("userName");
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIsUsable() {
		return isUsable;
	}

	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
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

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
