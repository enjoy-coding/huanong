package com.feather.napo.mapper;

import com.feather.napo.domain.NpIndustry;
import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 9:35
 */
public interface NpIndustryMapper {
    /**
     * 主键查询
     * @param industryId
     * @return
     */
    NpIndustry selectNpIndustryById(Long industryId);

    /**
     * 列表查询
     * @param npIndustry
     * @return
     */
    List<NpIndustry> selectNpIndustryList(NpIndustry npIndustry);

    /**
     * 插入一条npIndustry
     *
     * @param npIndustry
     * @return
     */
    int insertNpIndustry(NpIndustry npIndustry);

    /**
     * 更新一条npIndustry
     * @param npIndustry
     * @return
     */
    int updateNpIndustry(NpIndustry npIndustry);

    /**
     * 删除一条npIndustry
     * @param industryId
     * @return
     */
    int deleteNpIndustryById(Long industryId);

    /**
     * 删除一组详情
     * @param ids
     * @return
     */
    int deleteNpIndustryByIds(Long[] ids);
}
