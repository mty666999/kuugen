package com.kuugen.modular.worm.service;

import org.jsoup.nodes.Document;



public interface  WormService {

	/**
	 * 基础的获取html信息方法
	 * @param url
	 * @return
	 */
	public Document getHtmlForUrl(String url, int count);
}
