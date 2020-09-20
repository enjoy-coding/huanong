package com.feather.lpscommunity.controller;

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
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScPrizes;
import com.feather.lpscommunity.domain.ScPrizesVolunteer;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.service.IScPrizesService;
import com.feather.lpscommunity.service.IScPrizesVolunteerService;
import com.feather.lpscommunity.service.IScVolunteerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 奖品Controller
 * 
 * @author fancy
 * @date 2019-11-21
 */
@Api(tags = { "3-志愿者服务,4-领奖池" })
@Controller
@RequestMapping("/sc/prizes")
public class ScPrizesController extends BaseController {
    private String prefix = "lpscommunity/prizes";

    @Autowired
    private IScPrizesService scPrizesService;

    @Autowired
    private IScVolunteerService scVolunteerService;

    @Autowired
    private IScPrizesVolunteerService scPrizesVolunteerService;

    @RequiresPermissions("sc:prizes:view")
    @GetMapping()
    public String prizes() {
        return prefix + "/prizes";
    }

    /**
     * 查询奖品列表
     */
    @RequiresPermissions("sc:prizes:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScPrizes scPrizes, HttpServletRequest request) {
        startPage().setOrderBy("update_time desc");
        String basePath = getBasePath();
        List<ScPrizes> list = scPrizesService.selectScPrizesList(scPrizes, basePath);
        return getDataTable(list);
    }

    /**
     * 导出奖品列表
     */
    @RequiresPermissions("sc:prizes:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScPrizes scPrizes) {
        List<ScPrizes> list = scPrizesService.selectScPrizesList(scPrizes);
        ExcelUtil<ScPrizes> util = new ExcelUtil<ScPrizes>(ScPrizes.class);
        return util.exportExcel(list, "prizes");
    }

    /**
     * 新增奖品
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("scPrizes", new ScPrizes());
        return prefix + "/edit";
    }

    /**
     * 新增保存奖品
     */
    @RequiresPermissions("sc:prizes:add")
    @Log(title = "奖品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScPrizes scPrizes) {
        return toAjax(scPrizesService.insertScPrizes(scPrizes), scPrizes);
    }

    /**
     * 修改奖品
     */
    @GetMapping("/edit/{prizesId}")
    public String edit(@PathVariable("prizesId") Long prizesId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScPrizes scPrizes = scPrizesService.selectScPrizesFileById(prizesId, basePath);
        mmap.put("scPrizes", scPrizes);
        mmap.put("fileInfoList", scPrizes.getFiles());
        return prefix + "/edit";
    }

    /**
     * 修改保存奖品
     */
    @RequiresPermissions("sc:prizes:edit")
    @Log(title = "奖品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScPrizes scPrizes) {
        return toAjax(scPrizesService.updateScPrizes(scPrizes));
    }

    /**
     * 删除奖品
     */
    @RequiresPermissions("sc:prizes:remove")
    @Log(title = "奖品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scPrizesService.deleteScPrizesByIds(ids));
    }

    /**
     * 志愿者风采林发布
     */
    @ApiOperation("志愿者领奖")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "volunteer_id", value = "志愿者id", required = true), //
            @ApiImplicitParam(name = "prizes_id", value = "奖品id", required = true) //
    })
    @PostMapping(value = "/api/add")
    @ClearPage
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "volunteer_id", required = true) Long volunteerId,
            @RequestParam(value = "prizes_id", required = true) Long prizesId, HttpServletRequest request) {

        ScVolunteer volunteer = scVolunteerService.selectScVolunteerById(volunteerId);
        if (volunteer == null) {
            return AjaxResult.error("兑换失败！该志愿者失效");
        }
        ScPrizes prizes = scPrizesService.selectScPrizesById(prizesId);
        if (prizes == null) {
            return AjaxResult.error("兑换失败！该奖品失效");
        }
        // 判断奖品是否>0
        if (!scPrizesService.checkPrizesNumById(prizesId)) {
            return AjaxResult.error("兑换失败！该商品无库存");
        }
        // 判断志愿者积分是否能兑换
        if (!scPrizesVolunteerService.checkScore(prizesId, volunteerId)) {
            return AjaxResult.error("兑换失败！积分不够");
        }
        // 兑换奖品
        ScPrizesVolunteer scPrizesVolunteer = new ScPrizesVolunteer(volunteerId, prizesId);
        scPrizesVolunteerService.insertScPrizesVolunteer(scPrizesVolunteer);
        return AjaxResult.success();
    }

    /**
     * 志愿者风采林发布
     */
    @ApiOperation("奖品池列表")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true), //
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true) //
    })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(value = "pageNum", required = true) int pageNum,
            @RequestParam(value = "pageSize", required = true) int pageSize, HttpServletRequest request) {
        String basePath = getBasePath();
        ScPrizes prizes = new ScPrizes();
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<Map<String, Object>> scParkrentListMap = scPrizesService.screenScPrizesList(prizes, basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(scParkrentListMap);
        return AjaxResult.success(pageInfo);
    }
}