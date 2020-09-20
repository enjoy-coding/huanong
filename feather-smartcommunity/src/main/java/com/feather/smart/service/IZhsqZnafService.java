package com.feather.smart.service;

import java.util.List;
import java.util.Map;

/**
 * 社区资产Service接口
 *
 * @author fancy
 * @date 2020-05-14
 */
public interface IZhsqZnafService {
    public List<Map> getCountSb(String sqid, String xqid);

    public List<Map> getYcList(String sqid, String xqid);

    public List<Map> getCountMj(String sqid, String xqid);

    public List<Map> getMjjcList(String sqid, String xqid);

    public List<Map> getCountZj(String sqid, String xqid);

    public List<Map> getZjjcList(String sqid, String xqid);

    public List<Map> getCountDg(String sqid, String xqid);

    public List<Map> getDgjcList(String sqid, String xqid);

    public List<Map> getSxtList(String wzlx, String sqid, String xqid);
}