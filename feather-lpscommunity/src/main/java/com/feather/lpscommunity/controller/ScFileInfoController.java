package com.feather.lpscommunity.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.service.IScFileInfoService;

/**
 * 智慧社区实体多图Controller
 *
 * @author dufan
 * @date 2019-10-17
 */
@Controller
@RequestMapping("/sc/fileInfo")
public class ScFileInfoController extends BaseController {
    private String prefix = "lpscommunity/fileInfo";

    @Autowired
    private IScFileInfoService scFileInfoService;

    @RequiresPermissions("sc:fileInfo:view")
    @GetMapping()
    public String fileInfo() {
        return prefix + "/fileInfo";
    }

    /**
     * 查询智慧社区实体多图列表
     */
    @RequiresPermissions("sc:fileInfo:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScFileInfo scFileInfo) {
        startPage();
        List<ScFileInfo> list = scFileInfoService.selectScFileInfoList(scFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出智慧社区实体多图列表
     */
    @RequiresPermissions("sc:fileInfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScFileInfo scFileInfo) {
        List<ScFileInfo> list = scFileInfoService.selectScFileInfoList(scFileInfo);
        ExcelUtil<ScFileInfo> util = new ExcelUtil<ScFileInfo>(ScFileInfo.class);
        return util.exportExcel(list, "fileInfo");
    }

    /**
     * 新增智慧社区实体单图
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("sc:fileInfo:add")
    @Log(title = "实体单图", businessType = BusinessType.INSERT)
    @PostMapping("/add/single")
    @ResponseBody
    public AjaxResult addSingleSave(@RequestParam("files") MultipartFile multipartFile,
            @RequestParam("file_code") String code, @RequestParam("file_target") Long target) throws Exception {
        return toAjax(scFileInfoService.insertScFileInfo(multipartFile, code, target));
    }

    /**
     * 新增保存实体多图
     * 
     * @throws Exception
     */
    @RequiresPermissions("sc:fileInfo:add")
    @Log(title = "实体多图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("files") MultipartFile[] files, @RequestParam("file_code") String code,
            @RequestParam("file_target") Long target) throws Exception {
        return toAjax(scFileInfoService.insertScFileInfo(files, code, target));
    }

    /**
     * 修改智慧社区实体多图
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap) {
        ScFileInfo scFileInfo = scFileInfoService.selectScFileInfoById(fileId);
        mmap.put("scFileInfo", scFileInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存智慧社区实体多图
     */
    @RequiresPermissions("sc:fileInfo:edit")
    @Log(title = "智慧社区实体多图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScFileInfo scFileInfo) {
        return toAjax(scFileInfoService.updateScFileInfo(scFileInfo));
    }

    /**
     * 删除智慧社区实体多图
     */
    @RequiresPermissions("sc:fileInfo:remove")
    @Log(title = "智慧社区实体多图", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scFileInfoService.deleteScFileInfoByIds(ids));
    }
}
