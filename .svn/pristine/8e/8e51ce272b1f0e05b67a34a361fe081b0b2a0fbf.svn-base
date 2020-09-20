package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupInspectDetail;
import com.feather.aupipes.service.IAupInspectDetailService;
import com.feather.aupipes.utils.InspectExcelUtil;
import com.feather.aupipes.utils.vo.AupInspectDetailVo;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡检设备Controller
 *
 * @author fancy
 * @date 2020-06-04
 */
@Controller
@RequestMapping("/aupipes/detail")
public class AupInspectDetailController extends BaseController
{
    private String prefix = "aupipes/detail";

    private String prefix1 = "aupipes/mobile/html";

    @Autowired
    private IAupInspectDetailService aupInspectDetailService;

//    @RequiresPermissions("aupipes:detail:view")
    @GetMapping()
    public String detail()
    {
        return prefix + "/detail";
    }

    /**
     * 查询巡检设备列表
     */
//    @RequiresPermissions("aupipes:detail:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupInspectDetail aupInspectDetail)
    {
        startPage();
        List<AupInspectDetail> list = aupInspectDetailService.selectAupInspectDetailList(aupInspectDetail);
        return getDataTable(list);
    }

    /**
     * 导出巡检设备列表
     */
//    @RequiresPermissions("aupipes:detail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupInspectDetail aupInspectDetail)
    {
        List<AupInspectDetail> list = aupInspectDetailService.selectAupInspectDetailList(aupInspectDetail);
        ExcelUtil<AupInspectDetail> util = new ExcelUtil<AupInspectDetail>(AupInspectDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 导出包含巡检设备的详情表
     * @param id
     * @return
     */
    @GetMapping("/customExport")
    @ResponseBody
    public AjaxResult customExport(Long id)
    {
        AupInspectDetailVo aupInspectDetailVo = aupInspectDetailService.selectAupInspectDetailRecurById(id);
        InspectExcelUtil util = new InspectExcelUtil();
        return util.exportDetailExcel(aupInspectDetailVo, "巡检记录表");
    }

    /**
     * 新增巡检设备
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
//    public String add(ModelMap mmap, AupInspectDetail detail)
    {
        AupInspectDetail detail = new AupInspectDetail();
        mmap.put("detail", detail);
        return prefix + "/edit";
    }

    /**
     * 新增保存巡检设备
     */
//    @RequiresPermissions("aupipes:detail:add")
    @Log(title = "巡检设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupInspectDetail aupInspectDetail)
    {
        if(aupInspectDetail.getTaskId() == null){
            return AjaxResult.error("不允许插入空值");
        }
        return toAjax(aupInspectDetailService.insertAupInspectDetail(aupInspectDetail), aupInspectDetail);
    }

    /**
     * 修改巡检设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupInspectDetail detail = aupInspectDetailService.selectAupInspectDetailById(id);
        mmap.put("detail", detail);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检设备
     */
//    @RequiresPermissions("aupipes:detail:edit")
    @Log(title = "巡检设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupInspectDetail aupInspectDetail)
    {
        return toAjax(aupInspectDetailService.updateAupInspectDetail(aupInspectDetail));
    }

    /**
     * 删除巡检设备
     */
//    @RequiresPermissions("aupipes:detail:remove")
    @Log(title = "巡检设备", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupInspectDetailService.deleteAupInspectDetailByIds(ids));
    }

    /**
     * 移动端开始巡检任务
     */
    @GetMapping("/startLook")
    public String startLook(String id, ModelMap mmap) {
        mmap.put("ydxjTaskId", id );
        return prefix1 + "/ydxj1";
    }

    /**
     * 移动端新增巡检任务
     */
    @GetMapping("/ydxjAdd")
    public String ydxjAdd(String id, ModelMap mmap) {
        mmap.put("ydxjTaskId", id );
        return prefix1 + "/ydxj2_add";
    }

    /**
     * 移动端查看巡检任务
     */
    @GetMapping("/ydxjLook")
    public String ydxjLook(String id, ModelMap mmap) {
        mmap.put("ydxjTaskId", id );
        return prefix1 + "/ydxj2_look";
    }

    /**
     * 移动端设备关联页面
     */
    @GetMapping("/ydxjSbgl")
    public String ydxjSbgl(String id, ModelMap mmap) {
        mmap.put("null", "" );
        return prefix1 + "/ydxj3";
    }

    /**
     * 新增保存巡检设备
     */
    @PostMapping("/addNew")
    @ResponseBody
    public AjaxResult addNew(String xjjl, String xjsb)
    {
            return aupInspectDetailService.insertAupInspectDetailAndSb(xjjl,xjsb);

    }


}
