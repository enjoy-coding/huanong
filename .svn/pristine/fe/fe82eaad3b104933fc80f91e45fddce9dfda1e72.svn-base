package com.feather.lpscommunity.controller;

import java.io.IOException;
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
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScPartyBuild;
import com.feather.lpscommunity.service.IScPartyBuildService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 智慧社区初心频道管理
 */
@Api(tags = { "1-初心频道" })
@Controller
@RequestMapping("/sc/partybuild")
public class ScPartyBuildController extends BaseController {
    private String prefix = "lpscommunity/partybuild";

    @Autowired
    private IScPartyBuildService scPartyBuildService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:partybuild:view")
    @GetMapping()
    public String facility() {
        return prefix + "/partybuild";
    }

    /**
     * 查询初心频道列表
     */
    @RequiresPermissions("sc:partybuild:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScPartyBuild scPartyBuild, HttpServletRequest request) {
        startPage().setOrderBy("p.partyBuild_time desc");
        String basePath = getBasePath();
        List<ScPartyBuild> list = scPartyBuildService.selectScPartyBuildFileByList(scPartyBuild, basePath);
        return getDataTable(list);
    }

    /**
     *
     * 新增初心频道
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("scFileInfo", new ScFileInfo());
        mmap.put("partybuild", new ScPartyBuild());
        mmap.put("videoUrl", "");
        return prefix + "/edit";
    }

    /**
     * 新增初心频道
     */
    @RequiresPermissions("sc:partybuild:add")
    @Log(title = "初心频道", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScPartyBuild scPartyBuild) throws IOException {
        // 设置id
        scPartyBuild.setPartyBuildId(uidWorker.getNextId());
        return toAjax(scPartyBuildService.insertScPartyBuild(scPartyBuild), scPartyBuild);
    }

    /**
     * 跳转修改页面
     */
    @GetMapping("/edit/{partybuildId}")
    public String edit(@PathVariable("partybuildId") Long partyBuildId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScPartyBuild scPartyBuild = scPartyBuildService.selectScPartyBuildFileById(partyBuildId, basePath);
        mmap.put("partybuild", scPartyBuild);
        // 图片集合
        mmap.put("fileInfoList", scPartyBuild.getFiles());
        // 视频集合（根据图片获取视频地址）
        mmap.put("videoUrl", scPartyBuildService.getVideoUrl(scPartyBuild));

        return prefix + "/edit";
    }

    /**
     * 修改初心频道
     */
    @RequiresPermissions("sc:partybuild:edit")
    @Log(title = "修改初心频道", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(ScPartyBuild scPartyBuild) {
        return toAjax(scPartyBuildService.updateScPartyBuild(scPartyBuild));
    }

    /**
     * 跳转修改页面
     */
    @GetMapping("/detail/{partybuildId}")
    public String dtatil(@PathVariable("partybuildId") Long partyBuildId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScPartyBuild scPartyBuild = scPartyBuildService.selectScPartyBuildFileById(partyBuildId, basePath);
        mmap.put("partybuild", scPartyBuild);
        // 图片集合
        mmap.put("fileInfoList", scPartyBuild.getFiles());
        // 视频集合（根据图片获取视频地址）
        mmap.put("videoUrl", scPartyBuildService.getVideoUrl(scPartyBuild));

        return prefix + "/detail";
    }

    /**
     * 删除初心频道
     */
    @RequiresPermissions("rsi:partybuild:remove")
    @Log(title = "初心频道", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scPartyBuildService.deleteScPartyBuildByIds(ids));
    }

    /**
     * 移动端获取初心频道列表
     */
    @ApiOperation(value = "获取初心频道", notes = "获取初心频道分页列表【videoUploadType是视频上传类型（no_video:非视频上传;upload_video:本地上传;link_video:外链上传）】")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true), //
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true), //
            @ApiImplicitParam(name = "partyBuild_type", value = "初心频道分类", required = true) //
    })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            @RequestParam(name = "partyBuild_type", required = true) String partyBuildType,
            HttpServletRequest request) {
        String basePath = getBasePath();
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize,
                "partybuild_time desc,update_time desc");
        List<Map<String, Object>> dataList = scPartyBuildService
                .screenScPartyBuildByList(new ScPartyBuild(partyBuildType), basePath);

        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }
}
