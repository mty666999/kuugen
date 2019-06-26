/**
 * 微信公众平台开发模式(JAVA) SDK
 */
package com.kuugen.modular.wechat.api.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息数据对象
 */
public class Template extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private Map<String, TemplateItem> data = null;

	public Template(String touser, String template_id, String url, String topcolor) {
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.topcolor = topcolor;
	}

	public Template(String touser, String template_id) {
		this.touser = touser;
		this.template_id = template_id;
	}

	public Template(String touser, String template_id, String url) {
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, TemplateItem> getData() {
		return data;
	}

	public void setData(Map<String, TemplateItem> data) {  
		this.data = data;
	}

	public void push(String key, String value, String color) {
		if (this.data == null){
			this.data = new HashMap<String, TemplateItem>();
		}
		this.data.put(key, new TemplateItem(value, color));
	}

}
