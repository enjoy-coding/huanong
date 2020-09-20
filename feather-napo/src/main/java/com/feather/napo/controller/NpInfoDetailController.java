package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.domain.NpInfoDetail;
import com.feather.napo.service.INpInfoDetailService;
import com.feather.napo.service.INpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author nothing
 * @date 2020-06-19 11:14
 */
@Controller
@RequestMapping("/np/infoDetail")
public class NpInfoDetailController extends BaseController {
    private String prefix = "napo/infoDetail";

    @Autowired
    INpInfoService npInfoService;
    @Autowired
    INpInfoDetailService npInfoDetailService;

    @GetMapping()
    public String index(@RequestParam("infoType") Integer infoType, ModelMap mmap) {
        mmap.put("infoType", infoType);
        return prefix + "/list";
    }

    @GetMapping("/add/{infoId}")
    public String add(@PathVariable("infoId") long infoId, ModelMap mmap) {
        NpInfoDetail npInfoDetail = new NpInfoDetail();
        npInfoDetail.setInfoId(infoId);
        mmap.put("npInfoDetail", npInfoDetail);
        return retFormStyle(infoId);
    }

    @GetMapping("/edit/{infoDetailId}")
    public String edit(@PathVariable("infoDetailId") Long infoDetailId, ModelMap mmap) {
        NpInfoDetail npInfoDetail = npInfoDetailService.selectNpInfoDetailById(infoDetailId);
        mmap.put("npInfoDetail", npInfoDetail);
        return retFormStyle(npInfoDetail.getInfoId());
    }

    private String  retFormStyle(long infoId){
        NpInfo npInfo = npInfoService.selectNpInfoById(infoId);
        if (!Objects.isNull(npInfo)) {
            int type = npInfo.getInfoType();
            //添加或编辑店铺
            if (type == 1) {
                return prefix + "/editShop";
            } else if (type == 3) {
                //添加景点
                return prefix + "/editSpot";
            }
        }
        return prefix + "/editSpot";
    }

    @GetMapping("/selectNpInfoDetailById/{infoDetailId}")
    @ResponseBody
    public NpInfoDetail selectNpInfoDetailById(@PathVariable("infoDetailId") Long infoDetailId) {
        NpInfoDetail npInfoDetail = npInfoDetailService.selectNpInfoDetailById(infoDetailId);
        npInfoDetail = (npInfoDetail != null) ? npInfoDetail : new NpInfoDetail();
        return npInfoDetail;
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpInfoDetail npInfoDetail) {
        startPage();
        List<NpInfoDetail> list = npInfoDetailService.selectNpInfoDetailList(npInfoDetail);
        return getDataTable(list);
    }

    @Log(title = "那坡信息详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpInfoDetail npInfoDetail) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npInfoDetailService.insertNpInfoDetail(npInfoDetail), npInfoDetail);
    }

    @Log(title = "那坡信息详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpInfoDetail npInfoDetail) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return AjaxResult.success(npInfoDetailService.updateNpInfoDetail(npInfoDetail));
    }

    @Log(title = "那坡信息详情", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npInfoDetailService.deleteNpInfoDetailByIds(ids));
    }
}
