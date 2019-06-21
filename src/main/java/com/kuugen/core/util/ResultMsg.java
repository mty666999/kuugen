package com.kuugen.core.util;


/**
 * 返回信息封装.
 * 
 * @author mty
 */
public class ResultMsg {

	private boolean success = true;// 是否成功
	private Object data;// 返回数据
	private String message;// 返回信息
	private String resultCode;// 返回编码
	public ResultMsg() {
	}

	public ResultMsg(boolean success, Object data, String message) {
		this.success = success;
		this.data = data;
		this.message = message;
	}
    
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
		        .append("message:"+this.message)
				.append("data:"+this.data)
				.append("success:"+this.success)
				.append("resultCode:"+this.resultCode)
				.toString();
	}

}
