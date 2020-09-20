package com.feather.napo.mapper;

import com.feather.napo.domain.NpDevice;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 9:35
 */
public interface NpDeviceMapper {
    /**
     * 主键查询
     * @param deviceId
     * @return
     */
    NpDevice selectNpDeviceById(Long deviceId);

    /**
     * 列表查询
     * @param npDevice
     * @return
     */
    List<NpDevice> selectNpDeviceList(NpDevice npDevice);

    /**
     * 插入一条NpDevice
     *
     * @param npDevice
     * @return
     */
    int insertNpDevice(NpDevice npDevice);

    /**
     * 更新一条NpDevice
     * @param npDevice
     * @return
     */
    int updateNpDevice(NpDevice npDevice);

    /**
     * 删除一条NpDevice
     * @param deviceId
     * @return
     */
    int deleteNpDeviceById(Long deviceId);

    /**
     * 删除一组详情
     * @param ids
     * @return
     */
    int deleteNpDeviceByIds(Long[] ids);
}
