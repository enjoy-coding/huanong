package com.feather.patrol.mapper;

import java.util.List;

import com.feather.patrol.domain.forexmobi.Facility;

/**
 * Facility Mapper接口
 */
public interface FacilityMapper {

    /**
     * 查询Facility
     * 
     * @param qrcode
     *            巡点二维码
     * @return Facility
     */
    public List<Facility> selectFacilityByQrcode(String qrcode);
}