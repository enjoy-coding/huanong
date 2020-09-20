package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.StringUtils;
import com.feather.napo.domain.NpMobileDevice;
import com.feather.napo.mapper.NpMobileDeviceMapper;
import com.feather.napo.service.INpMobileDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuli
 * @date 2020-07-29 10:03
 */
@Service
public class NpMobileDeviceServiceImpl implements INpMobileDeviceService {

    @Autowired
    NpMobileDeviceMapper npMobileDeviceMapper;

    @Override
    public NpMobileDevice selectNpMobileDeviceById(String deviceId) {
        return npMobileDeviceMapper.selectNpMobileDeviceById(deviceId);
    }

    @Override
    public List<NpMobileDevice> selectNpMobileDeviceList(NpMobileDevice mobileDevice) {
        return npMobileDeviceMapper.selectNpMobileDeviceList(mobileDevice);
    }

    @Override
    public int insertNpMobileDevice(NpMobileDevice mobileDevice) {
        if (mobileDevice.getSendFlag() == null) {
            mobileDevice.setSendFlag(0);
        }
        if(mobileDevice.getCreateTime() == null){
            mobileDevice.setCreateTime(DateUtils.getNowDate());
        }
        return npMobileDeviceMapper.insertNpMobileDevice(mobileDevice);
    }

    @Override
    public int deleteNpMobileDeviceById(String deviceId) {
        return npMobileDeviceMapper.deleteNpMobileDeviceById(deviceId);
    }

    @Override
    public int deleteNpMobileDeviceByIds(String ids) {
        String[] arr = new String[]{};
        if (!StringUtils.isEmpty(ids)) {
            arr = ids.split(",");
        }
        return npMobileDeviceMapper.deleteNpMobileDeviceByIds(arr);
    }
}
