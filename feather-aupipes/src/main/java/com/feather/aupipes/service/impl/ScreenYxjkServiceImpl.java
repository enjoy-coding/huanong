package com.feather.aupipes.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.feather.aupipes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.HttpClient.HttpClinetLights;
import com.feather.aupipes.domain.AupArea;
import com.feather.aupipes.domain.AupBuildingSide;
import com.feather.aupipes.domain.AupSbtj;
import com.feather.aupipes.domain.NTAgriculturalUniversityCDForEnergyByBuilding;
import com.feather.aupipes.domain.UseMeter;
import com.feather.aupipes.mapper.AupBuildingSideMapper;
import com.feather.aupipes.mapper.AupScreenMapper;
import com.feather.aupipes.mapper.NTAgriculturalUniversityCDForEnergyByBuildingMapper;
import com.feather.common.core.domain.Ztree;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.NumberUtils;
import com.feather.common.utils.bean.BeanUtils;

@Service
public class ScreenYxjkServiceImpl implements IScreenYxjkService {
    @Autowired
    private IAupMonitorService monitorService;

    @Autowired
    private IAupIndexService indexService;

    @Autowired
    private IAupLeakService leakService;

    @Autowired
    private AupScreenMapper screen;

    @Autowired
    private IAupIndexService aupIndexService;

    @Autowired
    private IAupStreetlightService aupStreetlightService;

    @Autowired
    private NTAgriculturalUniversityCDForEnergyByBuildingMapper ntAgriculturalUniversityCDForEnergyByBuildingMapper;

    @Autowired
    private AupBuildingSideMapper aupBuildingSideMapper;

    @Autowired
    private AupAreaService aupAreaService; 

    @Override
    public List<Map<String, Object>> getOperationMonitorCount() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        AupSbtj sbtjZx = screen.sbtjZx();
        AupSbtj sbtjZs = screen.sbtjZs();
        Map<String,Object> streetLightCount = aupStreetlightService.getStreetLightCount();
        int sumOnline =Integer.parseInt(streetLightCount.get("sumOnline").toString());
        int sum =Integer.parseInt(streetLightCount.get("sum").toString());
        mapList.add(aupIndexService.getEquipmtCount(sbtjZx.getPdf(), sbtjZs.getPdf()));
        mapList.add(aupIndexService.getEquipmtCount(sbtjZx.getBf(), sbtjZs.getBf()));
        mapList.add(aupIndexService.getEquipmtCount(sbtjZx.getLf(), sbtjZs.getLf()));
        mapList.add(indexService.getEquipmtCount(sbtjZx.getMeterCount(), sbtjZs.getMeterCount()));
        mapList.add(aupIndexService.getEquipmtCount(sumOnline, sum));
        mapList.add(leakService.getCount(sbtjZx.getTl(), sbtjZs.getTl()));
        mapList.add(monitorService.getCount(sbtjZx.getJk(), sbtjZs.getJk()));
        mapList.add(aupIndexService.getEquipmtCount(sbtjZx.getSz(), sbtjZs.getSz()));

        return mapList;
    }

    /**
     * 查询路灯实时光照值和亮灯数
     * 
     * @return
     */
    @Override
    public Map<String, Object> queryStreetlightTime() {
        Map<String, Object> maps = new HashMap<>();
        Map<String,Object> streetLightCount = aupStreetlightService.getStreetLightCount();
        int sumOnline =Integer.parseInt(streetLightCount.get("sumOnline").toString());
        int sum =Integer.parseInt(streetLightCount.get("sum").toString());
        maps.put("lightcount", sumOnline);
        maps.put("totlacount", sum);
        HttpClinetLights httpClinetLights = new HttpClinetLights();
        JSONObject[] luxs = httpClinetLights.getLightLux();
        double luxsTotal = 0;
        for (int i = 0; i < luxs.length; i++) {
            JSONObject object = luxs[i];
            luxsTotal += Double.parseDouble(object.get("lux").toString());
        }
        luxsTotal = NumberUtils.formatNumber(luxsTotal / 1000, 2);
        maps.put("luxsTotal", luxsTotal);
        return maps;
    }


    @Override
    public List<Ztree> getOthertTree(String code) {
        List<Ztree> ztreeList = new ArrayList<>();
        ztreeList.add(new Ztree(1L, 0L, "区域一(10/18)", "区域一(10/18)", true));
        ztreeList.add(new Ztree(2L, 1L, "泵房", "泵房", false));
        ztreeList.add(new Ztree(3L, 1L, "路灯", "路灯", false));
        ztreeList.add(new Ztree(4L, 1L, "视频", "视频", false));
        ztreeList.add(new Ztree(5L, 1L, "探漏", "探漏", false));

        return ztreeList;
    }

    /**
     * 能耗总用量月用量环比
     * 
     * @param id
     * @param address
     * @param code
     * @return
     */
    @Override
    public UseMeter nhjgUseMeter(int id, String address, String code) {

        NTAgriculturalUniversityCDForEnergyByBuilding ntzl = new NTAgriculturalUniversityCDForEnergyByBuilding();
        NTAgriculturalUniversityCDForEnergyByBuilding ntdy = new NTAgriculturalUniversityCDForEnergyByBuilding();
        NTAgriculturalUniversityCDForEnergyByBuilding ntsy = new NTAgriculturalUniversityCDForEnergyByBuilding();
        // 类型
        // if ("water".equals(code)) {
        // ntAgriculturalUniversityCDForEnergyByBuilding1.setEnergytype(0);
        // } else if ("electricity".equals(code)) {
        // ntAgriculturalUniversityCDForEnergyByBuilding1.setEnergytype(1);
        // }
        // 年份
        if (!"null".equals(id)) {
            ntzl.setYear(id);
        }

        // 区域
        // if ("null".equals(address)) {
        // ntAgriculturalUniversityCDForEnergyByBuilding1.setCacheid(null);
        // } else {
        // ntAgriculturalUniversityCDForEnergyByBuilding1.setCacheid(address);
        // }
        ntzl.setCacheid(address);
        List<NTAgriculturalUniversityCDForEnergyByBuilding> ntzls = ntAgriculturalUniversityCDForEnergyByBuildingMapper
                .selectByPrimaryKey(ntzl);

        // 求和总用量
        BigDecimal result2 = ntzls.stream().map(NTAgriculturalUniversityCDForEnergyByBuilding::getUsenumber)
                // 使用reduce()聚合函数,实现累加器
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double v = result2.doubleValue();
        DecimalFormat df1 = new DecimalFormat("0.00");
        String s2 = df1.format(v);

        // 求当月用量
        // 区域
        ntdy.setCacheid(address);
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String format = sdf.format(date);
        // 年份
        String[] split = format.split("-");
        // 当前月份
        int i2 = Integer.parseInt(split[1]);
        // 上一月份
        int i3 = i2 - 1;
        // String s3 = Integer.toString(i);
        ntdy.setYear(id);
        ntdy.setMonth(i2);
        List<NTAgriculturalUniversityCDForEnergyByBuilding> ntdys = ntAgriculturalUniversityCDForEnergyByBuildingMapper
                .selectByPrimaryKey(ntdy);
        String usenumber = null;
        String hascount = null;
        String readtime = null;
        for (NTAgriculturalUniversityCDForEnergyByBuilding ntAgriculturalUniversityCDForEnergyByBuilding : ntdys) {
            usenumber = ntAgriculturalUniversityCDForEnergyByBuilding.getUsenumber().toString();
            // 所辖水电表
            hascount = ntAgriculturalUniversityCDForEnergyByBuilding.getHascount().toString();
            // 超表时间
            Date readtime1 = ntAgriculturalUniversityCDForEnergyByBuilding.getReadtime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (readtime1 != null) {
                readtime = format1.format(readtime1);
            } else {
                readtime = "";
            }

        }
        if (usenumber == null) {
            usenumber = "0";
        }

        // 环比
        ntsy.setCacheid(address);
        // 类型
        ntsy.setYear(id);
        ntsy.setMonth(i3);
        List<NTAgriculturalUniversityCDForEnergyByBuilding> ntsys = ntAgriculturalUniversityCDForEnergyByBuildingMapper
                .selectByPrimaryKey(ntsy);
        String usenumber2 = null;
        for (NTAgriculturalUniversityCDForEnergyByBuilding ntAgriculturalUniversityCDForEnergyByBuilding : ntsys) {
            usenumber2 = ntAgriculturalUniversityCDForEnergyByBuilding.getUsenumber().toString();
        }
        if (usenumber2 == null) {
            usenumber2 = "0";
        }
        double v1 = Float.parseFloat(usenumber);
        String s3 = df1.format(v1);
        double v2 = Float.parseFloat(usenumber2);
        String sy = df1.format(v2);
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(1);
        String result = null;
        if ("0".equals(sy) || "0.00".equals(sy)) {
            result = numberFormat.format(100);
        } else {
            result = numberFormat.format((v1 - v2) / v2 * 100);
        }
        String substring = result.substring(0, 1);
        String sj;
        if ("-".equals(substring)) {
            sj = "0";
        } else {
            sj = "1";
        }
        UseMeter useMeter = new UseMeter(s2, usenumber, result + "%");

        if ("rootw".equals(code)) {
            useMeter = new UseMeter(s2, "(吨)", "总用水量", s3, result + "%", sj, usenumber2, hascount, readtime, code);
        } else if ("roote".equals(code)) {
            useMeter = new UseMeter(s2, "(度)", "总用电量", s3, result + "%", sj, usenumber2, hascount, readtime, code);
        }
        return useMeter;
    }

    /**
     * 重点建筑物 能耗总用量月用量环比
     * 
     * @param address
     * @param code
     * @return
     */
    @Override
    public UseMeter nhjgImportantUseMeter(int year, String address, String code, int level_, String important) {

        AupBuildingSide aupBuildingSide = new AupBuildingSide();
        // aupBuildingSide.setImportant(important);
        aupBuildingSide.setCacheId(address);
        // aupBuildingSide.setLevel_(level_);
        List<AupBuildingSide> aupBuildingSides = aupBuildingSideMapper.selectAupBuildingSideZdjzList(aupBuildingSide);
        NTAgriculturalUniversityCDForEnergyByBuilding ntzyl = new NTAgriculturalUniversityCDForEnergyByBuilding();
        ntzyl.setYear(year);
        ntzyl.setAupBuildingSideList(aupBuildingSides);
        List<NTAgriculturalUniversityCDForEnergyByBuilding> ntzyllist = null;
        DecimalFormat df = new DecimalFormat("0.00");
        String zyl = "";
        if (aupBuildingSides.size() == 0) {
            zyl = "0.00";
        } else {
            ntzyllist = ntAgriculturalUniversityCDForEnergyByBuildingMapper.selectByPrimaryImportantKey(ntzyl);
            // 求和总用量
            BigDecimal result2 = ntzyllist.stream().map(NTAgriculturalUniversityCDForEnergyByBuilding::getUsenumber)
                    // 使用reduce()聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            double v3 = result2.doubleValue();
            zyl = df.format(v3);
        }

        // 当月用量
        NTAgriculturalUniversityCDForEnergyByBuilding ntdyyl = new NTAgriculturalUniversityCDForEnergyByBuilding();
        ntdyyl.setYear(year);
        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String format = sdf.format(date);
        // 年份
        String[] split = format.split("-");
        // 当前月份
        int i2 = Integer.parseInt(split[1]);
        // 上一月份
        int i3 = i2 - 1;
        ntdyyl.setMonth(i2);
        ntdyyl.setAupBuildingSideList(aupBuildingSides);
        List<NTAgriculturalUniversityCDForEnergyByBuilding> ntdyyllist = null;
        String dyyl = "";
        double v4 = 0;
        if (aupBuildingSides.size() == 0) {
            dyyl = "0.00";
        } else {
            ntdyyllist = ntAgriculturalUniversityCDForEnergyByBuildingMapper.selectByPrimaryImportantKey(ntdyyl);
            BigDecimal result3 = ntdyyllist.stream().map(NTAgriculturalUniversityCDForEnergyByBuilding::getUsenumber)
                    // 使用reduce()聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            v4 = result3.doubleValue();
            dyyl = df.format(v4);
        }
        // 超表时间
        String readtime = null;
        String hascount = "0";
        // 所辖表具
        if (aupBuildingSides.size() == 0) {
            readtime = "0";
        } else {
            hascount = ntdyyllist.stream()
                    .collect(Collectors.summingInt(NTAgriculturalUniversityCDForEnergyByBuilding::getHascount))
                    .toString();
            for (NTAgriculturalUniversityCDForEnergyByBuilding ntAgriculturalUniversityCDForEnergyByBuilding : ntzyllist) {
                Date readtime1 = ntAgriculturalUniversityCDForEnergyByBuilding.getReadtime();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (readtime1 != null) {
                    readtime = format1.format(readtime1);
                } else {
                    readtime = "";
                }
            }
        }
        // 环比
        NTAgriculturalUniversityCDForEnergyByBuilding nthb = new NTAgriculturalUniversityCDForEnergyByBuilding();
        nthb.setMonth(i3);
        nthb.setAupBuildingSideList(aupBuildingSides);
        nthb.setYear(year);
        List<NTAgriculturalUniversityCDForEnergyByBuilding> nthbllist = null;

        String hbl = "";
        double v5 = 0;
        if (aupBuildingSides.size() == 0) {
            hbl = "0";
        } else {
            nthbllist = ntAgriculturalUniversityCDForEnergyByBuildingMapper.selectByPrimaryImportantKey(nthb);
            BigDecimal result4 = nthbllist.stream().map(NTAgriculturalUniversityCDForEnergyByBuilding::getUsenumber)
                    // 使用reduce()聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            v5 = result4.doubleValue();
            // 同比上月使用量
            hbl = df.format(v5);
        }

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(1);
        String result = null;
        if ("0".equals(hbl) || "0.00".equals(hbl)) {
            result = numberFormat.format(100);
        } else {
            result = numberFormat.format((v4 - v5) / v5 * 100);
        }
        String substring = result.substring(0, 1);
        String sj;
        if ("-".equals(substring)) {
            sj = "0";
        } else {
            sj = "1";
        }
        UseMeter useMeter = new UseMeter(zyl, dyyl, result + "%");

        if ("rootw".equals(code)) {
            useMeter = new UseMeter(zyl, "(吨)", "总用水量", dyyl, result + "%", sj, hbl, hascount, readtime, code);
        } else if ("roote".equals(code)) {
            useMeter = new UseMeter(zyl, "(度)", "总用电量", dyyl, result + "%", sj, hbl, hascount, readtime, code);
        }
        return useMeter;
    }

    @Override
    public List<Map<String, Object>> queryMeterInfo(String typeid, String queryType) {
        Map<String, Object> maps = new HashMap<String,Object>();
        maps.put("typeid", typeid);
        maps.put("queryType", queryType);
        return aupAreaService.queryMeterInfo(maps);
    }

     /**
     * 水电用户离线树
     */
    @Override
    public List<Ztree> queryMeterAreaUnline(String pid){
        AupArea aupArea = new AupArea();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("meterState", 0);
        aupArea.setPid(pid == null ? "root" : pid);
        aupArea.setParams(params);
        List<AupArea> areaList = aupAreaService.queryMeterArea(aupArea);
        return this.initZtree(areaList);
    }
    /**
     * 水电用户全部树
     */
    @Override
    public List<Ztree> queryMeterAreaAll(String pid){
        AupArea aupArea = new AupArea();
        aupArea.setPid(pid == null ? "root" : pid);
        List<AupArea> areaList = aupAreaService.queryMeterArea(aupArea);
        return this.initZtree(areaList);
    }

    /**
     * 初始化树
     */
    private List<Ztree> initZtree(List<AupArea> areaList){
        List<Ztree> ztrees = new ArrayList<>();
        for (AupArea m : areaList) {
            Ztree ztree = new Ztree();
            ztree.setId_(m.getId());
            ztree.setPid_(m.getPid());
            ztree.setTitle(m.getName());
            ztree.setCode(m.getAreaNo());
            ztree.setName(m.getName());
            ztree.setMaps(BeanUtils.beanToMap(m));
            int level = Integer.parseInt(m.getLevel());
            ztree.setLevel(level);
            if (level == 4) {
                ztree.setIsParent(false);
            } else {
                ztree.setIsParent(true);
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }
    /**
     * 获取所有控制器信息
     */
    @Override
    public JSONObject[] getAllControlInfo() {
        HttpClinetLights httpClinetLights = new HttpClinetLights();
        HttpClinetLights.login();
        return httpClinetLights.getControlInfo();
    }

    @Override
    public JSONObject getControlInfoById(String sid, String cuid) {
        HttpClinetLights httpClinetLights = new HttpClinetLights();
        HttpClinetLights.login();
        return httpClinetLights.getControlInfoByCuid(cuid, sid);
    }
     
}
