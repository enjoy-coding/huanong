package com.feather.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.cms.domain.CmsDomain;
import com.feather.cms.mapper.CmsDomainMapper;
import com.feather.cms.service.ICmsDomainService;
import com.feather.common.core.text.Convert;

@Service
public class CmsDomainServiceImpl implements ICmsDomainService {
    @Autowired
    private CmsDomainMapper cmsDomainMapper;

    /**
     * 查询cms域
     * 
     * @param domainId
     *            cms域ID
     * @return cms域
     */
    @Override
    public CmsDomain selectCmsDomainById(Long domainId) {
        return cmsDomainMapper.selectCmsDomainById(domainId);
    }

    /**
     * 查询cms域列表
     * 
     * @param cmsDomain
     *            cms域
     * @return cms域
     */
    @Override
    public List<CmsDomain> selectCmsDomainList(CmsDomain cmsDomain) {
        return cmsDomainMapper.selectCmsDomainList(cmsDomain);
    }

    /**
     * 新增cms域
     * 
     * @param cmsDomain
     *            cms域
     * @return 结果
     */
    @Override
    public int insertCmsDomain(CmsDomain cmsDomain) {
        return cmsDomainMapper.insertCmsDomain(cmsDomain);
    }

    /**
     * 修改cms域
     * 
     * @param cmsDomain
     *            cms域
     * @return 结果
     */
    @Override
    public int updateCmsDomain(CmsDomain cmsDomain) {
        return cmsDomainMapper.updateCmsDomain(cmsDomain);
    }

    /**
     * 删除cms域对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCmsDomainByIds(String ids) {
        return cmsDomainMapper.deleteCmsDomainByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除cms域信息
     * 
     * @param domainId
     *            cms域ID
     * @return 结果
     */
    public int deleteCmsDomainById(Long domainId) {
        return cmsDomainMapper.deleteCmsDomainById(domainId);
    }
}
