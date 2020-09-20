package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScOrder;

/**
 * 订单信息Mapper接口
 * 
 * @author fancy
 * @date 2019-11-25
 */
public interface ScOrderMapper 
{
    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息ID
     * @return 订单信息
     */
    public ScOrder selectScOrderById(Long orderId);

    public ScOrder selectScOrderByGoodsId(Long goodsId);

    /**
     * 查询订单信息列表
     * 
     * @param scOrder 订单信息
     * @return 订单信息集合
     */
    public List<ScOrder> selectScOrderList(ScOrder scOrder);

    /**
     * 新增订单信息
     * 
     * @param scOrder 订单信息
     * @return 结果
     */
    public int insertScOrder(ScOrder scOrder);

    /**
     * 修改订单信息
     * 
     * @param scOrder 订单信息
     * @return 结果
     */
    public int updateScOrder(ScOrder scOrder);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单信息ID
     * @return 结果
     */
    public int deleteScOrderById(Long orderId);

    /**
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScOrderByIds(String[] orderIds);
}
