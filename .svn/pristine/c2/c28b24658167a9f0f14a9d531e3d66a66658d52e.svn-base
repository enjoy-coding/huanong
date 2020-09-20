package com.feather.aupipes.controller;

import com.alibaba.fastjson.JSONArray;
import com.feather.aupipes.domain.AupBuildingWaterValve;
import com.feather.aupipes.domain.AupWaterValve;
import com.feather.aupipes.service.IAupBuildingWaterValveService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 水房屋回路Controller
 */
@Api(tags = "水房屋回路")
@Controller
@RequestMapping("/aupipes/buildingwatervalve")
public class AupBuildingWaterValveController extends BaseController {

    @Autowired
    private IAupBuildingWaterValveService aupBuildingWaterValveService;

    /**
     * 修改保存水房屋回路
     */
    /*@RequiresPermissions("aupipes:rel:edit")
    @Log(title = "水房屋回路", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult editSave(@RequestParam(value = "bwv",required = false)  List<AupBuildingWaterValve> bwv,
                               @RequestParam(name = "buildingNo", required = false) String buildingNo,
                               @RequestParam(name = "valveNo", required = false) String valveNo,
                               @RequestParam(name = "bwfJsonStr" ,required = false) String bwfJsonStr) {
        List<AupBuildingWaterValve> bwvList = (List<AupBuildingWaterValve>) JSONArray.parseArray(buildingNo, AupBuildingWaterValve.class);
        String loginName = ShiroUtils.getLoginName();
        Date date = new Date();
        for(AupBuildingWaterValve abwv : bwvList){
            abwv.setCreateBy(loginName);
            abwv.setCreateTime(date);
        }
        return toAjax(aupBuildingWaterValveService.insertAupBuildingWaterValve(bwvList));
    }*/

    /**
     * 通过房屋编码查询需要关闭的阀门
     */
    @ApiOperation(value = "通过房屋编码查询需要关闭的阀门")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "building", value = "房屋编码", required = true) //
    })
    @GetMapping("/api/getByBuilding") // getDirectValveByCode
    @ResponseBody
    public AjaxResult getByBuilding(@RequestParam(name = "building", required = true) String building) {
        List<AupBuildingWaterValve> list = aupBuildingWaterValveService.selectByBuilding(building);
        return AjaxResult.success(list);
    }

    /**
     * 通过阀门查询影响的楼栋
     */
    @ApiOperation(value = "通过阀门查询影响的楼栋")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "valve", value = "阀门，多个用逗号分隔", required = true) //
    })
    @GetMapping("/api/getByValve") // getImpactBuildingByValve
    @ResponseBody
    public AjaxResult getByValve(@RequestParam(name = "valve", required = true) String valves) {
        List<AupBuildingWaterValve> list = aupBuildingWaterValveService.selectByValve(valves);
        return AjaxResult.success(list);
    }

    /*-------------------------------------------------------------------------------------------------------------------------------------*/

    /**
     * 修改保存水房屋回路
     */
    @RequiresPermissions("aupipes:rel:edit")
    @Log(title = "水房屋回路", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult editSave(@RequestParam(name = "bwfJsonStr" ,required = false) String bwfJsonStr) {
        List<AupWaterValve> bwvList = (List<AupWaterValve>) JSONArray.parseArray(bwfJsonStr, AupWaterValve.class);
        String loginName = ShiroUtils.getLoginName();
        Date date = new Date();
        for(AupWaterValve abwv : bwvList){
            abwv.setCreateBy(loginName);
            abwv.setCreateTime(date);
            abwv.setUpdateBy(loginName);
            abwv.setUpdateTime(date);
        }
        return toAjax(aupBuildingWaterValveService.insertAupBuildingWaterValve(bwvList));
    }

    /**
     * 通过楼栋或者阀门查询影响的上游阀门
     */
    @GetMapping("/api/getByTypeAndValue") // getImpactBuildingByValve
    @ResponseBody
    public AjaxResult getByTypeAndValue(@RequestParam(name = "chooseType", required = true) String chooseType,@RequestParam(name = "value", required = true) String value) {
        List<AupWaterValve> list = aupBuildingWaterValveService.selectByTypeAndValue(chooseType,value);
        return AjaxResult.success(list);
    }

    /**
     * 通过楼栋或者阀门查询影响的下游阀门
     */
    @GetMapping("/api/getByTypeAndValueForDown") // getImpactBuildingByValve
    @ResponseBody
    public AjaxResult getByTypeAndValueForDown(@RequestParam(name = "chooseType", required = true) String chooseType,@RequestParam(name = "value", required = true) String value) {
        List<AupWaterValve> list = aupBuildingWaterValveService.selectByTypeAndValueForDown(chooseType,value);
        return AjaxResult.success(list);
    }


}
