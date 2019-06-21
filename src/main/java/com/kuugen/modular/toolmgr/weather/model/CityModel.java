package com.kuugen.modular.toolmgr.weather.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: mty
 * @Description: 城市实体类
 * @Date: Created in 15:39 2019-05-02
 * @Modified By:
 */
@TableName("bas_city")
public class CityModel {
    /**
     * 城市id
     */
    @TableId(value = "city_id", type = IdType.AUTO)
    private Long cityId;
    /**
     * 城市名称
     */
    @TableField(value = "city_name")
    private String cityName;
    /**
     * 城市代码,查询天气用
     */
    @TableField(value = "city_code")
    private String cityCode;


    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
