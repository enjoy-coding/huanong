package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.napo.domain.NpInfoReadLog;
import com.feather.napo.mapper.NpInfoReadLogMapper;
import com.feather.napo.service.INpInfoReadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author nothing
 * @date 2020-06-17 8:30
 */
@Service
public class NpInfoReadLogServiceImpl implements INpInfoReadLogService {

    @Autowired
    NpInfoReadLogMapper npInfoReadLogMapper;


    @Autowired
    private UidWorker uidWorker;

    @Override
    public NpInfoReadLog selectNpInfoReadLogById(Long readId) {
        return npInfoReadLogMapper.selectNpInfoReadLogById(readId);
    }

    @Override
    public List<NpInfoReadLog> selectNpInfoReadLogList(NpInfoReadLog npInfoReadLog) {
        return npInfoReadLogMapper.selectNpInfoReadLogList(npInfoReadLog);
    }

    @Override
    public int insertNpInfoReadLog(NpInfoReadLog npInfoReadLog) {
        if (npInfoReadLog.getReadId() == null) {
            npInfoReadLog.setReadId(uidWorker.getNextId());
        }
        if (npInfoReadLog.getReadFlag() == null) {
            npInfoReadLog.setReadFlag(0);
        }
        return npInfoReadLogMapper.insertNpInfoReadLog(npInfoReadLog);
    }

    @Override
    public int insertNpInfoReadLogBatch(List<NpInfoReadLog> npInfoReadLogs) {
        return npInfoReadLogMapper.insertNpInfoReadLogBatch(npInfoReadLogs);
    }

    @Override
    public int updateNpInfoReadLog(NpInfoReadLog npInfoReadLog) {
        return npInfoReadLogMapper.updateNpInfoReadLog(npInfoReadLog);
    }

    @Override
    public void changeNpInfoReadState(String deviceId, Long infoId) {
        NpInfoReadLog npInfoReadLog = new NpInfoReadLog();
        npInfoReadLog.setInfoId(infoId);
        npInfoReadLog.setDeviceId(deviceId);
        npInfoReadLog.setReadFlag(0);
        List<NpInfoReadLog> npInfoReadLogs = npInfoReadLogMapper.selectNpInfoReadLogList(npInfoReadLog);
        if (!Objects.isNull(npInfoReadLogs) && npInfoReadLogs.size() > 0) {
            for (NpInfoReadLog infoReadLog : npInfoReadLogs) {
                //更新状态为已读
                infoReadLog.setReadFlag(1);
                infoReadLog.setUpdateTime(DateUtils.getNowDate());
                updateNpInfoReadLog(infoReadLog);
            }
        }
    }

    @Override
    public int deleteNpInfoReadLogById(Long readId) {
        return npInfoReadLogMapper.deleteNpInfoReadLogById(readId);
    }

    @Override
    public int deleteNpInfoReadLogByIds(String ids) {
        Long[] npReadIds = Convert.toLongArray(ids);
        return npInfoReadLogMapper.deleteNpInfoReadLogByIds(npReadIds);
    }
}
