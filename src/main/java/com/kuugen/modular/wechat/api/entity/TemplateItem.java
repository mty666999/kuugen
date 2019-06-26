package com.kuugen.modular.wechat.api.entity;


public class TemplateItem extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String value;
	private String color = "#000000";

	public TemplateItem(String value) {
		this.value = value;
	}

	public TemplateItem(String value, String color) {
		this.value = value;
		this.color = color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getColor() {
		return color;
	}
}
