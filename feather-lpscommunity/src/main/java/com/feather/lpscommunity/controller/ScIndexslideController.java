package com.feather.lpscommunity.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.lpscommunity.domain.ScIndexslide;
import com.feather.lpscommunity.service.IScIndexslideService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页轮播图Controller
 * 
 * @author flogyin
 * @date 2019-10-23
 */
@Api(tags = { "0-首页轮播图" })
@Controller
@RequestMapping("/sc/indexslide")
public class ScIndexslideController extends BaseController {
    private String prefix = "lpscommunity/indexslide";

    @Autowired
    private IScIndexslideService scIndexslideService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:indexslide:view")
    @GetMapping()
    public String indexslide() {
        return prefix + "/indexslide";
    }

    /**
     * 查询首页轮播图列表
     */
    @RequiresPermissions("sc:indexslide:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScIndexslide scIndexslide) {
        List<ScIndexslide> list = scIndexslideService.selectScIndexslideList(scIndexslide);
        return getDataTable(list);
    }

    /**
     * 新增首页轮播图
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存首页轮播图
     * 
     * @throws Exception
     */
    @RequiresPermissions("sc:indexslide:add")
    @Log(title = "首页轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("slide_pic") MultipartFile file, ScIndexslide scIndexslide)
            throws Exception {
        String path = FileUploadUtils.upload(file, null, false);
        scIndexslide.setSlidePath(path);
        scIndexslide.setSlideId(uidWorker.getNextId());
        return toAjax(scIndexslideService.insertScIndexslide(scIndexslide));
    }

    /**
     * 修改首页轮播图
     */
    @GetMapping("/edit/{slideId}")
    public String edit(@PathVariable("slideId") Long slideId, ModelMap mmap) {
        ScIndexslide scIndexslide = scIndexslideService.selectScIndexslideById(slideId);
        mmap.put("scIndexslide", scIndexslide);
        return prefix + "/edit";
    }

    /**
     * 修改保存首页轮播图
     */
    @RequiresPermissions("sc:indexslide:edit")
    @Log(title = "首页轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScIndexslide scIndexslide) {
        return toAjax(scIndexslideService.updateScIndexslide(scIndexslide));
    }

    /**
     * 删除首页轮播图
     */
    @RequiresPermissions("sc:indexslide:remove")
    @Log(title = "首页轮播图", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scIndexslideService.deleteScIndexslideByIds(ids));
    }

    /**
     * 获取首页轮播图接口
     */
    @ApiOperation("首页轮播图")
    @GetMapping(value = "/api/list")
    @ResponseBody
    public AjaxResult restList(HttpServletRequest request) {
        String basePath = getBasePath();
        List<ScIndexslide> list = scIndexslideService.selectScIndexslideAll();
        List<String> data = new ArrayList<>();
        for (ScIndexslide item : list) {
            data.add(basePath + item.getSlidePath());
        }
        return AjaxResult.success(data);
    }
}
