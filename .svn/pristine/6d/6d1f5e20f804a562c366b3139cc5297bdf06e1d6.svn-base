package com.feather.napo.mapper;

import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpInfoDetail;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-16 17:53
 */
public interface NpInfoMapper {

    /**
     * 根据主键获取信息
     *
     * @param infoId
     * @return
     */
    NpInfo selectNpInfoById(Long infoId);


    /**
     * 批量查询
     * @param infoIds
     * @return
     */
    List<NpInfo> selectNpInfoByIds(Long[] infoIds);

    /**
     * 查询一组符合条件的信息
     *
     * @param npInfo
     * @return
     */
    List<NpInfo> selectNpInfoList(NpInfo npInfo);

    /**
     * 获取主页旅游服务数据
     * @param ids
     * @return
     */
    List<NpInfo> selectFrontLyfwList(Long[] ids);

    /**
     * 插入一条NpInfo
     *
     * @param npInfo
     * @return
     */
    int insertNpInfo(NpInfo npInfo);

    /**
     * 更新一条NpInfo
     * @param npInfo
     * @return
     */
    int updateNpInfo(NpInfo npInfo);

    /**
     * 删除一条NpInfo
     * @param infoId
     * @return
     */
    int deleteNpInfoById(Long infoId);

    /**
     * 删除一组NpInfo
     * @param ids
     * @return
     */
    int deleteNpInfoByIds(Long[] ids);
}
