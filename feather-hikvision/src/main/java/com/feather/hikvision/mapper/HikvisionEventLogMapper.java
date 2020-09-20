package com.feather.hikvision.mapper;

import java.util.List;

import com.feather.hikvision.domain.HikvisionEventLog;

/**
 * @author nothing
 * @date 2020/3/3 10:14
 */
public interface HikvisionEventLogMapper {

    /**
     * 获取海康平台事件日志根据ID
     *
     * @param id
     *            安防统计ID
     * @return 安防统计
     */
    public HikvisionEventLog selectHikvisionEventLogById(Long id);

    /**
     * 批量获取海康平台事件日志
     * 
     * @param hikvisionEventLog
     * @return
     */
    public List<HikvisionEventLog> selectHikvisionEventLogList(HikvisionEventLog hikvisionEventLog);

    /**
     * 新增海康平台事件日志
     *
     * @param hikvisionEventLog
     * @return 结果
     */
    public int insertHikvisionEventLog(HikvisionEventLog hikvisionEventLog);

    /**
     * 更新海康平台事件日志
     *
     * @param hikvisionEventLog
     * @return 结果
     */
    public int updateHikvisionEventLog(HikvisionEventLog hikvisionEventLog);

    /**
     * 删除海康平台事件日志结果
     *
     * @param id
     * @return 结果
     */
    public int deleteHikvisionEventLogById(Long id);

    /**
     * 批量删除海康平台事件日志
     *
     * @return 结果
     */
    public int deleteAllHikvisionEventLog();
}
