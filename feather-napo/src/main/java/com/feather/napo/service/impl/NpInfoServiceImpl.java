package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.napo.config.InfoType;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpInfoReadLog;
import com.feather.napo.domain.NpMobileDevice;
import com.feather.napo.mapper.NpInfoMapper;
import com.feather.napo.service.INpInfoReadLogService;
import com.feather.napo.service.INpInfoService;
import com.feather.napo.service.INpMobileDeviceService;
import com.feather.system.domain.SysDictData;
import com.feather.system.service.ISysDictDataService;
import com.feather.system.service.ISysDictTypeService;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author nothing
 * @date 2020-06-17 8:30
 */
@Service
public class NpInfoServiceImpl implements INpInfoService {

    private final String NP_SPOT_TYPE = "np_spot_type";

    @Autowired
    private NpInfoMapper npInfoMapper;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private INpMobileDeviceService npMobileDeviceService;

    @Autowired
    INpInfoReadLogService npInfoReadLogService;

    @Value("${napo.readType}")
    private String readType;

    @Override
    public NpInfo selectNpInfoById(Long infoId) {
        NpInfo npInfo = npInfoMapper.selectNpInfoById(infoId);
        if (npInfo != null) {
            int infoType = npInfo.getInfoType();
            //如果是旅游景点
            if (InfoType.I_12.getCode() == infoType) {
                String addInfo3 = dictDataService.selectDictLabel(NP_SPOT_TYPE, npInfo.getAddInfo2());
                npInfo.setAddInfo3(addInfo3);
            }
        } else {
            npInfo = new NpInfo();
        }
        return npInfo;
    }

    @Override
    public List<NpInfo> selectNpInfoByIds(String ids){
        Long[] npInfoIds = Convert.toLongArray(ids);
        return npInfoMapper.selectNpInfoByIds(npInfoIds);
    }


    @Override
    public List<NpInfo> selectNpInfoList(NpInfo npInfo) {
        List<NpInfo> npInfoList = npInfoMapper.selectNpInfoList(npInfo);
        if (npInfoList != null && npInfoList.size() > 0) {
            for (NpInfo info : npInfoList) {
                int infoType = info.getInfoType();
                String addInfo2 = info.getAddInfo2();
                if (InfoType.I_12.getCode() == infoType) {
                    List<SysDictData> dictDataList = dictDataService.selectDictDataByType(NP_SPOT_TYPE);
                    if (dictDataList != null && dictDataList.size() > 0) {
                        for (SysDictData dictData : dictDataList) {
                            if (dictData.getDictValue().equals(addInfo2)) {
                                info.setAddInfo3(dictData.getDictLabel());
                                break;
                            }
                        }
                    }

                }
            }
        }
        return npInfoList;
    }


    @Override
    public List<NpInfo> selectFrontLyfwList() {
        //查询的类型为吃美食;住酒店;买特产
        List<NpInfo> npInfoList = new ArrayList<>();
        String ids = "1,2,3";
        Map<Integer, Integer> map = ImmutableMap.of(1, 1, 2, 1, 3, 3);
        Long[] infoTypeIds = Convert.toLongArray(ids);
        List<NpInfo> dbNpInfoList = npInfoMapper.selectFrontLyfwList(infoTypeIds);
        if (!Objects.isNull(dbNpInfoList) && dbNpInfoList.size() > 0) {
            for (NpInfo npInfo : dbNpInfoList) {
                int infoType = npInfo.getInfoType();
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    int mapKey = entry.getKey();
                    int mapValue = entry.getValue();
                    if (infoType == mapKey && !checkInfoTypeIsFull(mapKey, mapValue, npInfoList)) {
                        npInfoList.add(npInfo);
                    }
                }
            }
        }
        return npInfoList;
    }

    /**
     * 判断特定类型的数据是否已经满了
     *
     * @param infoType
     * @param size
     * @param npInfoList
     * @return
     */
    private boolean checkInfoTypeIsFull(int infoType, int size, List<NpInfo> npInfoList) {
        boolean flag = false;
        int curSize = 0;
        for (NpInfo npInfo : npInfoList) {
            int curInfoType = npInfo.getInfoType();
            if (curInfoType == infoType) {
                curSize++;
            }
        }
        if (curSize >= size) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<NpInfo> selectUnReadNpInfoByDeviceId(String deviceId) {
        List<NpInfo> npInfos = new ArrayList<>();
        NpMobileDevice npMobileDevice = npMobileDeviceService.selectNpMobileDeviceById(deviceId);
        if (!Objects.isNull(npMobileDevice)) {
            NpInfoReadLog npInfoReadLog = new NpInfoReadLog();
            npInfoReadLog.setDeviceId(npMobileDevice.getDeviceId());
            npInfoReadLog.setReadFlag(0);
            List<NpInfoReadLog> npInfoReadLogs = npInfoReadLogService.selectNpInfoReadLogList(npInfoReadLog);
            if (!Objects.isNull(npInfoReadLogs) && npInfoReadLogs.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (NpInfoReadLog infoReadLog : npInfoReadLogs) {
                    sb.append(infoReadLog.getInfoId()).append(",");
                }
                npInfos = selectNpInfoByIds(sb.toString());
            }
        }

        return npInfos;
    }

    @Override
    public int insertNpInfo(NpInfo npInfo) {
        if (npInfo.getInfoId() == null) {
            npInfo.setInfoId(uidWorker.getNextId());
        }
        int rows = npInfoMapper.insertNpInfo(npInfo);
        try {
            addInfoReadLog(npInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    private void addInfoReadLog(NpInfo npInfo) {
        //查询所有的设备
        List<NpMobileDevice> npMobileDevices = npMobileDeviceService.selectNpMobileDeviceList(new NpMobileDevice());
        if ((!Objects.isNull(npMobileDevices) && npMobileDevices.size() > 0)
                && checkTypeInReadType(npInfo)) {
            NpInfoReadLog npInfoReadLog = null;
            List<NpInfoReadLog> npInfoReadLogs = new ArrayList<>();
            for (NpMobileDevice npMobileDevice : npMobileDevices) {
                if (npMobileDevice.getSendFlag() == 0) {
                    npInfoReadLog = new NpInfoReadLog();
                    npInfoReadLog.setReadId(uidWorker.getNextId());
                    npInfoReadLog.setInfoId(npInfo.getInfoId());
                    npInfoReadLog.setDeviceId(npMobileDevice.getDeviceId());
                    npInfoReadLog.setReadFlag(0);
                    npInfoReadLogs.add(npInfoReadLog);
                }
            }
            npInfoReadLogService.insertNpInfoReadLogBatch(npInfoReadLogs);
        }
    }

    /**
     * @return
     */
    private boolean checkTypeInReadType(NpInfo npInfo) {
        boolean flag = false;
        if (Strings.isBlank(readType)) {
            //如果未配置，即表示属于要记录未读的情况
            flag = true;
        }
        Integer infoType = npInfo.getInfoType();
        String[] readTypes = readType.split(",");
        for (String rType : readTypes) {
            if (rType.equals(infoType)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public int updateNpInfo(NpInfo npInfo) {
        return npInfoMapper.updateNpInfo(npInfo);
    }

    @Override
    public int deleteNpInfoById(Long infoId) {
        return npInfoMapper.deleteNpInfoById(infoId);
    }

    @Override
    public int deleteNpInfoByIds(String ids) {
        Long[] npInfoIds = Convert.toLongArray(ids);
        return npInfoMapper.deleteNpInfoByIds(npInfoIds);
    }
}
