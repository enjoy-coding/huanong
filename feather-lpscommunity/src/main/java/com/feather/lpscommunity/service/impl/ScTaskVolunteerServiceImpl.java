package com.feather.lpscommunity.service.impl;

import java.util.*;

import com.feather.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScParkrent;
import com.feather.lpscommunity.domain.ScRegister;
import com.feather.lpscommunity.domain.ScTaskVolunteer;
import com.feather.lpscommunity.mapper.ScParkrentMapper;
import com.feather.lpscommunity.mapper.ScRegisterMapper;
import com.feather.lpscommunity.mapper.ScTaskVolunteerMapper;
import com.feather.lpscommunity.service.IScTaskVolunteerService;

import org.springframework.transaction.annotation.Transactional;

/**
 * 志愿者任务表Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Service
public class ScTaskVolunteerServiceImpl implements IScTaskVolunteerService 
{
    @Autowired
    private ScTaskVolunteerMapper scTaskVolunteerMapper;

    @Autowired
    private ScRegisterMapper scRegisterMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private ScParkrentMapper scParkrentMapper;
    /**
     * 查询志愿者任务表
     * 
     * @param tvId 志愿者任务表ID
     * @return 志愿者任务表
     */
    @Override
    public ScTaskVolunteer selectScTaskVolunteerById(Long tvId)
    {
        return scTaskVolunteerMapper.selectScTaskVolunteerById(tvId);
    }

    /**
     * 查询志愿者任务表列表
     * 
     * @param scTaskVolunteer 志愿者任务表
     * @return 志愿者任务表
     */
    @Override
    public List<ScTaskVolunteer> selectScTaskVolunteerList(ScTaskVolunteer scTaskVolunteer)
    {
        return scTaskVolunteerMapper.selectScTaskVolunteerList(scTaskVolunteer);
    }

    /**
     * 判断任务与职员制唯一
     *
     * @param scTaskVolunteer 社区服务
     * @return 结果
     */
    @Override
    public ScTaskVolunteer checkTaskVolunteerUnique(ScTaskVolunteer scTaskVolunteer){
        return scTaskVolunteerMapper.checkTaskVolunteerUnique(scTaskVolunteer);
    }

    @Override
    public List<Map<String,Object>> screenScTaskVolunteerList(ScTaskVolunteer scTaskVolunteer){
        List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
        List<ScTaskVolunteer> scTaskVolunteers = this.selectScTaskVolunteerList(scTaskVolunteer);
        for (int i = 0; i < scTaskVolunteers.size(); i++) {
            ScTaskVolunteer scTaskVolunteer2 = scTaskVolunteers.get(i);
            Map<String,Object> map = new LinkedHashMap<String,Object>();
            map.put("parkrent_volunteer_id", scTaskVolunteer2.getTvId());
            //报名状态
            map.put("parkrent_volunteer_update_time",scTaskVolunteer2.getUpdateTime());
            map.put("parkrent_volunteer_audit_state",getAuidtStateLabel(scTaskVolunteer2.getAuditState()));
            map.put("parkrent_volunteer_audit_pass_state",getAuidtPassStateLabel(scTaskVolunteer2.getAuditPassState()));
            //20191127增加报名时间，审核状态
            //志愿者状态
            ScRegister scRegister = scRegisterMapper.selectScRegisterById(scTaskVolunteer2.getVolunteerId());
            map.put("volunteer_id", scTaskVolunteer2.getVolunteer().getUserId());
            map.put("volunteer_name", scTaskVolunteer2.getVolunteer().getVolunteerName());
            map.put("volunteer_tel", scTaskVolunteer2.getVolunteer().getVolunteerTel());
            map.put("volunteer_register_time",scRegister.getUpdateTime());
            map.put("volunteer_audit_state",getAuidtStateLabel(scRegister.getAuditState()));//志愿者审核状态
            map.put("volunteer_audit_pass_state",getAuidtPassStateLabel(scRegister.getAuditPassState()));//志愿者审核是否通过
            //任务状态
            ScParkrent scParkrent = scParkrentMapper.selectScParkrentById(scTaskVolunteer2.getTaskId());
            map.put("parkrent_id", scTaskVolunteer2.getParkrent().getParkrentId());
            map.put("parkrent_name", scTaskVolunteer2.getParkrent().getParkrentName());
            map.put("parkrent_title", scTaskVolunteer2.getParkrent().getParkrentTitle());
            map.put("parkrent_type", scParkrent.getParkrentType()==null||"".equals(scParkrent.getParkrentType())?"":sysDictDataMapper.selectDictLabel("sc_task_state1",scParkrent.getParkrentType()));
            map.put("parkrent_update_time",scParkrent.getUpdateTime());
            map.put("parkrent_audit_state",getAuidtStateLabel(scParkrent.getAuditState()));//志愿者审核状态
            map.put("parkrent_audit_pass_state",getAuidtPassStateLabel(scParkrent.getAuditPassState()));//志愿者审核是否通过


            mList.add(map);
        }
        return mList;
    }

    /**
     * 新增志愿者任务表
     * 
     * @param scTaskVolunteer 志愿者任务表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScTaskVolunteer(ScTaskVolunteer scTaskVolunteer)
    {
        return scTaskVolunteerMapper.insertScTaskVolunteer(scTaskVolunteer);
    }

    /**
     * 修改志愿者任务表
     * 
     * @param scTaskVolunteer 志愿者任务表
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScTaskVolunteer(ScTaskVolunteer scTaskVolunteer)
    {
        return scTaskVolunteerMapper.updateScTaskVolunteer(scTaskVolunteer);
    }

    /**
     * 删除志愿者任务表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScTaskVolunteerByIds(String ids)
    {
        return scTaskVolunteerMapper.deleteScTaskVolunteerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除志愿者任务表信息
     * 
     * @param tvId 志愿者任务表ID
     * @return 结果
     */
    @Override
    public int deleteScTaskVolunteerById(Long tvId)
    {
        return scTaskVolunteerMapper.deleteScTaskVolunteerById(tvId);
    }

    /**
     * 用户判断删除
     * 
     * @param volunteerIds 志愿者ID
     * @return 志愿者任务表
     */
    @Override
    public boolean checkScTaskVolunteerByVolunteerId(String volunteerIds){
        if(scTaskVolunteerMapper.checkScTaskVolunteerByVolunteerId(Convert.toStrArray(volunteerIds))!=null){
            return false;
        }
        return true;
    }

    /**
     * 用户判断删除
     * 
     * @param parkrentIds 任务表ID
     * @return 志愿者任务表
     */
    @Transactional
    @Override
    public boolean checkScTaskVolunteerByParkrentId(String parkrentIds){
        ScTaskVolunteer scTaskVolunteer = scTaskVolunteerMapper.checkScTaskVolunteerByParkrentId(Convert.toStrArray(parkrentIds));
        if(scTaskVolunteer==null){
            return false;
        }
        return true;
    }

    /**
     * 获取审核状态
     * @param auditStateValue
     * @return
     */
    public String getAuidtStateLabel(String auditStateValue){
        return auditStateValue == null||"".equals(auditStateValue)?"":sysDictDataMapper.selectDictLabel("sc_audit_status",auditStateValue);
    }

    /**
     * 获取审核通过状态
     * @param auditPassStateValue
     * @return
     */
    public String getAuidtPassStateLabel(String auditPassStateValue){
        return auditPassStateValue == null||"".equals(auditPassStateValue)?"":sysDictDataMapper.selectDictLabel("sc_audit_type",auditPassStateValue);
    }
}