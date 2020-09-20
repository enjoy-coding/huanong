package com.feather.napo.mapper;

import com.feather.napo.domain.NpMobileDevice;

import java.util.List;

/**
 * @author liuli
 * @date 2020-07-29 9:43
 */
public interface NpMobileDeviceMapper {

    /**
     * 主键查询
     * @param deviceId
     * @return
     */
    NpMobileDevice selectNpMobileDeviceById(String deviceId);


    /**
     * 列表查询
     * @param npMobileDevice
     * @return
     */
    List<NpMobileDevice> selectNpMobileDeviceList(NpMobileDevice npMobileDevice);


    /**
     * 插入
     * @param mobileDevice
     * @return
     */
    int insertNpMobileDevice(NpMobileDevice mobileDevice);

    /**
     * 删除一条NpMobileDevice
     * @param deviceId
     * @return
     */
    int deleteNpMobileDeviceById(String deviceId);

    /**
     * 删除一组NpMobileDevice
     * @param ids
     * @return
     */
    int deleteNpMobileDeviceByIds(String[] ids);
}
