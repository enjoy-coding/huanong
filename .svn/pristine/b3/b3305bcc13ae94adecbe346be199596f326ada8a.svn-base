package com.feather.napo.mapper;

import com.feather.napo.domain.NpInfoReadLog;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 9:35
 */
public interface NpInfoReadLogMapper {
    /**
     * 主键查询
     * @param readId
     * @return
     */
    NpInfoReadLog selectNpInfoReadLogById(Long readId);

    /**
     * 列表查询
     * @param npInfoReadLog
     * @return
     */
    List<NpInfoReadLog> selectNpInfoReadLogList(NpInfoReadLog npInfoReadLog);

    /**
     * 插入一条npInfoReadLog
     *
     * @param npInfoReadLog
     * @return
     */
    int insertNpInfoReadLog(NpInfoReadLog npInfoReadLog);

    /**
     * 批量插入读的记录
     * @param npInfoReadLogs
     * @return
     */
    int insertNpInfoReadLogBatch(List<NpInfoReadLog> npInfoReadLogs);

    /**
     * 更新一条npInfoReadLog
     * @param npInfoReadLog
     * @return
     */
    int updateNpInfoReadLog(NpInfoReadLog npInfoReadLog);

    /**
     * 删除一条npInfoReadLog
     * @param readId
     * @return
     */
    int deleteNpInfoReadLogById(Long readId);

    /**
     * 删除一组npInfoReadLog
     * @param ids
     * @return
     */
    int deleteNpInfoReadLogByIds(Long[] ids);
}
