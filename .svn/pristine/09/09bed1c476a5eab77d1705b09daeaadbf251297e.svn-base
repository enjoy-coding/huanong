package com.feather.lpscommunity.service.impl;

import java.util.*;

import com.feather.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.mapper.ScVolunteerMapper;
import com.feather.lpscommunity.service.IScRegisterService;
import com.feather.lpscommunity.service.IScVolunteerService;

/**
 * 志愿者信息Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Service
public class ScVolunteerServiceImpl implements IScVolunteerService {
    @Autowired
    private ScVolunteerMapper scVolunteerMapper;

    @Autowired
    private IScRegisterService scRegisterService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询志愿者信息
     * 
     * @param userId 志愿者信息ID
     * @return 志愿者信息
     */
    @Override
    public ScVolunteer selectScVolunteerById(Long userId) {
        return scVolunteerMapper.selectScVolunteerById(userId);
    }

    /**
     * 查询志愿者信息列表
     * 
     * @param scVolunteer 志愿者信息
     * @return 志愿者信息
     */
    @Override
    public List<ScVolunteer> selectScVolunteerList(ScVolunteer scVolunteer) {
        return scVolunteerMapper.selectScVolunteerList(scVolunteer);
    }

    /**
     * 新增志愿者信息
     * 
     * @param scVolunteer 志愿者信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScVolunteer(ScVolunteer scVolunteer) {
        return scVolunteerMapper.insertScVolunteer(scVolunteer);
    }

    /**
     * 修改志愿者信息
     * 
     * @param scVolunteer 志愿者信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateScVolunteer(ScVolunteer scVolunteer) {
        return scVolunteerMapper.updateScVolunteer(scVolunteer);
    }

    /**
     * 删除志愿者信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScVolunteerByIds(String ids) {
        //删除用户信息
        try{
            //删除志愿者信息
            scRegisterService.deleteScRegisterByIds(ids);
            //删除用户表信息
            sysUserService.deleteUserByIds(ids);
        }catch (Exception e){
            e.printStackTrace();
        }
        //删除志愿者信息
        return scVolunteerMapper.deleteScVolunteerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除志愿者信息信息
     * 
     * @param userId 志愿者信息ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScVolunteerById(Long userId)
    {

        //删除用户信息
        try{
            //删除志愿者信息
            scRegisterService.deleteScRegisterById(userId);
            //删除用户表信息
            sysUserService.deleteUserById(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        //删除志愿者信息
        return scVolunteerMapper.deleteScVolunteerById(userId);
    }

    @Override
    public List<Map<String, Object>> screenScVolunteerList(ScVolunteer scVolunteer,String headUrl) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<ScVolunteer> scVolunteers = this.selectScVolunteerList(scVolunteer);
        for (int i = 0; i < scVolunteers.size(); i++) {
            ScVolunteer scVolunteer2 = scVolunteers.get(i);
            Map<String, Object> map = new LinkedHashMap<String,Object>();
            map.put("volunteer_id", scVolunteer2.getUserId());
            map.put("volunteer_avatar", "".equals(scVolunteer2.getVolunteerAvatar())?"":headUrl+scVolunteer2.getVolunteerAvatar());
            map.put("volunteer_name", scVolunteer2.getVolunteerName());
            map.put("volunteer_tel",  scVolunteer2.getVolunteerTel());
            map.put("volunteer_synopsis", scVolunteer2.getVolunteerSynopsis());
            map.put("volunteer_sex", scVolunteer2.getVolunteerSex());
            map.put("volunteer_profession",scVolunteer2.getVolunteerProfession());
            map.put("volunteer_score", scVolunteer2.getVolunteerScore());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 兑换后减去分数
     */
    @Override
    @Transactional
    public int updateVolunteerScore(Long volunteerId, double score) {
        ScVolunteer scVolunteer = scVolunteerMapper.selectScVolunteerById(volunteerId);
        double s = Double.parseDouble(scVolunteer.getVolunteerScore()) - score;
        scVolunteer.setVolunteerScore(Double.toString(s));
        return scVolunteerMapper.updateScVolunteer(scVolunteer);
    }

}