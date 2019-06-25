package com.kuugen.core.common.exception;



/**
 * 爬虫请求失败异常
 * @author mty
 *
 */
@SuppressWarnings("serial")
public class WormException extends ApplicationException {

	public WormException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public WormException(String message) {
		super(message);
		
	}

	public WormException(Throwable cause) {
		super(cause);
		
	}

}
