package com.feather.aupipes.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.feather.aupipes.HttpClient.HttpClientLeak;
import com.feather.aupipes.domain.AupLeak;
import com.feather.aupipes.domain.AupYjinfotables;
import com.feather.aupipes.domain.AupYjtables;
import com.feather.aupipes.service.IAupLeakService;
import com.feather.aupipes.service.IAupYjczService;
import com.feather.aupipes.service.IAupYjinfotablesService;
import com.feather.aupipes.service.IAupYjtablesService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;

/**
 * 首页
 */
@Service
public class AupYjczServiceImpl implements IAupYjczService {

    @Value("classpath:/temp.json")
    private Resource jsondata;// 临时用下本地的json文件

    // 获取预警统计信息
    @Autowired
    private IAupYjtablesService iAupYjtablesService;

    // 获取预警信息详情
    @Autowired
    private IAupYjinfotablesService iAupYjinfotablesService;

    @Autowired
    IAupLeakService iAupLeakService;

    HttpClientLeak hcl = new HttpClientLeak();

    /*
     * 插入预警信息详情
     *
     */
    @Override
    public void addYjinfomation() {
        // 插入探漏预警
        addLeak();
        // 插入路灯数据
        // addLight();
        //

    }

    /*
     * 插入探漏数据
     *
     */
    public void addLeak() {
        String tlinfo = hcl.getLeakSiteData();
        JSONArray ja = JSONObject.parse(tlinfo, JSONArray.class);
        for (Object object : ja) {
            AupYjinfotables entity = new AupYjinfotables();
            Map map = (Map) object;
            Integer leakState = (Integer) map.get("LeakState");
            String placeId = (String) map.get("PlaceId");
            String sid = (String) map.get("SiteNO");
            entity.setSid(sid);
            List<AupYjinfotables> isList = iAupYjinfotablesService.selectAupYjinfotablesList(entity);
            if (isList.size() > 0) {
                if ("0".equals(isList.get(0).getItstatus())) { // 同sid下面状态未处理,不用重复添加
                    break;
                }
            }
            // 根据siteno查询详细信息
            AupLeak al = new AupLeak();
            al.setSiteno(sid);
            List<AupLeak> list = iAupLeakService.selectAupLeakList(al);
            String sarea = "";
            int areaNO = 0;
            String content = "";
            if (list.size() > 0) {
                sarea = list.get(0).getPlaceaddress();
                areaNO = list.get(0).getPlaceid();
                if (leakState == 1) {
                    content = sarea + "探漏仪编号为" + sid + "出现漏水情况,请排查";
                } else {
                    content = sarea + "探漏仪编号为" + sid + "有疑似漏水情况,请排查";
                }
            }

            String name = "探漏";
            String itid = UUID.randomUUID().toString().replaceAll("-", "");

            // 根据name查询tid
            AupYjtables entity1 = new AupYjtables();
            entity1.setName(name);
            List<AupYjtables> list1 = iAupYjtablesService.selectAupYjtablesList(entity1);
            String tid = "";
            if (list1.size() > 0) {
                tid = list1.get(0).getTid();
            }
            Date yjtime = new Date();
            String yjstatus = "未处理";
            String yjlevel = "一级预警";
            String sname = "探漏仪:" + sid; // 设备名称
            String yori = "探漏仪:" + sid; // 来源
            String yjname = name;
            String stype = name;
            String itstatus = "0";
            entity.setItid(itid);
            entity.setTid(tid);
            entity.setAreano(String.valueOf(areaNO));
            entity.setName(name);
            entity.setContent(content);
            entity.setSname(sname);
            entity.setSarea(sarea);
            entity.setStype(stype);
            entity.setItstatus(itstatus);
            entity.setYjname(yjname);
            entity.setYjtime(yjtime);
            entity.setYjstatus(yjstatus);
            entity.setYjlevel(yjlevel);
            entity.setYori(yori);

            iAupYjinfotablesService.insertAupYjinfotables(entity);
        }

    }

    /*
     * 获取报警统计信息
     *
     */
    @Override
    public List<Map> bjtj() {
        AupYjtables entity = new AupYjtables();
        List list = iAupYjtablesService.selectAupYjtablesList(entity);
        return list;
    }

    @Override
    public AjaxResult getPubAlarm() {
        String treeJson = "";

        try {
            treeJson = IOUtils.toString(jsondata.getInputStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AjaxResult.success(treeJson);
    }

    @Override
    public List<Map> getBjxxBytid(String tid) {
        String treeJson = "";
        AupYjinfotables entity = new AupYjinfotables();
        if ("1".equals(tid)) {
            entity.setTid("");
        } else {
            entity.setTid(tid);
        }
        List list = iAupYjinfotablesService.selectAupYjinfotablesList(entity);

        /*
         * try {
         * 
         * treeJson = IOUtils.toString(jsondata.getInputStream(),
         * Charset.forName("UTF-8")); JSONObject jb = (JSONObject)
         * JSONObject.parse(treeJson); JSONArray ja= (JSONArray)
         * jb.get("infaotables");
         * 
         * for(Object map:ja){ Map map1 = (Map) map;
         * if(tid.equals("1")||tid.equals("")||tid==null||tid.equals("undefined"
         * )){ list.add(map1); }else if(map1.get("tid").equals(tid)){
         * list.add(map1); }
         * 
         * }
         * 
         * } catch (IOException e) { e.printStackTrace(); }
         */
        return list;
    }

    @Override
    public List<List> getSyYjxxBytid() {
        String treeJson = "";
        List<Map> list = new ArrayList<Map>();
        List<List> list1 = new ArrayList<>();
        try {
            /*
             * treeJson = IOUtils.toString(jsondata.getInputStream(),
             * Charset.forName("UTF-8")); JSONObject jb = (JSONObject)
             * JSONObject.parse(treeJson); JSONArray ja= (JSONArray)
             * jb.get("infaotables");
             */
            AupYjinfotables entity = new AupYjinfotables();
            // List<AupYjinfotables> ja =
            // iAupYjinfotablesService.selectAupYjinfotablesList(entity);
            List<Map> ja = iAupYjinfotablesService.getWaringTableList("", "", "", "0", "", "", "", "");

            int i = 1;
            int j = 1;
            int k = 1;
            for (Map map : ja) {

                list.add(map);
                if (i == 2 || (ja.size() == k)) {
                    i = 0;
                    j++;
                    list1.add(list);
                    list = new ArrayList<Map>();
                }

                i++;
                k++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list1;
    }

    @Override
    public AjaxResult getYjxqxByitid(String itid) {
        String treeJson = "";
        AupYjinfotables entity = new AupYjinfotables();
        entity.setItid(itid);
        List list = iAupYjinfotablesService.selectAupYjinfotablesList(entity);

        /*
         * try { treeJson = IOUtils.toString(jsondata.getInputStream(),
         * Charset.forName("UTF-8")); JSONObject jb = (JSONObject)
         * JSONObject.parse(treeJson); JSONArray ja= (JSONArray)
         * jb.get("infaotables");
         * 
         * for(Object map:ja){ Map map1 = (Map) map;
         * if(map1.get("itid").equals(itid)){ return AjaxResult.success(map1); }
         * } } catch (IOException e) { e.printStackTrace(); }
         */

        return AjaxResult.success(list.get(0));
    }

}
