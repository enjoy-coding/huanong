package com.feather.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.cms.domain.CmsArgs;
import com.feather.cms.mapper.CmsArgsMapper;
import com.feather.cms.service.ICmsArgsService;
import com.feather.common.core.text.Convert;

/**
 * 参数Service业务层处理
 */
@Service
public class CmsArgsServiceImpl implements ICmsArgsService {
    @Autowired
    private CmsArgsMapper cmsArgsMapper;

    /**
     * 查询参数
     * 
     * @param argsId
     *            参数ID
     * @return 参数
     */
    @Override
    public CmsArgs selectCmsArgsById(Long argsId) {
        return cmsArgsMapper.selectCmsArgsById(argsId);
    }

    /**
     * 查询参数列表
     * 
     * @param cmsArgs
     *            参数
     * @return 参数
     */
    @Override
    public List<CmsArgs> selectCmsArgsList(CmsArgs cmsArgs) {
        return cmsArgsMapper.selectCmsArgsList(cmsArgs);
    }

    /**
     * 新增参数
     * 
     * @param cmsArgs
     *            参数
     * @return 结果
     */
    @Override
    public int insertCmsArgs(CmsArgs cmsArgs) {
        return cmsArgsMapper.insertCmsArgs(cmsArgs);
    }

    /**
     * 修改参数
     * 
     * @param cmsArgs
     *            参数
     * @return 结果
     */
    @Override
    public int updateCmsArgs(CmsArgs cmsArgs) {
        return cmsArgsMapper.updateCmsArgs(cmsArgs);
    }

    /**
     * 删除参数对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCmsArgsByIds(String ids) {
        return cmsArgsMapper.deleteCmsArgsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除参数信息
     * 
     * @param argsId
     *            参数ID
     * @return 结果
     */
    public int deleteCmsArgsById(Long argsId) {
        return cmsArgsMapper.deleteCmsArgsById(argsId);
    }
}
