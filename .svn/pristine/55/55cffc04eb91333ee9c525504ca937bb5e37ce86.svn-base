package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.napo.config.InfoType;
import com.feather.napo.domain.NpDevice;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.mapper.NpDeviceMapper;
import com.feather.napo.mapper.NpInfoMapper;
import com.feather.napo.service.INpDeviceService;
import com.feather.napo.service.INpInfoService;
import com.feather.system.domain.SysDictData;
import com.feather.system.service.ISysDictDataService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author nothing
 * @date 2020-06-17 8:30
 */
@Service
public class NpDeviceServiceImpl implements INpDeviceService {

    private final String NP_DEVICE_TYPE = "np_device_type";

    private final String NP_DEVICE_STATE = "np_device_state";

    @Autowired
    NpDeviceMapper npDeviceMapper;

    @Autowired
    ISysDictDataService sysDictDataService;

    @Autowired
    private UidWorker uidWorker;

    @Override
    public NpDevice selectNpDeviceById(Long deviceId) {
        return npDeviceMapper.selectNpDeviceById(deviceId);
    }

    @Override
    public List<NpDevice> selectNpDeviceList(NpDevice npDevice) {
        List<NpDevice> npDevices = npDeviceMapper.selectNpDeviceList(npDevice);
        List<SysDictData> typeDictDatas = sysDictDataService.selectDictDataByType(NP_DEVICE_TYPE);
        List<SysDictData> stateDictDatas = sysDictDataService.selectDictDataByType(NP_DEVICE_STATE);
        if (!Objects.isNull(npDevices) && npDevices.size() > 0) {
            for (NpDevice device : npDevices) {
                int type = device.getType();
                int state = device.getState();
                for (SysDictData typeDictData : typeDictDatas){
                    String dictLabel = typeDictData.getDictLabel();
                    String dictValue = typeDictData.getDictValue();
                    if(String.valueOf(type).equals(dictValue)){
                        device.setTypeVal(dictLabel);
                        break;
                    }
                }

                for (SysDictData stateDictData : stateDictDatas){
                    String dictLabel = stateDictData.getDictLabel();
                    String dictValue = stateDictData.getDictValue();
                    if(String.valueOf(state).equals(dictValue)){
                        device.setStateVal(dictLabel);
                        break;
                    }
                }
            }
        }
        return npDevices;

    }

    @Override
    public int insertNpDevice(NpDevice npDevice) {
        if (npDevice.getDeviceId() == null) {
            npDevice.setDeviceId(uidWorker.getNextId());
        }
        return npDeviceMapper.insertNpDevice(npDevice);
    }

    @Override
    public int updateNpDevice(NpDevice npDevice) {
        return npDeviceMapper.updateNpDevice(npDevice);
    }

    @Override
    public int deleteNpDeviceById(Long deviceId) {
        return npDeviceMapper.deleteNpDeviceById(deviceId);
    }

    @Override
    public int deleteNpDeviceByIds(String ids) {
        Long[] npDeviceIds = Convert.toLongArray(ids);
        return npDeviceMapper.deleteNpDeviceByIds(npDeviceIds);
    }
}
