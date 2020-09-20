package com.feather.aupipes.service;

import java.util.List;
import java.util.Map;

import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.domain.AupWarring1;

/**
 * 预警记录信息Service接口
 * 
 * @author fancy
 * @date 2020-01-15
 */
public interface IAupWarringService {
    /**
     * 查询预警记录信息
     * 
     * @param id
     *            预警记录信息ID
     * @return 预警记录信息
     */
    AupWarring selectAupWarringById(String id);

    /**
     * 查询预警记录信息列表
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 预警记录信息集合
     */
    List<AupWarring> selectAupWarringList(AupWarring aupWarring);

    /**
     * 查询所有带外部图片的
     * 
     * @return 预警记录信息集合
     */
    List<AupWarring> selectAllWithOuterPic();

    /**
     * 新增预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    int insertAupWarring(AupWarring aupWarring);

    /**
     * 修改预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    int updateAupWarring(AupWarring aupWarring);

    /**
     * 批量删除预警记录信息
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    int deleteAupWarringByIds(String ids);

    /**
     * 删除预警记录信息信息
     * 
     * @param id
     *            预警记录信息ID
     * @return 结果
     */
    int deleteAupWarringById(String id);

    List<AupWarring1> downWarringList(String content, String level, String author, String dateTime1,
            String dateTime2, String state, String name, String formSysName);

    List<Map> localWarring();

    List<Map> getlocalWarring(String formSysName, String id, int state);

    Map getPosition(String itid, String name);

    Map getInfoBySn(String sn);
}
