package com.feather.aupipes.service.impl;


import com.feather.aupipes.domain.query.Jsgxgx;
import com.feather.aupipes.domain.query.Jsgxhl;
import com.feather.aupipes.domain.query.Jspoint;
import com.feather.aupipes.domain.query.Point;
import com.feather.aupipes.mapper.JsDAO;
import com.feather.aupipes.service.JsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tql
 * @description 给水管线点分析接口实现类
 * @date 2020年3月25日15:12:36
 */
@Service
public class JsServiceImpl implements JsService {

    @Autowired
    private JsDAO jsDAO;

    @Override
    public List<Jsgxhl> getJsgxhl() {
        Map<String,Object> map = new HashMap<>(16);
        return jsDAO.getJshlgx(map);
    }
    @Override
    public List<Jsgxgx> getJsgxgx(List<Point> fm) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("fm",fm);
        return jsDAO.getJsgxgx(map);
    }
    @Override
    public List<Jsgxhl> getSyfm(String fmid) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("fmid",fmid);
        return jsDAO.getSyfm(map);
    }
    @Override
    public List<Jsgxhl> getXyfm(String fmid) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("fmid",fmid);
        return jsDAO.getXyfm(map);
    }
    @Override
    public List<Jspoint> getValveInfo(List<Jsgxhl> list, String type) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("list",list);
        map.put("type",type);
        return jsDAO.getValveInfo(map);
    }
}
