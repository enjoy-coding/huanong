package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupRegionCamera;
import com.feather.aupipes.mapper.AupRegionCameraMapper;
import com.feather.aupipes.service.IAupMonitorService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.domain.ZtreeNode;
import com.feather.common.utils.Arith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 监控
 */
@Service
public class AupMonitorServiceImpl implements IAupMonitorService {

    @Autowired
    AupRegionCameraMapper aupRegionCameraMapper;

    /**
     * 获取监控在线和总数
     *
     * @return
     */
    @Override
    public Map<String, Object> getCount(int online, int total) {
        Map<String, Object> map = new HashMap<>();
        String percent = Arith.percent(online, total);
        map.put("name", "安防");
        map.put("icon", "icon-shexiangtou");
        map.put("code", "monitor");
        map.put("number", online);
        map.put("total", total);
        map.put("percent", percent);
        return map;
    }


    @Override
    public List<ZtreeNode> getZtreeNode(String pid) {
        List<ZtreeNode> ztreeNodes = new ArrayList<>();
        List<AupRegionCamera> aupRegionCameras = aupRegionCameraMapper.selectAupRegionCameraAll();

        if (!Objects.isNull(aupRegionCameras) && aupRegionCameras.size() > 0) {
            for (AupRegionCamera aupRegionCamera : aupRegionCameras) {
                if (!Objects.isNull(aupRegionCamera)) {
                    ztreeNodes.add(aupRegionCamera.convertToZtree());
                }
            }
        }

        return ztreeNodes;
    }

    @Override
    public AupRegionCamera selectAupRegionCameraBycameraIndexCode(String cameraIndexCode){
        return aupRegionCameraMapper.selectAupRegionCameraBycameraIndexCode(cameraIndexCode);
    }

    @Override
    public AupRegionCamera selectAupRegionCameraByBIM_ID(String bimId){
        return aupRegionCameraMapper.selectAupRegionCameraByBIM_ID(bimId);
    }
}
