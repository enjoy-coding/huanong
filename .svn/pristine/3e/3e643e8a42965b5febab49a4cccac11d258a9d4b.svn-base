package com.feather.napo.service;

import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpInfoDetail;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-16 18:03
 */
public interface INpInfoService {

    NpInfo selectNpInfoById(Long infoId);

    List<NpInfo> selectNpInfoByIds(String ids);

    List<NpInfo> selectNpInfoList(NpInfo npInfo);

    List<NpInfo> selectFrontLyfwList();

    List<NpInfo> selectUnReadNpInfoByDeviceId(String deviceId);

    int insertNpInfo(NpInfo npInfo);

    int updateNpInfo(NpInfo npInfo);

    int deleteNpInfoById(Long infoId);

    int deleteNpInfoByIds(String ids);
}
