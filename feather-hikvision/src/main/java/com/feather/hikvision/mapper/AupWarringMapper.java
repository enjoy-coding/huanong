package com.feather.hikvision.mapper;

import com.feather.hikvision.domain.AupWarring;

/**
 * 预警记录信息Mapper接口
 */
public interface AupWarringMapper {
    /**
     * 新增预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    public int insertAupWarring(AupWarring aupWarring);

    public int updateAupWarring(String code);
}
