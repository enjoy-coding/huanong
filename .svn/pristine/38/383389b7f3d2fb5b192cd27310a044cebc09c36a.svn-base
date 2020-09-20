package com.feather.lpscommunity.service;

import java.util.List;
import java.util.Map;

import com.feather.lpscommunity.domain.ScShop;

/**
 * 社区商家信息Service接口
 *
 * @author dufan
 * @date 2019-10-16
 */
public interface IScShopService {
    /**
     * 查询社区商家信息
     *
     * @param shopId 社区商家信息ID
     * @return 社区商家信息
     */
    public ScShop selectScShopById(Long shopId);

    /**
     * 查询社区商家信息列表
     *
     * @param scShop 社区商家信息
     * @return 社区商家信息集合
     */
    public List<ScShop> selectScShopList(ScShop scShop);

    public List<ScShop> selectShopAll();
    
    /**
     * 筛选查询社区商家信息列表
     *
     * @param scShop 社区商家查询条件
     * @param headUrl 请求路径
     * @return 筛选后的社区商家信息集合
     */
    public List<Map<String, Object>> screenScShopList(ScShop scShop, String headUrl);

    /**
     * 筛选查询社区商家信息详情
     *
     * @param shopId  社区商家查询条件
     * @param domain 该对象所有的缩略图路径
     * @return 筛选后的社区商家信息详情
     */
    public Map<String, Object> screenScShopDetail(Long shopId, String domain);

    /**
     * 新增社区商家信息
     *
     * @param scShop
     *            社区商家信息
     * @return 结果
     */
    public int insertScShop(ScShop scShop);

    /**
     * 修改社区商家信息
     *
     * @param scShop
     *            社区商家信息
     * @return 结果
     */
    public int updateScShop(ScShop scShop);

    /**
     * 批量删除社区商家信息
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteScShopByIds(String ids);

    /**
     * 删除社区商家信息信息
     *
     * @param shopId
     *            社区商家信息ID
     * @return 结果
     */
    public int deleteScShopById(Long shopId);


    /**
     * 判断商家商品是否存在订单
     * @param shopIds
     * @return
     */
    public boolean checkOrderShop(String shopIds);

    public boolean checkGoodsShop(String shopIds);
}