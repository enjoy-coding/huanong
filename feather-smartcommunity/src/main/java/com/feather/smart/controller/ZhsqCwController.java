package com.feather.smart.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.smart.domain.ZhsqCw;
import com.feather.smart.service.IZhsqCwService;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 车位Controller
 * 
 * @author fancy
 * @date 2020-05-15
 */
@Controller
@RequestMapping("/smartcommunity/CW")
public class ZhsqCwController extends BaseController
{
    private String prefix = "smartcommunity/CW";

    @Autowired
    private IZhsqCwService zhsqCwService;

    @RequiresPermissions("smartcommunity:CW:view")
    @GetMapping()
    public String CW()
    {
        return prefix + "/CW";
    }

    /**
     * 查询车位列表
     */
    @RequiresPermissions("smartcommunity:CW:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqCw zhsqCw)
    {
        startPage();
        List<ZhsqCw> list = zhsqCwService.selectZhsqCwList(zhsqCw);
        return getDataTable(list);
    }

    /**
     * 导出车位列表
     */
    @RequiresPermissions("smartcommunity:CW:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqCw zhsqCw)
    {
        List<ZhsqCw> list = zhsqCwService.selectZhsqCwList(zhsqCw);
        ExcelUtil<ZhsqCw> util = new ExcelUtil<ZhsqCw>(ZhsqCw.class);
        return util.exportExcel(list, "CW");
    }

    /**
     * 新增车位
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqCw CW = new ZhsqCw();
        mmap.put("CW", CW);
        return prefix + "/add";
    }

    /**
     * 新增保存车位
     */
    @RequiresPermissions("smartcommunity:CW:add")
    @Log(title = "车位", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqCw zhsqCw)
    {
        return toAjax(zhsqCwService.insertZhsqCw(zhsqCw));
    }

    /**
     * 修改车位
     */
    @GetMapping("/edit/{cwid}")
    public String edit(@PathVariable("cwid") String cwid, ModelMap mmap)
    {
        ZhsqCw CW = zhsqCwService.selectZhsqCwById(cwid);
        mmap.put("CW", CW);
        return prefix + "/edit";
    }

    /**
     * 修改保存车位
     */
    @RequiresPermissions("smartcommunity:CW:edit")
    @Log(title = "车位", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqCw zhsqCw)
    {
        return toAjax(zhsqCwService.updateZhsqCw(zhsqCw));
    }

    /**
     * 删除车位
     */
    @RequiresPermissions("smartcommunity:CW:remove")
    @Log(title = "车位", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqCwService.deleteZhsqCwByIds(ids));
    }

    /**
     * 获取左侧列表车位统计数
     */
    @GetMapping( "/getCwInfoLeft")
    @ResponseBody
    public AjaxResult getCwInfoLeft(ZhsqCw zhsqCw)
    {
        List<Map<String,Object>> list= zhsqCwService.getCwInfoLeft(zhsqCw);

        return AjaxResult.success(list);
    }

    /**
     * 获取右侧停车场信息
     */
    @GetMapping( "/getTccInfoRight")
    @ResponseBody
    public AjaxResult getTccInfoRight(ZhsqCw zhsqCw)
    {
        startPage();
        List<Map> list = zhsqCwService.getTccInfoRight(zhsqCw);

        return AjaxResult.success(list);
    }

    /**
     * 根据停车场Id获取停车场详细信息列表
     */
    @GetMapping( "/getTccXxInfoCenter")
    @ResponseBody
    public AjaxResult getTccXxInfoCenter(@Param("tccId") String tccId)
    {
        List<Map> list = zhsqCwService.getTccXxInfoCenter(tccId);

        return AjaxResult.success(list);
    }

    /**
     * 查询空闲车位列表
     */
    @GetMapping( "/getKxcwInfo")
    @ResponseBody
    public AjaxResult getKxcwInfo(ZhsqCw zhsqCw)
    {
        startPage();
        List<Map> list = zhsqCwService.getKxcwInfo(zhsqCw);
        return AjaxResult.success(list);
    }

    /**
     * 查询空闲车位详细列表数据
     */
    @GetMapping( "/getKxcwXxInfoList")
    @ResponseBody
    public AjaxResult getKxcwXxInfoList(@Param("cwxx") String cwxx,ZhsqCw zhsqCw)
    {
        String[] split = cwxx.split(",");
        String cwLx = "";
        if(split[1]!=null){
            if(split[1].equals("地上")){
                zhsqCw.setCwlx("1");
            }else{
                zhsqCw.setCwlx("2");
            }
        }
        zhsqCw.setCwid(split[0]);
        zhsqCw.setTccid(split[2]);
        List<Map> list = zhsqCwService.getKxcwXxInfoList(zhsqCw);
        return AjaxResult.success(list);
    }

    /**
     * 查询占用车位列表
     */
    @GetMapping( "/getYycwInfo")
    @ResponseBody
    public AjaxResult getYycwInfo(ZhsqCw zhsqCw)
    {
        startPage();
        List<Map> list = zhsqCwService.getYycwInfo(zhsqCw);
        return AjaxResult.success(list);
    }

    /**
     * 查询空闲车位详细列表数据
     */
    @GetMapping( "/getYycwXxInfoList")
    @ResponseBody
    public AjaxResult getYycwXxInfoList(@Param("cwxx") String cwxx,ZhsqCw zhsqCw)
    {
        String[] split = cwxx.split(",");
        String cwLx = "";
        if(split[1]!=null){
            if(split[1].equals("地上")){
                zhsqCw.setCwlx("1");
            }else{
                zhsqCw.setCwlx("2");
            }
        }
        zhsqCw.setCwid(split[0]);
        zhsqCw.setTccid(split[2]);
        List<Map> list = zhsqCwService.getYycwXxInfoList(zhsqCw);
        return AjaxResult.success(list);
    }


}
