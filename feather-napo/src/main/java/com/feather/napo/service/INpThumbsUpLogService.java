package com.feather.napo.service;

import com.feather.napo.domain.NpInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuli
 * @date 2020-07-27 10:00
 */
public interface INpThumbsUpLogService {


    /**
     * 插入点赞记录
     * @param request
     * @param npInfo
     * @return
     */
    int insertNpThumbsUpLog(HttpServletRequest request, NpInfo npInfo);

    /**
     * 检查是否点赞
     * @param request
     * @param npInfo
     * @return
     */
    boolean checkIfThumbsUp(HttpServletRequest request, NpInfo npInfo);
}
