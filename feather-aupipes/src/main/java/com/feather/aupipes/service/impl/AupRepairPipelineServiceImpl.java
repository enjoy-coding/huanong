package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupRepairPipeline;
import com.feather.aupipes.mapper.AupRepairPipelineMapper;
import com.feather.aupipes.service.IAupRepairPipelineService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 【管线设备】Service业务层处理
 * 
 * @author fancy
 * @date 2020-04-16
 */
@Service
public class AupRepairPipelineServiceImpl implements IAupRepairPipelineService 
{
    @Autowired
    private AupRepairPipelineMapper aupRepairPipelineMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询【管线设备】
     * 
     * @param id 【管线设备】ID
     * @return 【管线设备】
     */
    @Override
    public AupRepairPipeline selectAupRepairPipelineById(Long id)
    {
        return aupRepairPipelineMapper.selectAupRepairPipelineById(id);
    }

    /**
     * 查询【管线设备】列表
     * 
     * @param aupRepairPipeline 【管线设备】
     * @return 【管线设备】
     */
    @Override
    public List<AupRepairPipeline> selectAupRepairPipelineList(AupRepairPipeline aupRepairPipeline)
    {
        return aupRepairPipelineMapper.selectAupRepairPipelineList(aupRepairPipeline);
    }

    /**
     * 新增【管线设备】
     * 
     * @param aupRepairPipelineList 【管线设备】
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAupRepairPipelines(List<AupRepairPipeline> aupRepairPipelineList)
    {
        for (AupRepairPipeline p:aupRepairPipelineList) {
            p.setId(uidWorker.getNextId());
            p.setCreateTime(DateUtils.getNowDate());
        }
        return aupRepairPipelineMapper.insertAupRepairPipelines(aupRepairPipelineList);
    }

    /**
     * 新增【管线设备】
     *
     * @param aupRepairPipeline 【管线设备】
     * @return 结果
     */
    @Override
    public int insertAupRepairPipeline(AupRepairPipeline aupRepairPipeline)
    {
        aupRepairPipeline.setId(uidWorker.getNextId());
        aupRepairPipeline.setCreateTime(DateUtils.getNowDate());
        return aupRepairPipelineMapper.insertAupRepairPipeline(aupRepairPipeline);
    }

    /**
     * 修改【管线设备】
     * 
     * @param aupRepairPipeline 【管线设备】
     * @return 结果
     */
    @Override
    public int updateAupRepairPipeline(AupRepairPipeline aupRepairPipeline)
    {
                                                                                                                                            
                aupRepairPipeline.setUpdateTime(DateUtils.getNowDate());
                            return aupRepairPipelineMapper.updateAupRepairPipeline(aupRepairPipeline);
    }

    /**
     * 删除【管线设备】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupRepairPipelineByIds(String ids)
    {
        return aupRepairPipelineMapper.deleteAupRepairPipelineByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【管线设备】信息
     * 
     * @param id 【管线设备】ID
     * @return 结果
     */
    @Override
    public int deleteAupRepairPipelineById(Long id)
    {
        return aupRepairPipelineMapper.deleteAupRepairPipelineById(id);
    }
}
