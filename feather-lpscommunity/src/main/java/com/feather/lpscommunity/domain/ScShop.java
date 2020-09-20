package com.feather.lpscommunity.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区商家信息对象 sc_shop
 *
 * @author dufan
 * @date 2019-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScShop extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long shopId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String shopName;
    /** 电话 */
    @Excel(name = "电话")
    private String shopTel;

    /** 标题 */
    @Excel(name = "标题")
    private String shopTitle;

    /** 地址 */
    @Excel(name = "地址")
    private String shopAddress;

    /** 内容 */
    @Excel(name = "内容")
    private String shopContent;

    /** 图片 */
    @Excel(name = "图片")
    private String shopFile;

    /** 分类 */
    @Excel(name = "分类")
    private String shopType;

    /** 状态 */
    @Excel(name = "状态")
    private String shopStatus;

    public ScShop(String shopType) {
        this.shopType = shopType;
    }

    public ScShop(Long shopId) {
        this.shopId = shopId;
    }

    public ScShop(Long shopId, String shopName, String shopTel, String shopContent) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopTel = shopTel;
        this.shopContent = shopContent;
       
    }

    

    
}