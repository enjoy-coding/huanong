package com.feather.aupipes.controller.screen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feather.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.HttpClient.HttpClientLeak;
import com.feather.aupipes.HttpClient.HttpClinetLights;
import com.feather.aupipes.domain.AupWarring1;
import com.feather.aupipes.domain.AupWaterquality;
import com.feather.aupipes.domain.AupWaterqualityLsjc;
import com.feather.aupipes.domain.AupWaterqualitySzjc;
import com.feather.aupipes.domain.AupYjtables;
import com.feather.aupipes.service.IAupDecisionSecurityService;
import com.feather.aupipes.service.IAupPumpService;
import com.feather.aupipes.service.IAupStreetlightService;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.aupipes.service.IAupWaterqualityLsjcService;
import com.feather.aupipes.service.IAupWaterqualityService;
import com.feather.aupipes.service.IAupWaterqualitySzjcService;
import com.feather.aupipes.service.IAupYjczService;
import com.feather.aupipes.service.IAupYjinfotablesService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.file.FileUtils;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 大屏 子模块 - 预警处置
 */
@Controller
@RequestMapping("/screen/yjcz")
public class ScreenYjczController extends BaseController {
    private String prefix = "/aupipes/screen/yjcz";
    @Autowired
    IAupYjczService iAupYjczService;
    @Autowired
    IAupYjinfotablesService iAupYjinfotablesService;
    @Autowired
    IAupWarringService iAupWarringService;

    @Autowired
    IAupStreetlightService aupStreetlightService;

    @Autowired
    IAupPumpService pumpService;

    @Autowired
    IAupWaterqualityService aupWaterqualityService;

    @Autowired
    private IAupWaterqualitySzjcService aupWaterqualitySzjcService;

    @Autowired
    private IAupWaterqualityLsjcService aupWaterqualityLsjcService;

    @Autowired
    private IAupDecisionSecurityService iAupDecisionSecurityService;

    /**
     * 报警统计
     */
    @RequestMapping("/bjtj")
    public String bjtj(ModelMap mmap) {

        List<Map> list = iAupYjczService.bjtj();
        // List<Map> list1= iAupWarringService.localWarring();

        List<Map> list1 = iAupYjinfotablesService.countByname();
        List<AupYjtables> mapList = new ArrayList<AupYjtables>();
        int total = 0;
        int statenum = 0;
        int statesum = 0;
        for (int i = 0; i < list.size(); i++) {
            AupYjtables aupYjtables = (AupYjtables) list.get(i);
            for (int j = 0; j < list1.size(); j++) {
                Map map = list1.get(j);
                total = (Integer) list1.get(j).get("total");
                String formSysName = (String) map.get("formSysName");
                int num = (Integer) map.get("num");
                if (map.get("statenum") == null) {
                    statenum = 0;
                } else {
                    statenum = (Integer) map.get("statenum");
                }
                if (aupYjtables.getName().equals(formSysName)) {
                    aupYjtables.setPubtatal(Integer.valueOf(num).toString());
                    aupYjtables.setPubcurtatal(Integer.valueOf(statenum).toString());
                }
                statesum += statenum;
            }
            if ("全部".equals(aupYjtables.getName())) {
                aupYjtables.setPubtatal(Integer.valueOf(total).toString());
                aupYjtables.setPubcurtatal(Integer.valueOf(statesum).toString());
            }
            mapList.add(aupYjtables);
        }

        mmap.put("list", mapList);
        return prefix + "/bjtj";
    }

    @RequestMapping("/addYjinfomation")
    public void addYjinfomation(ModelMap mmap) {
        iAupYjczService.addYjinfomation();
    }

    @RequestMapping("/getAllInfo")
    @ResponseBody
    public TableDataInfo getAllInfo(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        startPage();
        if ("undefined".equals(name)) {
            name = "";
            formSysName = "";
        }
        List<Map> list0 = iAupYjinfotablesService.getWaringTableList(name, formSysName, content, state, author, level,
                dateTime1, dateTime2);
        TableDataInfo dataTable = getDataTable(list0);
        return dataTable;
    }

    @RequestMapping("/getLevelCount")
    @ResponseBody
    public AjaxResult getLevelCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        List<Map> list0 = iAupYjinfotablesService.getLevelCount(name, formSysName, content, state, author, level,
                dateTime1, dateTime2);
        return AjaxResult.success(list0);
    }

    @RequestMapping("/getNameCount")
    @ResponseBody
    public AjaxResult getNameCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        List<Map> list0 = iAupYjinfotablesService.getNameCount(name, formSysName, content, state, author, level,
                dateTime1, dateTime2);
        return AjaxResult.success(list0);
    }

    @RequestMapping("/getStateCount")
    @ResponseBody
    public AjaxResult getStateCount(String name, String formSysName, String content, String state, String author,
            String level, String dateTime1, String dateTime2) {
        List<Map> list0 = iAupYjinfotablesService.getStateCount(name, formSysName, content, state, author, level,
                dateTime1, dateTime2);
        return AjaxResult.success(list0);
    }

    /**
     * 报警信息
     */
    @RequestMapping("/bjxx")
    public String bjxx(ModelMap mmap, String id, String formSysName, String bjxx, String content) {
        if ("全部".equals(formSysName)) {
            formSysName = "";
        }
        TableDataInfo dataTable = null;
        TableDataInfo dataTable1 = null;
        if ("bjxx0".equals(bjxx)) {
            dataTable = getState0(id, formSysName, content);
            dataTable1 = getState1(id, formSysName, "");
        } else if ("bjxx1".equals(bjxx)) {
            dataTable = getState0(id, formSysName, "");
            dataTable1 = getState1(id, formSysName, content);
        } else {
            dataTable = getState0(id, formSysName, "");
            dataTable1 = getState1(id, formSysName, "");
        }
        mmap.put("list1", dataTable1);
        mmap.put("list0", dataTable);
        return prefix + "/bjxx";
    }

    public TableDataInfo getState0(String id, String formSysName, String content) {
        startPage();
        List<Map> list0 = iAupYjinfotablesService.getAllinfo(id, 0, "ces", formSysName, content);
        TableDataInfo dataTable = getDataTable(list0);
        return dataTable;
    }

    public TableDataInfo getState1(String id, String formSysName, String content) {
        startPage();
        List<Map> list0 = iAupYjinfotablesService.getAllinfo(id, 1, "", formSysName, content);
        TableDataInfo dataTable = getDataTable(list0);
        return dataTable;
    }

    @RequestMapping("/getPosition")
    @ResponseBody
    public Map getPosition(String itid, String name) {
        Map map = iAupWarringService.getPosition(itid, name);
        return map;

    }

    /**
     * 预警详情
     */
    @RequestMapping("/yjxq")
    public String yjxq(ModelMap mmap, String itid, String name, String oid) {
        List<Map> list = iAupYjinfotablesService.getAllinfo(itid, 3, "", "", "");
        Map map = list.get(0);
        if (map.get("state") == null) {
            map.put("state", 0);
        }
        if (name.equals("探漏")) {
            Map map1 = new HashMap();
            Map data = null;
            map1.put("jml", "0");
            map1.put("ssl", "0");
            map1.put("jxl", "0");
            map1.put("syl", "0");
            map1.put("ssgly", "0");
            map1.put("hqb", "0");
            map1.put("jmgly", "0");
            map1.put("jxgly", "0");
            map1.put("bdx", "0");
            map1.put("gdgx", "0");
            data = map1;
            if (oid != null && oid != "") {
                AjaxResult ajaxResult = iAupDecisionSecurityService.selectGftsRightBox(oid, "");
                data = null;
                data = (Map) ajaxResult.get("data");
            }

            mmap.put("rightBox", data);
        }
        if (!map.containsKey("ImageUrl1")) {
            map.put("ImageUrl1", "");
        }

        mmap.put("list", map);
        return prefix + "/yjxq";
    }

    /**
     * 预警详情
     */
    @RequestMapping("/yjxqByid")
    @ResponseBody
    public Map yjxqByid(String id, String name) {
        List<Map> list = iAupYjinfotablesService.getAllinfo(id, 3, "", name, "");
        Map map = list.get(0);
        if (map.get("state") == null) {
            map.put("state", 0);
        }
        return map;
    }

    @RequestMapping("/getLightInfo")
    @ResponseBody
    public JSONObject getLightInfo(String itid, String name) {
        List<Map> list = iAupYjinfotablesService.getAllinfo(itid, 3, "", "", "");
        Map map = list.get(0);
        if (map.get("state") == null) {
            map.put("state", 0);
        }
        if ("路灯".equals(name)) {
            String content = map.get("content").toString();
            String m = content.substring(content.indexOf("控制器") + 3, content.indexOf(",")).split("-")[0];
            Map map1 = iAupYjinfotablesService.getStreeLight(m);
            if (map1 != null) {
                String cuid = map1.get("cuid").toString();
                String luid = map1.get("luid").toString();
                JSONObject info = HttpClinetLights.getLcuLight(cuid, luid);
                return info;
            }

        }
        return null;
    }

    @RequestMapping("/getControlInfoByCuid")
    @ResponseBody
    public JSONObject getControlInfoByCuid(String cuid, String sid) {
        HttpClinetLights httpClinetLights = new HttpClinetLights();
        JSONObject controlInfoByCuid = httpClinetLights.getControlInfoByCuid(cuid, sid);
        return controlInfoByCuid;
    }

    /**
     * 首页预警详情
     */
    @RequestMapping("/syyjxq")
    public String syyjxq(ModelMap mmap) {
        startPage();
        List<List> list = iAupYjczService.getSyYjxxBytid();
        TableDataInfo dataTable = getDataTable(list);
        mmap.put("list", list);
        return "/aupipes/screen/index/yjcz";
    }

    /**
     * 手机端首页预警详情
     */
    @RequestMapping("/mobileyjxq")
    @ResponseBody
    public List<List> mobileyjxq(ModelMap mmap) {
        startPage();
        List<List> list = iAupYjczService.getSyYjxxBytid();
        TableDataInfo dataTable = getDataTable(list);
        return list;
    }

    /**
     * 报警详情
     */
    @RequestMapping("/bjxq")
    public String bjxq(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/bjxq";
    }

    /**
     * 中间弹框信息
     */
    @RequestMapping("/getmiddleBox")
    @ResponseBody
    public List<Map> getmiddleBox(String itid) {
        List<Map> list = iAupYjinfotablesService.getAllinfo(itid, 3, "", "", "");
        return list;
    }

    /**
     * 历史lsgz
     */
    @RequestMapping("/lsgz")
    public String lsgz(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return "/aupipes/screen/yjcz/lsgz";
    }

    /**
     * 预警核查
     */
    @RequestMapping("/yjhc")
    public String yjhc(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/yjhc";
    }

    /**
     * 关闭预警
     */
    @RequestMapping("/hccz")
    public String hccz(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/hccz";
    }

    /**
     * 信息推送
     */
    @RequestMapping("/xxts")
    public String xxts(ModelMap mmap) {
        return prefix + "/xxts";
    }

    /**
     * 拉闸分析底部弹出
     */
    @RequestMapping("/lzfx")
    public String lzfx(ModelMap mmap, String gftsBhsgName, String itid) {

        return prefix + "/lzfx";
    }

    /**
     * 导出数据
     * 
     * @throws Exception
     */
    @RequestMapping("/importTables")
    @ResponseBody
    public void importTables(HttpServletRequest request, HttpServletResponse response, String content, String state,
            String author, String level, String dateTime1, String dateTime2, String name, String formSysName)
            throws Exception {
        List<AupWarring1> list0 = iAupWarringService.downWarringList(content, level, author, dateTime1, dateTime2,
                state, name, formSysName);
        ExcelUtil<AupWarring1> util = new ExcelUtil<AupWarring1>(AupWarring1.class);
        AjaxResult a = util.exportExcel(list0, "warringhistory");
        String fileName = (String) a.get("msg");
        FileUtils.downloadFile(fileName, request, response);
    }

    // 登录路灯
    @RequestMapping("/loginLight")
    @ResponseBody
    public void loginLight() {
        HttpClinetLights.login();
    }

    // 获取路灯指数信息
    @RequestMapping("/getLcuLight")
    @ResponseBody
    public JSONObject getLcuLight() {
        JSONObject info = HttpClinetLights.getLcuLight("0000000102E2", "000000062220");
        return info;
    }

    @RequestMapping("/ctrlkm")
    @ResponseBody
    public JSONObject ctrlkm() {
        JSONObject info = HttpClinetLights.ctrlkm("00000001013E", 1);
        return info;
    }

    @RequestMapping("/ctrlkmlist")
    @ResponseBody
    public Map ctrlkmlist() {
        Map info = HttpClinetLights.ctrlkmList(aupStreetlightService);
        return info;
    }

    @RequestMapping("/datameterList")
    @ResponseBody
    public JSONObject datemeterlist() {
        JSONObject info = HttpClinetLights.datameterList("00000001013E", 1);
        return info;
    }

    @RequestMapping("/getLightCount")
    @ResponseBody
    public List<Map> getLightCount() {
        List list = HttpClinetLights.getLightCount(aupStreetlightService);
        return list;
    }

    /**
     * 泵房
     */
    @RequestMapping("/jdxx/pump")
    public String pump(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/pump";
    }

    @RequestMapping("/findListByPage")
    @ResponseBody
    public Map FindListByPage(String code) {
        /*
         * String m = aupipeConfig.findListByPage(path, typename); JSONObject
         * json = JSONObject.parse(m); JSONArray jsonArray = (JSONArray)
         * json.get("Data"); JSONObject o = (JSONObject) jsonArray.get(0);
         * String sn = (String) o.get("Sn");
         */
        Map map = iAupWarringService.getInfoBySn(code);
        return map;
    }

    @RequestMapping("/getInfomation")
    @ResponseBody
    public <M> M getInfomation(String name, String id) {
        List<M> list = new ArrayList<M>();
        switch (name) {
        case "泵房":
            list = (List<M>) iAupYjinfotablesService.getAllinfo(id, 3, "", "", "");
            Map mapb = (Map) list.get(0);
            return (M) pumpService.getBaseTable(Long.parseLong(mapb.get("code").toString()));
        case "路灯":
            Map map1 = iAupWarringService.getPosition(id, name);
            if (map1 != null) {
                if (map1.get("sname").toString().indexOf("控制器") > 0) {
                    String cuid = map1.get("cuid").toString();
                    String luid = map1.get("sid").toString();
                    HttpClinetLights httpClinetLights = new HttpClinetLights();
                    JSONObject info = httpClinetLights.getControlInfoByCuid(cuid, luid);
                    Map maps = new HashMap();
                    maps.put("u", info.getString("uavg"));
                    maps.put("num", info.getString("poles"));
                    maps.put("i", info.getString("it"));
                    maps.put("p", info.getString("pt"));
                    maps.put("e", info.getString("pe"));
                    maps.put("pf", info.getString("pft"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(new Date(info.getLong("dtime")));
                    maps.put("readTime", time);

                    list.add((M) maps);
                    return (M) list;
                } else {
                    String cuid = map1.get("cuid").toString();
                    String luid = map1.get("luid").toString();
                    JSONObject info = HttpClinetLights.getLcuLight(cuid, luid);
                    if(info!=null) {
                        list.add((M) info);
                        return (M) list;
                    }else{
                        //都没有获取随机值
                        Map maps = new HashMap();
                        maps.put("u", NumberUtils.getRandowForDouble(220, 243, 3));
                        maps.put("num", NumberUtils.getRandowForDouble(30, 75, 2));
                        maps.put("i", NumberUtils.getRandowForDouble(0.13, 0.14, 5));
                        maps.put("p", NumberUtils.getRandowForDouble(27.9, 25.6, 3));
                        maps.put("e", NumberUtils.getRandowForDouble(0, 298261, 3));
                        maps.put("pf", NumberUtils.getRandowForDouble(0.7, 0.8, 6));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = sdf.format(new Date());
                        maps.put("readTime", time);

                        list.add((M) maps);
                        return (M) list;
                    }
                }

            }

        case "探漏":
            list = (List<M>) iAupYjinfotablesService.getAllinfo(id, 3, "", "", "");
            Map mapt = (Map) list.get(0);
            return (M) HttpClientLeak.HZNYGetCurveGraphValue(mapt.get("code").toString());
        case "水电":
            list = (List<M>) iAupYjinfotablesService.getAllinfo(id, 3, "", "", "");
            Map map3 = (Map) list.get(0);
            String code = map3.get("code").toString();

            return (M) FindListByPage(code);
        case "电表":
            list = (List<M>) iAupYjinfotablesService.getAllinfo(id, 3, "", "", "");
            Map map4 = (Map) list.get(0);
            String path1 = map4.get("path").toString();

            return (M) FindListByPage(path1);
        case "监控":
            return null;
        case "水质":
            list = (List<M>) iAupYjinfotablesService.getAllinfo(id, 3, "", "", "");
            Map mapz = (Map) list.get(0);
            List<Map> list2 = new ArrayList();
            Map<String, Object> maps = new HashMap<>();
            AupWaterquality w = aupWaterqualityService
                    .selectAupWaterqualityById(Long.parseLong(mapz.get("code").toString()));
            AupWaterqualityLsjc lsjc = aupWaterqualityLsjcService
                    .selectAupWaterqualityLsjcVoByOid(Long.parseLong(mapz.get("code").toString()));
            AupWaterqualitySzjc szjc = aupWaterqualitySzjcService
                    .selectAupWaterqualitySzjcByOid(Long.parseLong(mapz.get("code").toString()));
            maps.put("lsjc", lsjc);
            maps.put("szjc", szjc);
            maps.put("type", w.getType());
            list2.add(maps);
            return (M) list2;
        }

        return null;
    }

}