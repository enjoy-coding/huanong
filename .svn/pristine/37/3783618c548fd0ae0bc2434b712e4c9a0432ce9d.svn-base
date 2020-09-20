package com.feather.hikvision.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.hikvision.domain.HikvisionEventLog;
import com.feather.hikvision.mapper.HikvisionEventLogMapper;

/**
 * @author nothing
 * @date 2020/3/3 10:28
 */
@Service
public class HikvisionEventLogServiceImpl implements HikvisionEventLogMapper {
    @Autowired
    HikvisionEventLogMapper hikvisionEventLogMapper;

    @Override
    public HikvisionEventLog selectHikvisionEventLogById(Long id) {
        return hikvisionEventLogMapper.selectHikvisionEventLogById(id);
    }

    @Override
    public List<HikvisionEventLog> selectHikvisionEventLogList(HikvisionEventLog hikvisionEventLog) {
        return hikvisionEventLogMapper.selectHikvisionEventLogList(hikvisionEventLog);
    }

    @Override
    public int insertHikvisionEventLog(HikvisionEventLog hikvisionEventLog) {
        return hikvisionEventLogMapper.insertHikvisionEventLog(hikvisionEventLog);
    }

    @Override
    public int updateHikvisionEventLog(HikvisionEventLog hikvisionEventLog) {
        return hikvisionEventLogMapper.updateHikvisionEventLog(hikvisionEventLog);
    }

    @Override
    public int deleteHikvisionEventLogById(Long id) {
        return hikvisionEventLogMapper.deleteHikvisionEventLogById(id);
    }

    @Override
    public int deleteAllHikvisionEventLog() {
        return hikvisionEventLogMapper.deleteAllHikvisionEventLog();
    }
}
