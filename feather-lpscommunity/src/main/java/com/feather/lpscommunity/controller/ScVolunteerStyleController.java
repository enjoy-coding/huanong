package com.feather.lpscommunity.controller;

import java.util.HashMap;
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
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScVolunteerStyle;
import com.feather.lpscommunity.service.IScVolunteerStyleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 志愿者风采Controller
 * 
 * @author fancy
 * @date 2019-11-21
 */
@Api(tags = { "3-志愿者服务,3-风采林" })
@Controller
@RequestMapping("/sc/volunteerStyle")
public class ScVolunteerStyleController extends BaseController {
    private String prefix = "lpscommunity/volunteerStyle";

    @Autowired
    private IScVolunteerStyleService scVolunteerStyleService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:style:view")
    @GetMapping()
    public String style() {
        return prefix + "/style";
    }

    /**
     * 查询志愿者风采列表
     */
    @RequiresPermissions("sc:style:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScVolunteerStyle scVolunteerStyle) {
        startPage();
        List<ScVolunteerStyle> list = scVolunteerStyleService.selectScVolunteerStyleList(scVolunteerStyle);
        return getDataTable(list);
    }

    /**
     * 导出志愿者风采列表
     */
    @RequiresPermissions("sc:style:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScVolunteerStyle scVolunteerStyle) {
        List<ScVolunteerStyle> list = scVolunteerStyleService.selectScVolunteerStyleList(scVolunteerStyle);
        ExcelUtil<ScVolunteerStyle> util = new ExcelUtil<ScVolunteerStyle>(ScVolunteerStyle.class);
        return util.exportExcel(list, "style");
    }

    /**
     * 新增志愿者风采
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
        map.put("scVolunteerStyle", new ScVolunteerStyle());
        return prefix + "/edit";
    }

    /**
     * 新增保存志愿者风采
     */
    @RequiresPermissions("sc:style:add")
    @Log(title = "志愿者风采", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScVolunteerStyle scVolunteerStyle) {
        return toAjax(scVolunteerStyleService.insertScVolunteerStyle(scVolunteerStyle), scVolunteerStyle);
    }

    /**
     * 修改志愿者风采
     */
    @GetMapping("/edit/{styleId}")
    public String edit(@PathVariable("styleId") Long styleId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScVolunteerStyle scVolunteerStyle = scVolunteerStyleService.selectScVolunteerStyleFileById(styleId, basePath);
        mmap.put("scVolunteerStyle", scVolunteerStyle);
        mmap.put("fileInfoList", scVolunteerStyle.getFiles());
        return prefix + "/edit";
    }

    /**
     * 修改保存志愿者风采
     */
    @RequiresPermissions("sc:style:edit")
    @Log(title = "志愿者风采", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScVolunteerStyle scVolunteerStyle) {
        return toAjax(scVolunteerStyleService.updateScVolunteerStyle(scVolunteerStyle), scVolunteerStyle);
    }

    /**
     * 删除志愿者风采
     */
    @RequiresPermissions("sc:style:remove")
    @Log(title = "志愿者风采", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scVolunteerStyleService.deleteScVolunteerStyleByIds(ids));
    }

    /**
     * 志愿者风采林发布
     */
    @ApiOperation("志愿者发布风采林")
    @ApiImplicitParams({ @ApiImplicitParam(name = "volunteer_id", value = "志愿者id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "style_title", value = "标题", required = false, dataType = "String"),
            @ApiImplicitParam(name = "style_content", value = "内容", required = false, dataType = "String"),
            @ApiImplicitParam(name = "style_file", value = "多个图片集合", required = false, dataType = "MultipartFile") })
    @PostMapping(value = "/api/add")
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "volunteer_id", required = true) Long volunteerId,
            @RequestParam(value = "style_title", required = false) String styleTitle,
            @RequestParam(value = "style_content", required = false) String styleContent,
            @RequestParam(value = "style_file", required = false) MultipartFile[] styleFile) {

        ScVolunteerStyle scVolunteerStyle = new ScVolunteerStyle(uidWorker.getNextId(), volunteerId, styleTitle,
                styleContent);
        scVolunteerStyleService.insertScVolunteerStyle(scVolunteerStyle, styleFile);
        AjaxResult result = AjaxResult.success();
        return result;
    }

    /**
     * 志愿者风采林发布
     */
    @ApiOperation("获取风采林列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true),
            @ApiImplicitParam(name = "volunteer_id", value = "志愿者id", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "style_title", value = "标题", required = false, dataType = "String"),
            @ApiImplicitParam(name = "begin_time", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "end_time", value = "结束时间", required = false, dataType = "String") })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(value = "pageNum", required = true) int pageNum,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "volunteer_id", required = false) Long volunteerId,
            @RequestParam(value = "style_title", required = false) String styleTitle,
            @RequestParam(value = "begin_time", required = false) String beginTime,
            @RequestParam(value = "end_time", required = false) String endTime, HttpServletRequest request) {
        String basePath = getBasePath();
        Map<String, Object> map = new HashMap<>();
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        ScVolunteerStyle scVolunteerStyle = new ScVolunteerStyle(styleTitle, volunteerId);
        scVolunteerStyle.setParams(map);
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<Map<String, Object>> scParkrentListMap = scVolunteerStyleService
                .screenScVolunteerStyleList(scVolunteerStyle, basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(scParkrentListMap);
        return AjaxResult.success(pageInfo);
    }
}