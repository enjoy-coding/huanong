package com.feather.system.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.system.domain.SysDictData;
import com.feather.system.service.ISysDictDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 数据字典信息
 * 
 * @author feather
 */
@Api(tags = "数据字典")
@Controller
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    private String prefix = "system/dict/data";

    @Autowired
    private ISysDictDataService dictDataService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictData() {
        return prefix + "/data";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(SysDictData dictData) {
        startPage().setOrderBy("dict_sort");
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDictData dictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "字典数据");
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
        SysDictData dict = new SysDictData();
        dict.setDictType(dictType);
        mmap.put("dict", dict);
        return prefix + "/edit";
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDictData dict) {
        return toAjax(dictDataService.insertDictData(dict), dict);
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        mmap.put("dict", dictDataService.selectDictDataById(dictCode));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDictData dict) {
        return toAjax(dictDataService.updateDictData(dict), dict);
    }

    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(dictDataService.deleteDictDataByIds(ids));
    }

    @PostMapping("/ajaxList")
    @ResponseBody
    public AjaxResult ajaxList(String dictType) {
        List<SysDictData> list = dictDataService.selectDictDataByType(dictType);
        return AjaxResult.success(list);
    }

    /**
     * 根据类型获取列表
     */
    @ApiOperation("根据类型获取列表")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "dictType", value = "字典类型", required = true) //
    })
    @GetMapping("/api/list")
    @ResponseBody
    public AjaxResult restList(@RequestParam(name = "dictType", required = true) String dictType) {
        // List<ModelMap> dataList = new ArrayList<ModelMap>();
        List<SysDictData> list = dictDataService.selectDictDataByType(dictType);
        /*
         * for (int i = 0; i < list.size(); i++) { ModelMap data = new
         * ModelMap(); data.put("dictValue", list.get(i).getDictValue());
         * data.put("dictLabel", list.get(i).getDictLabel());
         * dataList.add(data); }
         */
        return AjaxResult.success(list);
    }
}
