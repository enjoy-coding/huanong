package com.feather.meeting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.StringUtils;
import com.feather.meeting.domain.MeetingInfo;
import com.feather.meeting.domain.MeetingPersonnel;
import com.feather.meeting.service.IMeetingInfoService;
import com.feather.meeting.service.IMeetingPersonnelService;
import com.feather.wx.config.WxConfig;
import com.feather.wx.service.WxManagerService;
import com.feather.wx.util.DateUtil;
import com.feather.wx.util.MyMessageAPI;

/**
 * @author nothing
 * @date 2019-11-07 16:28
 */
@Service
public class MeetingPersonalServiceImpl extends BaseService<MeetingPersonnel> implements IMeetingPersonnelService {

    private final Logger logger = LoggerFactory.getLogger(MeetingPersonalServiceImpl.class);

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    @Autowired
    private IMeetingInfoService meetingInfoService;
    @Autowired
    private WxManagerService wxManagerService;

    @Value("${meeting.templateid}")
    private String templateid;

    @Value("${meeting.useKf}")
    private boolean useKf;

    @Value("${meeting.menus}")
    private List<String> menus;

    @Override
    public MeetingPersonnel findById(Long id) {
        MeetingPersonnel meetingPersonnel = this.fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
        return meetingPersonnel;
    }

    @Override
    public List<MeetingPersonnel> findByMeetingId(Long meetingId) {
        return this.query(Cnd.where("delFlag", "=", 0).and("meetingId", "=", meetingId));
    }

    @Override
    public PageredData<MeetingPersonnel> list(MeetingPersonnel meetingPersonnel, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);
        if (Strings.isNotBlank(meetingPersonnel.getUserName())) {
            cnd.and("userName", "like", "%" + meetingPersonnel.getUserName() + "%");
        }
        if (Lang.isNotEmpty(meetingPersonnel.getMeetingId())) {
            cnd.and("meetingId", "=", meetingPersonnel.getMeetingId());
        }
        cnd.orderBy("createTime", "desc");
        PageredData<MeetingPersonnel> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public AjaxResult insertAddDo(MeetingPersonnel meetingPersonnel) {
        try {
            save(meetingPersonnel);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateEditDo(MeetingPersonnel meetingPersonnel) {
        try {
            updateIgnoreNull(meetingPersonnel);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByIds(String idStr) {
        try {
            Long[] ids = StringUtils.StringToLong(idStr);
            deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByIds(Long[] ids) {
        try {
            this.clear(Cnd.where("id", "in", ids));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByMeetingIds(Long[] meetingIds) {
        try {
            this.clear(Cnd.where("meetingId", "in", meetingIds));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByIdsWithLogic(String idStr) {
        Long[] ids = StringUtils.StringToLong(idStr);
        return deleteByIdsWithLogic(ids);
    }

    @Override
    public AjaxResult deleteByIdsWithLogic(Long[] ids) {
        try {
            this.update(Chain.make("delFlag", 2), Cnd.where("id", "in", ids));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByMeetingIdsWithLogic(Long[] meethingIds) {
        try {
            this.update(Chain.make("delFlag", 2), Cnd.where("meetingId", "in", meethingIds));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult showMeetingPersonnel(Long id) {
        Map<String, Object> data = new HashMap<>();
        try {
            MeetingPersonnel meetingPersonnel = findById(id);
            Long meetingId = meetingPersonnel.getMeetingId();
            data.put("meetingPersonnel", meetingPersonnel);
            if (Lang.isNotEmpty(meetingId)) {
                MeetingInfo meetingInfo = meetingInfoService.findById(meetingId);
                meetingInfo = fillDateStr(meetingInfo);
                data.put("meetingInfo", meetingInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success(data);

    }

    @Override
    public AjaxResult insertMeetingPersonnel(MeetingPersonnel meetingPersonnel) {
        String meetingTitle = "";
        Map<String, Object> data = new HashMap<>();
        String openId = meetingPersonnel.getOpenId();
        try {
            // 检验数据库中是否已经有存在的记录，存在即不存入
            MeetingPersonnel dbMeetingPersonnel = hasJoinMeeting(meetingPersonnel.getMeetingId(),
                    meetingPersonnel.getUniqueId());
            if (Lang.isNotEmpty(dbMeetingPersonnel))
                return AjaxResult.success(data);
            meetingPersonnel = save(meetingPersonnel);
            Long meetingId = meetingPersonnel.getMeetingId();
            data.put("meetingPersonnel", meetingPersonnel);
            if (Lang.isNotEmpty(meetingId)) {
                MeetingInfo meetingInfo = meetingInfoService.findById(meetingId);
                meetingInfo = fillDateStr(meetingInfo);
                meetingTitle = meetingInfo.getTitle();
                data.put("meetingInfo", meetingInfo);
            }
            String userName = meetingPersonnel.getUserName();
            if (Strings.isNotBlank(openId)) {
                sendJoinMeetingMsg(meetingTitle, userName, meetingPersonnel.getSchool(), meetingPersonnel.getUnit(),
                        meetingPersonnel.getJob(), meetingPersonnel.getAddress(), meetingPersonnel.getCode(),
                        meetingPersonnel.getMobile(), meetingPersonnel.getTelephone(), meetingPersonnel.getPhoto(),
                        openId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success(data);
    }

    @Override
    public AjaxResult updateMeetingPersonnel(MeetingPersonnel meetingPersonnel) {
        Map<String, Object> data = new HashMap<>();
        try {
            updateIgnoreNull(meetingPersonnel);
            data.put("meetingPersonnel", null);
            data.put("meetingInfo", null);
            if (Lang.isNotEmpty(meetingPersonnel.getId())) {
                MeetingPersonnel dbMeetingPersonnel = findById(meetingPersonnel.getId());
                if (Lang.isNotEmpty(dbMeetingPersonnel)) {
                    data.put("meetingPersonnel", dbMeetingPersonnel);
                    Long meetingId = dbMeetingPersonnel.getMeetingId();
                    if (Lang.isNotEmpty(meetingId)) {
                        MeetingInfo meetingInfo = meetingInfoService.findById(meetingId);
                        meetingInfo = fillDateStr(meetingInfo);
                        data.put("meetingInfo", meetingInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success(data);
    }

    @Override
    public MeetingPersonnel hasJoinMeeting(Long meetingId, String uniqueId) {
        Cnd cnd = Cnd.where("delFlag", "=", 0).and("meetingId", "=", meetingId).and("uniqueId", "=", uniqueId);
        return fetch(cnd);
    }

    private MeetingInfo fillDateStr(MeetingInfo meetingInfo) {
        if (Lang.isNotEmpty(meetingInfo)) {
            Date beginTime = meetingInfo.getBeginTime();
            Date endTime = meetingInfo.getEndTime();
            meetingInfo.setBeginTimeStr(DateUtils.getTime(beginTime));
            meetingInfo.setEndTimeStr(DateUtils.getTime(endTime));
        }
        return meetingInfo;
    }

    /**
     * 发送会议消息到微信上
     *
     * @param meetingTitle
     * @param userName
     * @param openId
     * @throws WexinReqException
     */
    private void sendJoinMeetingMsg(String meetingTitle, String userName, String school, String unit, String job,
            String address, String code, String mobile, String telephone, String photo, String openId)
            throws WexinReqException {
        String accessToken = wxManagerService.getAccessToken();
        WxConfig wxConfig = wxManagerService.getWxConfig();
        String filePath = "http://" + wxConfig.getDomain() + photo;
        if (StringUtils.isNotBlank(accessToken)) {
            if (useKf) {
                sendTextMessage(meetingTitle, userName, school, unit, job, address, code, mobile, telephone,
                        accessToken, openId);
                if (StringUtils.isNotBlank(photo)) {
                    threadPool.submit(() -> {
                        try {
                            MyMessageAPI.sendImageMessage(accessToken, openId, filePath);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    });
                }
            } else {
                sendTemplateMsg(meetingTitle, userName, school, unit, job, address, code, mobile, telephone,
                        accessToken, openId, templateid);
                /*
                 * if (Strings.isNotBlank(photo)) { new Thread(() -> { try {
                 * MySendMessageApi.messagePreviewImage(accessToken, openId,
                 * filePath); } catch (WexinReqException e) {
                 * e.printStackTrace(); } }).start(); }
                 */
            }
        }
    }

    /**
     * 发送普通消息
     *
     * @param meetingTitle
     * @param userName
     * @param school
     * @param unit
     * @param job
     * @param address
     * @param code
     * @param mobile
     * @param telephone
     * @param accessToken
     * @param openId
     * @throws WexinReqException
     */
    private void sendTextMessage(String meetingTitle, String userName, String school, String unit, String job,
            String address, String code, String mobile, String telephone, String accessToken, String openId) {
        StringBuffer sb = new StringBuffer();
        String title = DateUtil.getGreeting() + "欢迎您参加" + meetingTitle + "\r\n";
        sb.append(title).append("会议标题:").append(meetingTitle).append("\r\n").append("姓名:").append(userName)
                .append("\r\n").append("学校:").append(school).append("\r\n").append("单位:").append(unit).append("\r\n")
                .append("职务:").append(job).append("\r\n").append("地址:").append(address).append("\r\n").append("邮编:")
                .append(code).append("\r\n").append("手机号码:").append(mobile).append("\r\n").append("固定电话:")
                .append(telephone).append("\r\n");

        MyMessageAPI.sendTextMessage(accessToken, openId, sb.toString());
    }

    /**
     * 发送模板消息
     *
     * @param meetingTitle
     * @param userName
     * @param school
     * @param unit
     * @param job
     * @param address
     * @param code
     * @param mobile
     * @param telephone
     * @param openId
     */
    private void sendTemplateMsg(String meetingTitle, String userName, String school, String unit, String job,
            String address, String code, String mobile, String telephone, String accessToken, String openId,
            String templateid) throws WexinReqException {
        String color = "#173177";
        TemplateMessageSendResult sendResult = new TemplateMessageSendResult();
        Map<String, TemplateData> data = new HashMap<>();
        data.put("meetingTitle", new TemplateData(meetingTitle, color));
        data.put("name", new TemplateData(userName, color));
        data.put("school", new TemplateData(school, color));
        data.put("unit", new TemplateData(unit, color));
        data.put("job", new TemplateData(job, color));
        data.put("address", new TemplateData(address, color));
        data.put("code", new TemplateData(code, color));
        data.put("mobile", new TemplateData(mobile, color));
        data.put("telephone", new TemplateData(telephone, color));
        sendResult.setTouser(openId);
        sendResult.setTemplate_id(templateid);
        sendResult.setData(data);
        JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, sendResult);
    }
}
