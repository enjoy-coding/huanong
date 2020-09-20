package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupInspectCoordinate;
import com.feather.aupipes.mapper.AupInspectCoordinateMapper;
import com.feather.aupipes.service.IAupInspectCoordinateService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 巡检坐标Service业务层处理
 * 
 * @author fancy
 * @date 2020-03-13
 */
@Service
public class AupInspectCoordinateServiceImpl implements IAupInspectCoordinateService {
    @Autowired
    private AupInspectCoordinateMapper aupInspectCoordinateMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询巡检坐标
     * 
     * @param coordinateId
     *            巡检坐标ID
     * @return 巡检坐标
     */
    @Override
    public AupInspectCoordinate selectAupInspectCoordinateById(Long coordinateId) {
        return aupInspectCoordinateMapper.selectAupInspectCoordinateById(coordinateId);
    }

    /**
     * 查询巡检坐标列表
     * 
     * @param aupInspectCoordinate
     *            巡检坐标
     * @return 巡检坐标
     */
    @Override
    public List<AupInspectCoordinate> selectAupInspectCoordinateList(AupInspectCoordinate aupInspectCoordinate) {
        return aupInspectCoordinateMapper.selectAupInspectCoordinateList(aupInspectCoordinate);
    }

    @Override
    public AjaxResult selectAupInspectCoordinateGeoJson(AupInspectCoordinate aupInspectCoordinate) {
        return assembleGeoJson(aupInspectCoordinateMapper.selectAupInspectCoordinateList(aupInspectCoordinate));
    }

    private AjaxResult assembleGeoJson(List<AupInspectCoordinate> list) {
        if (list == null || list.size() == 0) {
            return AjaxResult.error("获取坐标失败");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "FeatureCollection");

        JSONArray features = new JSONArray();
        JSONObject features1 = new JSONObject();
        features1.put("type", "Feature");

        JSONObject geometry = new JSONObject();
        geometry.put("type", "MultiPoint");

        JSONObject properties = new JSONObject();

        JSONArray coordinates = new JSONArray();
        JSONArray time = new JSONArray();
        for (AupInspectCoordinate item : list) {
            JSONArray coordi = new JSONArray();
            coordi.add(item.getLongitude());
            coordi.add(item.getLatitude());
            coordi.add("35.1");

            coordinates.add(coordi);
            time.add(item.getCreateTime());
        }

        geometry.put("coordinates", coordinates);
        properties.put("time", time);
        features1.put("geometry", geometry);
        features1.put("properties", properties);

        features.add(features1);

        jsonObject.put("features", features);

        return AjaxResult.success(jsonObject);
    }

    /**
     * 新增巡检坐标
     * 
     * @param aupInspectCoordinate
     *            巡检坐标
     * @return 结果
     */
    @Override
    public int insertAupInspectCoordinate(AupInspectCoordinate aupInspectCoordinate) {
        //aupInspectCoordinate.setCoordinateId(uidWorker.getNextId());
        aupInspectCoordinate.setCreateTime(new Date());
        return aupInspectCoordinateMapper.insertAupInspectCoordinate(aupInspectCoordinate);
    }

    /**
     * 修改巡检坐标
     * 
     * @param aupInspectCoordinate
     *            巡检坐标
     * @return 结果
     */
    @Override
    public int updateAupInspectCoordinate(AupInspectCoordinate aupInspectCoordinate) {
        return aupInspectCoordinateMapper.updateAupInspectCoordinate(aupInspectCoordinate);
    }

    /**
     * 删除巡检坐标对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectCoordinateByIds(String ids) {
        return aupInspectCoordinateMapper.deleteAupInspectCoordinateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡检坐标信息
     * 
     * @param coordinateId
     *            巡检坐标ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectCoordinateById(Long coordinateId) {
        return aupInspectCoordinateMapper.deleteAupInspectCoordinateById(coordinateId);
    }
}
