package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScIndexslide;

/**
 * 首页轮播图Mapper接口
 * 
 * @author flogyin
 * @date 2019-10-23
 */
public interface ScIndexslideMapper {
    /**
     * 查询首页轮播图
     * 
     * @param slideId
     *            首页轮播图ID
     * @return 首页轮播图
     */
    public ScIndexslide selectScIndexslideById(Long slideId);

    /**
     * 查询首页轮播图列表
     * 
     * @param scIndexslide
     *            首页轮播图
     * @return 首页轮播图集合
     */
    public List<ScIndexslide> selectScIndexslideList(ScIndexslide scIndexslide);

    /**
     * 查询全部首页轮播图
     * 
     * @return 首页轮播图集合
     */
    public List<ScIndexslide> selectScIndexslideAll();

    /**
     * 新增首页轮播图
     * 
     * @param scIndexslide
     *            首页轮播图
     * @return 结果
     */
    public int insertScIndexslide(ScIndexslide scIndexslide);

    /**
     * 修改首页轮播图
     * 
     * @param scIndexslide
     *            首页轮播图
     * @return 结果
     */
    public int updateScIndexslide(ScIndexslide scIndexslide);

    /**
     * 删除首页轮播图
     * 
     * @param slideId
     *            首页轮播图ID
     * @return 结果
     */
    public int deleteScIndexslideById(Long slideId);

    /**
     * 批量删除首页轮播图
     * 
     * @param slideIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteScIndexslideByIds(String[] slideIds);
}
