package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 预警用户对象 aup_warring_user
 * 
 * @author fancy
 * @date 2020-04-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupWarringUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;
    /** 预警类型 */
    @Excel(name = "预警类型")
    private String warringcategory;
    /** 推送人 */
    @Excel(name = "推送人")
    private String name;
    /** 推送人id */
    @Excel(name = "推送人id")
    private String userid;
    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phonenumber;
    /** 微信id */
    @Excel(name = "微信id")
    private String openid;
    /** 微信id */
    private String loginname;
    private String obj;
}
