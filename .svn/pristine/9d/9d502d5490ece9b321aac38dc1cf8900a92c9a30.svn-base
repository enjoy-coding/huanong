package com.feather.lpscommunity.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.feather.lpscommunity.domain.ScOrder;

/**
 * 订单信息Service接口
 * 
 * @author fancy
 * @date 2019-11-25
 */
public interface IScOrderService 
{
    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息ID
     * @return 订单信息
     */
    public ScOrder selectScOrderById(Long orderId);

    /**
     * 根据商品id查询订单id
     * @param goodsId
     * @return
     */
    public ScOrder selectScGoodsById(Long goodsId);

    /**
     * 查询订单信息列表
     * 
     * @param scOrder 订单信息
     * @return 订单信息集合
     */
    public List<ScOrder> selectScOrderList(ScOrder scOrder);

    /**
     * 查询订单信息列表
     *
     * @param scOrder 订单信息
     * @return 订单信息集合
     */
    public List<Map<String,Object>> screenScOrderList(ScOrder scOrder,String headUrl);

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
     * 批量删除订单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScOrderByIds(String ids);

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息ID
     * @return 结果
     */
    public int deleteScOrderById(Long orderId);

    /**
     * 判断5秒内是否该设备继续提交该商品
     * @param scOrder
     * @return
     */
    public boolean checkRepeatInsertOrder(ScOrder scOrder, Date todayDate);


}
