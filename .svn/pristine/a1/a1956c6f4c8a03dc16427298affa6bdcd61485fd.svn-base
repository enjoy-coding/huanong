package com.feather.smart.mapper;

import com.feather.smart.domain.ZhsqYc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 异常信息Mapper接口
 * 
 * @author fancy
 * @date 2020-05-14
 */
public interface ZhsqYcMapper 
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
     * 删除异常信息
     * 
     * @param ycid 异常信息ID
     * @return 结果
     */
    public int deleteZhsqYcById(String ycid);

    /**
     * 批量删除异常信息
     * 
     * @param ycids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqYcByIds(String[] ycids);


    public List<Map> getSourceCount(@Param("sqid") String sqid, @Param("xqid") String xqid);
    public List<Map> getStatusCount(@Param("sqid") String sqid, @Param("xqid") String xqid);



}
