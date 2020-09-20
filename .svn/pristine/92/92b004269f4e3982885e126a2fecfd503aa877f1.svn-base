package com.feather.prd.service;

import java.util.List;

import com.feather.prd.domain.PrdMsg;
import com.feather.prd.domain.PrdMsgUser;

/**
 * 消息发送日志Service接口
 */
public interface IPrdMsgService {
    /**
     * 查询消息发送日志
     * 
     * @param msgId
     *            消息发送日志ID
     * @return 消息发送日志
     */
    public PrdMsg selectPrdMsgById(Long msgId);

    /**
     * 查询消息发送日志列表
     * 
     * @param prdMsg
     *            消息发送日志
     * @return 消息发送日志集合
     */
    public List<PrdMsg> selectPrdMsgList(PrdMsg prdMsg);

    /**
     * 查询消息接收人列表
     * 
     * @param prdMsgUser
     *            消息接收人日志
     * @return 消息接收人集合
     */
    public List<PrdMsgUser> selectPrdMsgUser(PrdMsgUser prdMsgUser);

    /**
     * 新增消息发送日志
     * 
     * @param prdMsg
     *            消息发送日志
     * @return 结果
     */
    // public int insertPrdMsg(PrdMsg prdMsg);

    /**
     * 删除消息发送日志信息
     * 
     * @param msgId
     *            消息发送日志ID
     * @return 结果
     */
    public int deletePrdMsgById(Long msgId);

    /**
     * 批量删除消息发送日志
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePrdMsgByIds(String ids);

    /**
     * 删除消息接收日志
     * 
     * @param prdMsgUser
     *            消息接收日志
     * @return 结果
     */
    public int deletePrdMsgUserById(Long msgId);

    /**
     * 批量删除消息接收日志
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePrdMsgUserByIds(String ids);

    /**
     * 发送消息到用户
     */
    public int sendToUser(PrdMsg prdMsg, Long[] users, boolean synch);

    /**
     * 发送消息到用户
     */
    public int sendToUser(PrdMsg prdMsg, String users, boolean synch);

    /**
     * 发送消息到角色
     */
    public int sendToRole(PrdMsg prdMsg, String roles, boolean synch);
}