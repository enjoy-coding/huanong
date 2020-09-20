package com.feather.prd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.core.text.Convert;
import com.feather.prd.domain.PrdAppversion;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.mapper.PrdAppversionMapper;
import com.feather.prd.mapper.PrdAttachmentMapper;
import com.feather.prd.service.IPrdAppversionService;

/**
 * App版本Service业务层处理
 * 
 * @author flogyin
 * @date 2019-09-25
 */
@Service
@CacheConfig(cacheNames = "prd:appversion")
public class PrdAppversionServiceImpl implements IPrdAppversionService {

    @Autowired
    private PrdAppversionMapper PrdAppversionMapper;

    @Autowired
    private PrdAttachmentMapper prdAttachmentMapper;

    /**
     * 查询App版本
     * 
     * @param versionId
     *            App版本ID
     * @return App版本
     */
    @Override
    public PrdAppversion selectPrdAppversionById(Long versionId) {
        return PrdAppversionMapper.selectPrdAppversionById(versionId);
    }

    /**
     * 查询App版本列表
     * 
     * @param PrdAppversion
     *            App版本
     * @return App版本
     */
    @Override
    public List<PrdAppversion> selectPrdAppversionList(PrdAppversion prdAppversion) {
        return PrdAppversionMapper.selectPrdAppversionList(prdAppversion);
    }

    /**
     * 查询App最新版本
     * 
     * @param PrdAppversion
     *            App版本
     * @return App版本
     */
    @Override
    @Cacheable(key = "#root.methodName +':'+ #root.args[0]", unless = "#result.size() == 0")
    public List<PrdAppversion> selectPrdAppversionLastByPackage(String versionPackage) {
        return PrdAppversionMapper.selectPrdAppversionLastByPackage(versionPackage);
    }

    /**
     * 新增App版本
     * 
     * @param PrdAppversion
     *            App版本
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public int insertPrdAppversion(PrdAppversion prdAppversion) {
        PrdAttachment appAttachment = prdAppversion.getAppAttachment();
        PrdAttachment iconAttachment = prdAppversion.getIconAttachment();
        if (appAttachment != null) {
            appAttachment.setCreateTime(prdAppversion.getCreateTime());
            appAttachment.setCreateBy(prdAppversion.getCreateBy());
            prdAttachmentMapper.insertPrdAttachment(appAttachment);
        }
        if (iconAttachment != null) {
            iconAttachment.setCreateTime(prdAppversion.getCreateTime());
            iconAttachment.setCreateBy(prdAppversion.getCreateBy());
            prdAttachmentMapper.insertPrdAttachment(iconAttachment);
        }
        return PrdAppversionMapper.insertPrdAppversion(prdAppversion);
    }

    /**
     * 修改App版本
     * 
     * @param PrdAppversion
     *            App版本
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int updatePrdAppversion(PrdAppversion prdAppversion) {
        return PrdAppversionMapper.updatePrdAppversion(prdAppversion);
    }

    /**
     * 删除App版本对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int deletePrdAppversionByIds(String ids) {
        return PrdAppversionMapper.deletePrdAppversionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除App版本信息
     * 
     * @param versionId
     *            App版本ID
     * @return 结果
     */
    @CacheEvict(allEntries = true)
    public int deletePrdAppversionById(Long versionId) {
        return PrdAppversionMapper.deletePrdAppversionById(versionId);
    }
}
