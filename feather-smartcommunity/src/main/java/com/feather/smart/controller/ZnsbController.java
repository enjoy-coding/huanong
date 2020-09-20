package com.feather.smart.controller;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.smart.domain.ZhsqZnsb;
import com.feather.smart.service.IZhsqZnsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/znsb")
public class ZnsbController extends BaseController {

    private final static String URL="http://api.jiai.pro:8080/jiai/";

    private final static String CPID="1279";

    private final static String KEY="fca3b9d3e31afb3e90e8bc67dc8fdb85";

    //手表串号
    private final static String IMEI="356201199014211";

    private final static String JSON="{\"imei\":\"356201199014211\"}";

    @Autowired
    IZhsqZnsbService zhsqZnsbService;

    /**
     * 获取心率测量请求
     * @return
     */
    @PostMapping("/{name}")
    @ResponseBody
    public AjaxResult health (@PathVariable("name")String name,@RequestBody String requestBody){
        String id = UUID.randomUUID().toString().replace("-","");
        JSONObject jsonObject = JSONObject.parse(requestBody);
        if(jsonObject.size()<=0){
            return  AjaxResult.error("推送数据为空");
        }
        String imei = jsonObject.get("imei").toString();
        Date dates = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(dates);
        if("health".equals(name)){

            System.out.println("串口号："+imei);
            String heartRate = jsonObject.get("heartRate").toString();
            System.out.println("心率 ："+heartRate);
            String dbp = jsonObject.get("dbp").toString();
            System.out.println("低压："+dbp);
            String sdp = jsonObject.get("sdp").toString();
            System.out.println("高压："+sdp);
            String oxygen = jsonObject.get("oxygen").toString();
            System.out.println("血氧 ："+oxygen);
            String bloodSugar = jsonObject.get("bloodSugar").toString();
            System.out.println("血糖 ："+bloodSugar);
            String temperature = jsonObject.get("temperature").toString();
            System.out.println("温度 ："+temperature);
            ZhsqZnsb znsb = new ZhsqZnsb();
            znsb.setImei(imei);
            znsb.setBloodsugar(bloodSugar);

            znsb.setId(id);
            znsb.setCreatedate(date);
            znsb.setDbp(dbp);
            znsb.setSdp(sdp);
            znsb.setHeartrate(heartRate);
            znsb.setOxygen(oxygen);
            znsb.setType("health");
            znsb.setTemperature(temperature);

            zhsqZnsbService.insertZhsqZnsb(znsb);
            return AjaxResult.success("推送血压心率数据成功");
        }
        if("location".equals(name)){
            System.out.println("串口号："+imei);
            String type = jsonObject.get("type").toString();
            System.out.println("地图类型 ："+type);
            String lat = jsonObject.get("lat").toString();
            System.out.println("高德坐标纬度 ："+lat);
            String lon = jsonObject.get("lon").toString();
            System.out.println("高德坐标经度 ："+lon);
            ZhsqZnsb znsb = new ZhsqZnsb();
            znsb.setImei(imei);
            znsb.setType("location");
            znsb.setLat(lat);
            znsb.setLon(lon);

            znsb.setCreatedate(date);
            znsb.setId(id);
            zhsqZnsbService.insertZhsqZnsb(znsb);
            return AjaxResult.success("推送定位数据成功");
        }
        return null;
    }


}