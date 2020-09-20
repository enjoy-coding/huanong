package com.feather.lpscommunity.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 订单信息对象 sc_order
 * 
 * @author fancy
 * @date 2019-11-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回类")
public class ScOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Long orderId;
    /** 订单名称 */
    @Excel(name = "订单名称")
    @ApiModelProperty(value="订单名称")
    private String orderName;
    /** 订单商品 */
    @Excel(name = "订单商品")
    @ApiModelProperty(value="订单商品")
    private Long goodsId;
    /** 设备id */
    @ApiModelProperty(value="设备id")
    @Excel(name = "设备id")
    private String equipmentid;

    private ScGoods goods;

    private Long ShopId;
    private String shopName;

    public ScOrder(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public ScOrder(Long orderId, String orderName, Long goodsId, String equipmentid) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.goodsId = goodsId;
        this.equipmentid = equipmentid;
    }

    public ScOrder(Long goodsId, String equipmentid) {
        this.goodsId = goodsId;
        this.equipmentid = equipmentid;
    }
}
