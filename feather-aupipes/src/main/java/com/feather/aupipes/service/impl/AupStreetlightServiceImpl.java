package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupLdqk;
import com.feather.aupipes.domain.AupSbtj;
import com.feather.aupipes.domain.AupStreetlight;
import com.feather.aupipes.domain.AupStreetlightControl;
import com.feather.aupipes.mapper.AupScreenMapper;
import com.feather.aupipes.mapper.AupStreetlightControlMapper;
import com.feather.aupipes.mapper.AupStreetlightMapper;
import com.feather.aupipes.service.IAupIndexService;
import com.feather.aupipes.service.IAupStreetlightService;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AupStreetlightServiceImpl implements IAupStreetlightService {

    @Autowired
    private AupStreetlightMapper aupStreetlightMapper;

    @Autowired
    private AupStreetlightControlMapper aupStreetlightControlMapper;

    @Autowired
    private IAupIndexService aupIndexService;

    @Autowired
    private AupScreenMapper screen;

    /**
     * 查询路灯
     *
     * @param luid 路灯ID
     * @return 路灯
     */
    @Override
    public AupStreetlight selectAupStreetlightByIuid(String luid){
        return aupStreetlightMapper.selectAupStreetlightByIuid(luid);
    }


    @Override
    public void updateLightTime(String offTime, String onTime) {
        aupStreetlightMapper.updateLightTime(offTime,onTime);
    }

    @Override
    public Map<String,Object> getLightTime() {
        return aupStreetlightMapper.getLightTime();
    }

    /**
     * 获取首页路灯情况
     * @return 结果
     */
    @Override
    public Map<String, Object> getIndexDetail() {
        return BeanUtils.beanToMap(aupIndexService.getRealTimeLdqk());
    }

    /**
     * 查询路灯
     *
     * @param lid 路灯ID
     * @return 路灯
     */
    @Override
    public AupStreetlight selectAupStreetlightById(String lid)
    {
        return aupStreetlightMapper.selectAupStreetlightById(lid);
    }

    /**
     * 查询路灯列表
     *
     * @param aupStreetlight 路灯
     * @return 路灯
     */
    @Override
    public List<AupStreetlight> selectAupStreetlightList(AupStreetlight aupStreetlight)
    {
        return aupStreetlightMapper.selectAupStreetlightList(aupStreetlight);
    }

    /**
     * 根据父节点查询路灯或者路灯控制器
     */
    @Override
    public List<Map<String,Object>> queryStreetlightControlTree(String pid){
        List<Map<String,Object>> mList = new ArrayList<>();
        String zero = "0";
        if(zero.equals(pid==null?zero:pid)){
            List<AupStreetlightControl> aupStreetlightControls = aupStreetlightControlMapper.selectAupStreetlightControlList(new AupStreetlightControl());
            mList = BeanUtils.listConvert(aupStreetlightControls); 
        }else{
            AupStreetlight aupStreetlight = new AupStreetlight();
            aupStreetlight.setSid(pid);
            List<AupStreetlight> aupStreetlights = this.selectAupStreetlightList(aupStreetlight);
            mList = BeanUtils.listConvert(aupStreetlights);
        }
        return mList;
    }

    @Override
    public Map<String,Object> getStreetLightCount(){
        Map<String, Object> maps = new HashMap<>(16);
        AupSbtj sbtjZx = screen.sbtjZx();
        AupSbtj sbtjZs = screen.sbtjZs();
        int sumOnline = sbtjZx.getLd();
        int sum = sbtjZs.getLd();
        int sumUnOnline = 0;
        AupLdqk ldqk = aupIndexService.getRealTimeLdqk();
        if (ldqk != null) {
            sumOnline = ldqk.getLightsCount();
            sum = ldqk.getTotalCount();
            sumUnOnline = sum - sumOnline;
        }
        maps.put("sumOnline", sumOnline);
        maps.put("sum", sum);
        maps.put("sumUnOnline", sumUnOnline);
        return maps;
    }
}
