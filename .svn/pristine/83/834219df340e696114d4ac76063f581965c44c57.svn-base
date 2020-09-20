package com.feather.aupipes.controller.screen;

import com.feather.aupipes.domain.statistics.AupTjWaterSideByYearMonth;
import com.feather.aupipes.domain.AupWaterSide;
import com.feather.aupipes.domain.UseMeter;
import com.feather.aupipes.domain.statistics.AupTjElectricityByYearMonth;
import com.feather.aupipes.service.*;
import com.feather.common.annotation.ClearPage;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.utils.poi.ExcelUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大屏 子模块 - 能耗监管
 */
@Controller
@RequestMapping("/screen/nhjg")
public class ScreennNhjgController extends BaseController {
    private String prefix = "/aupipes/screen/nhjg";


    @Autowired
    private IScreenYxjkService screenYxjkService;

    @Autowired
    private IAupNhjgService iAupNhjgService;

    @Autowired
    private IAupBuildingSideService aupBuildingSideService;

    @Autowired
    private IAupWaterSideService waterSideService;

    @Autowired
    private IAupTjWaterSideByYearMonthService waterSideByYearMonthService;

    @Autowired
    private IAupTjElectricityByYearMonthService electricityByYearMonthService;

    /**
     * 左边面板
     */
    @RequestMapping("/left")
    public String left(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/left";
    }

    /**
     * 加载查询用电平衡
     */
    @RequestMapping("/ydphtk")
    public String ydphtk() {
        return prefix+"/ydph_new";
    }

     /**
     * 分页查询用水平衡
     */
    @RequestMapping("/ydphTable")
    @ResponseBody
    @ClearPage
    public TableDataInfo ydphTable(AupTjElectricityByYearMonth tj) {
        /*获取合计行*/
        AupTjElectricityByYearMonth totalRow = electricityByYearMonthService.queryAupTjElectricityByParamsTotalRow(tj);
        /*按照年份和月份排序*/
        startPage().setOrderBy("e.year,e.month");
        List<AupTjElectricityByYearMonth> tjelectricityByYearMonths = electricityByYearMonthService.queryAupTjElectricityByParams(tj);
        return getDataTable(tjelectricityByYearMonths,totalRow);
    }

    /**
     * 用电平衡导出
     * @param tj 统计
     * @return 结果
     */
    @RequestMapping("/ydphTable/export")
    @ResponseBody
    public AjaxResult ydExport(AupTjElectricityByYearMonth tj) {
        List<AupTjElectricityByYearMonth> tjelectricityByYearMonths = electricityByYearMonthService.queryAupTjElectricityByParams(tj);

        ExcelUtil<AupTjElectricityByYearMonth> util = new ExcelUtil<>(AupTjElectricityByYearMonth.class);
        return util.exportExcel(tjelectricityByYearMonths, "用电平衡");
    }


    /**
     * 加载查询用水平衡
     */
    @RequestMapping("/ysphtk")
    public String ysphtk() {
        return prefix+"/ysph_new";
    }

    /**
     * 加载查询用水平衡
     */
    @RequestMapping("/ysphTable")
    @ResponseBody
    @ClearPage
    public TableDataInfo ysphTable(AupTjWaterSideByYearMonth tj) {
        /*获取合计*/
        AupTjWaterSideByYearMonth totalRow = waterSideByYearMonthService.queryAupTjByParamsTotalRow(tj);
        /*按照年份和月份排序*/
        startPage();
        List<AupTjWaterSideByYearMonth> tjWaterSideByYearMonthList = waterSideByYearMonthService.queryAupTjByParams(tj);
        return getDataTable(tjWaterSideByYearMonthList,totalRow);
    }

    /**
     * 用水平衡导出
     * @param tj 统计
     * @return 结果
     */
    @RequestMapping("/ysphTable/export")
    @ResponseBody
    public AjaxResult export(AupTjWaterSideByYearMonth tj) {
        List<AupTjWaterSideByYearMonth> tjWaterSideByYearMonthList = waterSideByYearMonthService.queryAupTjByParams(tj);

        ExcelUtil<AupTjWaterSideByYearMonth> util = new ExcelUtil<>(AupTjWaterSideByYearMonth.class);
        return util.exportExcel(tjWaterSideByYearMonthList, "用水平衡");
    }

    /**
     * 加载用水平衡级联
     */
    @GetMapping("/cascade/{id}")
    @ResponseBody
    public AjaxResult cascadeById(@PathVariable("id") Long id) {
       //根据父节点获取子节点
        AupWaterSide aupWaterSide = new AupWaterSide();
        aupWaterSide.setPid(id);
        aupWaterSide.setHasChildren(11);
        List<AupWaterSide> waterSideList = waterSideService.selectAupWaterSideList(aupWaterSide);
        return AjaxResult.success(waterSideList);
    }


    /*-----------------------------能源统计-----------------------------*/


    /**
     * 能源统计
     */
    @GetMapping("/nhtj")
    public String nhtj() {
        return prefix + "/nhtj";
    }

    /**
     * 查询树节点点击总的用水量
     */
    @RequestMapping("/queryTotalUseNumber")
    @ResponseBody
    public AjaxResult queryTotalEleUseNumber(@Param("cacheId")String cacheId,@Param("year")String year,@Param("important")String important) {
        Map<String,Object> params = new HashMap<>();
        params.put("cacheId",cacheId);
        params.put("year",year);
        params.put("important",important);
        Map<String,Object> result = aupBuildingSideService.queryTotalUseNumber(params);
        return AjaxResult.success(result);
    }

    /**
     * 查詢當前年份的沒一個月的用量以及上一年度每一個月份的用電量
     */
    @RequestMapping("/queryMonthUseNumber")
    @ResponseBody
    public AjaxResult queryMonthUseNumber(@Param("cacheId")String cacheId,@Param("year")String year,@Param("important")String important) {
        Map<String,Object> params = new HashMap<>(16);
        params.put("cacheId",cacheId);
        params.put("year",year);
        params.put("important",important);
        List<Map<String,Object>> resultList = waterSideByYearMonthService.queryMonthUseWater(params);
        return AjaxResult.success(resultList);
    }


    /**
     * 查詢當前年份的沒一個月的用量以及上一年度每一個月份的用電量
     */
    @RequestMapping("/querySideUseNumber")
    @ResponseBody
    public AjaxResult querySideUseNumber(@Param("cacheId")String cacheId,@Param("year")String year,@Param("important")String important) {
        Map<String,Object> params = new HashMap<>();
        params.put("cacheId",cacheId);
        params.put("year",year);
        params.put("important",important);
        List<Map<String,Object>> resultList = waterSideByYearMonthService.querySideUseNumber(params);
        return AjaxResult.success(resultList);
    }

    /**
     * 重点建筑
     * 水电能源统计
     */
    @GetMapping("/nhtjzdjz")
    public String nhtj(@Param("year") int year, @Param("address") String address, @Param("code") String code, @Param("level_") int level, @Param("important") String important, ModelMap mmap) {
        mmap.put("useMeter", screenYxjkService.nhjgImportantUseMeter(year,address,code,level,important));
        return prefix + "/nhtj";
    }




    /**
     * 20200410修改能耗统计-面积水耗，电耗
     * @param cacheId 地区
     * @param year 年份
     * @return 结果
     */
    @ResponseBody
    @GetMapping("/nhtj/echarts/area")
    public AjaxResult nhjgEchartsArea(@Param("cacheId") String cacheId, @Param("year") String year,@Param("important") String important) {
        Map<String, Object> map = new HashMap<>();
        map.put("cacheId", cacheId);
        map.put("year", year);
        map.put("important",important);
        return AjaxResult.success(iAupNhjgService.queryAreaUseNumber(map));
    }




    /**
     * 用水详情
     */
    @RequestMapping("/ysxq")
    public String ysxq(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/ysxq";
    }

    /**
     * 用电详情
     */
    @RequestMapping("/ydxq")
    public String ydxq(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/ydxq";
    }

    /*-----------------------------定位-----------------------------*/


    @GetMapping("/position")
    public String position(@Param("year") int year,@Param("code") String code,  @Param("name") String name, @Param("cacheId") String cacheId, @Param("important") String important,ModelMap mmap) {
        UseMeter useMeter = new UseMeter();
        if ("1".equals(important)){
            useMeter = screenYxjkService.nhjgImportantUseMeter(year, cacheId, code,4,important);
        }else {
            useMeter = screenYxjkService.nhjgUseMeter(year, cacheId, code);
        }
        // UseMeter useMeter = screenYxjkService.nhjgUseMeter(year, cacheId, code);
        //总用量
        String totalNum = useMeter.getTotalNum();
        //当月用量
        String currtNum = useMeter.getCurrtNum();
        //上月用量
        String ultimoNum = useMeter.getUltimoNum();
        //所辖表具
        String hascount = useMeter.getHascount();
        //抄表时间
        String readtime = useMeter.getReadtime();
        UseMeter position = new UseMeter();
        if ("roote".equals(code) || "rootw".equals(code)) {
            position = new UseMeter(totalNum, currtNum,ultimoNum,hascount,readtime,code,name);
        }
        mmap.put("dw", position);
        return prefix + "/nhdw";
    }

    /**
     * 水表定位框
     * @return 页面
     */
    @GetMapping("/waterPosition")
    public String waterPosition() {
        return prefix + "/nhdw_new_water";
    }

    /**
     * 水表定位框
     * @return 结果
     */
    @ResponseBody
    @GetMapping("/waterPositionInfo")
    public AjaxResult waterPositionInfo(@Param("cacheId") String cacheId, @Param("year") String year,@Param("important") String important) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("cacheId", cacheId);
        map.put("year", year);
        map.put("important",important);
        return AjaxResult.success(waterSideByYearMonthService.queryRootUseWater(map));
    }

    /**
     * 水表定位框
     * @return 结果
     */
    @ResponseBody
    @GetMapping("/waterHousePositionInfo")
    public AjaxResult waterHousePositionInfo(@Param("cacheId") String cacheId, @Param("year") String year) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("cacheId", cacheId);
        map.put("year", year);
        return AjaxResult.success(waterSideByYearMonthService.queryRootHouseUseWater(map));
    }
}