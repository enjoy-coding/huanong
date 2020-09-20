package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupPumpPointStatus;
import com.feather.aupipes.mapper.AupPumpPointStatusMapper;
import com.feather.aupipes.service.IAupPumpPointStatusService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 泵组状态值Service业务层处理
 *
 * @author fancy
 * @date 2020-04-18
 */
@Service
public class AupPumpPointStatusServiceImpl implements IAupPumpPointStatusService
{
    @Autowired
    private AupPumpPointStatusMapper aupPumpPointStatusMapper;

    @Autowired
    private UidWorker uidWorker;
    /**
     * 查询泵组状态值
     *
     * @param id 泵组状态值ID
     * @return 泵组状态值
     */
    @Override
    public AupPumpPointStatus selectAupPumpPointStatusById(Long id)
    {
        return aupPumpPointStatusMapper.selectAupPumpPointStatusById(id);
    }

    @Override
    public Long selectAupPumpPointStatusByPointId(AupPumpPointStatus aupPumpPointStatus){
        return aupPumpPointStatusMapper.selectAupPumpPointStatusByPointId(aupPumpPointStatus);
    }
    /**
     * 查询泵组状态值列表
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 泵组状态值
     */
    @Override
    public List<AupPumpPointStatus> selectAupPumpPointStatusList(AupPumpPointStatus aupPumpPointStatus)
    {
        return aupPumpPointStatusMapper.selectAupPumpPointStatusList(aupPumpPointStatus);
    }

    /**
     * 新增泵组状态值
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 结果
     */
    @Override
    public int insertAupPumpPointStatus(AupPumpPointStatus aupPumpPointStatus)
    {
        aupPumpPointStatus.setId(uidWorker.getNextId());
        return aupPumpPointStatusMapper.insertAupPumpPointStatus(aupPumpPointStatus);
    }

    /**
     * 修改泵组状态值
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 结果
     */
    @Override
    public int updateAupPumpPointStatus(AupPumpPointStatus aupPumpPointStatus)
    {
        return aupPumpPointStatusMapper.updateAupPumpPointStatus(aupPumpPointStatus);
    }

    /**
     * 删除泵组状态值对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpPointStatusByIds(String ids)
    {
        return aupPumpPointStatusMapper.deleteAupPumpPointStatusByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除泵组状态值信息
     *
     * @param id 泵组状态值ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpPointStatusById(Long id)
    {
        return aupPumpPointStatusMapper.deleteAupPumpPointStatusById(id);
    }

    @Override
    public List<AupPumpPointStatus> getAupPumpPointStatusByPoint(Long point) {
        return aupPumpPointStatusMapper.selectAupPumpPointStatusByPoint(point);
    }
}