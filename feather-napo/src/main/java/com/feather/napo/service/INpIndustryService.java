package com.feather.napo.service;

import com.feather.napo.domain.NpCar;
import com.feather.napo.domain.NpIndustry;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 14:27
 */
public interface INpIndustryService {
    NpIndustry selectNpIndustryById(Long industryId);

    List<NpIndustry> selectNpIndustryList(NpIndustry npIndustry);

    int insertNpIndustry(NpIndustry npIndustry);

    int updateNpIndustry(NpIndustry npIndustry);

    int deleteNpIndustryById(Long industryId);

    int deleteNpIndustryByIds(String ids);
}
