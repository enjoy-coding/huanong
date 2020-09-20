package com.feather.aupipes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupYjtablesMapper;
import com.feather.aupipes.domain.AupYjtables;
import com.feather.aupipes.service.IAupYjtablesService;
import com.feather.common.core.text.Convert;

/**
 * 预警信息统计Service业务层处理
 * 
 * @author fancy
 * @date 2020-01-06
 */
@Service
public class AupYjtablesServiceImpl implements IAupYjtablesService 
{
    @Autowired
    private AupYjtablesMapper aupYjtablesMapper;

    /**
     * 查询预警信息统计
     * 
     * @param tid 预警信息统计ID
     * @return 预警信息统计
     */
    @Override
    public AupYjtables selectAupYjtablesById(String tid)
    {
        return aupYjtablesMapper.selectAupYjtablesById(tid);
    }

    /**
     * 查询预警信息统计列表
     * 
     * @param aupYjtables 预警信息统计
     * @return 预警信息统计
     */
    @Override
    public List<AupYjtables> selectAupYjtablesList(AupYjtables aupYjtables)
    {
        return aupYjtablesMapper.selectAupYjtablesList(aupYjtables);
    }

    /**
     * 新增预警信息统计
     * 
     * @param aupYjtables 预警信息统计
     * @return 结果
     */
    @Override
    public int insertAupYjtables(AupYjtables aupYjtables)
    {
        return aupYjtablesMapper.insertAupYjtables(aupYjtables);
    }

    /**
     * 修改预警信息统计
     * 
     * @param aupYjtables 预警信息统计
     * @return 结果
     */
    @Override
    public int updateAupYjtables(AupYjtables aupYjtables)
    {
        return aupYjtablesMapper.updateAupYjtables(aupYjtables);
    }

    /**
     * 删除预警信息统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupYjtablesByIds(String ids)
    {
        return aupYjtablesMapper.deleteAupYjtablesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预警信息统计信息
     * 
     * @param tid 预警信息统计ID
     * @return 结果
     */
    @Override
    public int deleteAupYjtablesById(String tid)
    {
        return aupYjtablesMapper.deleteAupYjtablesById(tid);
    }
}
