package com.kuugen.modular.wechat.util;

import com.kuugen.modular.wechat.api.config.ApiConfig;

public class APIConfigUtils {

	/**
	 * 默认公众号对应的 ApiConfig
	 */
	private static ApiConfig config = null;

	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可
	 */
	public static ApiConfig getApiConfig() {
		if (APIConfigUtils.config == null) {
			String appId = ConfKit.get("AppId");
			String appSecret = ConfKit.get("AppSecret");
			//mty 开启jsApi
			APIConfigUtils.config = new ApiConfig(appId, appSecret,true);
		}
		return APIConfigUtils.config;
	}

}
