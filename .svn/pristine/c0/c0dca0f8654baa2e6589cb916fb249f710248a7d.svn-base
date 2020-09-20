package com.feather.lpscommunity.controller;

import java.util.Date;
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
import com.feather.lpscommunity.domain.ScGoods;
import com.feather.lpscommunity.domain.ScOrder;
import com.feather.lpscommunity.domain.ScShop;
import com.feather.lpscommunity.service.IScGoodsService;
import com.feather.lpscommunity.service.IScOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 订单信息Controller
 * 
 * @author fancy
 * @date 2019-11-25
 */
@Api(tags = { "4-社区商家,2-加入订单" })
@Controller
@RequestMapping("/sc/order")
public class ScOrderController extends BaseController {
    private String prefix = "lpscommunity/order";

    @Autowired
    private IScOrderService scOrderService;

    @Autowired
    private IScGoodsService scGoodsService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:order:view")
    @GetMapping()
    public String order() {
        return prefix + "/order";
    }

    /**
     * 查询订单信息列表
     */
    @RequiresPermissions("sc:order:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScOrder scOrder) {
        startPage().setOrderBy("update_time");
        List<ScOrder> list = scOrderService.selectScOrderList(scOrder);
        for (int i = 0; i < list.size(); i++) {
            long goodsId = list.get(i).getGoodsId();
            ScGoods scGoods = scGoodsService.selectScGoodsById(goodsId);
            list.get(i).setShopId(scGoods.getShop().getShopId());
            list.get(i).setShopName(scGoods.getShop().getShopName());
        }
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @RequiresPermissions("sc:order:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScOrder scOrder) {
        List<ScOrder> list = scOrderService.selectScOrderList(scOrder);
        ExcelUtil<ScOrder> util = new ExcelUtil<ScOrder>(ScOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单信息
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("scOrder", new ScOrder());
        return prefix + "/edit";
    }

    /**
     * 新增保存订单信息
     */
    @RequiresPermissions("sc:order:add")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScOrder scOrder) {
        scOrder.setOrderId(uidWorker.getNextId());
        return toAjax(scOrderService.insertScOrder(scOrder), scOrder);
    }

    /**
     * 修改订单信息
     */
    @GetMapping("/edit/{orderId}")
    public String edit(@PathVariable("orderId") Long orderId, ModelMap mmap) {
        ScOrder scOrder = scOrderService.selectScOrderById(orderId);
        ScGoods scGoods = scGoodsService.selectScGoodsById(scOrder.getGoodsId());
        ScShop scShop = scGoods.getShop();
        mmap.put("scOrder", scOrder);
        mmap.put("scGoods", scGoods);
        mmap.put("scShop", scShop);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单信息
     */
    @RequiresPermissions("sc:order:edit")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScOrder scOrder) {
        return toAjax(scOrderService.updateScOrder(scOrder));
    }

    /**
     * 删除订单信息
     */
    @RequiresPermissions("sc:order:remove")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scOrderService.deleteScOrderByIds(ids));
    }

    /**
     * 加入订单
     */
    @ApiOperation(value = "加入订单")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "goods_id", value = "商品id", required = true), //
            @ApiImplicitParam(name = "equipment_id", value = "设备id", required = true) //
    })
    @PostMapping(value = "/api/add")
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "goods_id", required = true) Long goodsId,
            @RequestParam(value = "equipment_id", required = true) String equipmentId) {
        // 查询商品是否失效
        ScGoods scGoods = scGoodsService.selectScGoodsById(goodsId);
        if (scGoods == null) {
            return AjaxResult.error("加入失败,该商品已失效！");
        }
        if (scGoods.getShop() == null) {
            return AjaxResult.error("加入失败,该商店已失效!");
        }
        if (!scOrderService.checkRepeatInsertOrder(new ScOrder(goodsId, equipmentId), new Date())) {
            return AjaxResult.error("加入过于频繁，请稍后再试！");
        }
        ScOrder order = new ScOrder(uidWorker.getNextId(), equipmentId + "[" + scGoods.getGoodsName() + "]", goodsId,
                equipmentId);
        scOrderService.insertScOrder(order);
        AjaxResult result = AjaxResult.success();
        return result;
    }

    /**
     * 订单列表
     */
    @ApiOperation("订单列表")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true), //
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true), //
            @ApiImplicitParam(name = "equipment_id", value = "设备id", required = true) //
    })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "pageNum", required = true) int pageNum,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            @RequestParam(name = "equipment_id", required = true) String equipmentId, HttpServletRequest request) {
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        String basePath = getBasePath();
        List<Map<String, Object>> dataList = scOrderService.screenScOrderList(new ScOrder(equipmentId), basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(dataList);
        return AjaxResult.success(pageInfo);
    }

    /**
     * 移除订单
     */
    @ApiOperation(value = "移除订单", notes = "多个id逗号隔开")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "order_ids", value = "多个订单id或单个", required = true) //
    })
    @PostMapping(value = "/api/remove")
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "order_ids", required = true) String orderIds) {
        scOrderService.deleteScOrderByIds(orderIds);
        AjaxResult result = AjaxResult.success();
        return result;
    }
}
