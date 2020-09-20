package com.feather.meeting.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Lang;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.Log;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.DateUtils;
import com.feather.common4nutz.controller.NutzBaseController;
import com.feather.meeting.domain.MeetingInfo;
import com.feather.meeting.service.IMeetingInfoService;

/**
 * @author nothing
 * @date 2019-11-07 16:23
 */
@Controller
@RequestMapping("meeting/info")
public class MeetingInfoController extends NutzBaseController {
    private String prefix = "meeting/info";

    @Autowired
    private IMeetingInfoService meetingInfoService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/dialogList")
    public String dialogList() {
        return prefix + "/dialogList";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Date date = DateUtils.getNowDate();
        String dateStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, date);
        MeetingInfo meetingInfo = MeetingInfo.builder().beginTime(date).beginTimeStr(dateStr).endTime(date)
                .endTimeStr(dateStr).build();
        mmap.put("meetingInfo", meetingInfo);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MeetingInfo meetingInfo = meetingInfoService.findById(id);
        if (Lang.isEmpty(meetingInfo)) {
            meetingInfo = new MeetingInfo();
        }
        String beginTimeStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, meetingInfo.getBeginTime());
        String endTimeStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, meetingInfo.getEndTime());
        meetingInfo.setBeginTimeStr(beginTimeStr);
        meetingInfo.setEndTimeStr(endTimeStr);
        mmap.put("meetingInfo", meetingInfo);
        return prefix + "/edit.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingInfo meetingInfo) {
        PageDomain pageDomain = getPageDomain();
        PageredData<MeetingInfo> pageredData = meetingInfoService.list(meetingInfo, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加会议", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(MeetingInfo meetingInfo) {
        return meetingInfoService.insertAddDo(meetingInfo);
    }

    @Log(title = "编辑会议", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(MeetingInfo meetingInfo) {
        return meetingInfoService.updateEditDo(meetingInfo);
    }

    @Log(title = "逻辑删除会议", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return meetingInfoService.deleteByIdsWithLogic(ids);
    }

    @GetMapping("/exportWord/{meetingId}")
    public void exportWord(@PathVariable Long meetingId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String basePath = getBasePath();
        meetingInfoService.exportWord(meetingId, basePath, request, response);
    }

    @GetMapping("/exportExcel/{meetingId}")
    public void exportExcel(@PathVariable Long meetingId, HttpServletRequest request, HttpServletResponse response) {
        String basePath = getBasePath();
        meetingInfoService.exportExcel(meetingId, basePath, request, response);
    }
}
