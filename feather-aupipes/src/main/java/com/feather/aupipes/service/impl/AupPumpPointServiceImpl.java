package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupPumpPoint;
import com.feather.aupipes.domain.DevGroups;
import com.feather.aupipes.mapper.AupPumpPointMapper;
import com.feather.aupipes.service.IAupPumpPointService;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;

/**
 * 泵房点位Service业务层处理
 *
 * @author fancy
 * @date 2020-04-17
 */
@Service
public class AupPumpPointServiceImpl implements IAupPumpPointService {
    private final  String CHARACTER = ",";
    @Autowired
    private AupPumpPointMapper aupPumpPointMapper;

    /**
     * 查询泵房点位
     *
     * @param id
     *            泵房点位ID
     * @return 泵房点位
     */
    @Override
    public AupPumpPoint selectAupPumpPointById(Long id) {
        return aupPumpPointMapper.selectAupPumpPointById(id);
    }

    /**
     * 查询泵房点位列表
     *
     * @param aupPumpPoint
     *            泵房点位
     * @return 泵房点位
     */
    @Override
    public List<AupPumpPoint> selectAupPumpPointList(AupPumpPoint aupPumpPoint) {
        return aupPumpPointMapper.selectAupPumpPointList(aupPumpPoint);
    }

    /**
     * 获取输出频率，水箱液位，出口压力，设定压力值的请求参数
     * 
     * @param point 对象
     * @return 结果
     */
    @Override
    public String getPumpPointValue(AupPumpPoint point) {
        List<DevGroups> devGroupsList = new ArrayList<>();
        List<String> pointList = new ArrayList<>();
        for (int i = 0; i < point.getAttrValue().split(CHARACTER).length; i++) {
            String pointCode = point.getAttrValue().split(CHARACTER)[i];
            String points = point.getPoint() + "_" + pointCode;
            pointList.add(points);
        }
        // 加上市政压力（进口压力）
        String szylCode = point.getPoint() + "_" + point.getSzyl();
        pointList.add(szylCode);
        // 转换为数组
        int pointSize = pointList.size();
        String[] pointNumber = pointList.toArray(new String[pointSize]);
        DevGroups devGroups = new DevGroups(1, pointNumber);
        devGroupsList.add(devGroups);
        Map<String, Object> devGroupsMap = new HashMap<>(16);
        devGroupsMap.put("devGroups", devGroupsList);

        return JSONObject.toJsonString(devGroupsMap);
    }

    /**
     * 获取变频工频状态的请求参数
     * 
     * @param point 点
     * @return 结果
     */
    @Override
    public String getPumpPointStatesValue(AupPumpPoint point) {
        List<DevGroups> devGroupsList = new ArrayList<>();
        DevGroups devGroups = new DevGroups();
        devGroups.setdType(1);
        
        // 数组长度，初始化为变频长度
        int devUnitsLength = point.getBp().split(CHARACTER).length;
        // 加入变频
        String[] devUnits0 = new String[point.getBp().split(CHARACTER).length];
        for (int i = 0; i < point.getBp().split(CHARACTER).length; i++) {
            devUnits0[i] = point.getPoint() + "_" + point.getBp().split(CHARACTER)[i];
        }
        // 如果工频属性存在，则增加请求工频属性
        String[] devUnits;
        if (point.getGp() != null) {
            devUnitsLength += point.getGp().split(CHARACTER).length;
            devUnits = new String[devUnitsLength];
            String[] devUnits1 = new String[point.getGp().split(CHARACTER).length];
            for (int i = 0; i < point.getGp().split(CHARACTER).length; i++) {
                devUnits1[i] = point.getPoint() + "_" + point.getGp().split(CHARACTER)[i];
            }
            System.arraycopy(devUnits0, 0, devUnits, 0, devUnits0.length);
            System.arraycopy(devUnits1, 0, devUnits, devUnits0.length, devUnits1.length);
        } else {
            devUnits = new String[devUnitsLength];
            devUnits = devUnits0;
        }

        devGroups.setDevUnits(devUnits);
        devGroupsList.add(devGroups);
        Map<String, Object> devGroupsMap = new HashMap<>(16);
        devGroupsMap.put("devGroups", devGroupsList);
        return JSONObject.toJsonString(devGroupsMap);
    }

    /**
     * 新增泵房点位
     *
     * @param aupPumpPoint
     *            泵房点位
     * @return 结果
     */
    @Override
    public int insertAupPumpPoint(AupPumpPoint aupPumpPoint) {
        return aupPumpPointMapper.insertAupPumpPoint(aupPumpPoint);
    }

    /**
     * 修改泵房点位
     *
     * @param aupPumpPoint
     *            泵房点位
     * @return 结果
     */
    @Override
    public int updateAupPumpPoint(AupPumpPoint aupPumpPoint) {
        return aupPumpPointMapper.updateAupPumpPoint(aupPumpPoint);
    }

    /**
     * 删除泵房点位对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpPointByIds(String ids) {
        return aupPumpPointMapper.deleteAupPumpPointByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除泵房点位信息
     *
     * @param pumId
     *            泵房点位ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpPointById(Long pumId) {
        return aupPumpPointMapper.deleteAupPumpPointById(pumId);
    }
}