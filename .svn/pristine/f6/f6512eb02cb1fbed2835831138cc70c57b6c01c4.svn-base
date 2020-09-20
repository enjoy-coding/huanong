package com.feather.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.core.text.Convert;
import com.feather.system.domain.SysDictData;
import com.feather.system.mapper.SysDictDataMapper;
import com.feather.system.service.ISysDictDataService;

/**
 * 字典 业务层处理
 * 
 * @author feather
 */
@Service
@CacheConfig(cacheNames = "sys:dict")
public class SysDictDataServiceImpl implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData
     *            字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType
     *            字典类型
     * @return 字典数据集合信息
     */
    @Override
    @Cacheable(key = "#root.methodName +':'+ #root.args[0]")
    public List<SysDictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType
     *            字典类型
     * @param dictValue
     *            字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode
     *            字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 通过字典ID删除字典数据信息
     * 
     * @param dictCode
     *            字典数据ID
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int deleteDictDataById(Long dictCode) {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据
     * 
     * @param ids
     *            需要删除的数据
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int deleteDictDataByIds(String ids) {
        return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param dictData
     *            字典数据信息
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int insertDictData(SysDictData dictData) {
        if (dictData.getDictCode() == null) {
            dictData.setDictCode(uidWorker.getNextId());
        }
        if (StringUtils.isBlank(dictData.getStatus())) {
            dictData.setStatus(Constants.SUCCESS_OR_ENABLED);
        }
        if (StringUtils.isBlank(dictData.getIsDefault())) {
            dictData.setIsDefault(Constants.DEFAULT_NO);
        }
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param dictData
     *            字典数据信息
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int updateDictData(SysDictData dictData) {
        return dictDataMapper.updateDictData(dictData);
    }

    @Override
    public String selectDictValue(String dictType, String dictLabel) {
        return dictDataMapper.selectDictValue(dictType, dictLabel);
    }

}
