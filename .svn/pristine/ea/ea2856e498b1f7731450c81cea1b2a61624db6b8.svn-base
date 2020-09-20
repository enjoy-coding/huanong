package com.feather.lpscommunity.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScParkrent;
import com.feather.lpscommunity.domain.ScRegister;
import com.feather.lpscommunity.domain.ScTaskVolunteer;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.service.IScParkrentService;
import com.feather.lpscommunity.service.IScRegisterService;
import com.feather.lpscommunity.service.IScTaskVolunteerService;
import com.feather.lpscommunity.service.IScVolunteerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 志愿者任务表Controller
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Api(tags = { "3-志愿者服务,2-心愿树,2-志愿者报名" })
@Controller
@RequestMapping("/sc/taskVolunteer")
public class ScTaskVolunteerController extends BaseController {
    private String prefix = "lpscommunity/taskVolunteer";

    @Autowired
    private IScTaskVolunteerService scTaskVolunteerService;

    @Autowired
    private IScParkrentService scParkrentService;

    @Autowired
    private IScVolunteerService scVolunteerService;

    @Autowired
    private IScRegisterService scRegisterService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:taskVolunteer:view")
    @GetMapping()
    public String TaskVolunteer() {
        return prefix + "/taskVolunteer";
    }

    /**
     * 查询志愿者任务表列表
     */
    @RequiresPermissions("sc:taskVolunteer:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScTaskVolunteer scTaskVolunteer) {
        startPage();
        List<ScTaskVolunteer> list = scTaskVolunteerService.selectScTaskVolunteerList(scTaskVolunteer);
        return getDataTable(list);
    }

    /**
     * 导出志愿者任务表列表
     */
    @RequiresPermissions("sc:taskVolunteer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScTaskVolunteer scTaskVolunteer) {
        List<ScTaskVolunteer> list = scTaskVolunteerService.selectScTaskVolunteerList(scTaskVolunteer);
        ExcelUtil<ScTaskVolunteer> util = new ExcelUtil<ScTaskVolunteer>(ScTaskVolunteer.class);
        return util.exportExcel(list, "TaskVolunteer");
    }

    /**
     * 新增志愿者任务表
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存志愿者任务表
     */
    @RequiresPermissions("sc:taskVolunteer:add")
    @Log(title = "志愿者任务表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScTaskVolunteer scTaskVolunteer) {
        return toAjax(scTaskVolunteerService.insertScTaskVolunteer(scTaskVolunteer));
    }

    /**
     * 修改志愿者任务表
     */
    @GetMapping("/edit/{tvId}")
    public String edit(@PathVariable("tvId") Long tvId, ModelMap mmap, HttpServletRequest request) {
        // 获取志愿者任务详情
        ScTaskVolunteer scTaskVolunteer = scTaskVolunteerService.selectScTaskVolunteerById(tvId);
        // 获取任务图片
        String basePath = getBasePath();
        ScParkrent scParkrent = scParkrentService.selectScParkrentFileById(scTaskVolunteer.getTaskId(), basePath);

        // 获取志愿者照片
        String pathUrl = "".equals(scTaskVolunteer.getVolunteer().getVolunteerAvatar()) ? ""
                : basePath + scTaskVolunteer.getVolunteer().getVolunteerAvatar();
        mmap.put("scTaskVolunteer", scTaskVolunteer);
        mmap.put("scFileInfoList", scParkrent.getFiles());
        mmap.put("sysFilePath", pathUrl);
        return prefix + "/edit";
    }

    /**
     * 修改保存志愿者任务表
     */
    @RequiresPermissions("sc:taskVolunteer:edit")
    @Log(title = "志愿者任务表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScTaskVolunteer scTaskVolunteer) {
        return toAjax(scTaskVolunteerService.updateScTaskVolunteer(scTaskVolunteer));
    }

    /**
     * 修改保存志愿者注册
     */
    @RequiresPermissions("sc:register:edit")
    @Log(title = "管理员审核志愿者报名信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/audit")
    @ResponseBody
    public AjaxResult editAuditSave(ScTaskVolunteer scTaskVolunteer) {
        scTaskVolunteer.setAuditState("1");
        return toAjax(scTaskVolunteerService.updateScTaskVolunteer(scTaskVolunteer), scTaskVolunteer);
    }

    /**
     * 修改志愿者任务表
     */
    @GetMapping("/audit/detail/{tvId}")
    public String auditDetail(@PathVariable("tvId") Long tvId, ModelMap mmap, HttpServletRequest request) {
        // 获取志愿者任务详情
        ScTaskVolunteer scTaskVolunteer = scTaskVolunteerService.selectScTaskVolunteerById(tvId);
        String basePath = getBasePath();
        // 获取任务图片
        ScParkrent scParkrent = scParkrentService.selectScParkrentFileById(scTaskVolunteer.getTaskId(), basePath);

        // 获取志愿者照片
        String pathUrl = "".equals(scTaskVolunteer.getVolunteer().getVolunteerAvatar()) ? ""
                : basePath + scTaskVolunteer.getVolunteer().getVolunteerAvatar();
        mmap.put("scTaskVolunteer", scTaskVolunteer);
        mmap.put("scFileInfoList", scParkrent.getFiles());
        mmap.put("sysFilePath", pathUrl);
        return prefix + "/audit";
    }

    /**
     * 删除志愿者任务表
     */
    @RequiresPermissions("sc:taskVolunteer:remove")
    @Log(title = "志愿者任务表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scTaskVolunteerService.deleteScTaskVolunteerByIds(ids));
    }

    /**
     * 移动端注册职员制
     */
    @ApiOperation("志愿者报名任务提交")
    @ApiImplicitParams({ @ApiImplicitParam(name = "parkrent_id", value = "任务id", required = true),
            @ApiImplicitParam(name = "volunteer_id", value = "志愿者id", required = true) })
    @PostMapping(value = "/api/add")
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "parkrent_id", required = true) Long parkrent_id,
            @RequestParam(value = "volunteer_id", required = true) Long volunteer_id) {
        ScTaskVolunteer scTaskVolunteer = new ScTaskVolunteer(uidWorker.getNextId(), parkrent_id, volunteer_id, "0");
        ScParkrent scParkrent = scParkrentService.selectScParkrentById(parkrent_id);
        ScVolunteer volunteer = scVolunteerService.selectScVolunteerById(volunteer_id);
        ScTaskVolunteer scTaskVolunteer2 = scTaskVolunteerService.checkTaskVolunteerUnique(scTaskVolunteer);
        ScRegister scRegister = scRegisterService.selectScRegisterById(volunteer_id);
        if (scRegister == null) {
            return AjaxResult.error("报名失败" + "," + "该志愿者未注册!");
        } else if (scRegister.getAuditState().equals("0")) {
            return AjaxResult.error("报名失败" + "," + "该志愿者注册未审核!");
        } else if (scRegister.getAuditPassState().equals("1")) {
            return AjaxResult.error("报名失败" + "," + "该志愿者注册审核未通过!");
        }

        if (scParkrent == null) {
            return AjaxResult.error("报名失败" + "," + "该任务已失效!");
        } else if (scParkrent.getAuditState().equals("0")) {
            return AjaxResult.error("报名失败" + "," + "该任务未审核!");
        } else if (scParkrent.getAuditPassState().equals("1")) {
            return AjaxResult.error("报名失败" + "," + "该任务审核未通过!");
        }

        if (volunteer == null) {
            return AjaxResult.error("报名失败" + "," + "该志愿者已失效!");
        }
        if (scTaskVolunteer2 != null) {
            String msg = "";
            if ("0".equals(scTaskVolunteer2.getAuditState())) {
                msg = "该报名信息待审核,请等待!";
            } else {
                msg = "该报名信息审核已通过,请勿重复提交!";
            }
            return AjaxResult.error("报名失败" + "," + msg);
        }
        // 插入关系表
        scTaskVolunteerService.insertScTaskVolunteer(scTaskVolunteer);
        AjaxResult result = AjaxResult.success("报名成功,请等待审核");
        return result;
    }

    /**
     * 志愿者获取个人任务列表
     */
    @ApiOperation("志愿者获取个人任务列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true),
            @ApiImplicitParam(name = "volunteer_id", value = "志愿者id", required = false) })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            @RequestParam(name = "volunteer_id", required = false) Long volunteer_id, HttpServletRequest request) {
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        ScTaskVolunteer scTaskVolunteer = new ScTaskVolunteer();
        scTaskVolunteer.setVolunteerId(volunteer_id);
        scTaskVolunteer.setAuditState("1");
        List<Map<String, Object>> dataList = scTaskVolunteerService.screenScTaskVolunteerList(scTaskVolunteer);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }

}