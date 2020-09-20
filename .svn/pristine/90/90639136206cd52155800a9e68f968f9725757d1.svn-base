package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupRegionCamera;

import java.util.List;

/**
 * @author nothing
 * @date 2020-01-20 11:33
 */
public interface AupRegionCameraMapper {


    /**
     * 查询所有摄像头
     *
     * @return
     */
    public List<AupRegionCamera> selectAupRegionCameraAll();

    /**
     * 查询摄像头列表
     *
     * @param aupRegionCamera
     * @return
     */
    public List<AupRegionCamera> selectAupRegionCameraList(AupRegionCamera aupRegionCamera);

    /**
     * 插入摄像头
     *
     * @param aupRegionCamera
     * @return
     */
    public int insertAupRegionCamera(AupRegionCamera aupRegionCamera);


    /**
     * 查询指定监控
     *
     * @param cameraIndexCode
     * @return
     */
    public AupRegionCamera selectAupRegionCameraBycameraIndexCode(String cameraIndexCode);


    /**
     * 根据BIM_ID查询监控
     *
     * @param bimId
     * @return
     */
    public AupRegionCamera selectAupRegionCameraByBIM_ID(String bimId);
}
