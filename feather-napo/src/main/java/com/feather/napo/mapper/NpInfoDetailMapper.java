package com.feather.napo.mapper;

import com.feather.napo.domain.NpInfoDetail;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-19 11:22
 */
public interface NpInfoDetailMapper {
    /**
     * 根据主键获取信息
     *
     * @param infoDetailId
     * @return
     */
    NpInfoDetail selectNpInfoDetailById(Long infoDetailId);

    /**
     * 查询一组符合条件的信息
     *
     * @param npInfoDetail
     * @return
     */
    List<NpInfoDetail> selectNpInfoDetailList(NpInfoDetail npInfoDetail);

    /**
     * 插入一条NpInfoDetail
     *
     * @param npInfoDetail
     * @return
     */
    int insertNpInfoDetail(NpInfoDetail npInfoDetail);

    /**
     * 更新一条NpInfoDetail
     * @param npInfoDetail
     * @return
     */
    int updateNpInfoDetail(NpInfoDetail npInfoDetail);

    /**
     * 删除一条NpInfoDetail
     * @param infoId
     * @return
     */
    int deleteNpInfoDetailById(Long infoId);

    /**
     * 删除一组详情
     * @param ids
     * @return
     */
    int deleteNpInfoDetailByIds(Long[] ids);

}
