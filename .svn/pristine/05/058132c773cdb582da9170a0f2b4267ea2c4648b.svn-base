package com.feather.prd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.feather.common.core.domain.UrlSeries;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.domain.PrdAttachment.AttachmentInfo;
import com.feather.prd.service.IPrdAttachmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 文件信息Controller
 * 
 * @author flogyin
 * @date 2019-09-26
 */
@Api(tags = "附件信息")
@Controller
@RequestMapping("/prd/attachment")
public class PrdAttachmentController extends BaseController {
    private String prefix = "prd/attachment";

    @Autowired
    private IPrdAttachmentService prdAttachmentService;

    @RequiresPermissions("prd:attachment:view")
    @GetMapping()
    public String attachment() {
        return prefix + "/attachment";
    }

    /**
     * 查询文件信息列表
     */
    @RequiresPermissions("prd:attachment:list")
    @ClearPage
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PrdAttachment prdAttachment) {
        startPage();
        List<PrdAttachment> list = prdAttachmentService.selectPrdAttachmentList(prdAttachment);
        return getDataTable(list);
    }

    /**
     * 新增文件信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件信息
     */
    @RequiresPermissions("prd:attachment:add")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PrdAttachment prdAttachment) {
        return toAjax(prdAttachmentService.insertPrdAttachment(prdAttachment));
    }

    /**
     * 修改文件信息
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap) {
        PrdAttachment prdAttachment = prdAttachmentService.selectPrdAttachmentById(fileId);
        mmap.put("prdAttachment", prdAttachment);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件信息
     */
    @RequiresPermissions("prd:attachment:edit")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PrdAttachment prdAttachment) {
        // 重新计算新地址文件大小

        File file = FileUploadUtils.getFileByUrl(prdAttachment.getFilePath(), true);
        if (file != null) {
            long size = file.length();
            prdAttachment.setFileSize(size);
        }
        return toAjax(prdAttachmentService.updatePrdAttachment(prdAttachment));
    }

    /**
     * 删除文件信息
     */
    @RequiresPermissions("prd:attachment:remove")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(prdAttachmentService.deletePrdAttachmentByIds(ids));
    }

    /**
     * 根据author获取附件
     */
    @Log(title = "根据author获取附件", businessType = BusinessType.API)
    @ApiOperation("根据author获取附件")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "author", value = "作者", required = true) //
    })
    @GetMapping("/api/list")
    @ClearPage
    @ResponseBody
    public AjaxResult listAttachmentByAuthor(@RequestParam(name = "author") String author) {
        Page<PrdAttachment> page = startPage();
        PrdAttachment prdAttachment = new PrdAttachment();
        prdAttachment.setCreateBy(author);
        List<PrdAttachment> list = null;
        if (StringUtils.isNotEmpty(author)) {
            list = prdAttachmentService.selectPrdAttachmentList(prdAttachment);
        } else {
            list = new ArrayList<>();
        }
        PageInfo<PrdAttachment> pageInfo = page.toPageInfo();
        pageInfo.setList(list);
        return AjaxResult.success(pageInfo);
    }

    /**
     * 上传文件
     */
    @Log(title = "上传文件", businessType = BusinessType.UPLOAD)
    @ApiOperation("上传文件")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "file", value = "文件附件", required = true), //
            @ApiImplicitParam(name = "thumb", value = "是否生成缩略图", required = false) //
    })
    @PostMapping("/api/upload")
    @ResponseBody
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile[] files,
            @RequestParam(name = "thumb", required = false) Boolean thumb) throws Exception {
        if (files != null) {
            List<AttachmentInfo> list = null;
            try {
                list = prdAttachmentService.upload(files, thumb, false);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return AjaxResult.error(e.getMessage(), list);
            }
            return AjaxResult.success(list);
        }
        return AjaxResult.error("没有找到文件");
    }

    /**
     * 上传附件
     */
    @Log(title = "上传附件", businessType = BusinessType.UPLOAD)
    @ApiOperation("上传附件")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "file", value = "文件附件", required = true), //
            @ApiImplicitParam(name = "thumb", value = "是否生成缩略图", required = false) //
    })
    @PostMapping("/api/uploadAttachment")
    @ResponseBody
    public AjaxResult uploadAttachment(@RequestParam("file") MultipartFile[] files,
            @RequestParam(name = "thumb", required = false) Boolean thumb) throws Exception {
        if (files != null) {
            List<AttachmentInfo> list = null;
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

    /**
     * 上传链接
     */
    @Log(title = "上传链接", businessType = BusinessType.UPLOAD)
    @ApiOperation("上传链接")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "link", value = "链接地址", required = true), //
            @ApiImplicitParam(name = "ext", value = "文件后缀", required = false) //
    })
    @GetMapping("/api/uploadLink")
    @ResponseBody
    public AjaxResult uploadLink(@RequestParam("link") String link,
            @RequestParam(name = "ext", required = false) String extension) throws Exception {
        try {
            AttachmentInfo info = prdAttachmentService.upload(link, extension, false);
            return AjaxResult.success(info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 上传链接附件
     */
    @Log(title = "上传链接附件", businessType = BusinessType.UPLOAD)
    @ApiOperation("上传链接附件")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "link", value = "链接地址", required = true), //
            @ApiImplicitParam(name = "ext", value = "文件后缀", required = false) //
    })
    @GetMapping("/api/uploadLinkAttachment")
    @ResponseBody
    public AjaxResult uploadLinkAttachment(@RequestParam("link") String link,
            @RequestParam(name = "ext", required = false) String extension) throws Exception {
        try {
            AttachmentInfo info = prdAttachmentService.upload(link, extension, false);
            return AjaxResult.success(info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据url获取系列地址（对应的缩略图地址或原始地址）
     */
    @ApiOperation("根据url获取系列地址（对应的缩略图地址或原始地址）")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "url", value = "url地址", required = true) //
    })
    @GetMapping("/api/series")
    @ResponseBody
    public AjaxResult getFileSeries(@RequestParam("url") String url, HttpServletRequest request) throws Exception {
        String basePath = getBasePath();
        UrlSeries urlSeries = prdAttachmentService.getFileSeries(url, basePath);
        return AjaxResult.success(urlSeries);
    }
}