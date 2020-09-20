package com.feather.smart.mapper;

import com.feather.smart.domain.ZhsqZc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 社区资产Mapper接口
 *
 * @author fancy
 * @date 2020-05-14
 */
public interface ZhsqZcMapper
{
    /**
     * 查询社区资产
     *
     * @param zcid 社区资产ID
     * @return 社区资产
     */
    public ZhsqZc selectZhsqZcById(String zcid);

    /**
     * 查询社区资产列表
     *
     * @param zhsqZc 社区资产
     * @return 社区资产集合
     */
    public List<ZhsqZc> selectZhsqZcList(ZhsqZc zhsqZc);

    /**
     * 新增社区资产
     *
     * @param zhsqZc 社区资产
     * @return 结果
     */
    public int insertZhsqZc(ZhsqZc zhsqZc);

    /**
     * 修改社区资产
     *
     * @param zhsqZc 社区资产
     * @return 结果
     */
    public int updateZhsqZc(ZhsqZc zhsqZc);

    /**
     * 删除社区资产
     *
     * @param zcid 社区资产ID
     * @return 结果
     */
    public int deleteZhsqZcById(String zcid);

    /**
     * 批量删除社区资产
     *
     * @param zcids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqZcByIds(String[] zcids);

    public List<Map> getSbJc(@Param("table1")String table1,@Param("col")String col,@Param("name")String name);
    public List<Map> getSbJcAndJm(@Param("table1")String table1,@Param("col")String col,@Param("name")String name);
    public List<Map> getAreaTree(String sqid);
    public List<Map> getSqAreaTree();
    public List<Map> getSbxxCount(@Param("sqid") String sqid,@Param("xqid") String xqid);
    public List<Map> getSblxYcCount(@Param("sqid") String sqid,@Param("xqid") String xqid);
    public List<Map> getListSb(@Param("sblx")String sblx,@Param("sbmc")String sbmc,@Param("zcid")String zcid,@Param("sqid")String sqid,@Param("xqid")String xqid);
    public List<Map> getCljcList(@Param("sqid")String sqid,@Param("xqid")String xqid,@Param("clhm")String clhm,@Param("id")String id,@Param("cllx")String cllx);
    public List<Map> getClList(@Param("sqid")String sqid,@Param("xqid")String xqid,@Param("clhm")String clhm,@Param("id")String id,@Param("cx")String cx);

}