package com.feather.lpscommunity.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScPrizes;
import com.feather.lpscommunity.domain.ScPrizesVolunteer;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.mapper.ScPrizesMapper;
import com.feather.lpscommunity.mapper.ScPrizesVolunteerMapper;
import com.feather.lpscommunity.mapper.ScVolunteerMapper;
import com.feather.lpscommunity.service.IScPrizesService;
import com.feather.lpscommunity.service.IScPrizesVolunteerService;
import com.feather.lpscommunity.service.IScVolunteerService;

/**
 * 志愿者领奖Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-22
 */
@Service
public class ScPrizesVolunteerServiceImpl implements IScPrizesVolunteerService 
{
    @Autowired
    private ScPrizesVolunteerMapper scPrizesVolunteerMapper;

    @Autowired
    private ScPrizesMapper scPrizesMapper;

    @Autowired
    private ScVolunteerMapper scVolunteerMapper;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private IScPrizesService scPrizesService;

    @Autowired
    private IScVolunteerService scVolunteerService;
    /**
     * 查询志愿者领奖
     * 
     * @param pvId 志愿者领奖ID
     * @return 志愿者领奖
     */
    @Override
    public ScPrizesVolunteer selectScPrizesVolunteerById(Long pvId)
    {
        return scPrizesVolunteerMapper.selectScPrizesVolunteerById(pvId);
    }

    /**
     * 查询志愿者领奖列表
     * 
     * @param scPrizesVolunteer 志愿者领奖
     * @return 志愿者领奖
     */
    @Override
    public List<ScPrizesVolunteer> selectScPrizesVolunteerList(ScPrizesVolunteer scPrizesVolunteer)
    {
        return scPrizesVolunteerMapper.selectScPrizesVolunteerList(scPrizesVolunteer);
    }

    /**
     * 新增志愿者领奖
     * 
     * @param scPrizesVolunteer 志愿者领奖
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScPrizesVolunteer(ScPrizesVolunteer scPrizesVolunteer)
    {
        scPrizesVolunteer.setPvId(uidWorker.getNextId());
        //奖品减少数量
        scPrizesService.updatePrizesNum(scPrizesVolunteer.getPrizesId(), 1);
        //志愿减少积分
        scVolunteerService.updateVolunteerScore(scPrizesVolunteer.getVolunteerId(),scPrizesVolunteer.getPrizes().getPrizesScore());
        return scPrizesVolunteerMapper.insertScPrizesVolunteer(scPrizesVolunteer);
    }

    /**
     * 修改志愿者领奖
     * 
     * @param scPrizesVolunteer 志愿者领奖
     * @return 结果
     */
    @Override
    public int updateScPrizesVolunteer(ScPrizesVolunteer scPrizesVolunteer)
    {
        return scPrizesVolunteerMapper.updateScPrizesVolunteer(scPrizesVolunteer);
    }

    /**
     * 删除志愿者领奖对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScPrizesVolunteerByIds(String ids)
    {
        return scPrizesVolunteerMapper.deleteScPrizesVolunteerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除志愿者领奖信息
     * 
     * @param pvId 志愿者领奖ID
     * @return 结果
     */
    public int deleteScPrizesVolunteerById(Long pvId)
    {
        return scPrizesVolunteerMapper.deleteScPrizesVolunteerById(pvId);
    }

    @Override
    public ScPrizesVolunteer selectScPrizesVolunteerByPrizesId(Long prizesId) {
        return scPrizesVolunteerMapper.selectScPrizesVolunteerByPrizesId(prizesId);
    }

    @Override
    public ScPrizesVolunteer selectScPrizesVolunteerByVolunteerId(Long volunteerId) {
        return scPrizesVolunteerMapper.selectScPrizesVolunteerByVolunteerId(volunteerId);
    }

    @Override
    public boolean checkScore(Long prizesId,Long volunteerId) {
        ScPrizes scPrizes  = scPrizesMapper.selectScPrizesById(prizesId);
        ScVolunteer scVolunteer = scVolunteerMapper.selectScVolunteerById(volunteerId);
        if(Double.parseDouble(scVolunteer.getVolunteerScore()) < scPrizes.getPrizesScore()){
            return false;
        }
       return true;
    }
}