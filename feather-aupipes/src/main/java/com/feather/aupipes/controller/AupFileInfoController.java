package com.feather.aupipes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.feather.aupipes.domain.AupFileInfo;
import com.feather.aupipes.service.IAupFileInfoService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 华农文件Controller
 * 
 * @author fancy
 * @date 2020-04-04
 */
@Controller
@RequestMapping("/aupipes/info")
public class AupFileInfoController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AupFileInfoController.class);

    private String prefix = "aupipes/info";

    @Autowired
    private IAupFileInfoService aupFileInfoService;

    // @RequiresPermissions("aupipes:info:view")
    @GetMapping()
    public String info() {
        return prefix + "/info";
    }

    /**
     * 查询华农文件列表
     */
    // @RequiresPermissions("aupipes:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AupFileInfo aupFileInfo) {
        startPage();
        List<AupFileInfo> list = aupFileInfoService.selectAupFileInfoList(aupFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出华农文件列表
     */
    // @RequiresPermissions("aupipes:info:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupFileInfo aupFileInfo) {
        List<AupFileInfo> list = aupFileInfoService.selectAupFileInfoList(aupFileInfo);
        ExcelUtil<AupFileInfo> util = new ExcelUtil<AupFileInfo>(AupFileInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增保存华农文件
     */
    // @RequiresPermissions("aupipes:info:add")
    @Log(title = "华农文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("file") MultipartFile[] files, @RequestParam("file_code") String code) {
        if (files != null) {
            int i;
            try {
                i = aupFileInfoService.insertAupFileInfo(files, code);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return AjaxResult.error(e.getMessage());
            }
            return AjaxResult.success(i);
        }

        return AjaxResult.error("没有找到文件");
    }

    /**
     * 修改华农文件
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap) {
        AupFileInfo aupFileInfo = aupFileInfoService.selectAupFileInfoById(fileId);
        mmap.put("aupFileInfo", aupFileInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存华农文件
     */
    // @RequiresPermissions("aupipes:info:edit")
    @Log(title = "华农文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupFileInfo aupFileInfo) {
        return toAjax(aupFileInfoService.updateAupFileInfo(aupFileInfo));
    }

    /**
     * 删除
     */
    // @RequiresPermissions("aupipes:info:remove")
    @Log(title = "华农文件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupFileInfoService.deleteAupFileInfoByIds(ids));
    }
}
