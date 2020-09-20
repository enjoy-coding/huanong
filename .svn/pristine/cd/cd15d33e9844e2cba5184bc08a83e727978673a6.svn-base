package com.feather.meeting.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.meeting.domain.MeetingInfo;

/**
 * @author nothing
 * @date 2019-11-07 16:21
 */
public interface IMeetingInfoService {
    /**
     * id查询
     *
     * @param id
     * @return
     */
    MeetingInfo findById(Long id);

    /**
     * 分页查询
     *
     * @param meetingInfo
     * @param pageDomain
     * @return
     */
    PageredData<MeetingInfo> list(MeetingInfo meetingInfo, PageDomain pageDomain);

    /**
     * 增加会议
     *
     * @param meetingInfo
     * @return
     */
    AjaxResult insertAddDo(MeetingInfo meetingInfo);

    /**
     * 编辑会议
     *
     * @param meetingInfo
     * @return
     */
    AjaxResult updateEditDo(MeetingInfo meetingInfo);

    /**
     * 物理删除会议
     *
     * @param idStr
     * @return
     */
    AjaxResult deleteByIds(String idStr);

    /**
     * 物理删除会议
     *
     * @param ids
     * @return
     */
    AjaxResult deleteByIds(Long[] ids);

    /**
     * 逻辑删除会议
     *
     * @param idStr
     * @return
     */
    AjaxResult deleteByIdsWithLogic(String idStr);

    /**
     * 逻辑删除会议
     *
     * @param ids
     * @return
     */
    AjaxResult deleteByIdsWithLogic(Long[] ids);

    /*************************** 接口 ************************************/

    /**
     * 获取最新的会议，该会议 是没有删除并且根据createDate排序最新的一条记录
     *
     * @return
     */
    AjaxResult getNewestMeeting(String uniqueId);

    /**
     * 根据id查询会议
     * 
     * @param id
     * @return
     */
    AjaxResult showMeeting(Long id);

    /**
     * 新增会议
     *
     * @param meetingInfo
     * @return
     */
    AjaxResult insertMeeting(MeetingInfo meetingInfo);

    /**
     * 编辑会议
     *
     * @param meetingInfo
     * @return
     */
    AjaxResult updateMeeting(MeetingInfo meetingInfo);

    /**
     * 删除会议
     *
     * @param
     * @return
     */
    AjaxResult deleteMeetingWithLogic();

    /**
     * 导出word
     * 
     * @param meetingId
     * @param basePath
     * @param request
     * @param response
     */
    void exportWord(Long meetingId, String basePath, HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    /**
     * 导出excel
     * 
     * @param meetingId
     * @param basePath
     * @param request
     * @param response
     */
    void exportExcel(Long meetingId, String basePath, HttpServletRequest request, HttpServletResponse response);

}
