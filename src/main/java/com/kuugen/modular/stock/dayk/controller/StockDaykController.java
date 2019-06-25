package com.kuugen.modular.stock.dayk.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




/**
 * 日k相关数据
 * @author mty
 *
 */
@Controller
@RequestMapping(value = "/stock/dayk", produces = "text/html;charset=UTF-8")
public class StockDaykController {
	/**
	 * Logger for this class
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	 
}
