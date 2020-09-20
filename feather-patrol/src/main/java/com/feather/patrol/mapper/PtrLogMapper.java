package com.feather.patrol.mapper;

import java.util.List;

import com.feather.patrol.domain.PtrFacade;
import com.feather.patrol.domain.PtrIssue;
import com.feather.patrol.domain.PtrLog;

/**
 * 巡检日志Mapper接口
 */
public interface PtrLogMapper {
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
     * @param ptrLog
     *            巡检日志
     * @return 结果
     */
    public int insertPtrLog(PtrLog ptrLog);

    /**
     * 新增巡检问题
     * 
     * @param ptrIssue
     * @return 结果
     */
    public int insertLogIssue(PtrIssue ptrIssue);

    /**
     * 新增巡检日志外观
     * 
     * @param ptrFacade
     * @return 结果
     */
    public int insertLogFacade(PtrFacade ptrFacade);

    /**
     * 修改巡检日志
     * 
     * @param ptrLog
     *            巡检日志
     * @return 结果
     */
    public int updatePtrLog(PtrLog ptrLog);

    /**
     * 删除巡检日志
     * 
     * @param logId
     *            巡检日志ID
     * @return 结果
     */
    public int deletePtrLogById(Long logId);

    /**
     * 批量删除巡检日志
     * 
     * @param logIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePtrLogByIds(String[] logIds);
}
