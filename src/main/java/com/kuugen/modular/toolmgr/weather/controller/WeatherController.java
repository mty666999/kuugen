/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kuugen.modular.toolmgr.weather.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuugen.core.common.annotion.BussinessLog;
import com.kuugen.core.common.annotion.Permission;
import com.kuugen.core.common.constant.Const;
import com.kuugen.core.common.constant.dictmap.DictMap;
import com.kuugen.core.common.constant.factory.ConstantFactory;
import com.kuugen.core.common.page.LayuiPageFactory;
import com.kuugen.core.log.LogObjectHolder;
import com.kuugen.modular.system.model.DictDto;
import com.kuugen.modular.system.service.DictService;
import com.kuugen.modular.system.warpper.DictWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * mty
 * 天气控制器
 */
@Controller
@RequestMapping("/weather")
public class WeatherController extends BaseController {

    private String PREFIX = "/modular/weather/";



    /**
     * 跳转到天气首页
     *
     * @author mty
     * @Date
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "weather.html";
    }



    /**
     * 获取天气列表
     *
     * @author mty
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Map<String, Object>> list = null;
        Page<Map<String, Object>> warpper = new DictWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(warpper);
    }



}
