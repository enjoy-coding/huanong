package com.feather.patrol.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.feather.patrol.domain.PtrLog;

/**
 * 巡检日志Service接口
 */
public interface IPtrLogService {
    /**
     * 查询巡检日志
     * 
     * @param logId
     *            巡检日志ID
     * @return 巡检日志
     */
    public PtrLog selectPtrLogById(Long logId);

    /**
     * 查询巡检日志列表
     * 
     * @param ptrLog
     *            巡检日志
     * @return 巡检日志集合
     */
    public List<PtrLog> selectPtrLogList(PtrLog ptrLog);

    /**
     * 查询最近日志外观照片
     */
    public List<String> selectTopFacadeUrls();

    /**
     * 新增巡检日志
     * 
     * @param qrcode
     *            二维码
     * @param coordinate
     *            gps坐标
     * @param altitude
     *            gps高程
     * @param address
     *            地址名称
     * @param issue
     *            问题说明
     * @param standards
     *            异常点
     * @param facades
     *            现场照片
     * @return 结果
     */
    public int insertPtrLog(String qrcode, String coordinate, String altitude, String address, String issue,
            Long[] standards, MultipartFile[] facades);

    /**
     * 修改巡检日志
     * 
     * @param ptrLog
     *            巡检日志
     * @return 结果
     */
    public int updatePtrLog(PtrLog ptrLog);

    /**
     * 批量删除巡检日志
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePtrLogByIds(String ids);

    /**
     * 删除巡检日志信息
     * 
     * @param logId
     *            巡检日志ID
     * @return 结果
     */
    public int deletePtrLogById(Long logId);
}
