package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupYjinfotables;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预警信息详情Mapper接口
 * 
 * @author fancy
 * @date 2020-01-07
 */
public interface AupYjinfotablesMapper 
{
    /**
     * 查询预警信息详情
     * 
     * @param itid 预警信息详情ID
     * @return 预警信息详情
     */
    public AupYjinfotables selectAupYjinfotablesById(String itid);

    /**
     * 查询预警信息详情列表
     * 
     * @param aupYjinfotables 预警信息详情
     * @return 预警信息详情集合
     */
    public List<AupYjinfotables> selectAupYjinfotablesList(AupYjinfotables aupYjinfotables);

    /**
     * 新增预警信息详情
     * 
     * @param aupYjinfotables 预警信息详情
     * @return 结果
     */
    public int insertAupYjinfotables(AupYjinfotables aupYjinfotables);

    /**
     * 修改预警信息详情
     * 
     * @param aupYjinfotables 预警信息详情
     * @return 结果
     */
    public int updateAupYjinfotables(AupYjinfotables aupYjinfotables);

    /**
     * 删除预警信息详情
     * 
     * @param itid 预警信息详情ID
     * @return 结果
     */
    public int deleteAupYjinfotablesById(String itid);

    /**
     * 批量删除预警信息详情
     * 
     * @param itids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupYjinfotablesByIds(String[] itids);


    public List<Map> getAllinfo(@Param("id")String id,@Param("state")int state,@Param("state1")String state1,@Param("formSysName")String formSysName,@Param("content")String content);

    public List<Map> countByname();
    public List<Map> getWaringTableList(@Param("name")String name,@Param("formSysName")String formSysName,@Param("content") String content,@Param("state") String state,@Param("author") String author,@Param("level") String level,@Param("dateTime1") String dateTime1,@Param("dateTime2") String dateTime2);
    public List<Map> getSearchWarringList(@Param("name")String name,@Param("formSysName")String formSysName,@Param("content") String content,@Param("state") String state,@Param("author") String author,@Param("level") String level,@Param("dateTime1") String dateTime1,@Param("dateTime2") String dateTime2);

    public List<Map> getLevelCount(@Param("name")String name,@Param("formSysName")String formSysName,@Param("content") String content,@Param("state") String state,@Param("author") String author,@Param("level") String level,@Param("dateTime1") String dateTime1,@Param("dateTime2") String dateTime2);
    public List<Map> getNameCount(@Param("name")String name,@Param("formSysName")String formSysName,@Param("content") String content,@Param("state") String state,@Param("author") String author,@Param("level") String level,@Param("dateTime1") String dateTime1,@Param("dateTime2") String dateTime2);
    public List<Map> getStateCount(@Param("name")String name,@Param("formSysName")String formSysName,@Param("content") String content,@Param("state") String state,@Param("author") String author,@Param("level") String level,@Param("dateTime1") String dateTime1,@Param("dateTime2") String dateTime2);

    Map getStreeLight(@Param("name")String name);
}
