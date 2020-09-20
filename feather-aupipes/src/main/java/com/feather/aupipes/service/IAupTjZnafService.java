package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupTjZnaf;
import java.util.List;

/**
 * 统计_智能安防Service接口
 *
 * @author fancy
 * @date 2020-03-12
 */
public interface IAupTjZnafService
{
    /**
     * 查询统计_智能安防
     *
     * @param mj 统计_智能安防ID
     * @return 统计_智能安防
     */
    AupTjZnaf selectAupTjZnafById(Long mj);

    /**
     * 查询统计_智能安防列表
     *
     * @param aupTjZnaf 统计_智能安防
     * @return 统计_智能安防集合
     */
    List<AupTjZnaf> selectAupTjZnafList(AupTjZnaf aupTjZnaf);

    /**
     * 新增统计_智能安防
     *
     * @param aupTjZnaf 统计_智能安防
     * @return 结果
     */
    int insertAupTjZnaf(AupTjZnaf aupTjZnaf);

    /**
     * 修改统计_智能安防
     *
     * @param aupTjZnaf 统计_智能安防
     * @return 结果
     */
    int updateAupTjZnaf(AupTjZnaf aupTjZnaf);

    /**
     * 批量删除统计_智能安防
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupTjZnafByIds(String ids);

    /**
     * 删除统计_智能安防信息
     *
     * @param mj 统计_智能安防ID
     * @return 结果
     */
    int deleteAupTjZnafById(Long mj);

    /**
     * 定时修改统计智能安防数据
     */
    void timeUpdateTjZnaf();
}