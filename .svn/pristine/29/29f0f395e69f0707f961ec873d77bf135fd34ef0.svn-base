package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScRegister;

/**
 * 志愿者注册Mapper接口
 *
 * @author fancy
 * @date 2019-11-14
 */
public interface ScRegisterMapper
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
     * 新增志愿者注册
     *
     * @param scRegister 志愿者注册
     * @return 结果
     */
    public int insertScRegister(ScRegister scRegister);

    /**
     * 修改志愿者注册
     *
     * @param scRegister 志愿者注册
     * @return 结果
     */
    public int updateScRegister(ScRegister scRegister);

    /**
     * 删除志愿者注册
     *
     * @param registerId 志愿者注册ID
     * @return 结果
     */
    public int deleteScRegisterById(Long registerId);

    /**
     * 批量删除志愿者注册
     *
     * @param registerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScRegisterByIds(String[] registerIds);

    public ScRegister checkAccountUnique(ScRegister scRegister);
}