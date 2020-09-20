package com.feather.meeting.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.meeting.domain.MeetingInfo;
import com.feather.meeting.domain.MeetingPersonnel;
import com.feather.meeting.service.IMeetingInfoService;
import com.feather.meeting.service.IMeetingPersonnelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author nothing
 * @date 2019-11-18 15:49
 */

@Api(tags = "会议报名接口", description = "（nothing）")
@RestController
@RequestMapping("api/meeting")
public class MeetingApiController extends BaseController {
    @Autowired
    private IMeetingInfoService meetingInfoService;
    @Autowired
    private IMeetingPersonnelService meetingPersonnelService;

    @ApiOperation("(获取最新的会议该会议为0或者1,最多只有一条)")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "uniqueId", value = "该人员该会议唯一id") //
    })
    @RequestMapping(value = "/getNewestMeeting", method = { RequestMethod.GET })
    public AjaxResult getNewestMeeting(String uniqueId) {
        return meetingInfoService.getNewestMeeting(uniqueId);
    }

    @ApiOperation("(显示会议详情)")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "id", value = "会议id") //
    })
    @RequestMapping(value = "/showMeeting", method = { RequestMethod.GET })
    public AjaxResult showMeeting(Long id) {
        return meetingInfoService.showMeeting(id);
    }

    @ApiOperation("(增加会议)")
    @RequestMapping(value = "/addMeeting", method = { RequestMethod.POST })
    public AjaxResult addMeeting(MeetingInfo meetingInfo) {
        return meetingInfoService.insertMeeting(meetingInfo);
    }

    @ApiOperation("(编辑会议)")
    @RequestMapping(value = "/editMeeting", method = { RequestMethod.POST })
    public AjaxResult editMeeting(MeetingInfo meetingInfo) {
        return meetingInfoService.updateMeeting(meetingInfo);
    }

    @ApiOperation("(显示参会人员详情)")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "id", value = "参会人员的id") //
    })
    @RequestMapping(value = "/showMeetingPersonnel", method = { RequestMethod.GET })
    public AjaxResult showMeetingPersonnel(Long id) {
        return meetingPersonnelService.showMeetingPersonnel(id);
    }

    @ApiOperation("(增加参会人员)")
    @RequestMapping(value = "/addMeetingPersonnel", method = { RequestMethod.POST })
    public AjaxResult addMeetingPersonnel(MeetingPersonnel meetingPersonnel) {
        return meetingPersonnelService.insertMeetingPersonnel(meetingPersonnel);
    }

    @ApiOperation("(编辑参会人员)")
    @RequestMapping(value = "/editMeetingPersonnel", method = { RequestMethod.POST })
    public AjaxResult editMeetingPersonnel(MeetingPersonnel meetingPersonnel) {
        return meetingPersonnelService.updateMeetingPersonnel(meetingPersonnel);
    }

    @ApiOperation("(导出参会人员(Word))")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "id", value = "会议id") //
    })
    @RequestMapping(value = "/exportWord", method = { RequestMethod.GET })
    public void exportWord(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String basePath = getBasePath();
        meetingInfoService.exportWord(id, basePath, request, response);
    }

    @ApiOperation("(导出参会人员(excel))")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "id", value = "会议id") //
    })
    @RequestMapping(value = "/exportExcel", method = { RequestMethod.GET })
    public void exportExcel(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String basePath = getBasePath();
        meetingInfoService.exportExcel(id, basePath, request, response);
    }
}
