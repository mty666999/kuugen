package com.kuugen.modular.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Author: mty
 * @Description:用户关注表
 * @Date: Created in 15:52 2019-05-02
 * @Modified By:
 */
@TableName("user_follow")
public class UserFollowModel {
    /**
     * 表主键
     */
    @TableId(value = "follow_id", type = IdType.AUTO)
    private Long followId;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 关注对象的id,关注城市就是城市id,股票就是股票id;等等
     */
    @TableField(value = "object_id" )
    private Long objectId;
    /**
     * 关注类型,城市 就是 city 等
     */
    @TableField(value = "follow_type" )
    private String followType;
    /**
     * 关注时间
     */
    @TableField(value = "follow_date" )
    private Date followDate;

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }
}
