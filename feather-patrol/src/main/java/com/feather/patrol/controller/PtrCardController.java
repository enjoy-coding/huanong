package com.feather.patrol.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.patrol.domain.PtrCard;
import com.feather.patrol.service.IPtrCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 巡点Controller
 */
@Api(tags = "巡点台账")
@Controller
@RequestMapping("/patrol/card")
public class PtrCardController extends BaseController {
    private String prefix = "patrol/card";

    @Autowired
    private IPtrCardService ptrCardService;

    @RequiresPermissions("patrol:card:view")
    @GetMapping()
    public String card() {
        return prefix + "/card";
    }

    /**
     * 查询巡点列表
     */
    @RequiresPermissions("patrol:card:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(PtrCard ptrCard) {
        startPage();
        List<PtrCard> list = ptrCardService.selectPtrCardList(ptrCard);
        return getDataTable(list);
    }

    /**
     * 查询所有巡点
     */
    @RequiresPermissions("patrol:card:list")
    @GetMapping("/allWithCoordinate")
    @ResponseBody
    public AjaxResult listAllWithCoordinate() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("case", "coor");
        PtrCard ptrCard = new PtrCard();
        ptrCard.setParams(params);
        List<PtrCard> list = ptrCardService.selectPtrCardList(ptrCard);
        return AjaxResult.success(list);
    }

    /**
     * 导出巡点列表
     */
    @RequiresPermissions("patrol:card:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PtrCard ptrCard) {
        List<PtrCard> list = ptrCardService.selectPtrCardList(ptrCard);
        ExcelUtil<PtrCard> util = new ExcelUtil<PtrCard>(PtrCard.class);
        return util.exportExcel(list, "card");
    }

    /**
     * 新增巡点
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        PtrCard card = new PtrCard();
        mmap.put("card", card);
        return prefix + "/edit";
    }

    /**
     * 新增保存巡点
     */
    @RequiresPermissions("patrol:card:add")
    @Log(title = "巡点", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PtrCard ptrCard) {
        return toAjax(ptrCardService.insertPtrCard(ptrCard), ptrCard);
    }

    /**
     * 修改巡点
     */
    @GetMapping("/edit/{cardId}")
    public String edit(@PathVariable("cardId") Long cardId, ModelMap mmap) {
        PtrCard card = ptrCardService.selectPtrCardById(cardId);
        mmap.put("card", card);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡点
     */
    @RequiresPermissions("patrol:card:edit")
    @Log(title = "巡点", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PtrCard ptrCard) {
        return toAjax(ptrCardService.updatePtrCard(ptrCard));
    }

    /**
     * 删除巡点
     */
    @RequiresPermissions("patrol:card:remove")
    @Log(title = "巡点", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ptrCardService.deletePtrCardByIds(ids));
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) {
        try {
            return toAjax(ptrCardService.importData(file, false));
        } catch (Exception ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * 根据二维码获取巡点信息
     */
    @ApiOperation("根据二维码获取巡点信息")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "qrcode", value = "二维码", required = true) //
    })
    @GetMapping("/api/find")
    @ResponseBody
    public AjaxResult findCardByQrcode(String qrcode) {
        List<PtrCard> list = ptrCardService.selectPtrCardByCode(qrcode, true);
        if (list == null || list.size() == 0) {
            return AjaxResult.error("不能识别的二维码");
        }
        if (list.size() > 1) {
            return AjaxResult.error("此二维码冲突，请联系系统管理员。");
        }
        return AjaxResult.success(list.get(0));
    }
}