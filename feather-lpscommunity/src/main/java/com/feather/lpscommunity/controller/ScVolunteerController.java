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
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScRegister;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.service.IScRegisterService;
import com.feather.lpscommunity.service.IScTaskVolunteerService;
import com.feather.lpscommunity.service.IScVolunteerService;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.service.IPrdAttachmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 志愿者信息Controller
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Api(tags = { "3-志愿者服务,1-红马甲,2-红马甲列表" })
@Controller
@RequestMapping("/sc/volunteer")
public class ScVolunteerController extends BaseController {
    private String prefix = "lpscommunity/volunteer";

    @Autowired
    private IScVolunteerService scVolunteerService;

    @Autowired
    private IScRegisterService scRegisterService;
    @Autowired
    private IScTaskVolunteerService scTaskVolunteerService;

    @Autowired
    private IPrdAttachmentService prdAttachmentService;

    @RequiresPermissions("sc:volunteer:view")
    @GetMapping()
    public String volunteer() {
        return prefix + "/volunteer";
    }

    /**
     * 查询志愿者信息列表
     */
    @RequiresPermissions("sc:volunteer:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScVolunteer scVolunteer) {
        startPage();
        List<ScVolunteer> list = scVolunteerService.selectScVolunteerList(scVolunteer);
        return getDataTable(list);
    }

    /**
     * 导出志愿者信息列表
     */
    @RequiresPermissions("sc:volunteer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScVolunteer scVolunteer) {
        List<ScVolunteer> list = scVolunteerService.selectScVolunteerList(scVolunteer);
        ExcelUtil<ScVolunteer> util = new ExcelUtil<ScVolunteer>(ScVolunteer.class);
        return util.exportExcel(list, "volunteer");
    }

    /**
     * 新增志愿者信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存志愿者信息
     */
    @RequiresPermissions("sc:volunteer:add")
    @Log(title = "志愿者信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScVolunteer scVolunteer) {
        return toAjax(scVolunteerService.insertScVolunteer(scVolunteer));
    }

    /**
     * 修改志愿者信息
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        ScVolunteer scVolunteer = scVolunteerService.selectScVolunteerById(userId);
        mmap.put("scVolunteer", scVolunteer);
        return prefix + "/edit";
    }

    /**
     * 修改志愿者信息
     */
    @GetMapping("/detail/{userId}")
    public String detail(@PathVariable("userId") Long userId, ModelMap mmap, HttpServletRequest request) {
        ScVolunteer scVolunteer = scVolunteerService.selectScVolunteerById(userId);
        ScRegister scRegister = scRegisterService.selectScRegisterById(userId);
        PrdAttachment prdAttachment = prdAttachmentService.selectPrdAttachmentById(userId);
        String basePath = getBasePath();
        String pathUrl = "";
        if (prdAttachment != null) {
            pathUrl = StringUtils.isEmpty(prdAttachment.getFilePath()) ? "" : basePath + prdAttachment.getFilePath();
        }
        mmap.put("scVolunteer", scVolunteer);
        mmap.put("scRegister", scRegister);
        mmap.put("sysFilePath", pathUrl);
        return prefix + "/detail";
    }

    /**
     * 修改保存志愿者信息
     */
    @RequiresPermissions("sc:volunteer:edit")
    @Log(title = "志愿者信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScVolunteer scVolunteer) {
        return toAjax(scVolunteerService.updateScVolunteer(scVolunteer));
    }

    /**
     * 删除志愿者信息
     */
    @RequiresPermissions("sc:volunteer:remove")
    @Log(title = "志愿者信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (scTaskVolunteerService.checkScTaskVolunteerByVolunteerId(ids)) {
            return AjaxResult.error("删除失败！存在已报名的志愿者无法删除!");
        }
        return toAjax(scVolunteerService.deleteScVolunteerByIds(ids));
    }

    /**
     * 移动端获取志愿者注册详情
     */
    @ApiOperation("红马甲详情")
    @ApiImplicitParams({ @ApiImplicitParam(name = "volunteer_id", value = "志愿者id", required = true) })
    @RequestMapping(value = "/api/detail", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restDetail(@RequestParam(name = "volunteer_id", required = true) long volunteer_id,
            HttpServletRequest request) {
        String basePath = getBasePath();
        ScVolunteer scVolunteer = scVolunteerService.selectScVolunteerById(volunteer_id);
        scVolunteer.setVolunteerAvatar(
                "".equals(scVolunteer.getVolunteerAvatar()) ? "" : basePath + scVolunteer.getVolunteerAvatar());
        return AjaxResult.success(scVolunteer);
    }

    /**
     * 移动端获取志愿者列表
     */
    @ApiOperation("红马甲列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true) })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize, HttpServletRequest request) {
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        String basePath = getBasePath();
        List<Map<String, Object>> dataList = scVolunteerService.screenScVolunteerList(new ScVolunteer(), basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }
}