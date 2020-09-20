package com.feather.lpscommunity.service;

import org.springframework.web.multipart.MultipartFile;

import com.feather.lpscommunity.domain.ScRegister;

import java.util.List;
import java.util.Map;

/**
 * 志愿者注册Service接口
 *
 * @author fancy
 * @date 2019-11-14
 */
public interface IScRegisterService
{
    /**
     * 查询志愿者注册
     *
     * @param registerId 志愿者注册ID
     * @return 志愿者注册
     */
    public ScRegister selectScRegisterById(Long registerId);

    /**
     * 查询志愿者注册列表
     *
     * @param scRegister 志愿者注册
     * @return 志愿者注册集合
     */
    public List<ScRegister> selectScRegisterList(ScRegister scRegister);


    /**
     * 查询志愿者注册列表
     *
     * @param scRegister 志愿者注册
     * @return 志愿者注册集合
     */
    public List<Map<String, Object>> screenScRegisterList(ScRegister scRegister,String headUrl);

    /**
     * 新增志愿者注册
     *
     * @param scRegister 志愿者注册
     * @return 结果
     */
    public int insertScRegister(ScRegister scRegister, MultipartFile headsculpture)throws Exception;

    /**
     * 新增志愿者注册
     *
     * @param scRegister 志愿者注册
     * @return 结果
     */
    public int insertScRegister(ScRegister scRegister);


    /**
     * 新增志愿者注册
     *
     * @param scRegister 志愿者审核
     * @return 结果
     */
    public int editScRegisterAudit(ScRegister scRegister);

    /**
     * 修改志愿者注册
     *
     * @param scRegister 志愿者注册
     * @return 结果
     */
    public int updateScRegister(ScRegister scRegister);

    /**
     * 批量删除志愿者注册
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScRegisterByIds(String ids);

    /**
     * 删除志愿者注册信息
     *
     * @param registerId 志愿者注册ID
     * @return 结果
     */
    public int deleteScRegisterById(Long registerId);

    /**
     * 一个设备号唯一一个志愿者账号
     * @param register
     * @return
     */
    public ScRegister checkAccountUnique(ScRegister register);
}