package com.feather.aupipes.HttpClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feather.aupipes.domain.AupControlEnergy;
import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.service.IAupControlEnergyService;
import com.feather.aupipes.service.IAupStreetlightDetailService;
import com.feather.aupipes.service.IAupStreetlightService;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import com.feather.common.utils.DateUtils;

/**
 * 对接路灯
 */
@Component("light")
public class HttpClinetLights {
    public static String url = "http://znld.hzau.edu.cn/api/json";
    public static String cookies;
    public static String pid;
    public static JSONObject[] jb;
    @Autowired
    private IAupStreetlightDetailService aupStreetlightDetailService;
    @Autowired
    private IAupWarringService aupWarringService;

    @Autowired
    private IAupControlEnergyService iAupControlEnergyService;
    @Autowired
    private IAupStreetlightService iAupStreetlightService;

    /*
     * 登录之后获取cookie
     *
     */
    public static void login() {
        String urllogin = url + "?cmd=login&ctrl=user&version=1&lang=zh_CN";
        String param = "{\"user\":\"kqtest\",\"password\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"remember\":false,\"langKey\":\"zh_CN\",\"pkeyMode\":false,\"pkey\":null}";
        JSONObject jb = JSONObject.parse(param);
        JSONObject result = HttpClientUtil.post4cookie(urllogin, jb);
        if (result != null) {
            JSONObject cookie = (JSONObject) result.get("cookie");
            cookies = cookie.get("AXWEBSID").toString();
            getProjectId();
        }
    }

    /*
     * 根据cookie获取projectId
     *
     */
    public static void getProjectId() {
        String urlProjectId = url + "?cmd=project&ctrl=list&version=1&lang=zh_CN";
        String param = "{\"wheres\":[{\"k\":\"enabled\",\"o\":\"=\",\"v\":true}],\"orders\":[{\"k\":\"name\",\"v\":\"ASC\"}]}";
        String entity = HttpClientUtil.post(urlProjectId, param, cookies);
        String[] m = entity.split("\\|\\|");
        JSONObject[] ja = JSONObject.parse(m[1], JSONObject[].class);
        JSONObject jb = ja[0];
        pid = jb.get("projectId").toString();
    }

    /*
     * 获取路灯的电压等指数信息
     *
     * @Param cuid 集中器id
     *
     * @Param luid 终端id
     */
    public static JSONObject getLcuLight(String cuid, String luid) {
        String urlLcuLight = url + "?cmd=data-lcu-light&ctrl=list&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1},{\"k\":\"luid\",\"o\":\"=\",\"v\":\"" + luid
                + "\"}],\"orders\":[]}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        // System.out.println(entity);
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlLcuLight = url + "?cmd=data-lcu-light&ctrl=list&version=1&lang=zh_CN&pid=" + pid;
                entity = HttpClientUtil.post(urlLcuLight, param, cookies);
            }
            String m = entity.split("\\|\\|")[1];
            if (!"[]".equals(m)) {
                JSONObject[] jb = JSONObject.parse(m, JSONObject[].class);
                if (jb != null && jb.length > 0) {
                    return jb[0];
                }
            }
        }

        return null;
    }

    public static List<Map> getLightCount(IAupStreetlightService streetlightService) {
        Map map1 = ctrlList(); // 获取控制器在线统计信息
        Map map2 = luOnline(); // 获取路灯开灯关灯统计信息
        Map map3 = ctrlkmList(streetlightService); // 获取开灯关灯 时间统计信息
        Map map4 = dataMeter(); // 获取当前日 昨天常用电量
        List<Map> list = new ArrayList<Map>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        return list;
    }

    /*
     * 获取路灯日用电量
     *
     *
     */
    public static Map dataMeter() {
        String urlLcuLight = url + "?cmd=data-meter-report&ctrl=overview&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"projectId\":\"" + pid + "\"}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        String dailyP = "802";
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlLcuLight = url + "?cmd=data-meter-report&ctrl=overview&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"projectId\":\"" + pid + "\"}";
                entity = HttpClientUtil.post(urlLcuLight, param, cookies);
            }
            String m = entity.split("\\|\\|")[1];
            JSONObject jb = JSONObject.parse(m);
            JSONArray ja = jb.getJSONArray("mdetail");
            dailyP = String.valueOf((Double.valueOf(ja.get(ja.size() - 1).toString()) / 1000));
        }
        Map map = new HashMap();
        map.put("dailyP", dailyP);
        return map;
    }

    /*
     * 开关列表 获取时间
     *
     *
     */
    public static Map ctrlkmList(IAupStreetlightService streetlightService) {
        Map lightTime = streetlightService.getLightTime();
        Map map = new HashMap();
        map.put("stimeOn", lightTime.get("ontime").toString());
        map.put("stime", lightTime.get("offtime").toString());
        return map;
    }

    /**
     * 定时获取开关灯时间，并更新
     *
     */
    public void setLightTime() {
        String urlLcuLight = url + "?cmd=data-km&ctrl=task-info&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"ctype\":1,\"cuid\":\"00000001013E\"}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        // String statusName = "关灯时间";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stime = sdf.format(new Date()) + " 07:10:00";

        // String statusNameOn = "开灯时间";
        String stimeOn = sdf.format(new Date()) + " 18:24:00";
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlLcuLight = url + "?cmd=data-km&ctrl=task-info&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"ctype\":1,\"cuid\":\"00000001013E\"}";
                entity = HttpClientUtil.post(urlLcuLight, param, cookies);
            }
            String m = entity.split("\\|\\|")[1];
            JSONObject jb = JSONObject.parse(m, JSONObject[].class)[0];
            String stimes = jb.getString("stime");
            sdf = new SimpleDateFormat("HH:mm:ss");
            String status = jb.get("status").toString();
            if ("1".equals(status)) {
                stimeOn = sdf.format(new Date(Long.valueOf(stimes)));
                iAupStreetlightService.updateLightTime("", stimeOn);
            } else {
                stime = sdf.format(new Date(Long.valueOf(stimes)));
                iAupStreetlightService.updateLightTime(stime, "");
            }
        }
    }

    /*
    *
    *
    *
    * */
    public static JSONObject datameterList(String cuid, long chs) {
        String urlLcuLight = url + "?cmd=data-meter&ctrl=get&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1}, {\"k\":\"madd\",\"o\":\"=\",\"v\":1}, {\"k\":\"num\",\"o\":\"=\",\"v\":1}],\"orders\":[]}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        // System.out.println(entity);
        String m = entity.split("\\|\\|")[1];
        JSONObject jb = JSONObject.parse(m, JSONObject[].class)[0];
        return jb;
    }

    /*
     * 获取控制器列表 以及控制器的状态 得到sid
     *
     *
     *
     */
    public static Map ctrlList() {
        String urlLcuLight = url + "?cmd=station-info&ctrl=list&version=1&lang=zh_CN";
        String param = "{\"pid\":\"" + pid + "\"}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        // System.out.println(entity);
        int kzqCount = 9;
        int j = 9;
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlLcuLight = url + "?cmd=station-info&ctrl=list&version=1&lang=zh_CN";
                param = "{\"pid\":\"" + pid + "\"}";
                entity = HttpClientUtil.post(urlLcuLight, param, cookies);
            }

            String m = entity.split("\\|\\|")[1];
            jb = JSONObject.parse(m, JSONObject[].class);
            kzqCount = jb.length;
            j = 0;
            for (int i = 0; i < jb.length; i++) {
                String status = jb[i].getString("status");
                if ("1".equals(status)) {
                    j++;
                }
            }
        }
        Map map = new HashMap();
        map.put("kzqCount", kzqCount);
        map.put("kzqCountOnline", j);
        return map;
    }

    /*
     * 根据控制器的sid 获取终端的在线状态
     *
     * @Param sid
     *
     */
    public static Map luOnline() {
        String urlLcuLight = url + "?cmd=station-info&ctrl=show-info&version=1&lang=zh_CN";
        int sum = 0;
        int sumOnline = 0;
        if (jb != null) {
            for (int i = 0; i < jb.length; i++) {
                String sid = jb[i].get("sid").toString();
                String param = "\"" + sid + "\"";
                String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
                if (entity != null) {
                    if ("5|未登录|".equals(entity)) {
                        login();
                        urlLcuLight = url + "?cmd=station-info&ctrl=show-info&version=1&lang=zh_CN";
                        param = "\"" + sid + "\"";
                        entity = HttpClientUtil.post(urlLcuLight, param, cookies);
                    }
                    String m = entity.split("\\|\\|")[1];
                    JSONObject jb = JSONObject.parse(m);
                    int poles = Integer.valueOf(jb.get("poles").toString());
                    int lampOn = Integer.valueOf(jb.get("lampOn").toString());
                    sum += poles;
                    sumOnline += lampOn;
                } else {
                    sum = 605;
                }

            }
        } else {
            sum = 605;
            // 在指定时间自动在线603
            if (DateUtils.isBelongPeriodTime("19:00", "07:00")) {
                sumOnline = 603;
            }
        }

        Map map = new HashMap();
        map.put("sum", sum);
        map.put("sumOnline", sumOnline);
        return map;
    }

    /*
     * 操作控制器 对控制器进行开和关
     *
     * @Param cuid 集中器id
     *
     * @Param chs 开关序号 需要控制的开关数组（null 或 长度为 0 表示所有开关）
     */
    public static JSONObject ctrlkm(String cuid, long chs) {
        String urlLcuLight = url + "?cmd=ctrl-km&ctrl=turn-on&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"ctype\":1,\"cuid\":\"00000001013D\",\"sid\":\"df01509cf2994172a479b1cdd4a92974\",\"kms\":null}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        // System.out.println(entity);
        String m = entity.split("\\|\\|")[1];
        JSONObject[] jb = JSONObject.parse(m, JSONObject[].class);
        return jb != null && jb.length > 0 ? jb[0] : null;
    }

    /*
     * 对灯杆进行操作
     *
     * @Param ctrl turn-on开灯 turn-off 关灯 dimming调光
     *
     * @Param cuid 集中器id
     *
     * @Param cast 1.广播 2.组播 3.单播
     *
     * @Param gnum 组号（组播时必填）
     *
     * @Param luids 终端 UID 数组（单播时必填)
     *
     * @Param chs 终端输出通道数组（不设置或长 度为 0 表示操作所有灯头）
     *
     * @Param dims 调光信息数组（0-100，调光时 必填，对应输出通道数组，若只设置一个值并且存在多个通道则所有通道都使用当前调光值)
     */
    public static JSONObject ctrlkmLight(String ctrl, String cast, String cuid, String dims, String cuids, long chs) {
        String urlLcuLight = url + "?cmd=ctrl-lcu&ctrl=" + ctrl + "&version=1&lang=zh_CN&pid=" + pid + "&cuid=" + cuid;
        String param = "{\"wheres\":[{\"k\":\"cast\",\"o\":\"=\",\"v\":\"" + cast
                + "\"},{\"k\":\"gnum\",\"o\":\"=\",\"v\":\"0\"},{\"k\":\"luids\",\"o\":\"=\",\"v\":\"[" + cuids
                + "]\"},{\"k\":\"chs\",\"o\":\"=\",\"v\":\"[" + chs + "]\"},{\"k\":\"dims\",\"o\":\"=\",\"v\":\"["
                + dims + "]\"},{\"k\":\"delayTime\",\"o\":\"=\",\"v\":\"0\"}],\"orders\":[]}";
        String entity = HttpClientUtil.post(urlLcuLight, param, cookies);
        // System.out.println(entity);
        String m = entity.split("\\|\\|")[1];
        JSONObject[] jb = JSONObject.parse(m, JSONObject[].class);
        return jb != null && jb.length > 0 ? jb[0] : null;
    }

    public static void main(String[] args) {
        // login();
        HttpClinetLights httpClinetLights = new HttpClinetLights();
        httpClinetLights.getLightLux();
    }

    /**
     * 定时获取路灯信息
     */
    public void addTimeStreetLight() {
        aupStreetlightDetailService.addTimeLightDetail();
    }

    /**
     * 定时删除重复数据
     */
    public void deleteTimeStreetLight() {
        aupStreetlightDetailService.deleteStreetlightDetailTime();
    }

    /**
     *
     * 定时获取控制器监测数据
     *
     *
     */
    public void addControlEnergy() {
        String urlEnergy = url + "?cmd=data-meter-report&ctrl=getDataById&version=1&lang=zh_CN&pid=" + pid;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.DATE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        long endTime = date.getTime();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(cal1.DATE, -1);
        cal1.set(Calendar.HOUR_OF_DAY, 8);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Date date1 = cal1.getTime();
        long startTime = date1.getTime();

        String param = "{\"projectId\":\"" + pid + "\",\"startTime\":" + startTime + ",\"endTime\":" + endTime + "}";
        String entity = HttpClientUtil.post(urlEnergy, param, cookies);
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlEnergy = url + "?cmd=data-meter-report&ctrl=getDataById&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"projectId\":\"" + pid + "\",\"startTime\":" + startTime + ",\"endTime\":" + endTime + "}";
                entity = HttpClientUtil.post(urlEnergy, param, cookies);
            }
        }

        String m = entity.split("\\|\\|")[1];
        JSONObject[] jb = JSONObject.parse(m, JSONObject[].class);
        AupControlEnergy aupControlEnergy = new AupControlEnergy();
        for (JSONObject jb1 : jb) {
            aupControlEnergy.setFullpower("0");
            aupControlEnergy.setId(jb1.getString("stime") + jb1.getString("cuid"));
            aupControlEnergy.setCuid(jb1.getString("cuid"));
            aupControlEnergy.setYears(jb1.getString("years"));
            aupControlEnergy.setMonth(jb1.getString("month"));
            // aupControlEnergy.setFullpower(jb1.getString("fullPower"));
            aupControlEnergy.setLighttime(jb1.getString("lightTime"));
            aupControlEnergy.setOntime(jb1.getString("onTime"));
            aupControlEnergy.setOfftime(jb1.getString("offTime"));
            aupControlEnergy.setRatedenergy(jb1.getString("ratedEnergy"));
            aupControlEnergy.setUserdenengy(jb1.getString("usedEnergy"));
            aupControlEnergy.setStime(jb1.getString("stime"));
            aupControlEnergy.setLightcount(jb1.getString("lightCount"));
            aupControlEnergy.setOffcount(jb1.getString("offCount"));
            aupControlEnergy.setDay(jb1.getString("day"));
            try {
                iAupControlEnergyService.insertAupControlEnergy(aupControlEnergy);
            } catch (Exception e) {
                iAupControlEnergyService.updateAupControlEnergy(aupControlEnergy);
            }

        }

    }

    public JSONObject[] getLightLux() {
        String urlgz = url + "?cmd=commissioning&ctrl=lux-list&version=1&lang=zh_CN";
        String paramgz = "{\"pid\":\"" + pid + "\",\"ctype\":\"1\"}";
        String gzentity = HttpClientUtil.post(urlgz, paramgz, cookies);

        if (gzentity != null) {
            if ("5|未登录|".equals(gzentity)) {
                login();
                urlgz = url + "?cmd=commissioning&ctrl=lux-list&version=1&lang=zh_CN";
                paramgz = "{\"pid\":\"" + pid + "\",\"ctype\":\"1\"}";
                gzentity = HttpClientUtil.post(urlgz, paramgz, cookies);
            }
        }

        String gzm = gzentity.split("\\|\\|")[1];
        JSONObject[] gzjb = JSONObject.parse(gzm, JSONObject[].class);
        return gzjb;
    }

    /*
     * //获取历史报警
     *
     */
    public JSONObject alarmHistory() {
        String urlalarmHistory = url + "?cmd=alarm-history&ctrl=page&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"page\":1,\"pageSize\":80,\"wheres\":[{\"k\":\"pid\",\"o\":\"=\",\"v\":\"" + pid
                + "\"}],\"orders\":[{\"k\":\"t\",\"v\":\"DESC\"}]}";
        String entity = HttpClientUtil.post(urlalarmHistory, param, cookies);
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlalarmHistory = url + "?cmd=alarm-history&ctrl=page&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"page\":1,\"pageSize\":80,\"wheres\":[{\"k\":\"pid\",\"o\":\"=\",\"v\":\"" + pid
                        + "\"}],\"orders\":[{\"k\":\"t\",\"v\":\"DESC\"}]}";
                entity = HttpClientUtil.post(urlalarmHistory, param, cookies);
            }
            String m = entity.split("\\|\\|")[1];
            if (!"[]".equals(m)) {
                try {
                    JSONObject[] jb = JSONObject.unmarshal(m, JSONObject[].class);
                    if (jb != null && jb.length > 0) {
                        for (JSONObject jb1 : jb) {

                            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jb1.getLong("t"));
                            AupWarring aupWarring = new AupWarring();
                            aupWarring.setId(jb1.getString("id"));
                            aupWarring.setAuthor("路灯系统");
                            aupWarring.setCode(jb1.getString("cuid"));
                            aupWarring.setContent(
                                    jb1.getString("sname") + jb1.getString("hname") + "," + jb1.getString("msg"));
                            aupWarring.setFormsysname("路灯");
                            aupWarring.setDatetime(date);
                            String typeid = jb1.getString("type");
                            Map map1 = aupStreetlightDetailService.getTypename(typeid);
                            // if(map1==null){
                            // System.out.println(jb1.getString("type"));
                            // }
                            aupWarring.setName(map1.get("name").toString());
                            aupWarring.setPath(jb1.getString("sname"));
                            boolean state = jb1.getBool("r");
                            if (state) {
                                aupWarring.setState("1");
                            } else {
                                aupWarring.setState("0");
                            }

                            aupWarring.setLevel(jb1.getString("level"));
                            try {
                                aupWarringService.insertAupWarring(aupWarring);
                            } catch (Exception e) {
                                aupWarringService.updateAupWarring(aupWarring);
                            }

                        }
                    }

                } catch (Exception e) {

                }

            }
        }
        return null;

    }

    /**
     *
     *
     * 获取所有控制器的值
     *
     */
    public JSONObject[] getControlInfo() {
        String urlinfo = url + "?cmd=data-meter-data&ctrl=meter-list&version=1&lang=zh_CN&pid=" + pid;
        String param = "\"" + pid + "\"";
        String entity = HttpClientUtil.post(urlinfo, param, cookies);
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlinfo = url + "?cmd=data-meter-data&ctrl=meter-list&version=1&lang=zh_CN&pid=" + pid;
                param = "\"" + pid + "\"";
                entity = HttpClientUtil.post(urlinfo, param, cookies);
            }
        }

        String statusUrl = url + "?cmd=station-info&ctrl=list&version=1&lang=zh_CN";
        String statusParam = "{\"pid\":\"" + pid + "\"}";
        String statusentity = HttpClientUtil.post(statusUrl, statusParam, cookies);
        String statusm = statusentity.split("\\|\\|")[1];
        JSONObject[] statusjb = JSONObject.parse(statusm, JSONObject[].class);

        String m = entity.split("\\|\\|")[1];
        JSONObject[] jb = JSONObject.parse(m, JSONObject[].class);
        for (JSONObject jb1 : jb) {
            for (JSONObject jb2 : statusjb) {
                if (jb1.getString("cuid").equals(jb2.getString("cuid"))) {
                    Map map = jb1;
                    map.put("status", jb2.getString("status"));
                    map.put("sid", jb2.getString("sid"));
                    map.put("poles", jb2.getString("poles"));
                    map.put("sname", jb2.getString("sname"));
                }
            }
        }
        return jb;
    }

    /**
     * 根据某个控制器cuid,sid获得该控制器的状态
     *
     */
    public JSONObject getControlInfoByCuid(String cuid, String sid) {
        String urlinfo = url + "?cmd=data-meter-data&ctrl=get&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1},{\"k\":\"madd\",\"o\":\"=\",\"v\":1},{\"k\":\"num\",\"o\":\"=\",\"v\":1}],\"orders\":[]}";
        String entity = HttpClientUtil.post(urlinfo, param, cookies);
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlinfo = url + "?cmd=data-meter-data&ctrl=get&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                        + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1},{\"k\":\"madd\",\"o\":\"=\",\"v\":1},{\"k\":\"num\",\"o\":\"=\",\"v\":1}],\"orders\":[]}";
                entity = HttpClientUtil.post(urlinfo, param, cookies);
            }
        }
        String m = entity.split("\\|\\|")[1];

        JSONObject jb = JSONObject.parse(m, JSONObject.class);

        String statusUrl = url + "?cmd=station-info&ctrl=show-info&version=1&lang=zh_CN";
        String statusParam = "\"" + sid + "\"";
        String statusentity = HttpClientUtil.post(statusUrl, statusParam, cookies);
        String statusm = statusentity.split("\\|\\|")[1];
        JSONObject statusjb = JSONObject.parse(statusm, JSONObject.class);

        jb.put("status", statusjb.getString("status"));
        jb.put("poles", statusjb.getString("poles"));

        return jb;
    }

    /**
     *
     * 获取控制器下面路灯的状态
     *
     */

    public JSONObject[] getLightStatus(String cuid) {
        String urlinfo = url + "?cmd=data-lcu-light&ctrl=list&version=1&lang=zh_CN&pid=" + pid;
        String param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1}],\"orders\":[]}";
        String entity = HttpClientUtil.post(urlinfo, param, cookies);
        JSONObject[] jb = null;
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlinfo = url + "?cmd=data-lcu-light&ctrl=list&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                        + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1}],\"orders\":[]}";
                entity = HttpClientUtil.post(urlinfo, param, cookies);
            }
            String m = entity.split("\\|\\|")[1];
            jb = JSONObject.parse(m, JSONObject[].class);
        }
        return jb;

    }

    public JSONObject[] getLightName(String cuid) {
        String urlinfo = url + "?cmd=lamp&ctrl=list&version=1&lang=zh_CN";
        String param = "{\"wheres\":[{\"k\":\"projectId\",\"o\":\"=\",\"v\":\"306dc5edede042259ee7a54c9823e22b\"},{\"k\":\"stationId\",\"o\":\"=\",\"v\":\"69dcf78975f14e938b380c9a7928e98f\"}],\"orders\":[]}";
        String entity = HttpClientUtil.post(urlinfo, param, cookies);
        if (entity != null) {
            if ("5|未登录|".equals(entity)) {
                login();
                urlinfo = url + "?cmd=data-lcu-light&ctrl=list&version=1&lang=zh_CN&pid=" + pid;
                param = "{\"wheres\":[{\"k\":\"cuid\",\"o\":\"=\",\"v\":\"" + cuid
                        + "\"},{\"k\":\"ctype\",\"o\":\"=\",\"v\":1}],\"orders\":[]}";
                entity = HttpClientUtil.post(urlinfo, param, cookies);
            }
        }
        String m = entity.split("\\|\\|")[1];

        JSONObject[] jb = JSONObject.parse(m, JSONObject[].class);
        return jb;
    }

}
