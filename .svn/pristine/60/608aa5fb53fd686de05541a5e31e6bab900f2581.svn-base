package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupStreetlight;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 路灯Mapper接口
 *
 * @author fancy
 * @date 2019-12-13
 */
public interface AupStreetlightMapper
{
    /**
     * 查询路灯
     *
     * @param lid 路灯ID
     * @return 路灯
     */
    public AupStreetlight selectAupStreetlightById(String lid);

    /**
     * 查询路灯
     *
     * @param luid 路灯ID
     * @return 路灯
     */
    public AupStreetlight selectAupStreetlightByIuid(String luid);

    /**
     * 查询路灯列表
     *
     * @param aupStreetlight 路灯
     * @return 路灯集合
     */
    public List<AupStreetlight> selectAupStreetlightList(AupStreetlight aupStreetlight);

    public void updateLightTime(@Param("offtime") String offtime, @Param("ontime") String ontime);

    public Map<String,Object> getLightTime();
}