package com.kuugen.modular.wechat.api;

import com.kuugen.modular.wechat.api.config.ApiConfig;
import com.kuugen.modular.wechat.api.entity.Template;
import com.kuugen.modular.wechat.api.enums.ResultType;
import com.kuugen.modular.wechat.api.response.BaseResponse;
import com.kuugen.modular.wechat.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateAPI extends BaseAPI {
	/**
	 * Logger for this class
	 */
	private static final Logger log = LoggerFactory.getLogger(TemplateAPI.class);

	public TemplateAPI(ApiConfig config) {
		super(config);
	}
	
	public ResultType sendTemplateData(Template templateData) {
        BeanUtil.requireNonNull(templateData, "TemplateData is null");
        log.debug("创建菜单.....");
        String url = BASE_API_URL + "cgi-bin/message/template/send?access_token=#";
        BaseResponse response = executePost(url, templateData.toJsonString());
        return ResultType.get(response.getErrcode());
    }

}
