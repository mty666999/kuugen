package com.kuugen.modular.work.ipconfig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.kuugen.modular.work.ipconfig.model.TbWorkIpModel;

import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface TbWorkIpMapper extends BaseMapper<TbWorkIpModel> {

	int getWorkIpCount(@Param("ip") String ip);

	void addWorkIp(TbWorkIpModel ipinfo);

	TbWorkIpModel getOne();
	Map<String,String> getOneMap();
	void delete(TbWorkIpModel ip);

	 
}
