package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 首页统计数据的mapper
 * 
 * @author 李十通
 * @date 2020年1月8日11:08:38
 */
public interface AupScreenMapper {
    /** 设备统计在线 */
    public AupSbtj sbtjZx();

    /** 设备统计总数 */
    public AupSbtj sbtjZs();

    /** 路灯情况 */
    public AupLdqk ldqk();

    /** 智能安防 */
    public AupZnaf znaf();

    /**
     * 查询泵房基本信息
     * @return
     */
    public  List<Map<String,Object>> queryPumpNumber();

    /**
     * 能耗统计_查询总用量
     * @param cacheId
     * @return
     */
    Map<String,Object> queryIndexTotalUseNumber(String cacheId);


}
