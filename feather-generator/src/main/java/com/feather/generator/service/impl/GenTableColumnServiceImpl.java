package com.feather.generator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.generator.domain.GenTableColumn;
import com.feather.generator.mapper.GenTableColumnMapper;
import com.feather.generator.service.IGenTableColumnService;

/**
 * 业务字段 服务层实现
 * 
 * @author feather
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询业务字段列表
     * 
     * @param genTableColumn
     *            业务字段信息
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn) {
        return genTableColumnMapper.selectGenTableColumnListByTableId(genTableColumn);
    }

    /**
     * 新增业务字段
     * 
     * @param genTableColumn
     *            业务字段信息
     * @return 结果
     */
    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn) {
        genTableColumn.setColumnId(uidWorker.getNextId());
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

    /**
     * 修改业务字段
     * 
     * @param genTableColumn
     *            业务字段信息
     * @return 结果
     */
    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    /**
     * 删除业务字段对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGenTableColumnByIds(String ids) {
        return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }
}