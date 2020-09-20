package com.feather.napo.mapper;

import com.feather.napo.domain.NpThumbsUpLog;

import java.util.List;

/**
 * @author liuli
 * @date 2020-07-27 9:40
 */
public interface NpThumbsUpLogMapper {
    /**
     * 主键查询
     * @param thumbsUpId
     * @return
     */
    NpThumbsUpLog selectNpThumbsUpLogById(Long thumbsUpId);

    /**
     * 根据ip查询文章是否点赞
     * @param npThumbsUpLog
     * @return
     */
    List<NpThumbsUpLog> selectNpThumbsUpLogByIp(NpThumbsUpLog npThumbsUpLog);

    /**
     * 插入记录
     * @param npThumbsUpLog
     * @return
     */
    int insertNpThumbsUpLog(NpThumbsUpLog npThumbsUpLog);
}
