package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpInfoDetail;
import com.feather.napo.mapper.NpInfoDetailMapper;
import com.feather.napo.mapper.NpInfoMapper;
import com.feather.napo.service.INpInfoDetailService;
import com.feather.napo.service.INpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-17 8:30
 */
@Service
public class NpInfoDetailServiceImpl implements INpInfoDetailService {

    @Autowired
    private NpInfoDetailMapper npInfoDetailMapper;

    @Autowired
    private UidWorker uidWorker;

    @Override
    public NpInfoDetail selectNpInfoDetailById(Long infoId) {
        return npInfoDetailMapper.selectNpInfoDetailById(infoId);
    }

    @Override
    public List<NpInfoDetail> selectNpInfoDetailList(NpInfoDetail npInfoDetail) {
        return npInfoDetailMapper.selectNpInfoDetailList(npInfoDetail);
    }

    @Override
    public int insertNpInfoDetail(NpInfoDetail npInfoDetail) {
        if (npInfoDetail.getInfoDetailId() == null) {
            npInfoDetail.setInfoDetailId(uidWorker.getNextId());
        }
        return npInfoDetailMapper.insertNpInfoDetail(npInfoDetail);
    }

    @Override
    public int updateNpInfoDetail(NpInfoDetail npInfoDetail) {
        return npInfoDetailMapper.updateNpInfoDetail(npInfoDetail);
    }

    @Override
    public int deleteNpInfoDetailById(Long infoDetailId) {
        return npInfoDetailMapper.deleteNpInfoDetailById(infoDetailId);
    }

    @Override
    public int deleteNpInfoDetailByIds(String ids) {
        Long[] npInfoIds = Convert.toLongArray(ids);
        return npInfoDetailMapper.deleteNpInfoDetailByIds(npInfoIds);
    }
}
