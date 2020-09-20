package com.feather.aupipes.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

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

import com.feather.aupipes.domain.AupPlansManage;
import com.feather.aupipes.service.IAupPlansManageService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.Global;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 预案管理Controller
 *
 * @author niecheng
 * @date 2019-12-30
 */
@Controller
@RequestMapping("/aupipes/plansManage")
public class AupPlansManageController extends BaseController {
    private String prefix = "aupipes/plansManage";

    @Autowired
    private IAupPlansManageService aupPlansManageService;

    @RequiresPermissions("aupipes:plansManage:view")
    @GetMapping()
    public String manage() {
        return prefix + "/plansManage";
    }

    /**
     * 导出预案管理列表
     */
    @RequiresPermissions("aupipes:plansManage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupPlansManage aupPlansManage) {
        List<AupPlansManage> list = aupPlansManageService.selectAupPlansManageList(aupPlansManage);
        ExcelUtil<AupPlansManage> util = new ExcelUtil<AupPlansManage>(AupPlansManage.class);
        return util.exportExcel(list, "manage");
    }

    /**
     * 查询预案管理信息列表
     */
    @RequiresPermissions("aupipes:plansManage:list")
    @RequestMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupPlansManage aupPlansManage) {
        startPage().setOrderBy("id");
        List<AupPlansManage> list = aupPlansManageService.selectAupPlansManageList(aupPlansManage);
        return getDataTable(list);
    }

    /**
     * 新增房屋信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预案管理
     */
    @RequiresPermissions("aupipes:plansManage:add")
    @Log(title = "预案管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupPlansManage aupPlansManage,
            @RequestParam(value = "file", required = false) MultipartFile file, String uploadUrl) {
        aupPlansManage.setFileurl(uploadUrl);
        return toAjax(aupPlansManageService.insertAupPlansManage(aupPlansManage));
    }

    /**
     * 修改预案管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupPlansManage aupPlansManage = aupPlansManageService.selectAupPlansManageById(id);
        mmap.put("aupPlansManage", aupPlansManage);
        return prefix + "/edit";
    }

    /**
     * 修改保存预案管理
     */
    @RequiresPermissions("aupipes:plansManage:edit")
    @Log(title = "预案管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupPlansManage aupPlansManage) {

        // aupPlansManage.setFileurl(myfile);
        return toAjax(aupPlansManageService.updateAupPlansManage(aupPlansManage));
    }

    /**
     * 删除预案管理
     */
    @RequiresPermissions("aupipes:plansManage:remove")
    @Log(title = "预案管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupPlansManageService.deleteAupPlansManageByIds(ids));
    }

    @RequestMapping("/initSelect")
    @ResponseBody
    public AjaxResult initSelect(String categoryparentid) {

        return aupPlansManageService.selectInitSelect(categoryparentid);
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public void uploadFile(MultipartFile file) throws IllegalStateException, IOException {
        // 原始图片名称
        String oldFileName = file.getOriginalFilename(); // 获取上传文件的原名
        // 存储路径
        String saveFilePath = Global.getDownloadPath() + "yaglFile";
        File fileDic = new File(saveFilePath);
        if (!fileDic.exists()) {
            fileDic.mkdirs();
        }
        // 新的图片名称
        String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
        // 新图片
        File newFile = new File(saveFilePath + "\\" + newFileName);
        // 将内存中的数据写入磁盘
        file.transferTo(newFile);
        // 将路径名存入全局变量myfile
        // String myfile = newFileName;
    }

    // 下载文件
    @RequestMapping("/downLoads")
    public void downloads(HttpServletResponse response, String fileUrl, String oldName) throws Exception {

        // 下载本地文件
        String fileName = fileUrl.toString(); // 文件的默认保存名
        oldName = oldName + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        oldName = URLEncoder.encode(oldName, "UTF-8");

        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + oldName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        // 读到流中
        // 文件的存放路径
        try (InputStream inStream = new FileInputStream(Global.getDownloadPath() + "yaglFile/" + fileName)) {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            throw e;
        }
    }

}
