package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScVolunteer;

/**
 * 志愿者信息Mapper接口
 * 
 * @author fancy
 * @date 2019-11-19
 */
public interface ScVolunteerMapper 
{
    /**
     * 查询志愿者信息
     * 
     * @param userId 志愿者信息ID
     * @return 志愿者信息
     */
    public ScVolunteer selectScVolunteerById(Long userId);

    /**
     * 查询志愿者信息列表
     * 
     * @param scVolunteer 志愿者信息
     * @return 志愿者信息集合
     */
    public List<ScVolunteer> selectScVolunteerList(ScVolunteer scVolunteer);

    /**
     * 新增志愿者信息
     * 
     * @param scVolunteer 志愿者信息
     * @return 结果
     */
    public int insertScVolunteer(ScVolunteer scVolunteer);

    /**
     * 修改志愿者信息
     * 
     * @param scVolunteer 志愿者信息
     * @return 结果
     */
    public int updateScVolunteer(ScVolunteer scVolunteer);

    /**
     * 删除志愿者信息
     * 
     * @param userId 志愿者信息ID
     * @return 结果
     */
    public int deleteScVolunteerById(Long userId);

    /**
     * 批量删除志愿者信息
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScVolunteerByIds(String[] userIds);
}