package com.kuugen.modular.wechat.controller;


import com.kuugen.core.quartz.QuartzThread;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbStockMainModel;
import com.kuugen.modular.stock.model.TbSysUserModel;
import com.kuugen.modular.stock.userstock.service.TbSysUserService;
import com.kuugen.modular.wechat.message.BaseMsg;
import com.kuugen.modular.wechat.message.TextMsg;
import com.kuugen.modular.wechat.message.req.BaseEvent;
import com.kuugen.modular.wechat.message.req.MenuEvent;
import com.kuugen.modular.wechat.message.req.TextReqMsg;
import com.kuugen.modular.wechat.servlet.WeixinControllerSupport;
import com.kuugen.modular.wechat.util.ConfKit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/weixin")
public class WeChatMsgController extends WeixinControllerSupport {
	/**
	 * Logger for this class
	 */

	private static final Log logger = LogFactory.getLog(WeChatMsgController.class);
	@Autowired
	private TbSysUserService tbSysUserService;
	
	/*@Autowired
	private TWeChatUserService tWeChatUserService;

	@Autowired
	private MsgFctService msgFctService;
	
	@Autowired
	private AffordInfoService affordInfoService;*/

	// 设置TOKEN，用于绑定微信服务器
	@Override
	protected String getToken() {
		return ConfKit.get("Token");
	}

	// 使用安全模式时设置：APPID
	@Override
	protected String getAppId() {
		return ConfKit.get("AppId");
	}

	// 使用安全模式时设置：密钥
	@Override
	protected String getAESKey() {
		return ConfKit.get("AESKey");
	}

	/**
	 * 处理Text微信消息
	 */
	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		String content = msg.getContent();
		ResultMsg rs =new ResultMsg();
		logger.debug("用户发送到服务器的内容:{" + content + "}");
		// 判断微信号是否绑定
		TbSysUserModel user = tbSysUserService.getUserByOpenId(msg.getFromUserName());
		
		String openId=msg.getFromUserName();
		// 消息订阅 信息处理
		content = content.replace("，", ","); // 分割符号处理
		content = content.replace(".", ",");// 分割符号处理
		String[] contentArr = content.split(",");
		if(contentArr.length == 2){
			String msgType =  contentArr[0];
			msgType = msgType.toUpperCase();
			if("BD".equals(msgType)){
				rs=tbSysUserService.bindWx(openId,contentArr[1]);
			}else if("GZ".equals(msgType)){
				if (user == null) {
					return new TextMsg("您当前微信未与平台帐号绑定,请先输入 'BD'+逗号+姓名 发送信息进行绑定用户!");
				}
				rs=tbSysUserService.gzStock(user.getUser_id(),contentArr[1]);
			}
			return new TextMsg(rs.getMessage());
		 
		}else {
			TextMsg result=new TextMsg();
			result.add("您好，下面是快捷命令方法：").addln();
			result.add("1  绑定账号:").addln();
			result.add("   输入 'BD'+逗号或点+姓名 发送信息进行绑定用户").addln();
			result.add("2  关注股票:").addln();
			result.add("   输入 'GZ'+逗号或点+股票编码 发送信息关注股票").addln();
			result.add("3  查询报告:").addln();
			result.add("   输入 'CXBG'+逗号或点+股票编码 发送信息查询该股票的数据").addln();
			result.addln();
		 
			return result;
		}
		 
	}

	/**
	 * 取消关注
	 */
	@Override
	protected BaseMsg handleUnsubscribe(BaseEvent event) {
		// 取消关注 取消帐号绑定
		/*TWeChatUser user = tWeChatUserService.getUserByOpenId(event.getFromUserName());
		if (user != null) {
			tWeChatUserService.updateOpenIdByUserId(false, user.getUserId(), null);
		}*/
		return null;
	}

	/**
	 * 处理对应菜单Click事件微信消息
	 */
	@Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		logger.debug(event.toString());
		// 获取关注股票列表
		if (event.getEventKey().equals("KEY_STOCKLIST")) {
			List<TbStockMainModel> stocklist =tbSysUserService.getUserStockByOpenId(event.getFromUserName());
			
			TextMsg msg = new TextMsg();
			if(stocklist.size() >0){
				for(int i=0;i<stocklist.size();i++){
					TbStockMainModel stockinfo =stocklist.get(i);
					msg.add(stockinfo.getStock_name());
					msg.add(" "+stockinfo.getStock_code());
					msg.add(" "+stockinfo.getNow_price());
					msg.addln();
				}
			}else{
				msg.add("没有关注股票");
			}
			return msg;
		//
		}else if(event.getEventKey().equals("KEY_STRONG_STOCK")){
			//返回关注的13均线上方股票
			List<TbStockMainModel> stocklist =tbSysUserService.getUserStrongStock(event.getFromUserName()); 
			TextMsg msg = new TextMsg();
			
			if(stocklist.size() >0){
				msg.addln("13均线上方的");
				for(int i=0;i<stocklist.size();i++){
					TbStockMainModel stockinfo =stocklist.get(i);
					msg.add(stockinfo.getStock_name());
					msg.add(" "+stockinfo.getStock_code());
					msg.add(" "+stockinfo.getNow_price());
					msg.add(" 均线"+stockinfo.getAve_price());
					msg.addln();
				}
			}else{
				msg.add("没有符合的股票");
			}
			return msg;
            
		}else if(event.getEventKey().equals("KEY_WEAK_STOCK")){
			//返回关注的13均线下方股票
			List<TbStockMainModel> stocklist =tbSysUserService.getUserWeakStock(event.getFromUserName()); 
			TextMsg msg = new TextMsg();
			
			if(stocklist.size() >0){
				msg.addln("13均线下方的");
				for(int i=0;i<stocklist.size();i++){
					TbStockMainModel stockinfo =stocklist.get(i);
					msg.add(stockinfo.getStock_name());
					msg.add(" "+stockinfo.getStock_code());
					msg.add(" "+stockinfo.getNow_price());
					msg.add(" 均线"+stockinfo.getAve_price());
					msg.addln();
				}
			}else{
				msg.add("没有符合的股票");
			}
			return msg;
            
		}else {
			return new TextMsg("功能正在开发中...");
		}
	}

	/**
	 * 处理对应菜单View事件微信消息
	 */
	@Override
	protected BaseMsg handleMenuViewEvent(MenuEvent event) {
		logger.debug(event.toString());
		return null;
	}

}
