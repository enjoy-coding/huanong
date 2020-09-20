package com.feather.aupipes.controller;

import com.feather.aupipes.service.AupSearchService;
import com.feather.aupipes.service.IAupIndexService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页信息
 */
@Api(tags = "首页图表展示")
@Controller
@RequestMapping("/api/screen")
public class AupScreenController extends BaseController {

    @Autowired
    private IAupIndexService aupIndexService;

    @Autowired
    private AupSearchService aupSearchService;

    @ApiOperation("获取设备统计")
    @GetMapping("/index/sbtj")
    @ResponseBody
    public AjaxResult getSbtj() {
        Map<String,Object> map = new HashMap<String,Object>();
        // 设备统计
        map.put("sbtj", aupIndexService.getEquipmentCount());
        return AjaxResult.success(map);
    }



    @ApiOperation("获取预警情况")
    @GetMapping("/index/yjtj")
    @ResponseBody
    public AjaxResult getYjtj() {
        Map<String,Object> map = new HashMap<String,Object>();
        // 预警统计
        map.put("yjtj", aupIndexService.getWarringCount());
        return AjaxResult.success(map);
    }

    @ApiOperation("获取预案总数")
    @GetMapping("/index/yazs")
    @ResponseBody
    public AjaxResult getYazs() {
        Map<String,Object> map = new HashMap<String,Object>();
        // 预案总数
        map.put("yazs", aupIndexService.getPlanNum());
        return AjaxResult.success(map);
    }

    @ApiOperation("获取路灯情况")
    @GetMapping("/index/ldqk")
    @ResponseBody
    public AjaxResult getLdqk() {
        Map<String,Object> map = new HashMap<String,Object>();
        // 预案总数
        map.put("ldqk", aupIndexService.getLightsDetail());
        return AjaxResult.success(map);
    }

    @ApiOperation("获取智能安防")
    @GetMapping("/index/znaf")
    @ResponseBody
    public AjaxResult getZnaf() {
        Map<String,Object> map = new HashMap<String,Object>();
        // 智能安防
        map.put("znaf", aupIndexService.getSecurity());
        return AjaxResult.success(map);
    }

    @ApiOperation("获取用水用电总量")
    @GetMapping("/index/nhjgTotal")
    @ResponseBody
    public AjaxResult getTotalUseNumber() {
        return AjaxResult.success(aupIndexService.getNhtjUseNumber());
    }

    @ApiOperation("获取泵房信息")
    @GetMapping("/index/pump")
    @ResponseBody
    public AjaxResult getPumpInfo() {
        // 泵房信息
        return AjaxResult.success(aupIndexService.queryPumpNumber());
    }

    @ApiOperation("获取预警处置")
    @GetMapping("/index/yjcz")
    @ResponseBody
    public AjaxResult getYjcz() {
        Map<String,Object> map = new HashMap<String,Object>();
        // 泵房信息
        map.put("yjcz", aupIndexService.getYjcz());
        return AjaxResult.success(map);
    }


    /**
     * 设备统计
     * 
     * @param
     * @return
     */
    @ApiOperation("设备统计")
    @GetMapping("/equipmentCount")
    @ResponseBody
    public AjaxResult equipmentCount() {
        List<Map<String, Object>> equipmentMap = aupIndexService.getEquipmentCount();
        return AjaxResult.success(equipmentMap);
    }

    @ApiOperation("预警统计")
    @GetMapping("/warning/list")
    @ResponseBody
    public AjaxResult warningList() {
        return AjaxResult.success(aupIndexService.getWarringCount());
    }

    @ApiOperation("预案总数")
    @GetMapping("/planNum")
    @ResponseBody
    public AjaxResult planNum() {
        return AjaxResult.success(aupIndexService.getPlanNum());
    }

    @ApiOperation("智能安防")
    @GetMapping("/security")
    @ResponseBody
    public AjaxResult security() {
        return AjaxResult.success(aupIndexService.getSecurity());
    }



    @ApiOperation("泵房日供水量")
    @GetMapping("/bfxx/echarts")
    @ResponseBody
    public AjaxResult getBfxx_echarts(@Param("pumpId") int pumpId) {
        Map<String,Object> map = aupSearchService.queryPumpDayUseWaterNumber(pumpId);
        return AjaxResult.success(map);
    }


    @ApiOperation("配电房信息")
    @GetMapping("/power")
    @ResponseBody
    public AjaxResult getPowerHouse() {
        return AjaxResult.success(aupIndexService.queryPowerHouseInfo());
    }


    @ApiOperation("巡检数")
    @GetMapping("/index/inspectNum")
    @ResponseBody
    public AjaxResult getInspectNum() {
        return AjaxResult.success(aupSearchService.getInspectNum());
    }


    @ApiOperation("巡检数")
    @GetMapping("/index/inspectNumCurM")
    @ResponseBody
    public AjaxResult getInspectNumCurM() {
        return AjaxResult.success(aupSearchService.getInspectNumCurM());
    }

    @GetMapping("/sub/{modelName}")
    public String subPage(@PathVariable("modelName") String modelName, Model model) {
        model.addAttribute("modelName", modelName);
        return "screen2";
    }
}