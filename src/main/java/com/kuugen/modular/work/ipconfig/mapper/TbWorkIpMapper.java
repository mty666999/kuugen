package com.kuugen.modular.work.ipconfig.mapper;

import com.kuugen.modular.work.ipconfig.model.TbWorkIpModel;

import org.apache.ibatis.annotations.Param;


public interface TbWorkIpMapper {

	int getWorkIpCount(@Param("ip") String ip);

	void addWorkIp(TbWorkIpModel ipinfo);

	TbWorkIpModel getOne();

	void delete(TbWorkIpModel ip);

	 
}
