package com.feather.lpscommunity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScIndexslide;
import com.feather.lpscommunity.mapper.ScIndexslideMapper;
import com.feather.lpscommunity.service.IScIndexslideService;

/**
 * 首页轮播图Service业务层处理
 * 
 * @author flogyin
 * @date 2019-10-23
 */
@Service
@CacheConfig(cacheNames = "sc:indexslide")
public class ScIndexslideServiceImpl implements IScIndexslideService {

    @Autowired
    private ScIndexslideMapper scIndexslideMapper;

    /**
     * 查询首页轮播图
     * 
     * @param slideId
     *            首页轮播图ID
     * @return 首页轮播图
     */
    @Override
    public ScIndexslide selectScIndexslideById(Long slideId) {
        return scIndexslideMapper.selectScIndexslideById(slideId);
    }

    /**
     * 查询首页轮播图列表
     * 
     * @param scIndexslide
     *            首页轮播图
     * @return 首页轮播图
     */
    @Override
    public List<ScIndexslide> selectScIndexslideList(ScIndexslide scIndexslide) {
        return scIndexslideMapper.selectScIndexslideList(scIndexslide);
    }

    /**
     * 查询全部首页轮播图
     * 
     * @return 首页轮播图
     */
    @Override
    @Cacheable(key = "#root.methodName")
    public List<ScIndexslide> selectScIndexslideAll() {
        return scIndexslideMapper.selectScIndexslideAll();
    }

    /**
     * 新增首页轮播图
     * 
     * @param scIndexslide
     *            首页轮播图
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int insertScIndexslide(ScIndexslide scIndexslide) {
        return scIndexslideMapper.insertScIndexslide(scIndexslide);
    }

    /**
     * 修改首页轮播图
     * 
     * @param scIndexslide
     *            首页轮播图
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int updateScIndexslide(ScIndexslide scIndexslide) {
        return scIndexslideMapper.updateScIndexslide(scIndexslide);
    }

    /**
     * 删除首页轮播图对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int deleteScIndexslideByIds(String ids) {
        return scIndexslideMapper.deleteScIndexslideByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除首页轮播图信息
     * 
     * @param slideId
     *            首页轮播图ID
     * @return 结果
     */
    @CacheEvict(allEntries = true)
    public int deleteScIndexslideById(Long slideId) {
        return scIndexslideMapper.deleteScIndexslideById(slideId);
    }
}
