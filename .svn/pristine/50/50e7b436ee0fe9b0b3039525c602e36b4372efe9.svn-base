package com.feather.cms.controller;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.cms.domain.CmsFile;
import com.feather.cms.service.ICmsContentService;
import com.feather.cms.service.ICmsFileService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 文件Controller
 */
@Controller
@RequestMapping("/cms/file")
public class CmsFileController extends BaseController {
    private String prefix = "cms/file";

    @Autowired
    private ICmsFileService cmsFileService;

    @Autowired
    private ICmsContentService cmsContentService;

    @RequiresPermissions("cms:file:view")
    @GetMapping()
    public String file() throws IOException {
        // CmsContent cc = new CmsContent();
        // cc.setFileId("123");
        // cc.setFileContent(FileUtils.readFileToByteArray(new
        // File("C:/J1OROU9D_693826277.jpg")));
        // cmsContentService.insertCmsContent(cc);
        return prefix + "/file";
    }

    /**
     * 查询文件列表
     */
    @RequiresPermissions("cms:file:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(CmsFile cmsFile) {
        startPage();
        List<CmsFile> list = cmsFileService.selectCmsFileList(cmsFile);
        return getDataTable(list);
    }

    /**
     * 导出文件列表
     */
    @RequiresPermissions("cms:file:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsFile cmsFile) {
        List<CmsFile> list = cmsFileService.selectCmsFileList(cmsFile);
        ExcelUtil<CmsFile> util = new ExcelUtil<CmsFile>(CmsFile.class);
        return util.exportExcel(list, "file");
    }

    /**
     * 新增文件
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        CmsFile file = new CmsFile();
        mmap.put("file", file);
        return prefix + "/edit";
    }

    /**
     * 新增保存文件
     */
    @RequiresPermissions("cms:file:add")
    @Log(title = "文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsFile cmsFile) {
        return toAjax(cmsFileService.insertCmsFile(cmsFile));
    }

    /**
     * 修改文件
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") String fileId, ModelMap mmap) {
        CmsFile file = cmsFileService.selectCmsFileById(fileId);
        mmap.put("file", file);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件
     */
    @RequiresPermissions("cms:file:edit")
    @Log(title = "文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsFile cmsFile) {
        return toAjax(cmsFileService.updateCmsFile(cmsFile));
    }

    /**
     * 删除文件
     */
    @RequiresPermissions("cms:file:remove")
    @Log(title = "文件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cmsFileService.deleteCmsFileByIds(ids));
    }
}
