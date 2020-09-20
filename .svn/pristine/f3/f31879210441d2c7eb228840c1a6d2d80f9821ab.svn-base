package com.feather.aupipes.service;

import java.util.List;
import java.util.Map;

import com.feather.aupipes.domain.AupYjinfotables;

/**
 * 预警信息详情Service接口
 * 
 * @author fancy
 * @date 2020-01-07
 */
public interface IAupYjinfotablesService {
    /**
     * 查询预警信息详情
     * 
     * @param itid
     *            预警信息详情ID
     * @return 预警信息详情
     */
    AupYjinfotables selectAupYjinfotablesById(String itid);

    /**
     * 查询预警信息详情列表
     * 
     * @param aupYjinfotables
     *            预警信息详情
     * @return 预警信息详情集合
     */
    List<AupYjinfotables> selectAupYjinfotablesList(AupYjinfotables aupYjinfotables);

    /**
     * 新增预警信息详情
     * 
     * @param aupYjinfotables
     *            预警信息详情
     * @return 结果
     */
    int insertAupYjinfotables(AupYjinfotables aupYjinfotables);

    /**
     * 修改预警信息详情
     * 
     * @param aupYjinfotables
     *            预警信息详情
     * @return 结果
     */
    int updateAupYjinfotables(AupYjinfotables aupYjinfotables);

    /**
     * 批量删除预警信息详情
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    int deleteAupYjinfotablesByIds(String ids);

    /**
     * 删除预警信息详情信息
     * 
     * @param itid
     *            预警信息详情ID
     * @return 结果
     */
    int deleteAupYjinfotablesById(String itid);

    List<Map> getAllinfo(String id, int state, String state1, String formSysName, String content);

    List<Map> getAllWarring(String id, int state, String state1, String formSysName);

    List<Map> countByname();

    List<Map> getWaringTableList(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2);

    List<Map> getSearchWarringList(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2);

    List<Map> getLevelCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2);

    List<Map> getNameCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2);

    List<Map> getStateCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2);

    Map getStreeLight(String name);

}
