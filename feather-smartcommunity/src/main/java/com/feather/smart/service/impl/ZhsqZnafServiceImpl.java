package com.feather.smart.service.impl;

import com.feather.common.core.text.Convert;
import com.feather.smart.domain.ZhsqZc;
import com.feather.smart.mapper.ZhsqZcMapper;
import com.feather.smart.mapper.ZhsqZnafMapper;
import com.feather.smart.service.IZhsqZcService;
import com.feather.smart.service.IZhsqZnafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 社区资产Service业务层处理
 *
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqZnafServiceImpl implements IZhsqZnafService
{
    @Autowired
    private ZhsqZnafMapper zhsqZnafMapper;


    @Override
    public List<Map> getCountSb(String sqid,String xqid) {
        return zhsqZnafMapper.getCountSb(sqid,xqid);
    }

    @Override
    public List<Map> getYcList(String sqid,String xqid) {
        return zhsqZnafMapper.getYcList(sqid,xqid);
    }

    @Override
    public List<Map> getCountMj(String sqid,String xqid) {
        return zhsqZnafMapper.getCountMj(sqid,xqid);
    }
    @Override
    public List<Map> getMjjcList(String sqid,String xqid) {
        return zhsqZnafMapper.getMjjcList(sqid,xqid);
    }

    @Override
    public List<Map> getCountZj(String sqid,String xqid) {
        return zhsqZnafMapper.getCountZj(sqid,xqid);
    }

    @Override
    public List<Map> getZjjcList(String sqid,String xqid) {
        return zhsqZnafMapper.getZjjcList(sqid,xqid);
    }

    @Override
    public List<Map> getCountDg(String sqid,String xqid) {
        return zhsqZnafMapper.getCountDg(sqid,xqid);
    }

    @Override
    public List<Map> getDgjcList(String sqid,String xqid) {
        return zhsqZnafMapper.getDgjcList(sqid,xqid);
    }

    @Override
    public List<Map> getSxtList(String wzlx, String sqid, String xqid) {
        return zhsqZnafMapper.getSxtList(wzlx,sqid,xqid);
    }
}