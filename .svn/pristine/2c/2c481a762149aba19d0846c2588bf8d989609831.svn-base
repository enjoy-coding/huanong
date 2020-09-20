package com.feather.smart.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.smart.mapper.ZhsqZnsbMapper;
import com.feather.smart.domain.ZhsqZnsb;
import com.feather.smart.service.IZhsqZnsbService;
import com.feather.common.core.text.Convert;

/**
 * smartcommunityService业务层处理
 * 
 * @author fancy
 * @date 2020-09-03
 */
@Service
public class ZhsqZnsbServiceImpl implements IZhsqZnsbService 
{
    @Autowired
    private ZhsqZnsbMapper zhsqZnsbMapper;

    /**
     * 查询smartcommunity
     * 
     * @param id smartcommunityID
     * @return smartcommunity
     */
    @Override
    public ZhsqZnsb selectZhsqZnsbById(String id)
    {
        return zhsqZnsbMapper.selectZhsqZnsbById(id);
    }

    /**
     * 查询smartcommunity列表
     * 
     * @param zhsqZnsb smartcommunity
     * @return smartcommunity
     */
    @Override
    public List<ZhsqZnsb> selectZhsqZnsbList(ZhsqZnsb zhsqZnsb)
    {
        return zhsqZnsbMapper.selectZhsqZnsbList(zhsqZnsb);
    }

    /**
     * 新增smartcommunity
     * 
     * @param zhsqZnsb smartcommunity
     * @return 结果
     */
    @Override
    public int insertZhsqZnsb(ZhsqZnsb zhsqZnsb)
    {
        return zhsqZnsbMapper.insertZhsqZnsb(zhsqZnsb);
    }

    /**
     * 修改smartcommunity
     * 
     * @param zhsqZnsb smartcommunity
     * @return 结果
     */
    @Override
    public int updateZhsqZnsb(ZhsqZnsb zhsqZnsb)
    {
        return zhsqZnsbMapper.updateZhsqZnsb(zhsqZnsb);
    }

    /**
     * 删除smartcommunity对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqZnsbByIds(String ids)
    {
        return zhsqZnsbMapper.deleteZhsqZnsbByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除smartcommunity信息
     * 
     * @param id smartcommunityID
     * @return 结果
     */
    public int deleteZhsqZnsbById(String id)
    {
        return zhsqZnsbMapper.deleteZhsqZnsbById(id);
    }

    @Override
    public AjaxResult saveSb(String name,String requestBody) {
        String id = UUID.randomUUID().toString().replace("-","");
        JSONObject jsonObject = JSONObject.parse(requestBody);
        if(jsonObject.size()<=0){
            return  AjaxResult.error("推送数据为空");
        }
        System.out.println(jsonObject);
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

            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送血压心率数据成功");
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
            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送定位数据成功");
            return AjaxResult.success("推送定位数据成功");
        }

        if("warning".equals(name)){
            String warringType = jsonObject.get("warningType").toString();
            String battery = jsonObject.get("battery").toString();
            String address = jsonObject.get("address").toString();
            String longitude = jsonObject.get("longitude").toString();
            String latitude  = jsonObject.get("latitude").toString();

            ZhsqZnsb znsb = new ZhsqZnsb();
            String content="";
            if("2".equals(warringType)){
                content="用户可能摔倒了，请尽快确认！";
            }
            znsb.setContent(content);
            znsb.setId(id);
            znsb.setCreatedate(date);
            znsb.setImei(imei);
            znsb.setWarningtype(warringType);
            znsb.setBattery(battery);
            znsb.setAddress(address);
            znsb.setLatitude(latitude);
            znsb.setLongitude(longitude);
            znsb.setType("warning");
            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送预警数据成功");
            return AjaxResult.success("推送预警数据成功");
        }
        if("beatHeart".equals(name)){
            String battery = jsonObject.get("battery").toString();
            String pedometer = jsonObject.get("pedometer").toString();
            String jtmBattery = jsonObject.get("tmBattery").toString();
            System.out.println("电量 ："+battery);
            System.out.println("计步 ："+pedometer);
            System.out.println("温度 ："+jtmBattery);
            ZhsqZnsb znsb = new ZhsqZnsb();
            znsb.setId(id);
            znsb.setImei(imei);
            znsb.setCreatedate(date);
            znsb.setType("beatHeart");
            znsb.setBattery(battery);
            znsb.setPedometer(pedometer);
            znsb.setJtmbattery(jtmBattery);
            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送计步温度数据成功");
            return AjaxResult.success("推送计步温度数据成功");
        }
        if("audio".equals(name)){
            String audio = jsonObject.get("audio").toString();
            ZhsqZnsb znsb = new ZhsqZnsb();
            znsb.setId(id);
            znsb.setImei(imei);
            znsb.setAudio(audio);
            znsb.setCreatedate(date);
            znsb.setType("audio");
            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送语音数据成功");
            return AjaxResult.success("推送语音数据成功");
        }
        if("bluetooth".equals(name)){
            String blueTooth  = jsonObject.get("blueTooth").toString();
            ZhsqZnsb znsb = new ZhsqZnsb();
            znsb.setId(id);
            znsb.setImei(imei);
            znsb.setCreatedate(date);
            znsb.setType("bluetooth");
            znsb.setBluetooth(blueTooth);
            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送蓝牙数据成功");
            return AjaxResult.success("推送蓝牙数据成功");
        }
        if("confirm".equals(name)){
            String oriSeq   = jsonObject.get("oriSeq").toString();
            ZhsqZnsb znsb = new ZhsqZnsb();
            znsb.setId(id);
            znsb.setImei(imei);
            znsb.setType("confirm");
            znsb.setCreatedate(date);
            znsb.setOriseq(oriSeq );
            zhsqZnsbMapper.insertZhsqZnsb(znsb);
            System.out.println("推送确认数据成功");
            return AjaxResult.success("推送确认数据成功");
        }

        return null;
    }
}
