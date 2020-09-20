package com.feather.aupipes.controller.screen;

import com.feather.aupipes.domain.*;
import com.feather.aupipes.service.*;
import com.feather.common.annotation.ClearPage;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.domain.ZtreeNode;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大屏 子模块 - 运行监控
 */
@Controller
@RequestMapping("/screen/yxjk")
public class ScreenYxjkController extends BaseController {
    private String prefix = "/aupipes/screen/yxjk";

    @Autowired
    private IScreenYxjkService screenYxjkService;

    @Autowired
    private AupSearchService aupSearchService;

    @Autowired
    private IAupStreetlightService streetlightService;

    @Autowired
    private IAupStreetlightDetailService streetlightDetailService;

    @Autowired
    private IAupStreetlightControlService streetlightControlService;

    @Autowired
    private IAupPumpService pumpService;

    @Autowired
    private IAupLeakService leakService;

    @Autowired
    private IAupMonitorService monitorService;

    @Autowired
    private IAupBuildingAreaService aupBuildingAreaService;

    @Autowired
    private IAupWaterqualityService aupWaterqualityService;

    @Autowired
    private IAupWaterqualitySzjcService aupWaterqualitySzjcService;

    @Autowired
    private IAupWaterqualityLsjcService aupWaterqualityLsjcService;

    @Autowired
    private IAupBuildingService aupBuildingService;

    @Autowired
    private IAupLeakService aupLeakService;

    @Autowired
    private IAupPowerService powerService;

    @Autowired
    private IAupStreetlightDetailService aupStreetlightDetailService;

    @Autowired
    private IAupPumpPointService aupPumpPointService;

    @Autowired
    private IAupPumpPointStatusService aupPumpPointStatusService;

    @Autowired
    private IAupPumpPointBaseService aupPumpPointBaseService;

    @Autowired
    private IAupControlEnergyService aupControlEnergyService;

    /**
     * 运行监控
     */
    @GetMapping("/yxjk")
    public String yxjk() {
        return prefix + "/bjtj";
    }

    @ApiOperation("获取运行监控设备统计")
    @GetMapping("/sbtj")
    @ResponseBody
    public AjaxResult getSbtj() {
        Map<String, Object> map = new HashMap<>(16);
        // 根据20191228 提出需求从左到右，从上到下依次为配电房、泵房、楼栋、水表、电表、路灯、探漏、监控、水质
        // 设备统计
        map.put("sbtj", screenYxjkService.getOperationMonitorCount());
        return AjaxResult.success(map);
    }

    /*-----------------------------磅房-----------------------------*/

    /**
     * 泵房树结构
     */
    @ResponseBody
    @GetMapping("/jdxx/pump/tree")
    public List<Ztree> pumpTree(@Param("pid") Long id) {
        return  pumpService.getTree(id);
    }

    /**
     * 磅房基本信息表格
     */
    @ResponseBody
    @GetMapping("/jdxx/pump/base/table")
    public AjaxResult pumpBaseTable(@Param("id") Long id) {
        return AjaxResult.success(pumpService.getBaseTable(id));
    }

    /**
     * 磅房状态信息表格
     */
    @ResponseBody
    @GetMapping("/jdxx/pump/state/table")
    public AjaxResult pumpStateTable(@Param("id") Long id) {
        return AjaxResult.success(pumpService.getStateTable(id));
    }

    /**
     * 获取泵房的泵组个数
     */
    @ResponseBody
    @GetMapping("/jdxx/pump/point/{id}")
    public AjaxResult pumpPointCount(@PathVariable("id") Long id) {
        AupPumpPoint aupPumpPoint = new AupPumpPoint();
        aupPumpPoint.setPumpId(id);
        List<AupPumpPoint> pointList = aupPumpPointService.selectAupPumpPointList(aupPumpPoint);
        return AjaxResult.success(pointList);
    }

    /**
     * 泵房仪表盘表格
     */
    @ResponseBody
    @GetMapping("/jdxx/pump/echarts/gauge/{id}")
    public AjaxResult pumpEchartsGaugeTable(@PathVariable("id") Long id) {
        return AjaxResult.success(aupPumpPointBaseService.selectMaxTimeAupPumpPointBase(id));
    }

    /**
     * 泵房状态表格
     */
    @ResponseBody
    @GetMapping("/jdxx/pump/state/real/table/{id}")
    public AjaxResult pumpStatesTable(@PathVariable("id") Long id) {
        return AjaxResult.success(aupPumpPointStatusService.getAupPumpPointStatusByPoint(id));
    }

    /**
     * 磅房
     */
    @GetMapping("/jdxx/pump")
    public String pump(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/pump";
    }

    /*-----------------------------路灯-----------------------------*/

    /**
     * 路灯树结构
     */
    @ResponseBody
    @GetMapping("/jdxx/streetlight/tree")
    public List<Ztree> streetlightTree(@Param("pid_") String pid_) {
        return streetlightControlService.selectAupStreetlightTree(pid_);
    }

    /**
     * 路灯树结构
     */
    @ResponseBody
    @GetMapping("/jdxx/streetlight/tree/unline")
    public List<Ztree> streetlightTreeUnline(@Param("pid_") String pid_) {
        return  streetlightControlService.selectAupStreetlightTreeUnline(pid_);
    }

    /**
     * 获取所有离线的路灯
     */
    @ResponseBody
    @GetMapping("/jdxx/streetlight/unline")
    public AjaxResult getStreetLightUnline() {
        List<Map<String, Object>> getStreetLightUnlineList = streetlightControlService.queryStreetlightTimeStatus();
        return AjaxResult.success(getStreetLightUnlineList);
    }

    /**
     * 路灯页面
     */
    @GetMapping("/jdxx/streetlight")
    public String streetlight() {
        return prefix + "/streetlight";
    }

    /**
     * 路灯控制器页面
     */
    @GetMapping("/all/streetlightControl")
    public String allStreetlightControl() {
        return prefix + "/streetlight_new";
    }

    @GetMapping("/one/streetlightControl")
    public String streetlightControl() {
        return prefix + "/streetlightControl";
    }

    /**
     * 路灯控制器用电量，电流，亮灯数和各种控制器的状态
     * 
     * @param aupControlEnergy 参数
     * @return 结果
     */
    @ResponseBody
    @RequestMapping("/streelightControl/queryParams")
    public AjaxResult streetlightControlQueryParams(AupControlEnergy aupControlEnergy) {
        Map<String, Object> maps = aupControlEnergyService.selectAupControlEnergyByTime(aupControlEnergy);
        // 实时光照值和亮灯数
        Map<String, Object> mapss = screenYxjkService.queryStreetlightTime();
        maps.putAll(mapss);
        return AjaxResult.success(maps);
    }

    @ResponseBody
    @RequestMapping("/streelightControl/getAll")
    public AjaxResult streetlightControlGetAll() {
        JSONObject[] resultJson = screenYxjkService.getAllControlInfo();
        return AjaxResult.success(resultJson);
    }

    @ResponseBody
    @RequestMapping("/streelightControl/getOneById")
    public AjaxResult streetlightControlGetAll(@Param("sid") String sid, @Param("cuid") String cuid) {
        JSONObject resultJson = screenYxjkService.getControlInfoById(sid, cuid);
        AupStreetlightControl aupStreetlightControl = streetlightControlService.selectAupStreetlightControlById(sid);
        resultJson.put("breakers", aupStreetlightControl.getBreakers());
        resultJson.put("lucs", aupStreetlightControl.getLcus());
        return AjaxResult.success(resultJson);
    }

    /**
     * 路灯详情
     */
    @ResponseBody
    @GetMapping("/jdxx/streetlight/detail/{luid}")
    public AjaxResult streetlightDetail(@PathVariable("luid") String luid) {
        return AjaxResult.success(aupStreetlightDetailService.getAupStreeLightTimeMonitor(luid));
    }

    /**
     * 路灯检测数据
     */
    @RequestMapping("/jdxx/streetlight/jscj/table")
    @ResponseBody
    @ClearPage
    public TableDataInfo streetlightJcsjTable(String luid, String startTime, String endTime) {

        startPage().setOrderBy("ltime desc");
        List<AupStreetlightDetail> streetlightDetails = streetlightDetailService.selectAupStreetlightDetailExpList(luid,
                startTime, endTime);
        return getDataTable(streetlightDetails);

    }

    @RequestMapping("/streetlight/export")
    @ResponseBody
    public AjaxResult export(String luid, String startTime, String endTime) {
        List<AupStreetlightDetail> streetlightDetails = streetlightDetailService.selectAupStreetlightDetailExpList(luid,
                startTime, endTime);
        for (AupStreetlightDetail s : streetlightDetails) {
            if (s.getU() != 0 && s.getI() != 0 && s.getLife() != null) {
                double power = (s.getI() * s.getLife() * s.getU()) / 1000;
                s.setPower(power);
            }
        }
        ExcelUtil<AupStreetlightDetail> util = new ExcelUtil<>(AupStreetlightDetail.class);
        return util.exportExcel(streetlightDetails, "路灯监控数据");
    }

    /*-----------------------------探漏-----------------------------*/

    /**
     * 探漏树结构
     */
    @ResponseBody
    @GetMapping("/jdxx/leak/tree")
    public List<Ztree> leakTree() {
        return leakService.selectAupLeakTreeList(new AupLeak());
    }

    /**
     * 探漏
     */
    @GetMapping("/jdxx/leak/{placeId}")
    public String leak(@PathVariable("placeId") int placeId, ModelMap mmap) {
        AupLeak leak = leakService.selectAupLeakById(placeId);
        mmap.put("leak", leak);
        return prefix + "/leak";
    }

    /*-----------------------------监控-----------------------------*/

    /**
     * 监控
     */
    @GetMapping("/jdxx/monitor/{id}")
    public String monitor(@PathVariable("id") String id, ModelMap mmap) {
        AupRegionCamera aupRegionCamera = monitorService.selectAupRegionCameraBycameraIndexCode(id);
        mmap.put("camera", aupRegionCamera);
        return prefix + "/monitor";
    }

    /**
     * 监控树结构
     */
    @ResponseBody
    @GetMapping("/jdxx/monitor/tree")
    public List<ZtreeNode> monitorTree(@Param("pid") String pid) {
        return monitorService.getZtreeNode(pid);
    }

    /*-----------------------------检测-----------------------------*/

    /**
     * 检测数据
     */
    @GetMapping("/jcsj/{jclx}")
    public String jkss(@PathVariable("jclx") String jclx, @RequestParam(value = "id") String id, ModelMap mmap) {
        mmap.put("jclx", jclx);
        mmap.put("id", id);
        return prefix + "/jcsj";
    }

    /*-----------------------------配电房-----------------------------*/

    /**
     * 配电房树数据
     */
    @ResponseBody
    @GetMapping("/jdxx/powerHouse/tree")
    public List<Ztree> powerHouseTree(@Param("pid") Long pid) {
        return powerService.initZtreePower(pid);
    }

    /**
     * 配电房详情
     */
    @GetMapping("/jdxx/power/detail/{id}")
    public String powerDetail(@PathVariable("id") Long id, ModelMap mmap) {
        AupPower power = powerService.selectAupPowerById(id);
        mmap.put("power", power);
        return prefix + "/power";

    }

    /**
     * 根据BIM_ID查监控
     */
    @ResponseBody
    @GetMapping("/jdxx/monitorInfo/{bimId}")
    public AjaxResult monitorInfo(@PathVariable("bimId") String bimId) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("list", monitorService.selectAupRegionCameraByBIM_ID(bimId));
        return AjaxResult.success(map);
    }

    /*-----------------------------水表-----------------------------*/

    /**
     * 水电用户树
     * 
     * @param pid_ 父节点
     * @return 子节点集合
     */
    @ResponseBody
    @GetMapping("/meterTree")
    public List<Ztree> waterTree(@Param("pid_") String pid_) {
        return screenYxjkService.queryMeterAreaAll(pid_);
    }

    /**
     * 水电表离线树
     *
     * @param pid_ 父节点
     * @return 子节点集合
     */
    @ResponseBody
    @GetMapping("/meterTree/unline")
    public List<Ztree> waterTreeUnline(@Param("pid_") String pid_) {
        return screenYxjkService.queryMeterAreaUnline(pid_);
    }

    @GetMapping("/meter")
    public String meterInfo(ModelMap mmap) {
        return prefix + "/meter";
    }

    @ResponseBody
    @GetMapping("/meterInfo")
    public AjaxResult waterTree(@Param("typeid") String typeid, @Param("queryType") String queryType) {
        List<Map<String, Object>> mapList = screenYxjkService.queryMeterInfo(typeid, queryType);
        return AjaxResult.success(mapList);
    }




    /**
     * 水质
     */
    @ResponseBody
    @GetMapping("/jdxx/waterQuality/tree")
    public List<Ztree> waterQualityTree() {
        return aupWaterqualityService.getWaterQualityTree();
    }

    /**
     * 水质右侧页面
     */
    @GetMapping("/jdxx/waterQuality/page/{id}")
    public String waterQualityRightPage(@PathVariable("id") long oid, ModelMap mmap) {
        AupWaterquality w = aupWaterqualityService.selectAupWaterqualityById(oid);
        mmap.put("w", w);
        return prefix + "/sz";
    }

    @ResponseBody
    @GetMapping("/jdxx/waterQuality/{id}")
    public AjaxResult waterQualityRightDate(@PathVariable("id") long oid) {
        Map<String, Object> maps = new HashMap<>();
        AupWaterquality w = aupWaterqualityService.selectAupWaterqualityById(oid);
        AupWaterqualityLsjc lsjc = aupWaterqualityLsjcService.selectAupWaterqualityLsjcVoByOid(oid);
        AupWaterqualitySzjc szjc = aupWaterqualitySzjcService.selectAupWaterqualitySzjcByOid(oid);
        maps.put("lsjc", lsjc);
        maps.put("szjc", szjc);
        maps.put("type", w.getType());
        return AjaxResult.success(maps);
    }


    /**
     * 其他数据
     */
    @ResponseBody
    @GetMapping("/jdxx/other/tree")
    public List<Ztree> otherTree(@Param("pid") String pid) {
        return screenYxjkService.getOthertTree(pid);
    }

    /*-----------------------------定位-----------------------------*/

    @GetMapping("/position")
    public String position(@Param("id") String id, @Param("code") String code, @Param("a3333reaNo") String areaNo,
            ModelMap mmap) {
        Position position = new Position("西苑泵站泵房", "NO15452", "西苑泵站泵房", "");
        List<Position> msgs = new ArrayList<>();
        if ("powerHouse".equals(code)) {
            AupPower power = powerService.selectAupPowerById(Long.parseLong(id));
            position = new Position(power.getName(), power.getBsm(), power.getType() + "_" + power.getName(), code);

            msgs.add(new Position("配电房等级", power.getType()));
            msgs.add(new Position("配电房名称", power.getName()));
        }
        if ("building".equals(code)) {
            AupBuilding building = aupBuildingService.selectAupBuildingById(id);
            position = new Position(building.getName(), building.getNo(), building.getAreaname() + building.getName(),
                    code);
            msgs.add(new Position("楼栋名称", building.getName()));

        }
        if ("streetlight".equals(code)) {
            AupStreetlight aupStreetlight = streetlightService.selectAupStreetlightById(id);
            position = new Position(aupStreetlight.getName(), aupStreetlight.getName(), aupStreetlight.getControl(),
                    code);

            msgs.add(new Position("设备名称", aupStreetlight.getName()));

        }
        if ("streetlightControl".equals(code)) {
            AupStreetlightControl aupStreetlightControl = streetlightControlService.selectAupStreetlightControlById(id);
            position = new Position(aupStreetlightControl.getSname(), aupStreetlightControl.getCuid(), "", code);
            msgs.add(new Position("设备名称", aupStreetlightControl.getSname()));
            msgs.add(new Position("开关数", aupStreetlightControl.getBreakers()));
            msgs.add(new Position("终端数量", aupStreetlightControl.getLcus()));
        }
        if ("area".equals(code)) {
            AupBuildingArea area = aupBuildingAreaService.selectAupBuildingAreaByAreaNo(areaNo);
            position = new Position(area.getName(), area.getAreaNo(), area.getName(), code);
        }
        if ("leak".equals(code)) {
            AupLeak leak = aupLeakService.selectAupLeakById(Integer.parseInt(id));
            position = new Position(leak.getSiteno(), id, leak.getPlaceaddress(), code);

            msgs.add(new Position("设备编号", leak.getSiteno()));
            msgs.add(new Position("安装地址", leak.getPlaceaddress()));
        }
        if ("pump".equals(code)) {
            AupPump pump = pumpService.selectAupPumpById(Long.parseLong(id));
            position = new Position(pump.getName(), pump.getBfid().toString(), pump.getName(), code);

            msgs.add(new Position("泵房名称", pump.getName()));

        }
        if ("waterQuality".equals(code)) {
            AupWaterquality aupWaterquality = aupWaterqualityService.selectAupWaterqualityById(Long.parseLong(id));
            position = new Position(aupWaterquality.getSname(), aupWaterquality.getType(), aupWaterquality.getSname(),
                    code);

            msgs.add(new Position("名称", aupWaterquality.getSname()));
            msgs.add(new Position("类型", aupWaterquality.getType()));
        }
        if ("electricity".equals(code) || "water".equals(code)) {
            AupBuilding building = aupBuildingService.selectAupBuildingEnergyById(id);
            position = new Position(building.getName(), building.getNo(), building.getAreaname() + building.getName(),
                    code);
            msgs.add(new Position("详细地址", building.getAreaname() + building.getName()));
        }
        mmap.put("dw", position);
        mmap.put("mm", msgs);
        return prefix + "/dw";
    }

    /************************ 移动端运行监控接口 ***************************/
    @ApiOperation("获取运行监控设备信息")
    @ResponseBody
    @GetMapping("/app/query")
    public AjaxResult appYxjkQueryType(@Param("queryType") String queryType) {

        List<Map<String, Object>> mapList = aupSearchService.appYxjkQueryInfo(queryType);
        return AjaxResult.success(mapList);
    }

    /**
     * 路灯详情
     */
    @ApiOperation("获取路灯详情")
    @ResponseBody
    @GetMapping("/app/streetlight/detail/{lid}")
    public AjaxResult appStreetlightDetail(@PathVariable("lid") String lid) {
        return AjaxResult.success(aupStreetlightDetailService.getAupStreeLightTimeMonitorByLid(lid));
    }

    /**
     * 移动端探漏
     */
    @ApiOperation("获取探漏")
    @GetMapping("/app/leakApp/{placeId}")
    @ResponseBody
    public AjaxResult leakApp(@PathVariable("placeId") int placeId) {
        AupLeak leak = leakService.selectAupLeakById(placeId);
        return AjaxResult.success(leak);
    }

    /**
     * 移动端监控
     */
    @ApiOperation("获取监控")
    @GetMapping("/app/monitorApp/{id}")
    @ResponseBody
    public AjaxResult monitorApp(@PathVariable("id") String id) {
        AupRegionCamera aupRegionCamera = monitorService.selectAupRegionCameraBycameraIndexCode(id);
        return AjaxResult.success(aupRegionCamera);
    }

    @ApiOperation("获取路灯控制器")
    @RequestMapping("/app/streetController/{id}")
    @ResponseBody
    public AjaxResult getLight(@PathVariable("id") String id) {
        AupStreetlightControl aupStreetlightControl = new AupStreetlightControl();
        aupStreetlightControl.setSid(id);
        List<AupStreetlightControl> list = streetlightControlService
                .selectAupStreetlightControlList(aupStreetlightControl);
        if (list.size() > 0) {
            AupStreetlightControl aupStreetlightControl1 = list.get(0);
            JSONObject resultJson = screenYxjkService.getControlInfoById(aupStreetlightControl1.getSid(),
                    aupStreetlightControl1.getCuid());
            return AjaxResult.success(resultJson);
        }
        return null;
    }

}
