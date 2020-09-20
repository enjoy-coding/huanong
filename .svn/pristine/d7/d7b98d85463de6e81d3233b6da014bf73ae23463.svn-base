package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.query.*;

import com.feather.aupipes.mapper.GdDAO;
import com.feather.aupipes.mapper.GdLineDAO;
import com.feather.aupipes.mapper.GdPointDAO;
import com.feather.aupipes.service.GdService;
import com.feather.common.annotation.DataSource;
import com.feather.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tql
 * @description 供电服务接口实现类
 * @date 2020年2月10日11:13:21
 */
@Service
public class GdServiceImpl implements GdService {

    @Autowired
    private GdDAO gdDAO;

    @Autowired
    private GdLineDAO gdLineDAO;

    @Autowired
    private GdPointDAO gdPointDAO;

    @Override
    public List<Fw> getFwInfo(String fwid) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("fwid",fwid);
        return gdDAO.getFwinfo(map);
    }

    @Override
    public List<Fw> getPdfInfo(String pdfid) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("pdfid",pdfid);
        return gdDAO.getPdfinfo(map);
    }
    @Override
    public List<Line> getGdLine(){
       return gdLineDAO.getGdLine();
    }
    @Override
    public List<Pdf> getPdfs() {
        Map<String,Object> map = new HashMap<>(16);
        return gdDAO.getPdfs(map);
    }
    @Override
    public List<Line> getGdLineByOid(String oid) {
        Map<String,Object> map = new HashMap<>();
        map.put("oid",oid);
        return gdLineDAO.getGdLineByOid(map);
    }
    @Override
    public List<Fw> getFws() {
        Map<String,Object> map = new HashMap<>(16);
        return  gdDAO.getFws(map);
    }
    @Override
    public List<Hlgx> getHlgxs(String hlid) {
        Map<String,Object> map = new HashMap<>();
        map.put("hlid",hlid);
        return gdDAO.getHlgxs(map);
    }
    @Override
    public List<Point> getGdPoint(){
        return gdPointDAO.getGdPoint();
    }


}
