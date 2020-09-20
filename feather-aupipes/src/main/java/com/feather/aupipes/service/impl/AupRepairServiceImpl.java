package com.feather.aupipes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupRepair;
import com.feather.aupipes.mapper.AupRepairMapper;
import com.feather.aupipes.service.IAupRepairService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;

/**
 * 维修记录Service业务层处理
 * 
 * @author fancy
 * @date 2020-04-01
 */
@Service
public class AupRepairServiceImpl implements IAupRepairService {
    @Autowired
    private AupRepairMapper aupRepairMapper;

    /**
     * 查询维修记录
     * 
     * @param id
     *            维修记录ID
     * @return 维修记录
     */
    @Override
    public AupRepair selectAupRepairById(Long id) {
        return aupRepairMapper.selectAupRepairById(id);
    }

    /**
     * 查询维修记录列表
     * 
     * @param aupRepair
     *            维修记录
     * @return 维修记录
     */
    @Override
    public List<AupRepair> selectAupRepairList(AupRepair aupRepair) {
        return aupRepairMapper.selectAupRepairList(aupRepair);
    }

    @Override
    public AjaxResult selectAupRepairListOfGeoJson() {
        return assembleGeoJson(aupRepairMapper.selectAupRepairList(new AupRepair()));
    }

    private AjaxResult assembleGeoJson(List<AupRepair> list) {
        if (list == null || list.size() == 0) {
            return AjaxResult.error("获取坐标失败");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "FeatureCollection");

        JSONArray features = new JSONArray();

        for (AupRepair item : list) {
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");

            JSONObject properties = new JSONObject();
            JSONObject geometry = new JSONObject();

            JSONArray coordi = new JSONArray();
            String location = item.getLocation();
            String[] locationArr = location.split(",");
            if (locationArr.length > 1) {
                coordi.add(Double.parseDouble(locationArr[0]));
                coordi.add(Double.parseDouble(locationArr[1]));
            }

            properties.put("id", item.getId());
            properties.put("name", item.getName());
            properties.put("userId", item.getUserId());
            properties.put("userName", item.getUserName());
            properties.put("repairTime", item.getRepairTime());
            properties.put("address", item.getAddress());
            properties.put("faultType", item.getFaultType());
            properties.put("faultId", item.getFaultId());
            properties.put("deviceId", item.getDeviceId());
            properties.put("deviceName", item.getDeviceName());
            properties.put("description", item.getDescription());

            properties.put("createTime", item.getCreateTime());
            properties.put("updateTime", item.getUpdateTime());

            feature.put("properties", properties);

            geometry.put("type", "Point");
            geometry.put("coordinates", coordi);

            feature.put("geometry", geometry);

            features.add(feature);
        }

        jsonObject.put("features", features);

        return AjaxResult.success(jsonObject);
    }

    /**
     * 新增维修记录
     * 
     * @param aupRepair
     *            维修记录
     * @return 结果
     */
    @Override
    public int insertAupRepair(AupRepair aupRepair) {
        return aupRepairMapper.insertAupRepair(aupRepair);
    }

    /**
     * 修改维修记录
     * 
     * @param aupRepair
     *            维修记录
     * @return 结果
     */
    @Override
    public int updateAupRepair(AupRepair aupRepair) {
        return aupRepairMapper.updateAupRepair(aupRepair);
    }

    /**
     * 删除维修记录对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupRepairByIds(String ids) {
        return aupRepairMapper.deleteAupRepairByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修记录信息
     * 
     * @param id
     *            维修记录ID
     * @return 结果
     */
    @Override
    public int deleteAupRepairById(Long id) {
        return aupRepairMapper.deleteAupRepairById(id);
    }
}
