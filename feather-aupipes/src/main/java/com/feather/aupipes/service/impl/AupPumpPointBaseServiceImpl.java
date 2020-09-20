package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupPumpPointBase;
import com.feather.aupipes.mapper.AupPumpPointBaseMapper;
import com.feather.aupipes.service.IAupPumpPointBaseService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 泵组基本数据值Service业务层处理
 *
 * @author fancy
 * @date 2020-04-18
 */
@Service
public class AupPumpPointBaseServiceImpl implements IAupPumpPointBaseService
{
    @Autowired
    private AupPumpPointBaseMapper aupPumpPointBaseMapper;

    @Autowired
    private UidWorker uidWorker;
    /**
     * 查询泵组基本数据值
     *
     * @param id 泵组基本数据值ID
     * @return 泵组基本数据值
     */
    @Override
    public AupPumpPointBase selectAupPumpPointBaseById(Long id)
    {
        return aupPumpPointBaseMapper.selectAupPumpPointBaseById(id);
    }

    @Override
    public AupPumpPointBase selectAupPumpPointBaseByPointId(Long pointId){
        return aupPumpPointBaseMapper.selectAupPumpPointBaseByPointId(pointId);
    }
    /**
     * 查询泵组基本数据值列表
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 泵组基本数据值
     */
    @Override
    public List<AupPumpPointBase> selectAupPumpPointBaseList(AupPumpPointBase aupPumpPointBase)
    {
        return aupPumpPointBaseMapper.selectAupPumpPointBaseList(aupPumpPointBase);
    }

    /**
     * 新增泵组基本数据值
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 结果
     */
    @Override
    public int insertAupPumpPointBase(AupPumpPointBase aupPumpPointBase)
    {
        aupPumpPointBase.setId(uidWorker.getNextId());
        return aupPumpPointBaseMapper.insertAupPumpPointBase(aupPumpPointBase);
    }

    /**
     * 修改泵组基本数据值
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 结果
     */
    @Override
    public int updateAupPumpPointBase(AupPumpPointBase aupPumpPointBase)
    {
        return aupPumpPointBaseMapper.updateAupPumpPointBase(aupPumpPointBase);
    }

    /**
     * 删除泵组基本数据值对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpPointBaseByIds(String ids)
    {
        return aupPumpPointBaseMapper.deleteAupPumpPointBaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除泵组基本数据值信息
     *
     * @param id 泵组基本数据值ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpPointBaseById(Long id)
    {
        return aupPumpPointBaseMapper.deleteAupPumpPointBaseById(id);
    }

    @Override
    public List<AupPumpPointBase> selectMaxTimeAupPumpPointBase(Long pointId){
        return aupPumpPointBaseMapper.selectMaxTimeAupPumpPointBase(pointId);
    }
}