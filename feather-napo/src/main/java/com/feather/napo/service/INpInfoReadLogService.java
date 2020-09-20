package com.feather.napo.service;

import com.feather.napo.domain.NpInfoReadLog;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 10:02
 */
public interface INpInfoReadLogService {
    NpInfoReadLog selectNpInfoReadLogById(Long readId);

    List<NpInfoReadLog> selectNpInfoReadLogList(NpInfoReadLog npInfoReadLog);

    int insertNpInfoReadLog(NpInfoReadLog npInfoReadLog);

    int insertNpInfoReadLogBatch(List<NpInfoReadLog> npInfoReadLogs);

    int updateNpInfoReadLog(NpInfoReadLog npInfoReadLog);

    void changeNpInfoReadState(String deviceId, Long infoId);

    int deleteNpInfoReadLogById(Long readId);

    int deleteNpInfoReadLogByIds(String ids);
}
