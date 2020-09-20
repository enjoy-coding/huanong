package com.feather.lpscommunity.domain;

import java.util.List;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息表对象 sc_goods
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScGoods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long goodsId;
    /** 商家 */
    @Excel(name = "商家")
    private Long shopId;
    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;
    /** 商品品牌 */
    @Excel(name = "商品品牌")
    private String goodsBrand;
    /** 商品描述 */
    @Excel(name = "商品描述")
    private String goodsContent;
    /** 商品价格 */
    @Excel(name = "商品价格")
    private Double goodsPrice;
    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long goodsQuantity;
    /** 单位 */
    @Excel(name = "单位")
    private String goodsUnit;

    private ScShop shop;

    private List<ScFileInfo> files;

    public ScGoods(Long shopId) {
        this.shopId = shopId;
    }
}