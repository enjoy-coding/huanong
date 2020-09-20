package com.feather.meeting.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.feather.common.config.Global;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.common.utils.file.FileUtils;
import com.feather.meeting.data.MeetingInfoData;
import com.feather.meeting.domain.MeetingInfo;
import com.feather.meeting.domain.MeetingPersonnel;
import com.feather.meeting.service.IMeetingInfoService;
import com.feather.meeting.service.IMeetingPersonnelService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

/**
 * @author nothing
 * @date 2019-11-07 16:44
 */
@Service
public class MeetingInfoServiceImpl extends BaseService<MeetingInfo> implements IMeetingInfoService {
    private final String SEGMENT = "word/segment.docx";
    private final String TXL = "word/txl.docx";
    private final String HZB = "word/bmhzb.xlsx";
    private final String TXL_DOC = "txl.docx";
    private final String HZB_XLSX = "bmhzb.xlsx";

    @Autowired
    IMeetingPersonnelService meetingPersonnelService;

    @Override
    public MeetingInfo findById(Long id) {
        MeetingInfo meetingInfo = this.fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
        return meetingInfo;
    }

    @Override
    public PageredData<MeetingInfo> list(MeetingInfo meetingInfo, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Strings.isNotBlank(meetingInfo.getTitle())) {
            cnd.and(Cnd.exps("title", "like", "%" + meetingInfo.getTitle() + "%"));
        }
        if (Lang.isNotEmpty(meetingInfo.getBeginTime())) {
            cnd.and("beginTime", ">=", meetingInfo.getBeginTime());
        }
        if (Lang.isNotEmpty(meetingInfo.getEndTime())) {
            cnd.and("endTime", "<=", meetingInfo.getEndTime());
        }
        cnd.orderBy("createTime", "desc");
        PageredData<MeetingInfo> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public AjaxResult insertAddDo(MeetingInfo meetingInfo) {
        try {
            deleteMeetingWithLogic();
            String beginTimeStr = meetingInfo.getBeginTimeStr();
            String endTimeStr = meetingInfo.getEndTimeStr();
            meetingInfo.setBeginTime(DateUtils.parseDate(beginTimeStr));
            meetingInfo.setEndTime(DateUtils.parseDate(endTimeStr));
            save(meetingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateEditDo(MeetingInfo meetingInfo) {
        try {
            String beginTimeStr = meetingInfo.getBeginTimeStr();
            String endTimeStr = meetingInfo.getEndTimeStr();
            meetingInfo.setBeginTime(DateUtils.parseDate(beginTimeStr));
            meetingInfo.setEndTime(DateUtils.parseDate(endTimeStr));
            updateIgnoreNull(meetingInfo);
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
            deleteByIdsWithLogic(ids);
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
    public AjaxResult deleteByIdsWithLogic(String idStr) {
        Long[] ids = StringUtils.StringToLong(idStr);
        return deleteByIdsWithLogic(ids);
    }

    @Transactional
    @Override
    public AjaxResult deleteByIdsWithLogic(Long[] ids) {
        try {
            this.update(Chain.make("delFlag", 2), Cnd.where("id", "in", ids));
            meetingPersonnelService.deleteByMeetingIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getNewestMeeting(String uniqueId) {
        List<MeetingInfo> meetingInfoList;
        Map<String, Object> data = new HashMap<>();
        try {
            Cnd cnd = Cnd.where("delFlag", "=", 0);
            cnd.orderBy("createTime", "desc");
            meetingInfoList = query(cnd, 1, 1);
            if (Lang.isNotEmpty(meetingInfoList) && meetingInfoList.size() == 1) {
                MeetingInfo meetingInfo = meetingInfoList.get(0);
                meetingInfo = fillDateStr(meetingInfo);
                data.put("meetingInfo", meetingInfo);
                if (Strings.isNotBlank(uniqueId)) {
                    MeetingPersonnel meetingPersonnel = meetingPersonnelService.hasJoinMeeting(meetingInfo.getId(),
                            uniqueId);
                    if (Lang.isNotEmpty(meetingPersonnel)) {
                        data.put("hasJoin", true);
                        data.put("meetingPersonnel", meetingPersonnel);
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
    public AjaxResult showMeeting(Long id) {
        Map<String, Object> data = new HashMap<>();
        try {
            MeetingInfo meetingInfo = findById(id);
            meetingInfo = fillDateStr(meetingInfo);
            data.put("meetingInfo", meetingInfo);
        } catch (Exception e) {
            return AjaxResult.success(e.getMessage());
        }
        return AjaxResult.success(data);
    }

    @Transactional
    @Override
    public AjaxResult insertMeeting(MeetingInfo meetingInfo) {
        Map<String, Object> data = new HashMap<>();
        try {
            deleteMeetingWithLogic();
            String beginTimeStr = meetingInfo.getBeginTimeStr();
            String endTimeStr = meetingInfo.getEndTimeStr();
            meetingInfo.setBeginTime(DateUtils.parseDate(beginTimeStr));
            meetingInfo.setEndTime(DateUtils.parseDate(endTimeStr));
            meetingInfo = save(meetingInfo);
            meetingInfo = fillDateStr(meetingInfo);
            data.put("meetingInfo", meetingInfo);
        } catch (Exception e) {
            return AjaxResult.success(e.getMessage());
        }

        return AjaxResult.success(data);
    }

    @Transactional
    @Override
    public AjaxResult updateMeeting(MeetingInfo meetingInfo) {
        Map<String, Object> data = new HashMap<>();
        try {
            String beginTimeStr = meetingInfo.getBeginTimeStr();
            String endTimeStr = meetingInfo.getEndTimeStr();
            meetingInfo.setBeginTime(DateUtils.parseDate(beginTimeStr));
            meetingInfo.setEndTime(DateUtils.parseDate(endTimeStr));
            updateIgnoreNull(meetingInfo);
            data.put("meetingInfo", null);
            if (Lang.isNotEmpty(meetingInfo.getId())) {
                MeetingInfo dbMeetingInfo = findById(meetingInfo.getId());
                if (Lang.isNotEmpty(dbMeetingInfo)) {
                    dbMeetingInfo = fillDateStr(dbMeetingInfo);
                    data.put("meetingInfo", dbMeetingInfo);
                }
            }
        } catch (Exception e) {
            return AjaxResult.success(e.getMessage());
        }

        return AjaxResult.success(data);
    }

    @Transactional
    @Override
    public AjaxResult deleteMeetingWithLogic() {
        try {
            List<Long> meetingIds = new ArrayList<>();
            // 逻辑删除会议的时候需要同时删除会议参会人员
            List<MeetingInfo> meetingInfos = this.query(Cnd.where("delFlag", "=", 0));
            if (Lang.isNotEmpty(meetingInfos) && meetingInfos.size() > 0) {
                for (MeetingInfo meetingInfo : meetingInfos) {
                    meetingIds.add(meetingInfo.getId());
                }
            }
            if (meetingIds.size() > 0) {
                Long[] arr = new Long[meetingIds.size()];
                meetingIds.toArray(arr);
                meetingPersonnelService.deleteByMeetingIdsWithLogic(arr);
            }
            update(Chain.make("delFlag", 2), Cnd.where("delFlag", "=", 0));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public void exportWord(Long meetingId, String basePath, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MeetingInfoData meetingInfoData = new MeetingInfoData();
        ClassPathResource segmentRecource = new ClassPathResource(SEGMENT);
        ClassPathResource txlRescource = new ClassPathResource(TXL);

        InputStream segmentInputStream = segmentRecource.getInputStream();
        InputStream meetingInfoInputStream = txlRescource.getInputStream();
        List<MeetingPersonnel> meetingPersonnels = meetingPersonnelService.findByMeetingId(meetingId);
        if (Lang.isNotEmpty(meetingPersonnels) && meetingPersonnels.size() > 0) {
            for (MeetingPersonnel meetingPersonnel : meetingPersonnels) {
                try {
                    meetingPersonnel.setPicture(basePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

            }
        } else {
            // 解决记录为0的时候，word页面是模板的问题
            meetingPersonnels.add(new MeetingPersonnel());
        }

        DocxRenderData segment = new DocxRenderData(segmentInputStream, meetingPersonnels);
        meetingInfoData.setSegment(segment);
        XWPFTemplate template = XWPFTemplate.compile(meetingInfoInputStream).render(meetingInfoData);
        String fileName = FileUploadUtils.extractFilename(TXL_DOC);
        String filePath = Global.getUploadPath() + File.separator + fileName;

        File desc = new File(filePath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }

        template.writeToFile(filePath);

        FileUtils.downloadDoc(fileName, request, response);

    }

    @Override
    public void exportExcel(Long meetingId, String basePath, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            TemplateExportParams templateExportParams = new TemplateExportParams(HZB);
            List<MeetingPersonnel> meetingPersonnels = meetingPersonnelService.findByMeetingId(meetingId);
            if (Lang.isNotEmpty(meetingPersonnels) && meetingPersonnels.size() > 0) {
                for (int i = 0; i < meetingPersonnels.size(); i++) {
                    Long index = i + 1L;
                    MeetingPersonnel meetingPersonnel = meetingPersonnels.get(i);
                    meetingPersonnel.setId(index);
                }
            }
            params.put("meetingPersonnels", meetingPersonnels);

            String fileName = FileUploadUtils.extractFilename(HZB_XLSX);
            String filePath = Global.getUploadPath() + File.separator + fileName;

            File desc = new File(filePath);
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
            if (!desc.exists()) {
                desc.createNewFile();
            }

            Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams, params);
            workbook.write(new FileOutputStream(desc));

            FileUtils.downloadDoc(fileName, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换日期
     *
     * @param meetingInfo
     * @return
     */
    private MeetingInfo fillDateStr(MeetingInfo meetingInfo) {
        if (Lang.isNotEmpty(meetingInfo)) {
            Date beginTime = meetingInfo.getBeginTime();
            Date endTime = meetingInfo.getEndTime();
            meetingInfo.setBeginTimeStr(DateUtils.getTime(beginTime));
            meetingInfo.setEndTimeStr(DateUtils.getTime(endTime));
        }
        return meetingInfo;
    }
}
