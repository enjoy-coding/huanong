package com.feather.aupipes.HttpClient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.service.IAupLeakCountService;
import com.feather.aupipes.service.IAupLeakService;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.common.json.JSONObject;

/**
 * 探漏接口请求
 */
@Component("leak")
public class HttpClientLeak {
    @Autowired
    private IAupLeakCountService aupLeakCountService;
    @Autowired
    private IAupLeakService aupLeakService;

    private static String url = "https://www.celou.vip/Ajax";

    private static String cookie;

    @Autowired
    private IAupWarringService iAupWarringService;

    /**
     * 获取漏水站点数据
     *
     * @param {
     *            username:用户名 password:密码 }
     * @return [{ "PlaceId":"000004", 地址id "SiteNO":"90000955", 探漏设备id
     *         "LeakState":1 探漏状态（1：漏水站点，2：表示疑漏站点） }]
     */
    public static String getLeakSiteData() {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("username", "hzny_user");
        param.put("password", "sdk123");
        String result = HttpClientUtil.httpPostWithJSON(url + "/GetLeakSiteData", param);
        return result;
    }

    /**
     * 获取探漏列表数据
     *
     * @param {
     *            username:用户名 password:密码 }
     * @return [{ "PlaceId": "000001", 安装地址id "SiteNO": "60000372", 探漏设备id
     *         "PlaceAddress": "附中教学楼西北角路边", 安装详细地址 "Lng": 114.34256, 经度 "Lat":
     *         30.4790878, 纬度 "PipeId": 4307 管线id }]
     */
    public String getSitesCoordData() {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("username", "hzny_sso");
        param.put("password", "d39b43de68ef31c48a2104a17cd5f8a6");
        param.put("encrypt", true);
        String result = HttpClientUtil.httpPostWithJSON(url + "/GetSitesCoordData", param);
        aupLeakService.insetOrUpdateLeak(result); // 更新数据库
        return result;
    }

    /**
     * 获取探漏运行情况
     *
     * @param {
     *            school:学校名称缩写小写 }
     * @return { "Normal": 101, 正常点的数量 "Leakage": 5, 漏水点的数量 "Lose": 7, 丢失点的数量
     *         "Lnterfere": 6, 干扰点的数量 "Doubtleak": 6, 疑漏点的数量 }
     *         在线设备数=正常漏点数量+漏水点数量+干扰点数量+疑漏点数量 总数量=正常漏点数量+漏水点数量+丢失点数量+干扰点数量+疑漏点数量
     */
    public void getSiteStatusData() {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("school", "hzny");
        String result = HttpClientUtil.httpPostWithJSON(url + "/GetSiteStatusData", param);
        aupLeakCountService.insertLeakStatus(result);// 插入表中
    }

    public static String HZNYSearchPlacesDataV2() {
        String url1 = "https://www.celou.vip/Ajax/HZNYSearchPlacesDataV2";
        String startTime = "2020/1/13";
        String endTime = "2020/1/13";
        String siteNo = "0";
        int curPage = 1;
        int pageSize = 15;
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("siteNo", siteNo);
        param.put("curPage", curPage);
        param.put("pageSize", pageSize);
        String result = HttpClientUtil.httpPostWithJSON(url1, param);
        return result;

    }

    /**
     * Data[0]//信号波动振幅数据 * Categories//毫秒 * SeriasData//信号波动振幅 * Data[1]//异常系数数据
     * * Categories//横轴 * SeriasData//异常系数 * SeriasData2//平均振幅
     *
     * @return
     */
    public static String HZNYGetCurveGraphValue(String siteNO) {
        String url1 = "https://www.celou.vip/Ajax/HZNYGetCurveGraphValue";
        // String recDateTime = "";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("siteNO", siteNO);
        String result = HttpClientUtil.httpPostWithJSON(url1, param);
        return result;

    }

    /**
     * 获取探漏管线接口数据
     *
     * @return
     */
    public static String GetPipesCoordData() {
        String url1 = "https://www.celou.vip/Ajax/GetPipesCoordData";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("username", "ys_user");
        param.put("password", "123");
        String result = HttpClientUtil.httpPostWithJSON(url1, param);
        return result;
    }

    public static void login() {
        String url = "https://www.celou.vip/Ajax/Login?username=hzny_user&password=sdk123";

        JSONObject posts = HttpClientUtil.posts(url);
        String coo = posts.getString("cookie");
        JSONObject parse = JSONObject.parse(coo);
        cookie = "ASP.NET_SessionId=" + parse.getString("ASP.NET_SessionId") + ";" + "city=wuhan" + ";" + "pipeOpacity="
                + parse.getString("pipeOpacity") + ";" + "PrjId=" + parse.getString("PrjId") + ";"
                + "ProjectDirectory=hzny" + "Rights=" + parse.getString("Rights") + ";" + "RMRole=ProjectUser;"
                + "roleName=ProjectUser;" + "siteScale=1;" + "auth=" + parse.getString("auth");
        // System.out.println(coo);
        // getAlarmHistory(coo);

    }

    public void insetAlarmCelouHistory() {
        if (cookie == null) {
            login();
        }
        getAlarmHistory();
        insertRepair();
    }

    public void getAlarmHistory() {
        String url = "https://www.celou.vip/View/Main/PageEdit.aspx?action=GetData&repaired=0";
        String celou = HttpClientUtil.getCelou(url, cookie);
        String cel1 = JSONObject.parse(celou).getString("Result");
        String[] split = cel1.split("\\|");
        for (int i = 0; i < split.length; i++) {
            String value = split[i];
            String[] split1 = value.split("\\,");
            AupWarring entity = new AupWarring();
            entity.setId("tl" + split1[0]);
            entity.setAuthor("供水管网渗漏报警平台");
            entity.setContent(split1[1] + "存在漏水情况!");
            entity.setFormsysname("探漏");
            entity.setLevel("2");
            entity.setName("确认漏水");
            entity.setPath(split1[1].substring(9));
            if (split1[4].trim().equals("")) {
                entity.setCode("1");
            } else {
                entity.setCode(split1[4]);
            }
            entity.setDatetime(split1[2]);
            entity.setState("0");

            try {
                iAupWarringService.insertAupWarring(entity);
            } catch (Exception e) {
                iAupWarringService.updateAupWarring(entity);
            }
        }
        // System.out.println(celou);
    }

    public void insertRepair() {
        String url = "https://www.celou.vip/View/Main/PageEdit.aspx?action=GetData&repaired=1";
        String celou = HttpClientUtil.getCelou(url, cookie);
        String cel1 = JSONObject.parse(celou).getString("Result");
        String[] split = cel1.split("\\|");
        for (int i = 0; i < split.length; i++) {
            String value = split[i];
            String[] split1 = value.split("\\,");
            AupWarring entity = new AupWarring();
            entity.setId("tl" + split1[0]);
            entity.setAuthor("供水管网渗漏报警平台");
            entity.setContent(split1[1] + "存在漏水情况!");
            entity.setFormsysname("探漏");
            entity.setLevel("2");
            entity.setName("确认漏水");
            entity.setPath(split1[1].substring(9));
            if (split1[4].trim().equals("")) {
                entity.setCode("1");
            } else {
                entity.setCode(split1[4]);
            }
            entity.setDatetime(split1[2]);
            entity.setState("1");

            try {
                iAupWarringService.insertAupWarring(entity);
            } catch (Exception e) {
                iAupWarringService.updateAupWarring(entity);
            }
        }
    }

    public static void main(String[] args) {
        login();
    }
}
