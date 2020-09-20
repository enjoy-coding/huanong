package com.feather.common4nutz.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.plugin.spring.boot.service.entity.DataBaseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feather.common.core.domain.IFeatherEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author nothing
 * @date 2019-10-21 8:27
 */
public class NutzBaseEntity extends DataBaseEntity implements IFeatherEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    /** 搜索值 */
    private String searchValue;

    @ApiModelProperty(hidden = true)
    /** 创建者 */
    @Column("create_by")
    private String createBy;

    @ApiModelProperty(hidden = true)
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("create_time")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    /** 更新者 */
    @Column("update_by")
    private String updateBy;

    @ApiModelProperty(hidden = true)
    /** 更新时间 */
    @Column("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    /** 删除标志（0代表存在 2代表删除） */
    @Column("del_flag")
    private String delFlag;

    @ApiModelProperty(hidden = true)
    /** 备注 */
    @Column("remark")
    private String remark;

    @ApiModelProperty(hidden = true)
    /** 请求参数 */
    private Map<String, Object> params;

    @Override
    public String getSearchValue() {
        return searchValue;
    }

    @Override
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
