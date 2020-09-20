package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.IpUtils;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpThumbsUpLog;
import com.feather.napo.mapper.NpThumbsUpLogMapper;
import com.feather.napo.service.INpThumbsUpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liuli
 * @date 2020-07-27 10:05
 */
@Service
public class NpThumbsUpLogService implements INpThumbsUpLogService {
    @Autowired
    private UidWorker uidWorker;
    @Autowired
    NpThumbsUpLogMapper thumbsUpLogMapper;

    @Override
    public int insertNpThumbsUpLog(HttpServletRequest request, NpInfo npInfo) {
        NpThumbsUpLog thumbsUpLog = new NpThumbsUpLog();
        thumbsUpLog.setThumbsUpId(uidWorker.getNextId());
        thumbsUpLog.setInfoId(npInfo.getInfoId());
        thumbsUpLog.setIp(IpUtils.getIpAddr(request));
        thumbsUpLog.setLogTime(DateUtils.getNowDate());
        return thumbsUpLogMapper.insertNpThumbsUpLog(thumbsUpLog);
    }

    @Override
    public boolean checkIfThumbsUp(HttpServletRequest request, NpInfo npInfo) {
        boolean flag = false;
        NpThumbsUpLog thumbsUpLog = new NpThumbsUpLog();
        thumbsUpLog.setInfoId(npInfo.getInfoId());
        thumbsUpLog.setIp(IpUtils.getIpAddr(request));
        List<NpThumbsUpLog> thumbsUpLogList = thumbsUpLogMapper.selectNpThumbsUpLogByIp(thumbsUpLog);
        if (thumbsUpLogList != null && thumbsUpLogList.size() > 0) {
            flag = true;
        }
        return flag;
    }
}
