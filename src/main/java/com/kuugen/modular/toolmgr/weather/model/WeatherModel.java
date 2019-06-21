package com.kuugen.modular.toolmgr.weather.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: mty
 * @Description:天气实体类
 * @Date: Created in 16:13 2019-05-02
 * @Modified By:
 */
@TableName("weather_mgr")
public class WeatherModel {
    /**
     *  天气id
     */
    @TableId(value = "weather_id", type = IdType.AUTO)
    private Long weatherId;
    /**
     * 城市id
     */
    @TableField(value = "city_id")
    private Long cityId;
    /**
     * 城市名称(记录新增时的名称)
     */
    @TableField(value = "city_name")
    private String cityName;
    /**
     * 上级城市名称
     */
    @TableField(value = "pcity_name")
    private String pcityName;

    /**
     * 日期(不含时分秒)
     */
    @DateTimeFormat(pattern="yyyy-MM-DD")
    @JSONField(format="yyyy-MM-DD")
    @TableField(value = "weather_date" )
    private Date weatherDate;
    /**
     * 当前温度
     */
    @TableField(value = "wendu" )
    private String wendu;

    /**
     * 湿度,47%
     */
    @TableField(value = "shidu")
    private String shidu;
    /**
     * pm25
     */
    @TableField(value = "pm25")
    private String pm25;
    /**
     * 空气质量(应该是个字典良)
     */
    @TableField(value = "quality")
    private String quality;
    /**
     * 高温
     */
    @TableField(value = "high")
    private String high;
    /**
     * 低温
     */
    @TableField(value = "low")
    private String low;
    /**
     * 风向
     */
    @TableField(value = "fx")
    private String fx;
    /**
     * 风力(也是字典)
     */
    @TableField(value = "fl")
    private String fl;
    /**
     * 天气类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 通知
     */
    @TableField(value = "notice")
    private String notice;


    /**
     * 数据来源时间(判断是否要更新)18:50 非时间类型
     */
    @TableField(value = "data_time" )
    private String dataTime;

    /**
     * 数据状态0历史数据,1当前数据,2未来数据
     */
    @TableField(value = "statue")
    private String statue;
    /**
     * 最后更新时间
     */
    @TableField(value = "update_time" )
    private Date update_time;

    public Long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

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

    public String getPcityName() {
        return pcityName;
    }

    public void setPcityName(String pcityName) {
        this.pcityName = pcityName;
    }

    public Date getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }
}
