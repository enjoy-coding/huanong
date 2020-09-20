package com.feather.meeting.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.meeting.domain.MeetingPersonnel;

/**
 * @author nothing
 * @date 2019-11-07 16:22
 */
public interface IMeetingPersonnelService {

    /**
     * id查询
     * 
     * @param id
     * @return
     */
    MeetingPersonnel findById(Long id);

    /**
     * 根据会议id查询会议参会人员
     * 
     * @param meetingId
     * @return
     */
    List<MeetingPersonnel> findByMeetingId(Long meetingId);

    /**
     * 分页查询
     * 
     * @param meetingPersonnel
     * @param pageDomain
     * @return
     */
    PageredData<MeetingPersonnel> list(MeetingPersonnel meetingPersonnel, PageDomain pageDomain);

    /**
     * 增加会议参会人员
     * 
     * @param meetingPersonnel
     * @return
     */
    AjaxResult insertAddDo(MeetingPersonnel meetingPersonnel);

    /**
     * 编辑会议参会人员
     * 
     * @param meetingPersonnel
     * @return
     */
    AjaxResult updateEditDo(MeetingPersonnel meetingPersonnel);

    /**
     * 物理删除会议会议参会人员
     * 
     * @param idStr
     * @return
     */
    AjaxResult deleteByIds(String idStr);

    /**
     * 物理删除会议会议参会人员
     * 
     * @param ids
     * @return
     */
    AjaxResult deleteByIds(Long[] ids);

    /**
     * 物理删除会议参会人员，根据会议id
     * 
     * @param meetingIds
     * @return
     */
    AjaxResult deleteByMeetingIds(Long[] meetingIds);

    /**
     * 逻辑删除会议参会人员
     * 
     * @param idStr
     * @return
     */
    AjaxResult deleteByIdsWithLogic(String idStr);

    /**
     * 逻辑删除会议参会人员
     * 
     * @param ids
     * @return
     */
    AjaxResult deleteByIdsWithLogic(Long[] ids);

    /**
     * 通过会议id逻辑删除参会人员
     * 
     * @param meethingIds
     * @return
     */
    AjaxResult deleteByMeetingIdsWithLogic(Long[] meethingIds);

    /************************* 接口 ****************************/

    /**
     * 显示参会人员详情
     * 
     * @param id
     * @return
     */
    AjaxResult showMeetingPersonnel(Long id);

    /**
     * 新增会议人员信息
     * 
     * @param meetingPersonnel
     * @return
     */
    AjaxResult insertMeetingPersonnel(MeetingPersonnel meetingPersonnel);

    /**
     * 编辑参会人员信息
     * 
     * @param meetingPersonnel
     * @return
     */
    AjaxResult updateMeetingPersonnel(MeetingPersonnel meetingPersonnel);

    /**
     * 判断该用户是否参与此次会议
     * 
     * @Param meetingId
     * @param uniqueId
     * @return
     */
    MeetingPersonnel hasJoinMeeting(Long meetingId, String uniqueId);
}
