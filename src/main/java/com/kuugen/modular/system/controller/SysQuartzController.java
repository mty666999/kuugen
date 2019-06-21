package com.kuugen.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.kuugen.core.log.LogObjectHolder;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.system.model.SysQuartz;
import com.kuugen.modular.system.service.SysQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定时任务控制器
 *
 * @author mty
 * @Date 2018-10-26 17:36:54
 */
@Controller
@RequestMapping("/sysQuartz")
public class SysQuartzController extends BaseController {

    private String PREFIX = "/system/sysQuartz/";

    @Autowired
    private SysQuartzService sysQuartzService;

    /**
     * 跳转到定时任务首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sysQuartz.html";
    }

    /**
     * 跳转到添加定时任务
     */
    @RequestMapping("/sysQuartz_add")
    public String sysQuartzAdd() {
        return PREFIX + "sysQuartz_add.html";
    }

    /**
     * 跳转到修改定时任务
     */
    @RequestMapping("/sysQuartz_update/{sysQuartzId}")
    public String sysQuartzUpdate(@PathVariable String sysQuartzId, Model model) {
        SysQuartz sysQuartz = sysQuartzService.getById(sysQuartzId);
        model.addAttribute("item",sysQuartz);
        LogObjectHolder.me().set(sysQuartz);
        return PREFIX + "sysQuartz_edit.html";
    }

    /**
     * 获取定时任务列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return sysQuartzService.list(null);
    }

    /**
     * 新增定时任务
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SysQuartz sysQuartz) {
        sysQuartzService.save(sysQuartz);
        return SUCCESS_TIP;
    }

    /**
     * 删除定时任务
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String sysQuartzId) {
        sysQuartzService.removeById(sysQuartzId);
        return SUCCESS_TIP;
    }
    /**
     * 启用定时任务
     */
    @RequestMapping(value = "/start/{sysQuartzId}")
    @ResponseBody
    public ResultMsg start(@PathVariable String sysQuartzId, Model model) {
        ResultMsg msg=new ResultMsg();
        msg = sysQuartzService.start(sysQuartzId);
        return  msg;
    }
    /**
     *  停用移除定时任务
     */
    @RequestMapping(value = "/shutdown/{sysQuartzId}")
    @ResponseBody
    public ResultMsg shutdown(@PathVariable String sysQuartzId) {
        ResultMsg msg=new ResultMsg();
        msg = sysQuartzService.shutdown(sysQuartzId);
        return  msg;
    }

    /**
     * 修改定时任务
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysQuartz sysQuartz) {
        sysQuartzService.updateById(sysQuartz);
        return SUCCESS_TIP;
    }

    /**
     * 定时任务详情
     */
    @RequestMapping(value = "/detail/{sysQuartzId}")
    @ResponseBody
    public Object detail(@PathVariable("sysQuartzId") String sysQuartzId) {
        return sysQuartzService.getById(sysQuartzId);
    }
}
