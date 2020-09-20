package com.feather.prd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.framework.manager.AsyncManager;
import com.feather.framework.util.ShiroUtils;
import com.feather.prd.domain.PrdMsg;
import com.feather.prd.domain.PrdMsgUser;
import com.feather.prd.mapper.PrdMsgMapper;
import com.feather.prd.service.IPrdMsgService;
import com.feather.prd.service.PrdMsgSender;
import com.feather.system.domain.SysUserRole;
import com.feather.system.mapper.SysUserRoleMapper;
import com.feather.system.service.ISysDictDataService;

/**
 * 消息发送日志Service业务层处理
 */
@Service
public class PrdMsgServiceImpl implements IPrdMsgService {
    protected final Logger logger = LoggerFactory.getLogger(IPrdMsgService.class);

    @Autowired
    private PrdMsgMapper prdMsgMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private UidWorker uidWorker;

    @Autowired(required = false)
    private PrdMsgSender prdMsgSender;

    /**
     * 查询消息发送日志
     * 
     * @param msgId
     *            消息发送日志ID
     * @return 消息发送日志
     */
    @Override
    public PrdMsg selectPrdMsgById(Long msgId) {
        return prdMsgMapper.selectPrdMsgById(msgId);
    }

    /**
     * 查询消息发送日志列表
     * 
     * @param prdMsg
     *            消息发送日志
     * @return 消息发送日志
     */
    @Override
    public List<PrdMsg> selectPrdMsgList(PrdMsg prdMsg) {
        return prdMsgMapper.selectPrdMsgList(prdMsg);
    }

    /**
     * 查询消息接收人列表
     * 
     * @param prdMsgUser
     *            消息接收人日志
     * @return 消息接收人集合
     */
    public List<PrdMsgUser> selectPrdMsgUser(PrdMsgUser prdMsgUser) {
        return prdMsgMapper.selectPrdMsgUser(prdMsgUser);
    }

    /**
     * 新增消息发送日志
     * 
     * @param prdMsg
     *            消息发送日志
     * @return 结果
     */
    // @Override
    // public int insertPrdMsg(PrdMsg prdMsg) {
    // return prdMsgMapper.insertPrdMsg(prdMsg);
    // }

    /**
     * 删除消息发送日志信息
     * 
     * @param msgId
     *            消息发送日志ID
     * @return 结果
     */
    @Transactional
    public int deletePrdMsgById(Long msgId) {
        int count = prdMsgMapper.deletePrdMsgById(msgId);
        prdMsgMapper.deletePrdMsgUserById(msgId);
        return count;
    }

    /**
     * 删除消息发送日志对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePrdMsgByIds(String ids) {
        String[] msgIds = Convert.toStrArray(ids);
        int count = prdMsgMapper.deletePrdMsgByIds(msgIds);
        prdMsgMapper.deletePrdMsgUserByIds(msgIds);
        return count;
    }

    /**
     * 删除消息接收日志
     * 
     * @param prdMsgUser
     *            消息接收日志
     * @return 结果
     */
    public int deletePrdMsgUserById(Long msgId) {
        return prdMsgMapper.deletePrdMsgUserById(msgId);
    }

    /**
     * 删除消息接收日志
     * 
     * @param prdMsgUser
     *            消息接收日志
     * @return 结果
     */
    public int deletePrdMsgUserByIds(String ids) {
        return prdMsgMapper.deletePrdMsgUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 发送消息到用户
     */
    @Override
    // @Transactional
    public int sendToUser(PrdMsg prdMsg, Long[] users, boolean synch) {
        if (prdMsgSender == null) {
            throw new RuntimeException("没有发现服务：" + PrdMsgSender.class.getName());
        }
        if (prdMsg != null && users != null && users.length > 0) {
            String typeName = sysDictDataService.selectDictLabel("prd_msg_type", prdMsg.getMsgType());
            prdMsg.setMsgTypeName(typeName);
            if (StringUtils.isNotBlank(prdMsg.getMsgApp())) {
                int count = 0;
                boolean has = false;
                String[] msgApp = prdMsg.getMsgApp().split(",");
                for (String app : msgApp) {
                    if (StringUtils.isNotBlank(app)) {
                        prdMsg.setMsgApp(app);
                        String appName = sysDictDataService.selectDictLabel("prd_msg_app", prdMsg.getMsgApp());
                        prdMsg.setMsgAppName(appName);
                        count += send(prdMsg, users, synch);
                        has = true;
                    }
                }
                if (has) {
                    return count;
                }
            }
            return send(prdMsg, users, synch);
        }
        return 0;
    }

    /**
     * 发送消息到用户
     */
    @Override
    public int sendToUser(PrdMsg prdMsg, String users, boolean synch) {
        Long[] userIds = Convert.toLongArray(users);
        return sendToUser(prdMsg, userIds, synch);
    }

    /**
     * 发送消息到角色
     */
    @Override
    public int sendToRole(PrdMsg prdMsg, String roles, boolean synch) {
        String[] keys = Convert.toStrArray(roles);
        List<SysUserRole> list = sysUserRoleMapper.selectUserByRoleKeys(keys);
        if (list != null && list.size() > 0) {
            Long[] userIds = new Long[list.size()];
            for (int i = 0; i < userIds.length; i++) {
                userIds[i] = list.get(i).getUserId();
            }
            return sendToUser(prdMsg, userIds, synch);
        }
        return 0;
    }

    private int send(PrdMsg msg, Long[] users, Boolean synch) {
        PrdMsg prdMsg = msg.copy();
        Date time = new Date();
        prdMsg.setMsgId(uidWorker.getNextId());
        if (prdMsg.getMsgBatch() == null) {
            prdMsg.setCreateBy(ShiroUtils.getLoginName());
            prdMsg.setCreateTime(time);
            prdMsg.setMsgBatch(prdMsg.getMsgId());
        }

        prdMsgSender.check(prdMsg);

        List<PrdMsgUser> prdMsgUsers = new ArrayList<>();
        // 记录用户(prd_msg_user)
        for (Long userId : users) {
            if (userId == null || exists(userId, prdMsgUsers)) {
                continue;
            }
            PrdMsgUser prdMsgUser = new PrdMsgUser();
            prdMsgUser.setMsgId(prdMsg.getMsgId());
            prdMsgUser.setUserId(userId);
            prdMsgUser.setSendStatus("-");
            prdMsgUser.setSendTime(time);
            prdMsgUser.setSendCount(0);
            prdMsgMapper.insertPrdMsgUser(prdMsgUser);

            prdMsgUsers.add(prdMsgUser);
        }
        // 记录消息(prd_msg)
        if (prdMsgUsers.size() > 0) {
            prdMsgMapper.insertPrdMsg(prdMsg);
        }
        if (synch != null && synch) {
            // 同步发送
            for (PrdMsgUser prdMsgUser : prdMsgUsers) {
                try {
                    Exception error = prdMsgSender.send(prdMsg, prdMsgUser.getUserId());
                    if (error == null) {
                        prdMsgUser.setSendStatus("0");
                    } else {
                        prdMsgUser.setSendStatus("1");
                        prdMsgUser.setSendError(error.getMessage());
                        logger.error(error.getMessage(), error);
                    }
                } catch (Exception ex) {
                    prdMsgUser.setSendStatus("1");
                    prdMsgUser.setSendError(ex.getMessage());
                    logger.error(ex.getMessage(), ex);
                }
                try {
                    prdMsgMapper.updatePrdMsgUser(prdMsgUser);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        } else {
            // 异步发送
            for (PrdMsgUser prdMsgUser : prdMsgUsers) {
                AsyncManager.me().execute(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Exception error = prdMsgSender.send(prdMsg, prdMsgUser.getUserId());
                            if (error == null) {
                                prdMsgUser.setSendStatus("0");
                            } else {
                                prdMsgUser.setSendStatus("1");
                                prdMsgUser.setSendError(error.getMessage());
                                logger.error(error.getMessage(), error);
                            }
                        } catch (Exception ex) {
                            prdMsgUser.setSendStatus("1");
                            prdMsgUser.setSendError(ex.getMessage());
                            logger.error(ex.getMessage(), ex);
                        }
                        prdMsgMapper.updatePrdMsgUser(prdMsgUser);
                    }
                });
            }
        }
        return prdMsgUsers.size();
    }

    private boolean exists(long userId, List<PrdMsgUser> prdMsgUsers) {
        for (PrdMsgUser item : prdMsgUsers) {
            if (userId == item.getUserId()) {
                return true;
            }
        }
        return false;
    }
}
