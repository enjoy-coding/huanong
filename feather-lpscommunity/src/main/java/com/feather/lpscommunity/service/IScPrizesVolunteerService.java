package com.feather.lpscommunity.service;

import java.util.List;

import com.feather.lpscommunity.domain.ScPrizesVolunteer;

/**
 * 志愿者领奖Service接口
 * 
 * @author fancy
 * @date 2019-11-22
 */
public interface IScPrizesVolunteerService 
{
    /**
     * 查询志愿者领奖
     * 
     * @param pvId 志愿者领奖ID
     * @return 志愿者领奖
     */
    public ScPrizesVolunteer selectScPrizesVolunteerById(Long pvId);

     /**
     * 查询志愿者领奖根据奖品查询
     * 
     * @param prizesId 奖品id
     * @return 志愿者领奖
     */
    public ScPrizesVolunteer selectScPrizesVolunteerByPrizesId(Long prizesId);

     /**
     * 查询志愿者领奖根据志愿者查询
     * 
     * @param volunteerId 志愿者领奖ID
     * @return 志愿者领奖
     */
    public ScPrizesVolunteer selectScPrizesVolunteerByVolunteerId(Long volunteerId);


    /**
     * 查询志愿者领奖列表
     * 
     * @param scPrizesVolunteer 志愿者领奖
     * @return 志愿者领奖集合
     */
    public List<ScPrizesVolunteer> selectScPrizesVolunteerList(ScPrizesVolunteer scPrizesVolunteer);

    /**
     * 查询志愿者积分是否能兑换奖品
     * @param  prizesId 奖品id
     * @param volunteerId 志愿者ID
     * @return 志愿者领奖
     */
    public boolean checkScore(Long prizesId,Long volunteerId);

    /**
     * 新增志愿者领奖
     * 
     * @param scPrizesVolunteer 志愿者领奖
     * @return 结果
     */
    public int insertScPrizesVolunteer(ScPrizesVolunteer scPrizesVolunteer);

    /**
     * 修改志愿者领奖
     * 
     * @param scPrizesVolunteer 志愿者领奖
     * @return 结果
     */
    public int updateScPrizesVolunteer(ScPrizesVolunteer scPrizesVolunteer);

    /**
     * 批量删除志愿者领奖
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScPrizesVolunteerByIds(String ids);

    /**
     * 删除志愿者领奖信息
     * 
     * @param pvId 志愿者领奖ID
     * @return 结果
     */
    public int deleteScPrizesVolunteerById(Long pvId);
}