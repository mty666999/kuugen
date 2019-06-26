package com.kuugen.modular.wechat.api.entity;

import com.kuugen.modular.wechat.util.JSONUtil;

/**
 * 抽象实体类
 * 
 * @author peiyu
 */
public abstract class BaseModel implements Model {
	@Override
	public String toJsonString() {
		return JSONUtil.toJson(this);
	}
}
