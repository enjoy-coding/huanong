package com.feather.smart.service;

import java.util.List;
import java.util.Map;

import com.feather.smart.domain.ZhsqZc;

/**
 * 社区资产Service接口
 *
 * @author fancy
 * @date 2020-05-14
 */
public interface IZhsqZcService {
    /**
     * 查询社区资产
     *
     * @param zcid
     *            社区资产ID
     * @return 社区资产
     */
    public ZhsqZc selectZhsqZcById(String zcid);

    /**
     * 查询社区资产列表
     *
     * @param zhsqZc
     *            社区资产
     * @return 社区资产集合
     */
    public List<ZhsqZc> selectZhsqZcList(ZhsqZc zhsqZc);

    /**
     * 新增社区资产
     *
     * @param zhsqZc
     *            社区资产
     * @return 结果
     */
    public int insertZhsqZc(ZhsqZc zhsqZc);

    /**
     * 修改社区资产
     *
     * @param zhsqZc
     *            社区资产
     * @return 结果
     */
    public int updateZhsqZc(ZhsqZc zhsqZc);

    /**
     * 批量删除社区资产
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqZcByIds(String ids);

    /**
     * 删除社区资产信息
     *
     * @param zcid
     *            社区资产ID
     * @return 结果
     */
    public int deleteZhsqZcById(String zcid);

    public List<Map> getSbJc(String type, String id);

    public List<Map> getAreaTree(String sqid);

    public List<Map> getSqAreaTree();

    public List<Map> getSbxxCount(String sqid, String xqid);

    public List<Map> getSblxYcCount(String sqid, String xqid);

    public List<Map> getListSb(String sblx, String sbmc, String zcid, String sqid, String xqid);

    public List<Map> getCljcList(String sqid, String xqid, String clhm, String id, String cllx);

    public List<Map> getClList(String sqid, String xqid, String clhm, String id, String cx);
}