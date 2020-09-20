package com.feather.lpscommunity.controller;

import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScRegister;
import com.feather.lpscommunity.service.IScRegisterService;
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
 * 志愿者注册Controller
 *
 * @author fancy
 * @date 2019-11-14
 */
@Api(tags = { "3-志愿者服务,1-红马甲,1-志愿者注册" })
@Controller
@RequestMapping("/sc/register")
public class ScRegisterController extends BaseController {
    private String prefix = "lpscommunity/register";

    @Autowired
    private IScRegisterService scRegisterService;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private IPrdAttachmentService prdAttachmentService;

    @RequiresPermissions("sc:register:view")
    @GetMapping()
    public String register() {

        return prefix + "/register";
    }

    /**
     * 查询志愿者注册列表
     */
    @RequiresPermissions("sc:register:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScRegister scRegister) {
        startPage();
        List<ScRegister> list = scRegisterService.selectScRegisterList(scRegister);
        return getDataTable(list);
    }

    /**
     * 导出志愿者注册列表
     */
    @RequiresPermissions("sc:register:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScRegister scRegister) {
        List<ScRegister> list = scRegisterService.selectScRegisterList(scRegister);
        ExcelUtil<ScRegister> util = new ExcelUtil<ScRegister>(ScRegister.class);
        return util.exportExcel(list, "register");
    }

    /**
     * 新增志愿者注册
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存志愿者注册
     */
    @RequiresPermissions("sc:register:add")
    @Log(title = "志愿者注册", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScRegister scRegister) {
        return toAjax(scRegisterService.insertScRegister(scRegister));
    }

    /**
     * 修改志愿者注册
     */
    @GetMapping("/edit/{registerId}")
    public String edit(@PathVariable("registerId") Long registerId, ModelMap mmap, HttpServletRequest request) {
        ScRegister scRegister = scRegisterService.selectScRegisterById(registerId);
        PrdAttachment prdAttachment = prdAttachmentService.selectPrdAttachmentById(registerId);
        String basePath = getBasePath();
        String pathUrl = "";
        if (prdAttachment != null) {
            pathUrl = StringUtils.isEmpty(prdAttachment.getFilePath()) ? "" : basePath + prdAttachment.getFilePath();
        }
        mmap.put("scRegister", scRegister);
        mmap.put("sysFilePath", pathUrl);
        return prefix + "/edit";
    }

    /**
     * 修改保存志愿者注册
     */
    @RequiresPermissions("sc:register:edit")
    @Log(title = "志愿者注册", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScRegister scRegister) {
        return toAjax(scRegisterService.updateScRegister(scRegister));
    }

    /**
     * 修改保存志愿者注册
     */
    @RequiresPermissions("sc:register:edit")
    @Log(title = "管理员审核志愿者注册信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/audit")
    @ResponseBody
    public AjaxResult editAuditSave(ScRegister scRegister) {
        scRegister.setAuditTime(new Date());
        scRegister.setAuditState("1");// 修改审核状态（已审核）

        return toAjax(scRegisterService.editScRegisterAudit(scRegister), scRegister);
    }

    /**
     * 显示审批详情
     */
    @GetMapping("/audit/detail/{registerId}")
    public String auditDetail(@PathVariable("registerId") Long registerId, ModelMap mmap, HttpServletRequest request) {
        ScRegister scRegister = scRegisterService.selectScRegisterById(registerId);
        PrdAttachment prdAttachment = prdAttachmentService.selectPrdAttachmentById(registerId);
        String basePath = getBasePath();
        String pathUrl = "";
        if (prdAttachment != null) {
            pathUrl = StringUtils.isEmpty(prdAttachment.getFilePath()) ? "" : basePath + prdAttachment.getFilePath();
        }
        mmap.put("scRegister", scRegister);
        mmap.put("sysFilePath", pathUrl);
        return prefix + "/audit";
    }

    /**
     * 删除志愿者注册
     */
    @RequiresPermissions("sc:register:remove")
    @Log(title = "志愿者注册", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) throws Exception {
        // 删除志愿者注册
        return toAjax(scRegisterService.deleteScRegisterByIds(ids));
    }

    /**
     * 移动端注册职员制
     */
    @ApiOperation("注册志愿者")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "register_name", value = "姓名", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_sex", value = "性别", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_bir", value = "出生日期", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_idcard", value = "身份证", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_address", value = "家庭住址", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_profession", value = "职业", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_synopsis", value = "个人简介", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "register_tel", value = "手机号(必填，作为账号)", required = true, dataType = "String"), //
            @ApiImplicitParam(name = "equipment_id", value = "设备id", required = true, dataType = "String"), //
            @ApiImplicitParam(name = "headsculpture", value = "头像", required = false, dataType = "MultipartFile") //
    })
    @PostMapping(value = "/api/add")
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "register_name", required = true) String register_name,
            @RequestParam(value = "register_sex", required = false) String register_sex,
            @RequestParam(value = "register_bir", required = false) String register_bir,
            @RequestParam(value = "register_idcard", required = false) String register_idcard,
            @RequestParam(value = "register_address", required = false) String register_address,
            @RequestParam(value = "register_profession", required = false) String register_profession,
            @RequestParam(value = "register_synopsis", required = false) String register_synopsis,
            @RequestParam(value = "register_tel", required = true) String register_tel,
            @RequestParam(value = "equipment_id", required = true) String equipmentId,
            @RequestParam(value = "headsculpture", required = false) MultipartFile headsculpture) {
        ScRegister scRegister = new ScRegister(uidWorker.getNextId(), register_name, register_tel, register_sex,
                register_bir, register_idcard, register_address, register_profession, register_synopsis, equipmentId,
                "0", "0");
        ScRegister scRegister2 = scRegisterService.checkAccountUnique(scRegister);
        if (scRegister2 != null) {
            String msg = "";
            if ("0".equals(scRegister2.getAuditState())) {
                msg = "该账号待审核,请等待!";
            } else {
                msg = "该账户审核已通过,请勿重复提交!";
            }
            return AjaxResult.error("申请失败，" + msg);
        }

        // 存入注册表
        try {
            scRegisterService.insertScRegister(scRegister, headsculpture);
        } catch (Exception e) {
            return AjaxResult.error("出错了！");
        }

        AjaxResult result = AjaxResult.success("提交成功，请等待审核!");
        return result;
    }

    /**
     * 移动端获取志愿者注册详情
     */
    @ApiOperation("获取志愿者信息详情（已审核的禁止修改）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "register_id", value = "注册内容id", required = true) })
    @RequestMapping(value = "/api/detail", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restDetail(@RequestParam(name = "register_id", required = true) long register_id,
            HttpServletRequest request) {
        String basePath = getBasePath();
        ScRegister scRegister = scRegisterService.selectScRegisterById(register_id);
        PrdAttachment prdAttachment = prdAttachmentService.selectPrdAttachmentById(scRegister.getRegisterId());
        scRegister.setHeadsculpture(prdAttachment != null ? basePath + prdAttachment.getFilePath() : "");
        return AjaxResult.success(scRegister);
    }

    /**
     * 移动端获取志愿者列表
     */
    @ApiOperation("获取志愿者注册列表")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true), //
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true), //
            @ApiImplicitParam(name = "equipment_id", value = "设备id", required = false), //
            @ApiImplicitParam(name = "audit_state", value = "审核状态", required = false), //
            @ApiImplicitParam(name = "audit_pass_state", value = "审核通过状态", required = false) //
    })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            @RequestParam(name = "audit_pass_state", required = false) String auditPassState,
            @RequestParam(name = "audit_state", required = false) String auditState,
            @RequestParam(name = "equipment_id", required = false) String equipmentId, HttpServletRequest request) {
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        String basePath = getBasePath();
        List<Map<String, Object>> dataList = scRegisterService
                .screenScRegisterList(new ScRegister(equipmentId, auditState, auditPassState), basePath);

        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }
}