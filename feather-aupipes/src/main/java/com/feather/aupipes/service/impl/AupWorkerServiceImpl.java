package com.feather.aupipes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupWorkerMapper;
import com.feather.aupipes.domain.AupWorker;
import com.feather.aupipes.service.IAupWorkerService;
import com.feather.common.core.text.Convert;

/**
 * 巡检人员Service业务层处理
 * 
 * @author fancy
 * @date 2020-01-06
 */
@Service
public class AupWorkerServiceImpl implements IAupWorkerService 
{
    @Autowired
    private AupWorkerMapper aupWorkerMapper;

    /**
     * 查询巡检人员
     * 
     * @param workerId 巡检人员ID
     * @return 巡检人员
     */
    @Override
    public AupWorker selectAupWorkerById(Long workerId)
    {
        return aupWorkerMapper.selectAupWorkerById(workerId);
    }

    /**
     * 查询巡检人员列表
     * 
     * @param aupWorker 巡检人员
     * @return 巡检人员
     */
    @Override
    public List<AupWorker> selectAupWorkerList(AupWorker aupWorker)
    {
        return aupWorkerMapper.selectAupWorkerList(aupWorker);
    }

    /**
     * 新增巡检人员
     * 
     * @param aupWorker 巡检人员
     * @return 结果
     */
    @Override
    public int insertAupWorker(AupWorker aupWorker)
    {
        return aupWorkerMapper.insertAupWorker(aupWorker);
    }

    /**
     * 修改巡检人员
     * 
     * @param aupWorker 巡检人员
     * @return 结果
     */
    @Override
    public int updateAupWorker(AupWorker aupWorker)
    {
        return aupWorkerMapper.updateAupWorker(aupWorker);
    }

    /**
     * 删除巡检人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWorkerByIds(String ids)
    {
        return aupWorkerMapper.deleteAupWorkerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡检人员信息
     * 
     * @param workerId 巡检人员ID
     * @return 结果
     */
    @Override
    public int deleteAupWorkerById(Long workerId)
    {
        return aupWorkerMapper.deleteAupWorkerById(workerId);
    }
}
