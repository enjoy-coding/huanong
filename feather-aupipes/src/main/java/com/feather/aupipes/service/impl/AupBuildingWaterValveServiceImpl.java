package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupBuildingWaterValve;
import com.feather.aupipes.domain.AupWaterValve;
import com.feather.aupipes.mapper.AupBuildingWaterValveMapper;
import com.feather.aupipes.service.IAupBuildingWaterValveService;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 水房屋回路Service业务层处理
 */
@Service
public class AupBuildingWaterValveServiceImpl implements IAupBuildingWaterValveService {

    @Autowired
    private AupBuildingWaterValveMapper aupBuildingWaterValveMapper;

    /**
     * 通过房屋查询
     * 
     * @param building
     *            楼栋
     * @return 关系集合
     */
    public List<AupBuildingWaterValve> selectByBuilding(String building) {
        return aupBuildingWaterValveMapper.selectByBuilding(building);
    }

    /**
     * 通过阀门查询
     * 
     * @param valves
     *            阀门
     * @return 关系集合
     */
    public List<AupBuildingWaterValve> selectByValve(String valve) {
        // TODO:
        return aupBuildingWaterValveMapper.selectByValve(Convert.toStrArray(valve));
    }

    /**
     * 新增关系
     * 
     * @param AupBuildingWaterValve
     *            关系
     * @return 结果
     */
    @Transactional
    public int insertAupBuildingWaterValve(List<AupWaterValve> aupWaterValve) {
        int size = aupWaterValve != null ? aupWaterValve.size() : 0;
        if (size > 0) {
            aupBuildingWaterValveMapper.deleteByBuilding(aupWaterValve.get(0).getVbCode());

            for (int i = 0; i < size; i++) {
                AupWaterValve bwv = aupWaterValve.get(i);
                aupBuildingWaterValveMapper.insertAupBuildingWaterValve(bwv);
            }
        }
        return size;
    }

    /**
     * 通过楼栋删除
     * 
     * @param building
     *            楼栋
     * @return 结果
     */
    public int deleteByBuilding(String building) {
        return aupBuildingWaterValveMapper.deleteByBuilding(building);
    }

    /**
     * 通过阀门楼栋删除
     * 
     * @param valve
     *            阀门
     * @return 结果
     */
    public int deleteByValve(String valve) {
        return aupBuildingWaterValveMapper.deleteByValve(valve);
    }

    /**
     * 通过楼栋或者阀门查询影响的阀门
     */
    public List<AupWaterValve> selectByTypeAndValue(String type, String value) {
        return aupBuildingWaterValveMapper.selectByTypeAndValue(type,value);
    }

    @Override
    public List<AupWaterValve> selectByTypeAndValueForDown(String chooseType, String value) {
        return aupBuildingWaterValveMapper.selectByTypeAndValueForDown(chooseType,value);
    }
}
