package com.kuugen.modular.house.data.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Author: mty
 * @Description:
 * @Date: Created in 11:48 2019-05-09
 * @Modified By:
 */
@TableName("house_bus_data")
public class HouseBusDataModel {
    /**
     *  流水号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 城市id
     */
    @TableField(value = "city_id")
    private Long cityId;
    /**
     * 日期
     */
    @TableField(value = "date")
    private Date date;
    /**
     * 数据详细网址
     */
    @TableField(value = "url")
    private String url;
    /**
     * 住宅数量
     */
    @TableField(value = "zhuzhai")
    private int zhuzhai;
    /**
     * 住宅总面积
     */
    @TableField(value = "total_area")
    private double totalArea;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getZhuzhai() {
        return zhuzhai;
    }

    public void setZhuzhai(int zhuzhai) {
        this.zhuzhai = zhuzhai;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
