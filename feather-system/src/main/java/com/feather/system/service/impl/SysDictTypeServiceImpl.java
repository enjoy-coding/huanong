package com.feather.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.text.Convert;
import com.feather.common.exception.BusinessException;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysDictType;
import com.feather.system.domain.SysUser;
import com.feather.system.mapper.SysDictDataMapper;
import com.feather.system.mapper.SysDictTypeMapper;
import com.feather.system.service.ISysDictTypeService;

/**
 * 字典 业务层处理
 * 
 * @author feather
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType
     *            字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null && !user.isAdmin()) {
            dictType.setDictRole(user.getUserId());
        }
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll() {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null && !user.isAdmin()) {
            SysDictType dictType = new SysDictType();
            dictType.setDictRole(user.getUserId());
            return dictTypeMapper.selectDictTypeList(dictType);
        }
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId
     *            字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     * 
     * @param dictType
     *            字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 通过字典ID删除字典信息
     * 
     * @param dictId
     *            字典ID
     * @return 结果
     */
    @Override
    public int deleteDictTypeById(Long dictId) {
        return dictTypeMapper.deleteDictTypeById(dictId);
    }

    /**
     * 批量删除字典类型
     * 
     * @param ids
     *            需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String ids) throws BusinessException {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds) {
            SysDictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }

        return dictTypeMapper.deleteDictTypeByIds(dictIds);
    }

    /**
     * 新增保存字典类型信息
     * 
     * @param dictType
     *            字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dictType) {
        if (dictType.getDictId() == null) {
            dictType.setDictId(uidWorker.getNextId());
        }
        if (StringUtils.isBlank(dictType.getStatus())) {
            dictType.setStatus(Constants.SUCCESS_OR_ENABLED);
        }
        return dictTypeMapper.insertDictType(dictType);
    }

    /**
     * 修改保存字典类型信息
     * 
     * @param dictType
     *            字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType) {
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        return dictTypeMapper.updateDictType(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dict
     *            字典类型
     * @return 结果
     */
    @Override
    public boolean checkDictTypeUnique(SysDictType dict) {
        Long dictId = dict.getDictId() == null ? -1L : dict.getDictId();
        SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (dictType != null && dictType.getDictId().longValue() != dictId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 查询字典类型树
     * 
     * @param dictType
     *            字典类型
     * @return 所有字典类型
     */
    @Override
    public List<Ztree> selectDictTree(SysDictType dictType) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDictType> dictList = dictTypeMapper.selectDictTypeList(dictType);
        for (SysDictType dict : dictList) {
            if (Constants.SUCCESS_OR_ENABLED.equals(dict.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    public String transDictName(SysDictType dictType) {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }
}
