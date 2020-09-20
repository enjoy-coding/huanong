package com.feather.lpscommunity.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 志愿者信息对象 sc_volunteer
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScVolunteer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long userId;
    /** 志愿者电话 */
    @Excel(name = "志愿者电话")
    private String volunteerTel;
    /** 志愿者账号 */
    @Excel(name = "志愿者账号")
    private String volunteerAccount;
    /** 志愿者积分 */
    @Excel(name = "志愿者积分")
    private String volunteerScore;
    /** 姓名 */
    @Excel(name = "姓名")
    private String volunteerName;
    /** 性别 */
    @Excel(name = "性别")
    private String volunteerSex;
    /** 出生日期 */
    @Excel(name = "出生日期")
    private String volunteerBir;
    /** 身份证号 */
    @Excel(name = "身份证号")
    private String volunteerIdcard;
    /** 地址 */
    @Excel(name = "地址")
    private String volunteerAddress;
    /** 职业 */
    @Excel(name = "职业")
    private String volunteerProfession;
    /** 个人简介 */
    @Excel(name = "个人简介")
    private String volunteerSynopsis;

    /** 头像路径 */
    @Excel(name = "头像路径")
    private String volunteerAvatar;

    public ScVolunteer(Long userId, String volunteerTel, String volunteerAccount,
            String volunteerName, String volunteerSex, String volunteerBir, String volunteerIdcard,
            String volunteerAddress, String volunteerProfession, String volunteerSynopsis) {
        this.userId = userId;
        this.volunteerTel = volunteerTel;
        this.volunteerAccount = volunteerAccount;
        this.volunteerName = volunteerName;
        this.volunteerSex = volunteerSex;
        this.volunteerBir = volunteerBir;
        this.volunteerIdcard = volunteerIdcard;
        this.volunteerAddress = volunteerAddress;
        this.volunteerProfession = volunteerProfession;
        this.volunteerSynopsis = volunteerSynopsis;
    }

    
}