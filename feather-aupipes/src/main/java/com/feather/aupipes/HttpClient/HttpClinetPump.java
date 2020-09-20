package com.feather.aupipes.HttpClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feather.aupipes.domain.AupPumpPoint;
import com.feather.aupipes.domain.AupPumpPointBase;
import com.feather.aupipes.domain.AupPumpPointStatus;
import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.domain.DevGroups;
import com.feather.aupipes.service.IAupPumpPointBaseService;
import com.feather.aupipes.service.IAupPumpPointService;
import com.feather.aupipes.service.IAupPumpPointStatusService;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import com.feather.common.utils.NumberUtils;
import com.feather.common.utils.bean.BeanUtils;

/**
 * 泵房接口
 */
@Component("pump")
public class HttpClinetPump {

    public static String cookies;

    private static String url = "http://211.69.132.74:8083";

    @Autowired
    private IAupPumpPointService pumpPointService;

    @Autowired
    private IAupPumpPointBaseService pumpPointBaseService;

    @Autowired
    private IAupPumpPointStatusService pumpPointStatusService;

    @Autowired
    private IAupWarringService aupWarringService;

    /**
     * 获取实时数据
     *
     * @param {
     *            "devGroups":[ { "dType":1, #固定 "devUnits":[ "xy1_zx1_C1" #位号
     *            必传 ] } ] }
     * @return
     */
    public static void getRealdata() {
        Map<String, Object> param = new HashMap<String, Object>();
        DevGroups devGroups = new DevGroups(1, new String[] { "xy1_zx1_C1" });
        List<DevGroups> devGroupsList = new ArrayList<>();
        devGroupsList.add(devGroups);
        param.put("devGroups", devGroupsList);
        String result = HttpClientUtil.httpPostWithJSON(url + "/scada/realdata", param);
        // System.out.println(result);
    }

    /**
     * 定时插入请求泵房的输出频率，水箱液位，出口压力，设定压力
     */
    public void getPumpPointBaseData() {
        List<AupPumpPoint> points = pumpPointService.selectAupPumpPointList(new AupPumpPoint());
        for (int i = 0; i < points.size(); i++) {
            String result = HttpClientUtil.httpPostWithjson("http://211.69.132.74:8083/scada/realdata",
                    pumpPointService.getPumpPointValue(points.get(i)));
            if (!"".equals(result)) {
                AupPumpPointBase base = getPumpPointBaseResult(result, points.get(i));
                if (base != null) {
                    // 判断当前点位信息是否存在，存在就修改,不存在就增加
                    AupPumpPointBase checkPoint = pumpPointBaseService
                            .selectAupPumpPointBaseByPointId(base.getPointId());
                    if (checkPoint != null) {
                        base.setId(checkPoint.getId());
                        pumpPointBaseService.updateAupPumpPointBase(base);
                    } else {
                        pumpPointBaseService.insertAupPumpPointBase(base);
                    }

                }
            }
        }
    }

    /**
     * 定时插入请求泵房的几个泵房的变频状态
     */
    public void getPumpPointStateData() {
        List<AupPumpPoint> points = pumpPointService.selectAupPumpPointList(new AupPumpPoint());
        for (int i = 0; i < points.size(); i++) {
            String result = HttpClientUtil.httpPostWithjson("http://211.69.132.74:8083/scada/realdata",
                    pumpPointService.getPumpPointStatesValue(points.get(i)));
            getPumpPointStateResult(result, points.get(i));
        }
    }

    /**
     * 根据返回值存储数据库
     * 
     * @param result
     * @param point
     * @return
     */
    public AupPumpPointBase getPumpPointBaseResult(String result, AupPumpPoint point) {
        JSONObject obj = JSONObject.parse(result);
        AupPumpPointBase appb = new AupPumpPointBase();
        if (obj.getInteger("status") == 200) {
            JSONArray data = JSONObject.parse(obj.getString("data"), JSONArray.class);
            if (data.size() > 0) {
                JSONObject dataObj = data.getJSONObject(0);
                JSONArray devGroupDatas = JSONObject.parse(dataObj.getString("devGroupDatas"), JSONArray.class);
                if (devGroupDatas.size() > 0) {
                    String time = devGroupDatas.getJSONObject(0).getString("time");
                    appb.setReadTime(time);
                }
                appb.setAttrName(point.getAttrName());
                String attrValue = "";
                for (int i = 0; i < devGroupDatas.size(); i++) {
                    JSONObject devGroupDatasObj = devGroupDatas.getJSONObject(i);
                    double devData = devGroupDatasObj.getDouble("devData");
                    String devUnit = devGroupDatasObj.getString("devUnit");
                    String s_value = devUnit.substring(devUnit.lastIndexOf("_") + 1);
                    if (s_value.equals("J1")) {
                        // 市政压力
                        appb.setSzyl(devData < 0 ? "无数据获取" : String.valueOf(devData));
                    }
                    attrValue += devData < 0 ? 0 : devData;
                    if (i < devGroupDatas.size()) {
                        attrValue += ",";
                    }
                }
                appb.setAttrValue(attrValue);
                appb.setPointId(point.getId());
                appb.setPumpId(point.getPumpId());
            }
            return appb;
        }
        return null;
    }

    /**
     * 根据返回值存储数据库
     * 
     * @param result
     * @param point
     * @return
     */
    public void getPumpPointStateResult(String result, AupPumpPoint point) {
        JSONObject obj = JSONObject.parse(result);
        if (obj != null && obj.getInteger("status") == 200) {
            JSONArray data = JSONObject.parse(obj.getString("data"), JSONArray.class);
            if (data.size() > 0) {
                JSONObject dataObj = data.getJSONObject(0);
                JSONArray devGroupDatas = JSONObject.parse(dataObj.getString("devGroupDatas"), JSONArray.class);
                if (point.getGp() != null && point.getGp().length() > 0) {
                    // 存在工频
                    existGpPumpStatus(devGroupDatas, point);
                } else {
                    notExistGpPumpStatus(devGroupDatas, point);
                }
            }
        }
    }

    /**
     * 工频不单独请求
     * 
     * @param devGroupDatas
     * @param point
     */
    public void notExistGpPumpStatus(JSONArray devGroupDatas, AupPumpPoint point) {
        for (int i = 0; i < devGroupDatas.size(); i++) {
            AupPumpPointStatus apps = new AupPumpPointStatus();
            JSONObject devGroupDatasObj = devGroupDatas.getJSONObject(i);
            String time = devGroupDatas.getJSONObject(0).getString("time");
            apps.setReadtime(time);
            double devData = devGroupDatasObj.getDouble("devData");
            String devUnit = devGroupDatasObj.getString("devUnit");
            apps.setStatusname(NumberUtils.toChinese2(String.valueOf(i + 1)) + "泵");
            String status = "";

            if (devData == 0) {
                status = "停止";
            } else if (devData == 2) {
                status = "工频";
            } else if (devData == 3) {
                status = "故障";
            } else {
                status = "变频";
            }

            apps.setStatus(status);
            apps.setPointId(point.getId());
            apps.setPumpId(point.getPumpId());
            apps.setPointCode(devUnit);
            if (pumpPointStatusService.selectAupPumpPointStatusByPointId(apps) != null) {
                Long id = pumpPointStatusService.selectAupPumpPointStatusByPointId(apps);
                apps.setId(id);
                pumpPointStatusService.updateAupPumpPointStatus(apps);
            } else {
                pumpPointStatusService.insertAupPumpPointStatus(apps);
            }
        }
    }

    /**
     * 工频单独请求
     * 
     * @param point
     * @param devGroupDatas
     *            xy1_zx1_G1
     * @param point
     * @return
     */
    public String existGpPumpStatus(JSONArray devGroupDatas, AupPumpPoint point) {
        // 如果工频单独请求，则存一条状态
        JSONArray reloadDevGroupDatas = new JSONArray();
        // 变频
        String pointBpNumber = point.getBp();
        // 工频
        String pointGpNumber = point.getGp();
        String[] statusArr = new String[pointBpNumber.split(",").length];
        for (int j = 0; j < pointBpNumber.split(",").length; j++) {
            String points = point.getPoint();
            // 变频
            String pointBpStr = points + "_" + pointBpNumber.split(",")[j];
            // 工频
            String pointGpStr = points + "_" + pointGpNumber.split(",")[j];
            String status = "停止";
            for (int i = 0; i < devGroupDatas.size(); i++) {
                JSONObject devGroupDatasObj = devGroupDatas.getJSONObject(i);
                double devData = devGroupDatasObj.getDouble("devData");
                String devUnit = devGroupDatasObj.getString("devUnit");
                if (pointGpStr.equals(devUnit)) {
                    if (devData == 1) {
                        status = "工频";
                    }
                }
                if (pointBpStr.equals(devUnit)) {
                    if (devData == 1) {
                        status = "变频";
                    }
                }
            }
            statusArr[j] = status;
        }
        for (int j = 0; j < pointBpNumber.split(",").length; j++) {
            for (int i = 0; i < devGroupDatas.size(); i++) {
                // 变频
                String pointBpStr = point.getPoint() + "_" + pointBpNumber.split(",")[j];
                if (pointBpStr.equals(devGroupDatas.getJSONObject(i).get("devUnit"))) {
                    String statusName = statusArr[i];
                    devGroupDatas.getJSONObject(i).put("statusName", statusName);
                    reloadDevGroupDatas.add(devGroupDatas.getJSONObject(i));
                }
            }
        }

        for (int i = 0; i < reloadDevGroupDatas.size(); i++) {
            AupPumpPointStatus apps = new AupPumpPointStatus();
            String time = reloadDevGroupDatas.getJSONObject(i).getString("time");
            String devUnit = reloadDevGroupDatas.getJSONObject(i).getString("devUnit");
            apps.setReadtime(time);
            apps.setStatusname(NumberUtils.toChinese2(String.valueOf(i + 1)) + "泵");
            apps.setStatus(reloadDevGroupDatas.getJSONObject(i).getString("statusName"));
            apps.setPointId(point.getId());
            apps.setPumpId(point.getPumpId());
            apps.setPointCode(devUnit);
            if (pumpPointStatusService.selectAupPumpPointStatusByPointId(apps) != null) {
                Long id = pumpPointStatusService.selectAupPumpPointStatusByPointId(apps);
                apps.setId(id);
                pumpPointStatusService.updateAupPumpPointStatus(apps);
            } else {
                pumpPointStatusService.insertAupPumpPointStatus(apps);
            }
        }
        return null;
    }

    /**
     * 获取历史数据
     *
     * @param {
     *            "devUnit":"xy1_zx1_C1", #位号，必传 "startTime":"2019-01-06
     *            11:00:00", #开始时间，必传 "dataCount":50, #查询次数，必传
     *            "secondSpace":"3000", #间隔时间,单位秒，必传 "dType":1 #固定 }
     * @return
     */
    public static void getHistorydata() {
        DevGroups devGroups = new DevGroups(1, "xy1_zx1_C1", "2019-01-06 11:00:00", 50, "3000");
        Map<String, Object> param = BeanUtils.beanToMap(devGroups);
        String result = HttpClientUtil.httpPostWithJSON(url + "/scada/historydata", param);
        // System.out.println(result);
    }

    public static void main(String[] args) {

        System.out.println();
    }

    //
    public static void loginPumb() {
        String urllogin = "http://211.69.132.74:9380/user/login?userPhone=15927210578&userPwd=123456";

        // JSONObject jb = JSONObject.parse(param);
        JSONObject result = HttpClientUtil.posts(urllogin);
        if (result != null) {
            JSONObject cookie = (JSONObject) result.get("cookie");
            cookies = cookie.get("JSESSIONID").toString();
        }
        // getPumbAlarmHistory();

    }

    public void getPumbAlarmHistory() {
        String url = "http://211.69.132.74:9380/warning/getHistoryAlarm?page=1&limit=20&type=0&area_id=0&keywords=";
        String entity = HttpClientUtil.getPump(url, cookies);
        JSONObject jb = JSONObject.parse(entity);
        String status = jb.getString("status");
        if (status.equals("403")) {
            loginPumb();
            getPumbAlarmHistory();
            return;
        }
        JSONArray ja = jb.getJSONArray("data");
        if (ja.size() > 0) {
            JSONObject jbs = (JSONObject) ja.get(0);
            JSONArray list = jbs.getJSONArray("list");
            // System.out.println(list);
            for (Object object : list) {
                JSONObject jb1 = (JSONObject) object;
                String path = ((JSONObject) jb1.get("pumpHouseBeans")).getString("pump_name");
                String createtime = jb1.getString("alarm_create_time");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
                try {
                    AupWarring aupWarring = new AupWarring();
                    Date date = sdf.parse(createtime);
                    Long time = date.getTime();
                    aupWarring.setId(jb1.getString("alarm_id") + String.valueOf(time));

                    if (path.equals("中心泵房")) {
                        aupWarring.setCode("3");
                    } else if (path.equals("西苑泵房")) {
                        aupWarring.setCode("2");
                    } else if (path.equals("人才楼")) {
                        aupWarring.setCode("4");
                        path = "青年" + path;
                    }
                    aupWarring.setPath(path);
                    aupWarring.setLevel(jb1.getString("alarm_level"));
                    String state = jb1.getString("alarm_status");
                    if (state.equals("1")) {
                        state = "0";
                    } else if (state.equals("2")) {
                        state = "1";
                    }
                    aupWarring.setState(state);
                    aupWarring.setAuthor("泵房系统");
                    aupWarring.setDatetime(createtime);

                    aupWarring.setFormsysname("泵房");
                    aupWarring.setContent(path + jb1.getString("alarm_desc"));

                    String name = jb1.getString("alarm_desc").substring(0, 2);
                    String name1 = "";
                    if (name.equals("市政")) {
                        name1 = "市政水压过低";
                    } else if (name.equals("出口")) {
                        name1 = "出口水压过低";
                    } else {
                        name1 = "水池液位过低";
                    }
                    aupWarring.setName(name1);
                    try {
                        aupWarringService.insertAupWarring(aupWarring);
                    } catch (Exception e) {
                        aupWarringService.updateAupWarring(aupWarring);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // aupWarring.setPath();
            }

        }

    }

}
