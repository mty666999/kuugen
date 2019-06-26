package com.kuugen.modular.wechat.controller;


import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.stock.model.TbWxMenuModel;
import com.kuugen.modular.stock.stockinfo.service.impl.StockMainServiceImpl;
import com.kuugen.modular.stock.userstock.mapper.TbWxMenuMapper;
import com.kuugen.modular.wechat.api.MenuAPI;
import com.kuugen.modular.wechat.api.UserAPI;
import com.kuugen.modular.wechat.api.entity.Menu;
import com.kuugen.modular.wechat.api.entity.MenuButton;
import com.kuugen.modular.wechat.api.enums.MenuType;
import com.kuugen.modular.wechat.api.enums.ResultType;
import com.kuugen.modular.wechat.api.response.GetUserInfoResponse;
import com.kuugen.modular.wechat.util.APIConfigUtils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class WeChatAPIController {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(WeChatAPIController.class);
	@Autowired
	private TbWxMenuMapper tbWxMenuMapper;

	/**
	 * 获取绑定微信帐号用户信息
	 * 
	 * @param openId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/userInfo/{openId}")
	public String getUser(@PathVariable String openId, ModelMap modelMap) {
		UserAPI userApi = new UserAPI(APIConfigUtils.getApiConfig());
		GetUserInfoResponse r = userApi.getUserInfo(openId);
		logger.debug(r.toJsonString());
		modelMap.addAttribute("user", r);
		return "system/user/user_info";
	}

	/**
	 * 创建更新自定义菜单
	 * 
	 * @return
	 */
	@RequestMapping("/createMenu")
	@ResponseBody
	public ResultMsg createMenu() {
		ResultMsg msg = new ResultMsg();
		try {
			List<TbWxMenuModel> menus = tbWxMenuMapper.getAll();
			if ((menus == null) || (menus.size() <= 0)) {
				msg.setSuccess(false);
				msg.setMessage("本地系统不存在自定义菜单!");
			}
			Menu weChatMenu = new Menu();
			List<MenuButton> btns = new ArrayList<MenuButton>();
			for (TbWxMenuModel menu : menus) {
				if (menu.getPmenu_id() == null) {
					MenuButton btn = new MenuButton();
					btn.setName(menu.getName());
					btn.setType(MenuType.get(menu.getType()));
					btn.setKey(menu.getKey());
					btn.setUrl(menu.getUrl());
					// 二级菜单
					List<MenuButton> usbBtns = new ArrayList<MenuButton>();
					for (TbWxMenuModel subMenu : menus) {
						if (menu.getMenu_id().equals(subMenu.getPmenu_id())) {
							MenuButton usbBtn = new MenuButton();
							usbBtn.setName(subMenu.getName());
							usbBtn.setType(MenuType.get(subMenu.getType()));
							usbBtn.setKey(subMenu.getKey());
							usbBtn.setUrl(subMenu.getUrl());
							usbBtns.add(usbBtn);
						}
					}
					btn.setSubButton(usbBtns);
					btns.add(btn);
				}
			}
			weChatMenu.setButton(btns);
			// 调用创建菜单API
			MenuAPI menuAPI = new MenuAPI(APIConfigUtils.getApiConfig());
			ResultType res = menuAPI.createMenu(weChatMenu);
			logger.debug(res.toString());
			if (res.getCode() == 0) {
				msg.setMessage("提交菜单成功！");
			} else {
				msg.setSuccess(false);
				msg.setMessage(res.getCode() + ":" + res.getDescription());
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
			return msg;
		}
	}

	/**
	 * 清除自定义菜单
	 * 
	 * @return
	 */
	@RequestMapping("/deleteMenu")
	@ResponseBody
	public ResultMsg deleteMenu() {
		ResultMsg msg = new ResultMsg();
		try {
			// 调用清除菜单API
			MenuAPI menuAPI = new MenuAPI(APIConfigUtils.getApiConfig());
			ResultType res = menuAPI.deleteMenu();
			logger.debug(res.toString());
			if (res.getCode() == 0) {
				msg.setMessage("清除菜单成功！");
			} else {
				msg.setSuccess(false);
				msg.setMessage(res.getCode() + ":" + res.getDescription());
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
			return msg;
		}
	}

}
