package com.feather.prd.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.feather.common.config.Global;
import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.ZXingQrCode;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.prd.domain.PrdAppversion;
import com.feather.prd.domain.PrdAttachment;
import com.feather.prd.service.IPrdAppversionService;
import com.github.pagehelper.PageHelper;
import com.google.zxing.WriterException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * App版本Controller
 * 
 * @author flogyin
 * @date 2019-09-25
 */
@Api(tags = "App版本管理")
@Controller
@RequestMapping("/prd/appversion")
public class PrdAppversionController extends BaseController {
    private String prefix = "prd/appversion";

    @Autowired
    private IPrdAppversionService prdAppversionService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("prd:appversion:view")
    @GetMapping()
    public String version() {
        return prefix + "/version";
    }

    /**
     * 查询App版本列表
     */
    @RequiresPermissions("prd:appversion:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(PrdAppversion prdAppversion) {
        startPage();
        List<PrdAppversion> list = prdAppversionService.selectPrdAppversionList(prdAppversion);
        return getDataTable(list);
    }

    /**
     * 查询App版本详情
     */
    @RequiresPermissions("prd:appversion:list")
    @GetMapping("/detail/{versionId}")
    public String detail(@PathVariable("versionId") Long versionId, ModelMap mmap, HttpServletRequest request) {
        PrdAppversion prdAppversion = prdAppversionService.selectPrdAppversionById(versionId);
        // ZXingQrCode zXingQrCode = createQrCode(prdAppversion, request);
        // prdAppversion.setQrcodeImage(zXingQrCode.createBase64());
        mmap.put("appVersion", prdAppversion);
        return prefix + "/detail";
    }

    /**
     * 新增App版本
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存App版本
     * 
     * @throws IOException
     */
    @RequiresPermissions("prd:appversion:add")
    @Log(title = "App版本", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("file_app") MultipartFile appFile,
            @RequestParam(name = "file_icon", required = false) MultipartFile iconFile, PrdAppversion prdAppversion)
            throws Exception {
        PrdAttachment appAttachment = new PrdAttachment();
        appAttachment.setFileId(uidWorker.getNextId());
        appAttachment.setFileName(appFile.getOriginalFilename());
        appAttachment.setFilePath(FileUploadUtils.upload(appFile, null, false));
        appAttachment.setFileSize(appFile.getSize());

        PrdAttachment iconAttachment = null;
        if (iconFile != null) {
            iconAttachment = new PrdAttachment();
            iconAttachment.setFileId(uidWorker.getNextId());
            iconAttachment.setFileName(iconFile.getOriginalFilename());
            iconAttachment.setFilePath(FileUploadUtils.upload(iconFile, null, false));
            iconAttachment.setFileSize(iconFile.getSize());
        }

        prdAppversion.setVersionId(uidWorker.getNextId());
        prdAppversion.setAppFile(appAttachment.getFileId());
        prdAppversion.setAppAttachment(appAttachment);
        prdAppversion.setIconFile(iconAttachment.getFileId());
        prdAppversion.setIconAttachment(iconAttachment);

        return toAjax(prdAppversionService.insertPrdAppversion(prdAppversion));
    }

    /**
     * 删除App版本
     */
    @RequiresPermissions("prd:appversion:remove")
    @Log(title = "App版本", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(prdAppversionService.deletePrdAppversionByIds(ids));
    }

    /**
     * 生成App版本二维码
     * 
     * @throws WriterException
     * @throws IOException
     */
    @RequiresPermissions("prd:appversion:list")
    @GetMapping("/qrcode/{versionId}")
    public void qrcode(@PathVariable("versionId") Long versionId, HttpServletRequest request,
            HttpServletResponse response) throws IOException, WriterException {
        PrdAppversion prdAppversion = prdAppversionService.selectPrdAppversionById(versionId);
        ZXingQrCode zXingQrCode = createQrCode(prdAppversion, request);
        zXingQrCode.create(response.getOutputStream());
    }

    /**
     * 下载app
     */
    @GetMapping("/api/download")
    public String download(@RequestParam(name = "package") String packageName, ModelMap mmap) {
        mmap.put("package", packageName);
        return prefix + "/app/download";
    }

    /**
     * 下载安卓app
     */
    @GetMapping("/api/download/android")
    @ClearPage
    public String downloadAandroid(@RequestParam(name = "package") String packageName, ModelMap mmap,
            HttpServletRequest request) {
        getLast(mmap, packageName);
        return prefix + "/app/android";
    }

    /**
     * 获取最新版本
     */
    @Log(title = "获取最新版本", businessType = BusinessType.API)
    @ApiOperation("获取最新版本")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "package", value = "包名", required = true) //
    })
    @GetMapping("/api/last")
    @ClearPage
    @ResponseBody
    public AjaxResult last(@RequestParam(name = "package") String packageName, HttpServletRequest request) {
        ModelMap data = new ModelMap();
        getLast(data, packageName);
        return AjaxResult.success(data);
    }

    private ZXingQrCode createQrCode(PrdAppversion prdAppversion, HttpServletRequest request) {
        String basePath = getBasePath();
        String url = basePath + "/" + prefix + "/api/download?package=" + prdAppversion.getVersionPackage();

        ZXingQrCode zXingQrCode = new ZXingQrCode();
        zXingQrCode.setContent(url);
        // zXingQrCode.setLabel(prdAppversion.getVersionName());
        String iconImagePath = prdAppversion.getIconImagePath();
        if (iconImagePath != null && iconImagePath.length() > 0) {
            // 本地资源路径
            String localPath = Global.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(iconImagePath, Constants.RESOURCE_PREFIX);
            zXingQrCode.setLogo(new File(downloadPath));
        }
        return zXingQrCode;
    }

    private void getLast(ModelMap data, String packageName) {
        if (packageName != null && packageName.length() > 0) {
            PageHelper.startPage(1, 1, "version_number desc");
            List<PrdAppversion> list = prdAppversionService.selectPrdAppversionLastByPackage(packageName);
            if (list != null && list.size() > 0) {
                String basePath = getBasePath();

                PrdAppversion one = list.get(0);
                PrdAttachment appFile = one.getAppAttachment();
                PrdAttachment iconFile = one.getIconAttachment();
                data.put("time", one.getCreateTime());
                data.put("name", one.getVersionName());
                data.put("number", one.getVersionNumber());
                data.put("file", (appFile != null ? (basePath + appFile.getFilePath()) : ""));
                data.put("icon", (iconFile != null ? (basePath + iconFile.getFilePath()) : ""));
                data.put("size", (appFile != null ? appFile.getFileSize() : 0));
                data.put("sizeStr", (appFile != null ? appFile.getSizeStr() : ""));
                data.put("special", one.getVersionSpecial());
                data.put("remark", one.getRemark());
            }
        }
    }
}