package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupInspectLog;
import com.feather.aupipes.mapper.AupInspectLogMapper;
import com.feather.aupipes.service.IAupInspectLogService;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 巡检任务微信消息推送日志Service业务层处理
 * 
 * @author fancy
 * @date 2020-06-20
 */
@Service
public class AupInspectLogServiceImpl implements IAupInspectLogService 
{
    @Autowired
    private AupInspectLogMapper aupInspectLogMapper;

    /**
     * 查询巡检任务微信消息推送日志
     * 
     * @param id 巡检任务微信消息推送日志ID
     * @return 巡检任务微信消息推送日志
     */
    @Override
    public AupInspectLog selectAupInspectLogById(Long id)
    {
        return aupInspectLogMapper.selectAupInspectLogById(id);
    }

    /**
     * 查询巡检任务微信消息推送日志列表
     * 
     * @param aupInspectLog 巡检任务微信消息推送日志
     * @return 巡检任务微信消息推送日志
     */
    @Override
    public List<AupInspectLog> selectAupInspectLogList(AupInspectLog aupInspectLog)
    {
        return aupInspectLogMapper.selectAupInspectLogList(aupInspectLog);
    }

    /**
     * 新增巡检任务微信消息推送日志
     * 
     * @param aupInspectLog 巡检任务微信消息推送日志
     * @return 结果
     */
    @Override
    public int insertAupInspectLog(AupInspectLog aupInspectLog)
    {
        return aupInspectLogMapper.insertAupInspectLog(aupInspectLog);
    }

    /**
     * 修改巡检任务微信消息推送日志
     * 
     * @param aupInspectLog 巡检任务微信消息推送日志
     * @return 结果
     */
    @Override
    public int updateAupInspectLog(AupInspectLog aupInspectLog)
    {
        return aupInspectLogMapper.updateAupInspectLog(aupInspectLog);
    }

    /**
     * 删除巡检任务微信消息推送日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectLogByIds(String ids)
    {
        return aupInspectLogMapper.deleteAupInspectLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡检任务微信消息推送日志信息
     * 
     * @param id 巡检任务微信消息推送日志ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectLogById(Long id)
    {
        return aupInspectLogMapper.deleteAupInspectLogById(id);
    }
}
