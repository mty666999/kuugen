package com.kuugen.modular.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuugen.core.quartz.QuartzUtil;
import com.kuugen.core.util.ResultMsg;
import com.kuugen.modular.system.mapper.SysQuartzMapper;
import com.kuugen.modular.system.model.SysQuartz;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 定时任务调用表 服务类
 * </p>
 *
 * @author mty
 * @since 2018-10-26
 */
@Service
public class SysQuartzService extends ServiceImpl<SysQuartzMapper, SysQuartz> {


    @Resource
    private SysQuartzMapper sysQuartzMapper;

    /**
     * 启用任务
     * @param sysQuartzId
     * @return
     */
    public ResultMsg start(String sysQuartzId)  {
        ResultMsg msg=new ResultMsg();
        //首先查询然后启动任务,最后更新数据
        SysQuartz quartz = sysQuartzMapper.selectById(sysQuartzId);
        Map<String, Object> extendeds = new HashMap<String, Object>();
        try {
            QuartzUtil.startJob(quartz.getJobName(),  Class.forName(quartz.getClassName()), quartz.getPlan(), extendeds);
            quartz.setIsActive("1");
            sysQuartzMapper.updateById(quartz);
            msg.setMessage("启动成功!");
        } catch (ClassNotFoundException e) {
            msg.setSuccess(false);
            msg.setMessage("启动失败!");
            msg.setData(e);
            return msg;
        }
        return msg;
    }

    public ResultMsg shutdown(String sysQuartzId) {
        ResultMsg msg=new ResultMsg();
        //首先查询然后停用任务,最后更新数据
        SysQuartz quartz = sysQuartzMapper.selectById(sysQuartzId);
        Map<String, Object> extendeds = new HashMap<String, Object>();
        QuartzUtil.removeJob(quartz.getJobName());
        quartz.setIsActive("0");
        sysQuartzMapper.updateById(quartz);
        msg.setMessage("停用成功!");
        return msg;
    }

}
