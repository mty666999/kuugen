package com.kuugen.modular.system.model;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 定时任务调用表
 * </p>
 *
 * @author mty
 * @since 2018-10-26
 */
@TableName("SYS_QUARTZ")
public class SysQuartz extends Model<SysQuartz> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 任务名称
     */
    @TableField("JOB_NAME")
    private String jobName;
    /**
     * jab class名称
     */
    @TableField("CLASS_NAME")
    private String className;
    /**
     * 执行计划
     */
    @TableField("PLAN")
    private String plan;
    /**
     * 任务描述
     */
    @TableField("REMARK")
    private String remark;
    /**
     * 排序
     */
    @TableField("SEQ")
    private Integer seq;
    /**
     * 是否执行 0否1是
     */
    @TableField("IS_ACTIVE")
    private String isActive;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysQuartz{" +
        "id=" + id +
        ", jobName=" + jobName +
        ", className=" + className +
        ", plan=" + plan +
        ", remark=" + remark +
        ", seq=" + seq +
        ", isActive=" + isActive +
        "}";
    }
}
