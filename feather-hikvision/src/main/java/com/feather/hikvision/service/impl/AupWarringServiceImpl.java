package com.feather.hikvision.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.hikvision.domain.AupWarring;
import com.feather.hikvision.mapper.AupWarringMapper;
import com.feather.hikvision.service.IAupWarringService;

/**
 * 预警记录信息Service业务层处理
 */
@Service
public class AupWarringServiceImpl implements IAupWarringService {
    @Autowired
    private AupWarringMapper aupWarringMapper;

    /**
     * 新增预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    @Override
    public int insertAupWarring(AupWarring aupWarring) {
        return aupWarringMapper.insertAupWarring(aupWarring);
    }

    @Override
    public int updateAupWarring(String code) {
        return aupWarringMapper.updateAupWarring(code);
    }
}
