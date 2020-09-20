package com.feather.smart.mapper;

import com.feather.smart.domain.ZhsqTsjy;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 投诉建议Mapper接口
 * 
 * @author fancy
 * @date 2020-05-14
 */
public interface ZhsqTsjyMapper 
{
    /**
     * 查询投诉建议
     * 
     * @param tsjyid 投诉建议ID
     * @return 投诉建议
     */
    public ZhsqTsjy selectZhsqTsjyById(String tsjyid);

    /**
     * 查询投诉建议列表
     * 
     * @param zhsqTsjy 投诉建议
     * @return 投诉建议集合
     */
    public List<ZhsqTsjy> selectZhsqTsjyList(ZhsqTsjy zhsqTsjy);

    /**
     * 新增投诉建议
     * 
     * @param zhsqTsjy 投诉建议
     * @return 结果
     */
    public int insertZhsqTsjy(ZhsqTsjy zhsqTsjy);

    /**
     * 修改投诉建议
     * 
     * @param zhsqTsjy 投诉建议
     * @return 结果
     */
    public int updateZhsqTsjy(ZhsqTsjy zhsqTsjy);

    /**
     * 删除投诉建议
     * 
     * @param tsjyid 投诉建议ID
     * @return 结果
     */
    public int deleteZhsqTsjyById(String tsjyid);

    /**
     * 批量删除投诉建议
     * 
     * @param tsjyids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqTsjyByIds(String[] tsjyids);

    public List<Map>getSuggestionList(@Param("sqid") String sqid, @Param("xqid") String xqid);
}
