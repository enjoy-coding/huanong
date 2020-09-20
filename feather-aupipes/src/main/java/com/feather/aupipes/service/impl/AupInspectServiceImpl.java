package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.feather.aupipes.domain.*;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.system.domain.SysUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.aupipes.config.AupipesMsgSender;
import com.feather.aupipes.mapper.AupInspectCoordinateMapper;
import com.feather.aupipes.mapper.AupInspectDetailMapper;
import com.feather.aupipes.mapper.AupInspectMapper;
import com.feather.aupipes.mapper.AupInspectServiceMapper;
import com.feather.aupipes.mapper.AupInspectWorkerMapper;
import com.feather.aupipes.service.IAupInspectService;
import com.feather.aupipes.utils.vo.AupInspectDetailVo;
import com.feather.aupipes.utils.vo.AupInspectServiceVo;
import com.feather.aupipes.utils.vo.AupInspectVo;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.framework.util.ShiroUtils;
import com.feather.prd.domain.PrdMsg;
import com.feather.prd.service.IPrdMsgService;

/**
 * 巡检任务Service业务层处理
 *
 * @author fancy
 * @date 2020-06-03
 */
@Service
public class AupInspectServiceImpl implements IAupInspectService {
    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private AupInspectMapper aupInspectMapper;

    @Autowired
    private AupInspectDetailMapper aupInspectDetailMapper;

    @Autowired
    private AupInspectServiceMapper aupInspectServiceMapper;

    @Autowired
    private AupInspectWorkerMapper aupInspectWorkerMapper;

    @Autowired
    private AupInspectCoordinateMapper aupInspectCoordinateMapper;

    @Autowired
    private IPrdMsgService iPrdMsgService;

    /**
     * 按巡检状态统计数量
     *
     * @param aupInspect
     * @return
     */

    @Override
    public AjaxResult selectAupInspectCount(AupInspect aupInspect) {
        Long userId = ShiroUtils.getUserId();
        Subject subject = ShiroUtils.getSubject();
        // 当前用户为巡检员时，只搜索自己的巡检任务
        String inspectMan = "inspect_man";
        if (subject.hasRole(inspectMan) && !SysUser.isAdmin(userId)) {
            aupInspect.setInspectWorkerId(userId + "");
        }

        List<AupPatrolCount> list = aupInspectMapper.selectAupInspectCount(aupInspect);

        JSONObject.JSONArray jsonArray = new JSONObject.JSONArray();

        String wx = "0";
        String zx = "0";
        String yx = "0";
        for (AupPatrolCount aupPatrolCount : list) {

            if (aupPatrolCount.getStatus().equals("2")) {// 已巡
                yx = aupPatrolCount.getTotal();
            } else if(aupPatrolCount.getStatus().equals("1")) {
                zx = aupPatrolCount.getTotal();
            } else if(aupPatrolCount.getStatus().equals("0")) {
                wx = aupPatrolCount.getTotal();
            }
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "已巡");
        jsonObject1.put("value", yx);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "在巡");
        jsonObject2.put("value", zx);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("name", "未巡");
        jsonObject3.put("value", wx);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);

        return AjaxResult.success(jsonArray);
    }


    /**
     * 查询巡检任务
     *
     * @param inspectId
     *            巡检任务ID
     * @return 巡检任务
     */
    @Override
    public AupInspect selectAupInspectById(Long inspectId) {
        return aupInspectMapper.selectAupInspectById(inspectId);
    }

    /**
     * 通过任务id数组，查询与之关联的记录列表和与记录关联的设备列表
     *
     * @param ids
     * @return
     */
    @Override
    public List<AupInspectVo> selectAupInspectVoListByIds(String ids) {
        List<AupInspectVo> aupInspectVoList = new ArrayList<>();

        String[] arr = Convert.toStrArray(ids);
        List<AupInspect> aupInspects = aupInspectMapper.selectAupInspectListByIds(arr);
        for (int i = 0; i < aupInspects.size(); i++) {// 遍历任务id
            AupInspect aupInspect = aupInspects.get(i);

            AupInspectDetail aupInspectDetail = new AupInspectDetail();// 新建一个用于查询的记录bean
            aupInspectDetail.setTaskId(aupInspect.getId() + "");
            List<AupInspectDetail> aupInspectDetails = aupInspectDetailMapper
                    .selectAupInspectDetailList(aupInspectDetail);// 通过任务id搜索到记录列表

            List<AupInspectDetailVo> aupInspectDetailVoList = new ArrayList<>();
            for (AupInspectDetail temp : aupInspectDetails) {// 遍历与任务id关联的记录列表
                AupInspectService aupInspectService = new AupInspectService();// 新建一个用于查询的设备bean
                aupInspectService.setDetailId(temp.getId());
                List<AupInspectService> aupInspectServices = aupInspectServiceMapper
                        .selectAupInspectServiceList(aupInspectService);

                List<AupInspectServiceVo> aupInspectServiceVoList = convertVo(aupInspectServices);

                AupInspectDetailVo aupInspectDetailVo = convertVo(temp, aupInspectServiceVoList);
                aupInspectDetailVoList.add(aupInspectDetailVo);
            }

            AupInspectVo aupInspectVo = convertVo(aupInspect, aupInspectDetailVoList);

            aupInspectVoList.add(aupInspectVo);
        }

        return aupInspectVoList;
    }

    /**
     * 将AupInspect转化为AupInspectVo
     */
    private AupInspectVo convertVo(AupInspect aupInspect, List<AupInspectDetailVo> aupInspectDetailVoList) {
        AupInspectVo aupInspectVo = new AupInspectVo();
        aupInspectVo.setInspectName(aupInspect.getInspectName());
        aupInspectVo.setInspectArea(aupInspect.getInspectArea());
        aupInspectVo.setDistributeMan(aupInspect.getDistributeWorker());
        aupInspectVo.setDistributeTime(DateUtils.getTime(aupInspect.getDistributeTime()));
        aupInspectVo.setStartTime(DateUtils.getTime(aupInspect.getStartTime()));
        aupInspectVo.setEndTime(DateUtils.getTime(aupInspect.getEndTime()));
        aupInspectVo.setInspectStatus(aupInspect.getInspectStatus());
        aupInspectVo.setInspectWorker(aupInspect.getInspectWorker());
        aupInspectVo.setAupInspectDetailVoList(aupInspectDetailVoList);

        return aupInspectVo;
    }

    /**
     * 将AupInspectDetail转化为AupInspectDetailVo
     * @param aupInspectDetail 对象
     * @param aupInspectServiceVoList 检查集合
     * @return 结果
     */
    private AupInspectDetailVo convertVo(AupInspectDetail aupInspectDetail,
            List<AupInspectServiceVo> aupInspectServiceVoList) {
        AupInspectDetailVo aupInspectDetailVo = new AupInspectDetailVo();
        aupInspectDetailVo.setAddressType(aupInspectDetail.getServiceType());
        aupInspectDetailVo.setInspectAddress(aupInspectDetail.getServiceAddress());
        aupInspectDetailVo.setInspectTime(DateUtils.getTime(aupInspectDetail.getCreateTime()));
        aupInspectDetailVo.setStatus(aupInspectDetail.getServiceStatusText());
        aupInspectDetailVo.setAupInspectServiceVoList(aupInspectServiceVoList);
        return aupInspectDetailVo;
    }

    /**
     * 将AupInspectService列表转化为AupInspectServiceVo列表
     * @param aupInspectServiceList 检查集合
     * @return
     */
    private List<AupInspectServiceVo> convertVo(List<AupInspectService> aupInspectServiceList) {
        List<AupInspectServiceVo> aupInspectServiceVoList = new ArrayList<>();

        for (AupInspectService aupInspectService : aupInspectServiceList) {
            AupInspectServiceVo aupInspectServiceVo = new AupInspectServiceVo();
            aupInspectServiceVo.setServiceName(aupInspectService.getServiceName());
            aupInspectServiceVo.setServiceSituation(aupInspectService.getServiceSituation());
            aupInspectServiceVo.setServiceStatus(aupInspectService.getServiceStatus());
            aupInspectServiceVoList.add(aupInspectServiceVo);
        }

        return aupInspectServiceVoList;
    }

    /**
     * 查询巡检任务列表
     *
     * @param aupInspect
     *            巡检任务
     * @return 巡检任务
     */
    @Override
    public List<AupInspect> selectAupInspectList(AupInspect aupInspect) {
        Long userId = ShiroUtils.getUserId();
        Subject subject = ShiroUtils.getSubject();
        // 当前用户为巡检员时，只搜索自己的巡检任务
        String inspectMan = "inspect_man";
        if (subject.hasRole(inspectMan) && !SysUser.isAdmin(userId)) {
            aupInspect.setInspectWorkerId(userId + "");
        }
        return aupInspectMapper.selectAupInspectList(aupInspect);
    }

    /**
     * 新增巡检任务
     *
     * @param aupInspect
     *            巡检任务
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAupInspect(AupInspect aupInspect) {
        aupInspect.setId(uidWorker.getNextId());
        aupInspect.setDistributeTime(new Date());
        aupInspect.setCreateTime(new Date());
        String inspectWorkerId = aupInspect.getInspectWorkerId();
        String[] inspectWorkerIds = inspectWorkerId.split(",");

        // 遍历提交的人员id，存储到中间表aup_inspect_worker中
        for (String id : inspectWorkerIds) {
            AupInspectWorker aupInspectWorker = new AupInspectWorker();
            aupInspectWorker.setId(uidWorker.getNextId());
            aupInspectWorker.setInspectId(aupInspect.getId());
            aupInspectWorker.setInspectWorkerId(Long.parseLong(id));
            aupInspectWorker.setCreateTime(new Date());
            aupInspectWorkerMapper.insertAupInspectWorker(aupInspectWorker);

        }
        String content = aupInspect.getDistributeWorker() + "给您下发了一条巡检任务，巡检区域为：" + aupInspect.getInspectArea();
        sendMsg(inspectWorkerId, AupipesMsgSender.WX_MSG, aupInspect, content);
        sendMsg(inspectWorkerId, AupipesMsgSender.SMS_MSG, aupInspect, content);

        return aupInspectMapper.insertAupInspect(aupInspect);
    }

    private void sendMsg(String userIds, String msgType, AupInspect aupInspect, String content) {
        PrdMsg prdMsg = new PrdMsg();
        prdMsg.setMsgId(uidWorker.getNextId());
        prdMsg.setMsgApp(msgType);
        prdMsg.setMsgType("inspect");
        prdMsg.setMsgTitle(aupInspect.getInspectName());
        prdMsg.setMsgContent(content);

        iPrdMsgService.sendToUser(prdMsg, userIds, false);
    }

    /**
     * 修改巡检任务
     *
     * @param aupInspect
     *            巡检任务
     * @return 结果
     */
    @Override
    public int updateAupInspect(AupInspect aupInspect) {
        aupInspect.setUpdateTime(new Date());
        if (aupInspect.getInspectStatus().equals("2")) {
            AupInspect aupInspectOfDB = aupInspectMapper.selectAupInspectById(aupInspect.getId());
            String inspectWorkerId = aupInspectOfDB.getInspectWorkerId();

            String content = "您的" + aupInspectOfDB.getInspectName() + "已完成";
            sendMsg(inspectWorkerId, AupipesMsgSender.WX_MSG, aupInspectOfDB, content);
            sendMsg(inspectWorkerId, AupipesMsgSender.SMS_MSG, aupInspectOfDB, content);

            String distributeContent = "您下发的" + aupInspectOfDB.getInspectName() + "已完成";
            sendMsg(aupInspectOfDB.getDistributeWorkerId(), AupipesMsgSender.WX_MSG, aupInspectOfDB, distributeContent);
            sendMsg(aupInspectOfDB.getDistributeWorkerId(), AupipesMsgSender.SMS_MSG, aupInspectOfDB,
                    distributeContent);

        }
        return aupInspectMapper.updateAupInspect(aupInspect);
    }

    /**
     * 删除巡检任务对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAupInspectByIds(String ids) {
        String[] arr = Convert.toStrArray(ids);

        aupInspectWorkerMapper.deleteAupInspectWorkerByTaskIds(arr);
        aupInspectDetailMapper.deleteAupInspectDetailByTaskIds(arr);
        aupInspectServiceMapper.deleteAupInspectServiceByTaskIds(arr);
        aupInspectCoordinateMapper.deleteAupInspectCoordinateByTaskIds(arr);

        return aupInspectMapper.deleteAupInspectByIds(arr);
    }

    /**
     * 删除巡检任务信息
     *
     * @param inspectId
     *            巡检任务ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectById(Long inspectId) {
        return aupInspectMapper.deleteAupInspectById(inspectId);
    }
}
