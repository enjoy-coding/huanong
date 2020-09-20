package com.feather.lpscommunity.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScGoods;
import com.feather.lpscommunity.service.IScGoodsService;

/**
 * 商品信息表Controller
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Controller
@RequestMapping("/sc/goods")
public class ScGoodsController extends BaseController {
    private String prefix = "lpscommunity/goods";

    @Autowired
    private IScGoodsService scGoodsService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:goods:view")
    @GetMapping()
    public String goods() {
        return prefix + "/goods";
    }

    /**
     * 查询商品信息表列表
     */
    @RequiresPermissions("sc:goods:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScGoods scGoods) {
        startPage();
        List<ScGoods> list = scGoodsService.selectScGoodsList(scGoods);
        return getDataTable(list);
    }

    /**
     * 导出商品信息表列表
     */
    @RequiresPermissions("sc:goods:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScGoods scGoods) {
        List<ScGoods> list = scGoodsService.selectScGoodsList(scGoods);
        ExcelUtil<ScGoods> util = new ExcelUtil<ScGoods>(ScGoods.class);
        return util.exportExcel(list, "goods");
    }

    /**
     * 新增商品信息表
     */
    @GetMapping("/add/{shopId}")
    public String add(@PathVariable("shopId") Long shopId, ModelMap mmap) {
        ScGoods scGoods = new ScGoods();
        scGoods.setShopId(shopId);
        mmap.put("scGoods", scGoods);
        return prefix + "/edit";
    }

    /**
     * 新增保存商品信息表
     */
    @RequiresPermissions("sc:goods:add")
    @Log(title = "商品信息表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScGoods scGoods) {
        scGoods.setGoodsId(uidWorker.getNextId());
        return toAjax(scGoodsService.insertScGoods(scGoods), scGoods);
    }

    /**
     * 修改商品信息表
     */
    @GetMapping("/edit/{goodsId}")
    public String edit(@PathVariable("goodsId") Long goodsId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScGoods scGoods = scGoodsService.selectScGoodsFileById(goodsId, basePath);
        mmap.put("scGoods", scGoods);
        mmap.put("scFileInfoList", scGoods.getFiles());
        return prefix + "/edit";
    }

    /**
     * 修改保存商品信息表
     */
    @RequiresPermissions("sc:goods:edit")
    @Log(title = "商品信息表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScGoods scGoods) {
        return toAjax(scGoodsService.updateScGoods(scGoods), scGoods);
    }

    /**
     * 删除商品信息表
     */
    @RequiresPermissions("sc:goods:remove")
    @Log(title = "商品信息表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (!scGoodsService.checkOrderGoods(ids)) {
            return AjaxResult.error("删除失败，该商品已加入订单");
        }
        return toAjax(scGoodsService.deleteScGoodsByIds(ids));
    }
}