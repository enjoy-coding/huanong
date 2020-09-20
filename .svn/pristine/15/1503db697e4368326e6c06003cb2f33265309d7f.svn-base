package com.feather.napo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.feather.napo.domain.NpMobileDevice;
import com.feather.napo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.StringUtils;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpInfoDetail;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.service.IPrdAttachmentService;
import com.feather.system.domain.SysUser;

/**
 * @author nothing
 * @date 2020-06-17 10:45
 */
@Api(tags = "1.1.1", description = "那坡旅游服务[np/api]")
@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("/np/api")
public class NpApiController extends BaseController {

    @Autowired
    INpInfoService npInfoService;
    @Autowired
    INpInfoDetailService npInfoDetailService;
    @Autowired
    IPrdAttachmentService prdAttachmentService;
    @Autowired
    INpThumbsUpLogService npThumbsUpLogService;
    @Autowired
    INpMobileDeviceService npMobileDeviceService;
    @Autowired
    INpInfoReadLogService npInfoReadLogService;

    @ApiOperation(value = "增加旅游信息",
            notes = "根据type字段区分增加的类别，大部分字段是通用的，个别字段有区别，主要集中在addInfo上")
    @Log(title = "那坡信息", businessType = BusinessType.INSERT)
    @PostMapping("/addNpInfo")
    @ResponseBody
    public AjaxResult addSave(@Validated NpInfo npInfo) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npInfoService.insertNpInfo(npInfo));
    }

    /**
     * 点赞功能
     */
    @ApiOperation(value = "点赞功能",
            notes = "插入需要点赞的id即可")
    @Log(title = "那坡信息", businessType = BusinessType.UPDATE)
    @PostMapping("/thumbsUp")
    @ResponseBody
    public AjaxResult thumbsUp(HttpServletRequest request, @Validated NpInfo npInfo) {
        Long infoId = npInfo.getInfoId();
        NpInfo dbNpInfo = npInfoService.selectNpInfoById(infoId);
        if (!Objects.isNull(dbNpInfo)) {
            boolean flag = checkIfThumbsUp(request, dbNpInfo);
            if (flag) {
                return toAjax(0, "该信息已点赞，请勿重复点赞！");
            }
            Integer hot = dbNpInfo.getHot();
            if (Objects.isNull(hot)) {
                hot = 1;
            } else {
                hot += 1;
            }
            npInfo.setHot(hot);
            npThumbsUpLogService.insertNpThumbsUpLog(request, npInfo);
        }
        return toAjax(npInfoService.updateNpInfo(npInfo), npInfo);
    }

    private boolean checkIfThumbsUp(HttpServletRequest request, NpInfo npInfo) {
        return npThumbsUpLogService.checkIfThumbsUp(request, npInfo);
    }

    @ApiOperation(value = "前端旅游服务",
            notes = "该模块展示的数据有些特殊，需要单独获取")
    @GetMapping("/frontLyfw")
    @ResponseBody
    public TableDataInfo frontLyfw() {
        startPage();
        List<NpInfo> list = npInfoService.selectFrontLyfwList();
        return getDataTable(list);
    }

    @ApiOperation(value = "列表展示",
            notes = "分页展示，可以进行排序，排序传入的字段orderByColumn，代表排序字段，字段isAsc，代表的是顺序还是倒序")
    @GetMapping("/listNpInfo")
    @ClearPage
    @ResponseBody
    public TableDataInfo listNpInfo(NpInfo npInfo) {
        startPage();
        List<NpInfo> list = npInfoService.selectNpInfoList(npInfo);
        return getDataTable(list);
    }

    /**
     * 列出玩尽兴的信息
     *
     * @param npInfo
     * @return
     */
    @ApiOperation(value = "列出玩尽兴的信息",
            notes = "该模块展示的数据有些特殊，需要单独获取")
    @GetMapping("/listWjx")
    @ClearPage
    @ResponseBody
    public TableDataInfo listWjx(NpInfo npInfo) {
        startPage();
        List<NpInfo> list = npInfoService.selectNpInfoList(npInfo);
        if (!Objects.isNull(list) && list.size() > 0) {
            for (NpInfo npInfo1 : list) {
                NpInfoDetail npInfoDetail = new NpInfoDetail();
                npInfoDetail.setInfoId(npInfo1.getInfoId());
                List<NpInfoDetail> list1 = npInfoDetailService.selectNpInfoDetailList(npInfoDetail);
                npInfo1.setNpInfoDetails(list1);
            }
        }
        return getDataTable(list);
    }

    /**
     * 批量获取详情
     *
     * @param npInfoDetail
     * @return
     */
    @ApiOperation(value = "列出信息的详情页",
            notes = "主要包括住酒店和商店这些存在二级多列表的情况")
    @GetMapping("/listNpInfoDetail")
    @ClearPage
    @ResponseBody
    public TableDataInfo listNpInfoDetail(NpInfoDetail npInfoDetail) {
        startPage();
        List<NpInfoDetail> list = npInfoDetailService.selectNpInfoDetailList(npInfoDetail);
        return getDataTable(list);
    }

    @ApiOperation(value = "根据信息id获取信息",
            notes = "主键获取npInfo中的数据")
    @GetMapping("/selectNpInfoById")
    @ClearPage
    @ResponseBody
    public AjaxResult selectNpInfoById(@RequestParam("infoId") Long infoId) {
        List<NpInfoDetail> npInfoDetails = new ArrayList<>();
        NpInfo npInfo = npInfoService.selectNpInfoById(infoId);
        if (npInfo != null) {
            NpInfoDetail npInfoDetail = new NpInfoDetail();
            // 增加点击次数
            Integer viewCount = npInfo.getViewCount();
            if (Objects.isNull(viewCount)) {
                viewCount = 1;
            } else {
                viewCount += 1;
            }
            npInfo.setViewCount(viewCount);
            npInfoService.updateNpInfo(npInfo);
            npInfoDetail.setInfoId(infoId);
            // 查详情
            npInfoDetails = npInfoDetailService.selectNpInfoDetailList(npInfoDetail);
            npInfo.setNpInfoDetails(npInfoDetails);

        }
        return AjaxResult.success(npInfo);
    }

    @ApiOperation(value = "根据信息详情id获取信息详情",
            notes = "主键获取npInfoDetail中的数据")
    @GetMapping("/selectNpInfoDetailById")
    @ClearPage
    @ResponseBody
    public AjaxResult selectNpInfoDetailById(@RequestParam("infoDetailId") Long infoDetailId) {
        NpInfoDetail npInfoDetail = npInfoDetailService.selectNpInfoDetailById(infoDetailId);
        npInfoDetail = (npInfoDetail != null) ? npInfoDetail : new NpInfoDetail();
        return AjaxResult.success(npInfoDetail);
    }


    @ApiOperation(value = "增加移动设备信息",
            notes = "在移动端需要根据设备判断查看未读信息，所以需要首先保存一段设备信息")
    @Log(title = "那坡移动设备", businessType = BusinessType.INSERT)
    @PostMapping("/addNpMobileDevice")
    @ResponseBody
    public AjaxResult addNpMobileDevice(@Validated NpMobileDevice npMobileDevice) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npMobileDeviceService.insertNpMobileDevice(npMobileDevice));
    }

    @ApiOperation(value = "获取未读消息",
            notes = "根据设备唯一编号获取未读的信息")
    @GetMapping("/getUnReadNpInfoByDeviceId")
    @ResponseBody
    public AjaxResult getUnReadNpInfoByDeviceId(@RequestParam("deviceId") String deviceId) {
        List<NpInfo> npInfos = npInfoService.selectUnReadNpInfoByDeviceId(deviceId);
        return AjaxResult.success(npInfos);
    }

    @ApiOperation(value = "更改信息阅读状态",
            notes = "根据设备id和信息id获取唯一信息后更改信息状态为已读")
    @PostMapping("/changeNpInfoReadState")
    @ResponseBody
    public AjaxResult changeNpInfoReadState(String deviceId, Long infoId) {
        try {
            npInfoReadLogService.changeNpInfoReadState(deviceId, infoId);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @ApiOperation(value = "通用文件上传",
            notes = "才去H5的FormData方式上传")
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile[] files,
                                 @RequestParam(name = "thumb", required = false) Boolean thumb) throws Exception {
        if (files != null) {
            List<PrdAttachment.AttachmentInfo> list = null;
            try {
                list = prdAttachmentService.upload(files, thumb, true);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return AjaxResult.error(e.getMessage(), list);
            }
            return AjaxResult.success(list);
        }
        return AjaxResult.error("没有找到文件");
    }

    @ApiOperation(value = "通用登陆",
            notes = "一般情况不需要，除非有自己的后台或者前后端分离的后台")
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult restLogin(HttpServletRequest request, String username, String password) {
        String msg = doLogin(request, username, password, false);
        if (StringUtils.isEmpty(msg)) {
            SysUser sysUser = ShiroUtils.getSysUser();
            sysUser.setPassword(null);
            AjaxResult result = AjaxResult.success(sysUser);
            result.put("token", getSession().getId());
            return result;
        }
        return AjaxResult.error(msg);
    }

    private String doLogin(HttpServletRequest request, String username, String password, Boolean rememberMe) {
        if (rememberMe == null) {
            rememberMe = false;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return null;
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return msg;
        }
    }

}
