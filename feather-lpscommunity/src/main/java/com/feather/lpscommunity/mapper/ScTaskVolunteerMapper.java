package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScTaskVolunteer;

/**
 * 志愿者任务表Mapper接口
 * 
 * @author fancy
 * @date 2019-11-19
 */
public interface ScTaskVolunteerMapper 
{
    /**
     * 查询志愿者任务表
     * 
     * @param tvId 志愿者任务表ID
     * @return 志愿者任务表
     */
    public ScTaskVolunteer selectScTaskVolunteerById(Long tvId);

    /**
     * 用户删除判断
     * 
     * @param volunteerIds 志愿者ID
     * @return 志愿者任务表
     */
    public ScTaskVolunteer checkScTaskVolunteerByVolunteerId(String [] volunteerIds);

    /**
     * 用户删除判断
     * 
     * @param parkrentIds 任务表ID
     * @return 志愿者任务表
     */
    public ScTaskVolunteer checkScTaskVolunteerByParkrentId(String [] parkrentIds);

    /**
     * 查询志愿者任务表列表
     * 
     * @param scTaskVolunteer 志愿者任务表
     * @return 志愿者任务表集合
     */
    public List<ScTaskVolunteer> selectScTaskVolunteerList(ScTaskVolunteer scTaskVolunteer);

    /**
     * 判断任务与职员制唯一
     *
     * @param scService 社区服务
     * @return 结果
     */
    public ScTaskVolunteer checkTaskVolunteerUnique(ScTaskVolunteer scTaskVolunteer);

    /**
     * 新增志愿者任务表
     * 
     * @param scTaskVolunteer 志愿者任务表
     * @return 结果
     */
    public int insertScTaskVolunteer(ScTaskVolunteer scTaskVolunteer);

    /**
     * 修改志愿者任务表
     * 
     * @param scTaskVolunteer 志愿者任务表
     * @return 结果
     */
    public int updateScTaskVolunteer(ScTaskVolunteer scTaskVolunteer);

    /**
     * 删除志愿者任务表
     * 
     * @param tvId 志愿者任务表ID
     * @return 结果
     */
    public int deleteScTaskVolunteerById(Long tvId);

    /**
     * 批量删除志愿者任务表
     * 
     * @param tvIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScTaskVolunteerByIds(String[] tvIds);
}