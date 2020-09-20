package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.napo.domain.NpIndustry;
import com.feather.napo.mapper.NpIndustryMapper;
import com.feather.napo.service.INpIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 14:32
 */
@Service
public class NpIndustryServiceImpl implements INpIndustryService {

    @Autowired
    NpIndustryMapper npIndustryMapper;
    @Autowired
    private UidWorker uidWorker;

    @Override
    public NpIndustry selectNpIndustryById(Long industryId) {
        return npIndustryMapper.selectNpIndustryById(industryId);
    }

    @Override
    public List<NpIndustry> selectNpIndustryList(NpIndustry npIndustry) {
        return npIndustryMapper.selectNpIndustryList(npIndustry);
    }

    @Override
    public int insertNpIndustry(NpIndustry npIndustry) {
        if (npIndustry.getIndustryId() == null) {
            npIndustry.setIndustryId(uidWorker.getNextId());
        }
        return npIndustryMapper.insertNpIndustry(npIndustry);
    }

    @Override
    public int updateNpIndustry(NpIndustry npIndustry) {
        return npIndustryMapper.updateNpIndustry(npIndustry);
    }

    @Override
    public int deleteNpIndustryById(Long industryId) {
        return npIndustryMapper.deleteNpIndustryById(industryId);
    }

    @Override
    public int deleteNpIndustryByIds(String ids) {
        Long[] npIndustryIds = Convert.toLongArray(ids);
        return npIndustryMapper.deleteNpIndustryByIds(npIndustryIds);
    }
}
