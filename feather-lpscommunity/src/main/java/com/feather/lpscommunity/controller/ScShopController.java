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
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScGoods;
import com.feather.lpscommunity.domain.ScShop;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScGoodsService;
import com.feather.lpscommunity.service.IScShopService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 社区商家信息Controller
 *
 * @author dufan
 * @date 2019-10-16
 */
@Api(tags = { "4-社区商家,1-商家列表" })
@Controller
@RequestMapping("/sc/shop")
public class ScShopController extends BaseController {
    private String prefix = "lpscommunity/shop";
    // private String code = "shop";

    @Autowired
    private IScShopService scShopService;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private IScFileInfoService scFileInfoService;

    @Autowired
    private IScGoodsService scGoodsService;

    @RequiresPermissions("sc:shop:view")
    @GetMapping()
    public String shop() {
        return prefix + "/shop";
    }

    /**
     * 查询社区商家信息列表
     */
    @RequiresPermissions("sc:shop:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScShop scShop) {
        startPage();
        List<ScShop> list = scShopService.selectScShopList(scShop);
        return getDataTable(list);
    }

    /**
     * 导出社区商家信息列表
     */
    @RequiresPermissions("sc:shop:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScShop scShop) {
        List<ScShop> list = scShopService.selectScShopList(scShop);
        ExcelUtil<ScShop> util = new ExcelUtil<ScShop>(ScShop.class);
        return util.exportExcel(list, "shop");
    }

    /**
     * 新增社区商家信息
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("scShop", new ScShop());
        mmap.put("scFileInfo", new ScFileInfo());
        return prefix + "/edit";
    }

    /**
     * 新增保存社区商家信息
     */
    @RequiresPermissions("sc:shop:add")
    @Log(title = "社区商家信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScShop scShop) {
        scShop.setShopId(uidWorker.getNextId());
        return toAjax(scShopService.insertScShop(scShop), scShop);
    }

    /**
     * 修改社区商家信息
     */
    @GetMapping("/edit/{shopId}")
    public String edit(@PathVariable("shopId") Long shopId, ModelMap mmap) {
        ScShop scShop = scShopService.selectScShopById(shopId);
        // 获取图片路径
        ScFileInfo scFileInfo = new ScFileInfo();
        scFileInfo.setFileTarget(scShop.getShopId());
        List<ScFileInfo> fileInfoList = scFileInfoService.selectScFileInfoList(scFileInfo);
        mmap.put("scShop", scShop);
        // 设置路径
        mmap.put("scFileInfoList", fileInfoList);
        return prefix + "/edit";
    }

    /**
     * 修改保存社区商家信息
     */
    @RequiresPermissions("sc:shop:edit")
    @Log(title = "社区商家信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScShop scShop) {
        return toAjax(scShopService.updateScShop(scShop));
    }

    /**
     * 删除社区商家信息
     */
    @RequiresPermissions("sc:shop:remove")
    @Log(title = "社区商家信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {

        // 判断该商家是否存在商品加入订单
        if (!scShopService.checkOrderShop(ids)) {
            return AjaxResult.error("删除失败，该商家存在商品已加入订单");
        }
        if (!scShopService.checkGoodsShop(ids)) {
            return AjaxResult.error("删除失败，该商家存在未删除的商品");
        }
        return toAjax(scShopService.deleteScShopByIds(ids));
    }

    /**
     * 查询商品详细
     */
    @RequiresPermissions("sc:shop:list")
    @GetMapping("/goods/{shopId}")
    public String goods(@PathVariable("shopId") Long shopId, ModelMap mmap) {
        mmap.put("shop", scShopService.selectScShopById(shopId));
        return "/sc/goods/goods";
    }

    /**
     * 移动端获取商家列表
     */
    @ApiOperation("获取商家列表信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true) })
    @GetMapping(value = "/api/list")
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize, HttpServletRequest request) {
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        String basePath = getBasePath();
        List<Map<String, Object>> dataList = scShopService.screenScShopList(new ScShop(), basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }

    /**
     * 移动端获取商家详情
     */
    @ApiOperation("获取商家详情")
    @ApiImplicitParams({ @ApiImplicitParam(name = "shop_id", value = "商家id", required = true) })
    @RequestMapping(value = "/api/detail", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult restDetail(@RequestParam(name = "shop_id", required = true) Long shopId,
            HttpServletRequest request) {
        String basePath = getBasePath();
        Map<String, Object> data = scShopService.screenScShopDetail(shopId, basePath);
        return AjaxResult.success(data);
    }

    /**
     * 移动端获取商家列表
     */
    @ApiOperation("根据商家获取商品列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "shop_id", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageNum", value = "每页条目数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true) })
    @RequestMapping(value = "/api/goods/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restGoodsList(@RequestParam(name = "shop_id", required = true) Long shopId,
            @RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize, HttpServletRequest request) {
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        String basePath = getBasePath();
        List<Map<String, Object>> dataList = scGoodsService.screenGoodsList(new ScGoods(shopId), basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }
}
