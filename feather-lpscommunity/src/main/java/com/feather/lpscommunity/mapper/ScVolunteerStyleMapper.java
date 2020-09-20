package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScVolunteerStyle;

/**
 * 志愿者风采Mapper接口
 * 
 * @author fancy
 * @date 2019-11-21
 */
public interface ScVolunteerStyleMapper 
{
    /**
     * 查询志愿者风采
     * 
     * @param styleId 志愿者风采ID
     * @return 志愿者风采
     */
    public ScVolunteerStyle selectScVolunteerStyleById(Long styleId);

    /**
     * 查询志愿者风采
     * 
     * @param styleId 志愿者风采ID
     * @return 志愿者风采
     */
    public ScVolunteerStyle selectScVolunteerStyleFileById(Long styleId);

    /**
     * 查询志愿者风采列表
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 志愿者风采集合
     */
    public List<ScVolunteerStyle> selectScVolunteerStyleList(ScVolunteerStyle scVolunteerStyle);

    /**
     * 新增志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    public int insertScVolunteerStyle(ScVolunteerStyle scVolunteerStyle);

    /**
     * 修改志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    public int updateScVolunteerStyle(ScVolunteerStyle scVolunteerStyle);

    /**
     * 删除志愿者风采
     * 
     * @param styleId 志愿者风采ID
     * @return 结果
     */
    public int deleteScVolunteerStyleById(Long styleId);

    /**
     * 批量删除志愿者风采
     * 
     * @param styleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScVolunteerStyleByIds(String[] styleIds);
}