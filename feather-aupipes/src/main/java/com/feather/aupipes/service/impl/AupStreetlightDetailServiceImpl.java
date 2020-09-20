package com.feather.aupipes.service.impl;

import com.feather.aupipes.HttpClient.HttpClinetLights;
import com.feather.aupipes.domain.AupStreetlight;
import com.feather.aupipes.domain.AupStreetlightDetail;
import com.feather.aupipes.mapper.AupStreetlightDetailMapper;
import com.feather.aupipes.service.IAupStreetlightDetailService;
import com.feather.aupipes.service.IAupStreetlightService;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 灯具Service业务层处理
 *
 * @author fancy
 * @date 2020-03-05
 */
@Service
public class AupStreetlightDetailServiceImpl implements IAupStreetlightDetailService {
    @Autowired
    private AupStreetlightDetailMapper aupStreetlightDetailMapper;

    @Autowired
    private IAupStreetlightService aupStreetlightService;

    /**
     * 查询灯具
     *
     * @param luid 灯具ID
     * @return 灯具
     */
    @Override
    public AupStreetlightDetail selectAupStreetlightDetailByIuid(String luid) {
        List<AupStreetlightDetail> aupStreetlightDetails = aupStreetlightDetailMapper
                .selectAupStreetlightDetailByIuid(luid);
        if (aupStreetlightDetails.size() > 0) {
            return aupStreetlightDetails.get(0);
        }
        return new AupStreetlightDetail();
    }

    /**
     * 查询灯具列表
     *
     * @param aupStreetlightDetail 灯具
     * @return 灯具
     */
    @Override
    public List<AupStreetlightDetail> selectAupStreetlightDetailList(AupStreetlightDetail aupStreetlightDetail) {
        return aupStreetlightDetailMapper.selectAupStreetlightDetailList(aupStreetlightDetail);
    }

    /**
     * 新增灯具
     *
     * @param aupStreetlightDetail 灯具
     * @return 结果
     */
    @Override
    public int insertAupStreetlightDetail(AupStreetlightDetail aupStreetlightDetail) {
        return aupStreetlightDetailMapper.insertAupStreetlightDetail(aupStreetlightDetail);
    }


    /**
     * 是否存在同一时间的数据
     * 
     * @param aupStreetlightDetail 路灯详情对象
     * @return 真假
     */
    @Override
    public boolean checkAupStreetlightDetailByIuid(AupStreetlightDetail aupStreetlightDetail) {
        List<AupStreetlightDetail> aupStreetlightDetailList = aupStreetlightDetailMapper
                .checkAupStreetlightDetailByIuid(aupStreetlightDetail);
        return aupStreetlightDetailList.size() > 0;
    }

    /**
     * 定时请求路灯监控数据并存入数据库
     */
    @Override
    public void addTimeLightDetail() {
        // 获取所有路灯
        List<AupStreetlight> aupStreetlights = aupStreetlightService.selectAupStreetlightList(new AupStreetlight());
        for (AupStreetlight aupStreetlight:aupStreetlights) {
            JSONObject jb = HttpClinetLights.getLcuLight(aupStreetlight.getCuid(), aupStreetlight.getLuid());
            if (jb != null) {
                AupStreetlightDetail sd = new AupStreetlightDetail();
                sd.setCuid(jb.getString("cuid"));
                sd.setLtime(Long.parseLong(jb.getString("ltime")));
                sd.setLuid(jb.getString("luid"));
                sd.setU(jb.getDouble("u"));
                // if(this.checkAupStreetlightDetailByIuid(sd)){
                sd.setRs(jb.getString("rs"));
                sd.setSrc(jb.getString("src"));
                sd.setE(jb.getString("e"));
                sd.setDtime(jb.getLongObject("dtime"));
                sd.setNum(jb.getString("num"));
                sd.setLs(jb.getString("ls"));
                sd.setAlarms(jb.getString("alarms"));
                sd.setDim(jb.getString("dim"));
                sd.setRtime(jb.getLongObject("rtime"));
                sd.setI(jb.getDouble("i"));
                sd.setLu(jb.getString("lu"));
                sd.setLife(jb.getLongObject("life"));
                sd.setP(jb.getString("p"));
                sd.setCtype(jb.getString("ctype"));
                sd.setPf(jb.getString("pf"));
                sd.setRi(jb.getString("ri"));
                sd.setRp(jb.getString("rp"));
                this.insertAupStreetlightDetail(sd);
                // }

            }
        }

    }

    @Override
    public List<AupStreetlightDetail> selectAupStreetlightDetailExpList(String luid, String startTime, String endTime) {
        AupStreetlightDetail aupStreetlightDetail = new AupStreetlightDetail();
        if (luid != null) {
            aupStreetlightDetail.setLuid(luid);
        }
        if (startTime != null && !"".equals(startTime)) {
            // 将时间转换为long
            aupStreetlightDetail.setRtime(DateUtils.stringParseLong(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            // 将时间转换为long
            aupStreetlightDetail.setDtime(DateUtils.stringParseLong(endTime));
        }
        List<AupStreetlightDetail> streetlightDetails = aupStreetlightDetailMapper
                .selectAupStreetlightDetailExpList(aupStreetlightDetail);
        for (AupStreetlightDetail aupStreetlightDetail1 : streetlightDetails) {
            aupStreetlightDetail1.setName(aupStreetlightDetail1.getAupStreetlight().getName());
            aupStreetlightDetail1.setD_time(DateUtils.getLongToString(aupStreetlightDetail1.getLtime().toString()));

        }
        return streetlightDetails;

    }

    /**
     * 获取路灯实时监控值
     *
     * @param lid  路灯id
     * @return 结果
     */
    @Override
    public Map<String,Object> getAupStreeLightTimeMonitorByLid(String lid) {
        AupStreetlight aupStreetlight = aupStreetlightService.selectAupStreetlightById(lid);
        return getStreetLightTimeMontiorData(aupStreetlight);
    }

    /**
     * 获取路灯实时监控值
     * 
     * @param luid 控制器id
     * @return 结果
     */
    @Override
    public Map<String,Object> getAupStreeLightTimeMonitor(String luid) {
        if (luid.length() < 7) {
            luid = "0000000" + luid;
        }

        AupStreetlight aupStreetlight = aupStreetlightService.selectAupStreetlightByIuid(luid);
        return getStreetLightTimeMontiorData(aupStreetlight);
    }

    /**
     * 获取路灯实时监控值
     * 
     * @param aupStreetlight 路灯对象
     * @return 结果
     */
    private Map<String, Object> getStreetLightTimeMontiorData(AupStreetlight aupStreetlight) {
        // 实时获取路灯数据
        JSONObject jb = HttpClinetLights.getLcuLight(aupStreetlight.getCuid(), aupStreetlight.getLuid());
        Map<String, Object> map = new HashMap<>(16);
        int rs = 2;
        String nightTime = "19:00";
        String morningTime = "07:00";
        if (DateUtils.isBelongPeriodTime(nightTime, morningTime)) {
            rs = 0;
        }
        long life ;
        double i;
        double u;
        double p;
        double pf;
        double e;
        String states = rs == 2 ? "灭灯" : "亮灯";
        String jcsj;
        map.put("name", aupStreetlight.getName());
        if (jb != null) {
            rs = Integer.valueOf(jb.get("rs").toString());
            life = Long.parseLong(jb.get("life").toString());
            i = Double.parseDouble(jb.get("i").toString());
            u = Double.parseDouble(jb.get("u").toString());
            p = Double.parseDouble(jb.get("p").toString());
            pf = Double.parseDouble(jb.get("pf").toString());
            e = Double.parseDouble(jb.get("e").toString());
            states = rs == 2 ? "灭灯" : "亮灯";
            jcsj = DateUtils.getLongToString(jb.get("ltime").toString());
        } else {
            List<AupStreetlightDetail> d = this
                    .selectAupStreetlightDetailList(new AupStreetlightDetail(aupStreetlight.getLuid()));
            if (d.size() > 0) {
                AupStreetlightDetail f = d.get(0);
                i = f.getI();
                u = f.getU();
                rs = Integer.parseInt(f.getRs());
                states = rs == 2 ? "灭灯" : "亮灯";
                p = Double.parseDouble(f.getP());
                pf = Double.parseDouble(f.getPf());
                e = Double.parseDouble(f.getE());
                life = f.getLife();
                jcsj = DateUtils.getLongToString(f.getLtime().toString());
            } else {
                i = NumberUtils.getRandowForDouble(0.1, 1, 5);
                u = NumberUtils.getRandowForDouble(200, 258, 3);
                p = NumberUtils.getRandowForDouble(45, 50, 3);
                pf = NumberUtils.getRandowForDouble(0.1, 1, 5);
                e = NumberUtils.getRandowForDouble(20, 25, 5);
                jcsj = DateUtils.parseDateToStr("YYYY-MM-dd HH:mm:ss", new Date());
                life = 279620L;
            }
        }
        map.put("power", e / 1000);
        map.put("states", states);
        map.put("i", i);
        map.put("u", u);
        map.put("p", p);
        map.put("pf", pf);
        map.put("jcsj", jcsj);
        map.put("life", life);
        return map;
    }

    @Override
    public void deleteStreetlightDetailTime() {
        aupStreetlightDetailMapper.deleteStreetlightDetailTime();
    }

    @Override
    public Map<String, Object> getTypename(String typeId) {
        return aupStreetlightDetailMapper.getTypename(typeId);
    }

}