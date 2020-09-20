package com.feather.lpscommunity.service;

import java.util.List;
import java.util.Map;

import com.feather.lpscommunity.domain.ScGoods;

/**
 * 商品信息表Service接口
 * 
 * @author fancy
 * @date 2019-11-19
 */
public interface IScGoodsService 
{
    /**
     * 查询商品信息表
     * 
     * @param goodsId 商品信息表ID
     * @return 商品信息表
     */
    public ScGoods selectScGoodsById(Long goodsId);

    public ScGoods selectScGoodsFileById(Long goodsId,String headUrl);

    public List<ScGoods> selectScGoodsListByShopId(Long shopId);

    /**
     * 查询商品信息表列表
     * 
     * @param scGoods 商品信息表
     * @return 商品信息表集合
     */
    public List<ScGoods> selectScGoodsList(ScGoods scGoods);

    /**
     * 新增商品信息表
     * 
     * @param scGoods 商品信息表
     * @return 结果
     */
    public int insertScGoods(ScGoods scGoods);

    /**
     * 修改商品信息表
     * 
     * @param scGoods 商品信息表
     * @return 结果
     */
    public int updateScGoods(ScGoods scGoods);

    /**
     * 批量删除商品信息表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScGoodsByIds(String ids);

    /**
     * 删除商品信息表信息
     * 
     * @param goodsId 商品信息表ID
     * @return 结果
     */
    public int deleteScGoodsById(Long goodsId);

    public List<Map<String, Object>> screenGoodsList(ScGoods scGoods,String headUrl);

    /**
     * 检查商品是否存在订单
     * @param ids
     * @return
     */
    public boolean checkOrderGoods(String ids);
}