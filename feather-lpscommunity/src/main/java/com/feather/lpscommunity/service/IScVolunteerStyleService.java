package com.feather.lpscommunity.service;

import org.springframework.web.multipart.MultipartFile;

import com.feather.lpscommunity.domain.ScVolunteerStyle;

import java.util.List;
import java.util.Map;

/**
 * 志愿者风采Service接口
 * 
 * @author fancy
 * @date 2019-11-21
 */
public interface IScVolunteerStyleService 
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
    public ScVolunteerStyle selectScVolunteerStyleFileById(Long styleId,String headUrl);

    /**
     * 查询志愿者风采列表
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 志愿者风采集合
     */
    public List<ScVolunteerStyle> selectScVolunteerStyleList(ScVolunteerStyle scVolunteerStyle);

    /**
     * 查询志愿者风采列表
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 志愿者风采集合
     */
    public List<Map<String,Object>> screenScVolunteerStyleList(ScVolunteerStyle scVolunteerStyle,String headUrl);

    /**
     * 新增志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    public int insertScVolunteerStyle(ScVolunteerStyle scVolunteerStyle);

     /**
     * 新增志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    public ScVolunteerStyle insertScVolunteerStyle(ScVolunteerStyle scVolunteerStyle,MultipartFile [] MultipartFile);

    /**
     * 修改志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    public int updateScVolunteerStyle(ScVolunteerStyle scVolunteerStyle);

    /**
     * 批量删除志愿者风采
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScVolunteerStyleByIds(String ids);

    /**
     * 删除志愿者风采信息
     * 
     * @param styleId 志愿者风采ID
     * @return 结果
     */
    public int deleteScVolunteerStyleById(Long styleId);
}