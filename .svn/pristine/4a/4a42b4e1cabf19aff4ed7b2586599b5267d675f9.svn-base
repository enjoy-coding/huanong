package com.feather.meeting.controller;

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
import com.feather.common4nutz.controller.NutzBaseController;
import com.feather.meeting.domain.MeetingInfo;
import com.feather.meeting.domain.MeetingPersonnel;
import com.feather.meeting.service.IMeetingInfoService;
import com.feather.meeting.service.IMeetingPersonnelService;

/**
 * @author nothing
 * @date 2019-11-07 16:23
 */
@Controller
@RequestMapping("meeting/personnel")
public class MeetingPersonnelController extends NutzBaseController {
    private String prefix = "meeting/personnel";

    @Autowired
    private IMeetingInfoService meetingInfoService;
    @Autowired
    private IMeetingPersonnelService meetingPersonalService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/dialogList/{meetingId}")
    public String dialogList(@PathVariable("meetingId") Long meetingId, ModelMap mmap) {
        mmap.put("meetingId", meetingId);
        return prefix + "/dialogList";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        MeetingPersonnel meetingPersonnel = MeetingPersonnel.builder().build();
        mmap.put("meetingPersonnel", meetingPersonnel);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MeetingPersonnel meetingPersonnel = meetingPersonalService.findById(id);
        if (Lang.isEmpty(meetingPersonnel)) {
            meetingPersonnel = new MeetingPersonnel();
        }
        if (Lang.isNotEmpty(meetingPersonnel.getMeetingId())) {
            MeetingInfo meetingInfo = meetingInfoService.findById(meetingPersonnel.getMeetingId());
            if (Lang.isNotEmpty(meetingInfo)) {
                meetingPersonnel.setMeetingTitle(meetingInfo.getTitle());
            }
        }
        mmap.put("meetingPersonnel", meetingPersonnel);
        return prefix + "/edit.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingPersonnel meetingPersonnel) {
        PageDomain pageDomain = getPageDomain();
        PageredData<MeetingPersonnel> pageredData = meetingPersonalService.list(meetingPersonnel, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加参会人员", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(MeetingPersonnel meetingPersonnel) {
        return meetingPersonalService.insertAddDo(meetingPersonnel);
    }

    @Log(title = "编辑参会人员", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(MeetingPersonnel meetingPersonnel) {
        return meetingPersonalService.updateEditDo(meetingPersonnel);
    }

    @Log(title = "逻辑删除参会人员", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return meetingPersonalService.deleteByIdsWithLogic(ids);
    }
}
