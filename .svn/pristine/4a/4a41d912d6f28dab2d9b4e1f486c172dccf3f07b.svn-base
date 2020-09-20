package com.feather.aupipes.service.impl;

import com.feather.aupipes.HttpClient.HttpClinetLights;
import com.feather.aupipes.domain.*;
import com.feather.aupipes.mapper.AupScreenMapper;
import com.feather.aupipes.service.*;
import com.feather.common.utils.Arith;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 首页
 */
@Service
public class AupIndexServiceImpl implements IAupIndexService {
    @Autowired
    private IAupStreetlightService streetlightService;

    @Autowired
    private IAupPlansManageService aupPlansManageService;

    @Autowired
    private AupScreenMapper screen;

    @Autowired
    private IAupPowerService aupPowerService;

    private int sum;
    private int sumOnline;
    private int sumUnOnline;

    /**
     * 设备统计
     * 
     * @return
     */
    @Override
    public List<Map<String, Object>> getEquipmentCount() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        AupSbtj sbtjZx = screen.sbtjZx();
        AupSbtj sbtjZs = screen.sbtjZs();
        this.getRealTimeLdqk();
        // 全部
        mapList.add(this.getEquipmtCount(sbtjZx.getPdf(), sbtjZs.getPdf()));
        // 泵房
        mapList.add(this.getEquipmtCount(sbtjZx.getBf(), sbtjZs.getBf()));
        // 路灯
        mapList.add(this.getEquipmtCount(sumOnline, sum));
        // 探漏
        mapList.add(this.getEquipmtCount(sbtjZx.getTl(), sbtjZs.getTl()));
        // 电表
        mapList.add(this.getEquipmtCount(sbtjZx.getDb(), sbtjZs.getDb()));
        // 水表
        mapList.add(this.getEquipmtCount(sbtjZx.getSb(), sbtjZs.getSb()));
        // 水质
        mapList.add(this.getEquipmtCount(sbtjZx.getSz(), sbtjZs.getSz()));
        // 监控
        mapList.add(this.getEquipmtCount(sbtjZx.getJk(), sbtjZs.getJk()));
        // 盛帆水表
        mapList.add(this.getEquipmtCount(sbtjZx.getSfWaterMeterCount(), sbtjZs.getSfWaterMeterCount()));
        // 盛帆电表
        mapList.add(this.getEquipmtCount(sbtjZx.getSfEleMeterCount(), sbtjZs.getSfEleMeterCount()));

        return mapList;
    }

    @Override
    public Map<String, Object> getEquipmtCount(int online, int total) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        String allPercent = Arith.percent(online, total);
        map.put("name", "盛帆表");
        map.put("code", "all");
        map.put("number", online);
        map.put("total", total);
        map.put("percent", allPercent);
        return map;
    }

    @Override
    public AupLdqk getRealTimeLdqk() {
        AupSbtj sbtjZx = screen.sbtjZx();
        AupSbtj sbtjZs = screen.sbtjZs();
        // HttpClinetLights.login();
        List<Map> d = HttpClinetLights.getLightCount(streetlightService);
        AupLdqk ldqk = new AupLdqk();
        if (d.size() > 0 && d.size() == 4) {
            sumOnline = Integer.parseInt(d.get(1).get("sumOnline").toString());
            sum = Integer.parseInt(d.get(1).get("sum").toString());
            sumUnOnline = sum - sumOnline;
            ldqk.setControllerOnlineCount(Integer.parseInt(d.get(0).get("kzqCountOnline").toString()));
            ldqk.setControllerTotalCount(Integer.parseInt(d.get(0).get("kzqCount").toString()));
            ldqk.setOpenLightTime(d.get(2).get("stimeOn").toString());
            ldqk.setCloseLightTime(d.get(2).get("stime").toString());
            ldqk.setPowerConsumption(d.get(3).get("dailyP").toString());
        } else {
            sum = sbtjZs.getLd();
            sumOnline = sbtjZx.getLd();
        }
        ldqk.setLightsCount(sumOnline);
        ldqk.setTotalCount(sum);
        return ldqk;
    }

    /**
     * 智能安防
     * 
     * @return
     */
    @Override
    public Map<String, Object> getSecurity() {
        AupZnaf znaf = screen.znaf();

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("accessControl", znaf.getMj());// 门禁
        map.put("waring", znaf.getHwxbj());// 红外线报警
        map.put("smokeAlarm", znaf.getYg());// 烟感
        map.put("camera", znaf.getSxt());// 摄像头
        map.put("animals", znaf.getXdwrq());// 小动物入侵
        return map;
    }

    /**
     * 用量统计
     * 
     * @return
     */
    @Override
    public Map<String, Object> getNhtjUseNumber() {
        Map<String, Object> wamp = screen.queryIndexTotalUseNumber("rootw");
        Map<String, Object> emap = screen.queryIndexTotalUseNumber("roote");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("water", wamp);
        map.put("ele", emap);
        return map;
    }



    /**
     * 预警处置
     * 
     * @return
     */
    @Override
    public ArrayList<String> getYjcz() {
        ArrayList<String> list = new ArrayList<>();
        String message1 = "2019-08-01 11:30:23荟园F1-F12设备损坏设备损坏设备损坏";
        String message2 = "2019-08-01 12:30:23荟园F2-F12设备损坏设备损坏设备损坏";
        String message3 = "2019-08-01 13:30:23荟园F1-F11设备损坏设备损坏设备损坏";
        String message4 = "2019-08-01 15:30:23荟园F1-F13设备损坏设备损坏设备损坏";
        list.add(message1);
        list.add(message2);
        list.add(message3);
        list.add(message4);
        return list;
    }

    @Override
    public int[] getWarringCount() {
        return new int[] { 90, 60, 75, 50, 60 };
    }

    /**
     * 路灯
     * 
     * @return
     */
    @Override
    public Map<String, Object> getLightsDetail() {
        return BeanUtils.beanToMap(this.getRealTimeLdqk());
    }

    /**
     * 预案总数
     * 
     * @return
     */
    @Override
    public Long getPlanNum() {
        AupPlansManage aupPlansManage = new AupPlansManage();
        Long count = aupPlansManageService.selectAupPlansManageCount(aupPlansManage);
        return count;
    }

    /**
     * 查询首页泵房水箱液位，出口压力，工作数信息
     * 弃用存储index_queryPumpNumber
     * @return
     */
    @Override
    public List<Map<String,Object>> queryPumpNumber(){
        List<Map<String,Object>> mList =  screen.queryPumpNumber();
        //提取出泵房名称
        List<Integer> pumpId = new ArrayList<Integer>();
        for (int i = 0; i < mList.size(); i++) {
            int id = Integer.parseInt(mList.get(i).get("id").toString());
            if(!pumpId.contains(id)){
                pumpId.add(id);
            }
        }
        //重组数组
        List<Map<String,Object>> newMList = new ArrayList<Map<String,Object>>();
        for (Integer i : pumpId) { //泵房个数
        Map<String,Object> newMaps = new HashMap<String,Object>();
        List<Map<String,Object>> attrs = new ArrayList<>();
        int workCount = 0 ;
          for (int j = 0; j < mList.size(); j++) { //泵房点位数
              Map<String,Object> mS =mList.get(j);
                if(i==Integer.parseInt(mS.get("id").toString())){
                    newMaps.put("pumpId",mS.get("id").toString());
                    newMaps.put("pumpName",mS.get("name").toString());
                    Map<String,Object> points = new HashMap<String,Object>();
                    List<Map<String,Object>> newAttrs  = new ArrayList<Map<String,Object>>();
                    //得到点位
                    String pointName = mS.get("point_name").toString();
                    //计算运行泵个数
                    workCount +=Integer.parseInt(mS.get("workCount").toString());
                    String [] attrNames = mS.get("attrName").toString().split(",");
                    //得到属性名称和属性值
                    for (int k = 0; k < attrNames.length; k++) {
                        Map<String,Object> newAttrMap = new HashMap<String,Object>();
                        if(attrNames[k].equals("水箱液位")||attrNames[k].equals("出口压力")||attrNames[k].equals("设定压力")){
                            newAttrMap.put("attrName",attrNames[k]);
                            newAttrMap.put("attrValue", mS.get("attrValue").toString().split(",")[k]);
                            newAttrs.add(newAttrMap);
                            points.put("attrs", newAttrs);
                        }
                       
                    }
                    points.put("pointName", pointName);
                    attrs.add(points);
                    newMaps.put("pointAttrs", attrs);
                    
                }
               newMaps.put("workCount", workCount);
            }
            newMList.add(newMaps);
        }
        return newMList;
    }

    /**
     * 一级配电房，回路数，变压器
     * 
     * @return
     */
    @Override
    public List<AupPower> queryPowerHouseInfo() {
        AupPower power = new AupPower();
        power.setLxbh(String.valueOf(1));
        return aupPowerService.selectAupPowerList(power);
    }


}
