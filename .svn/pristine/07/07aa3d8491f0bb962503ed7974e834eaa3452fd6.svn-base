package com.feather.aupipes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupYjinfotables;
import com.feather.aupipes.mapper.AupWarringMapper;
import com.feather.aupipes.mapper.AupYjinfotablesMapper;
import com.feather.aupipes.service.IAupYjinfotablesService;
import com.feather.common.core.text.Convert;

/**
 * 预警信息详情Service业务层处理
 * 
 * @author fancy
 * @date 2020-01-07
 */
@Service
public class AupYjinfotablesServiceImpl implements IAupYjinfotablesService {
    @Autowired
    private AupYjinfotablesMapper aupYjinfotablesMapper;

    @Autowired
    private AupWarringMapper aupWarringMapper;

    /**
     * 查询预警信息详情
     * 
     * @param itid
     *            预警信息详情ID
     * @return 预警信息详情
     */
    @Override
    public AupYjinfotables selectAupYjinfotablesById(String itid) {
        return aupYjinfotablesMapper.selectAupYjinfotablesById(itid);
    }

    /**
     * 查询预警信息详情列表
     * 
     * @param aupYjinfotables
     *            预警信息详情
     * @return 预警信息详情
     */
    @Override
    public List<AupYjinfotables> selectAupYjinfotablesList(AupYjinfotables aupYjinfotables) {
        return aupYjinfotablesMapper.selectAupYjinfotablesList(aupYjinfotables);
    }

    /**
     * 新增预警信息详情
     * 
     * @param aupYjinfotables
     *            预警信息详情
     * @return 结果
     */
    @Override
    public int insertAupYjinfotables(AupYjinfotables aupYjinfotables) {
        return aupYjinfotablesMapper.insertAupYjinfotables(aupYjinfotables);
    }

    /**
     * 修改预警信息详情
     * 
     * @param aupYjinfotables
     *            预警信息详情
     * @return 结果
     */
    @Override
    public int updateAupYjinfotables(AupYjinfotables aupYjinfotables) {
        return aupYjinfotablesMapper.updateAupYjinfotables(aupYjinfotables);
    }

    /**
     * 删除预警信息详情对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupYjinfotablesByIds(String ids) {
        return aupYjinfotablesMapper.deleteAupYjinfotablesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预警信息详情信息
     * 
     * @param itid
     *            预警信息详情ID
     * @return 结果
     */
    @Override
    public int deleteAupYjinfotablesById(String itid) {
        return aupYjinfotablesMapper.deleteAupYjinfotablesById(itid);
    }

    @Override
    public List<Map> getAllinfo(String id, int state, String state1, String formSysName, String content) {

        return aupYjinfotablesMapper.getAllinfo(id, state, state1, formSysName, content);
    }

    @Override
    public List<Map> getAllWarring(String id, int state, String state1, String formSysName) {
       return aupWarringMapper.getlocalWarring(formSysName, id, state);
    }

    @Override
    public List<Map> countByname() {
        return aupYjinfotablesMapper.countByname();
    }

    @Override
    public List<Map> getWaringTableList(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        return aupYjinfotablesMapper.getWaringTableList(name, formSysName, content, state, author, level, dateTime1,
                dateTime2);
    }

    @Override
    public List<Map> getSearchWarringList(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        return aupYjinfotablesMapper.getSearchWarringList(name, formSysName, content, state, author, level, dateTime1,
                dateTime2);
    }

    @Override
    public List<Map> getLevelCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        return aupYjinfotablesMapper.getLevelCount(name, formSysName, content, state, author, level, dateTime1,
                dateTime2);
    }

    @Override
    public List<Map> getNameCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        return aupYjinfotablesMapper.getNameCount(name, formSysName, content, state, author, level, dateTime1,
                dateTime2);
    }

    @Override
    public List<Map> getStateCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        return aupYjinfotablesMapper.getStateCount(name, formSysName, content, state, author, level, dateTime1,
                dateTime2);
    }

    @Override
    public Map getStreeLight(String name) {
        return aupYjinfotablesMapper.getStreeLight(name);
    }

}
