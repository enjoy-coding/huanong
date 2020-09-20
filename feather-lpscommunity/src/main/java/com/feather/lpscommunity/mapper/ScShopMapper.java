package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScShop;

/**
 * 社区商家信息Mapper接口
 *
 * @author dufan
 * @date 2019-10-16
 */
public interface ScShopMapper
{
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
     * 新增社区商家信息
     *
     * @param scShop 社区商家信息
     * @return 结果
     */
    public int insertScShop(ScShop scShop);

    /**
     * 修改社区商家信息
     *
     * @param scShop 社区商家信息
     * @return 结果
     */
    public int updateScShop(ScShop scShop);

    /**
     * 删除社区商家信息
     *
     * @param shopId 社区商家信息ID
     * @return 结果
     */
    public int deleteScShopById(Long shopId);

    /**
     * 批量删除社区商家信息
     *
     * @param shopIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScShopByIds(String[] shopIds);
}
