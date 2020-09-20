package com.feather.smart.service;

import com.feather.smart.domain.ZhsqYc;

import java.util.List;
import java.util.Map;

/**
 * 异常信息Service接口
 * 
 * @author fancy
 * @date 2020-05-14
 */
public interface IZhsqYcService 
{
    /**
     * 查询异常信息
     * 
     * @param ycid 异常信息ID
     * @return 异常信息
     */
    public ZhsqYc selectZhsqYcById(String ycid);

    /**
     * 查询异常信息列表
     * 
     * @param zhsqYc 异常信息
     * @return 异常信息集合
     */
    public List<ZhsqYc> selectZhsqYcList(ZhsqYc zhsqYc);

    /**
     * 新增异常信息
     * 
     * @param zhsqYc 异常信息
     * @return 结果
     */
    public int insertZhsqYc(ZhsqYc zhsqYc);

    /**
     * 修改异常信息
     * 
     * @param zhsqYc 异常信息
     * @return 结果
     */
    public int updateZhsqYc(ZhsqYc zhsqYc);

    /**
     * 批量删除异常信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqYcByIds(String ids);

    /**
     * 删除异常信息信息
     * 
     * @param ycid 异常信息ID
     * @return 结果
     */
    public int deleteZhsqYcById(String ycid);

    public List<Map> getSourceCount(String sqid,String xqid);
    public List<Map> getStatusCount(String sqid,String xqid);
}
