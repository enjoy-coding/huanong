package com.feather.aupipes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feather.aupipes.HttpClient.HttpClientFwPdf;
import com.feather.aupipes.domain.*;
import com.feather.aupipes.mapper.*;
import com.feather.aupipes.service.IAupBuildingWaterValveService;
import com.feather.aupipes.service.IAupDecisionSecurityService;
import com.feather.common.config.Global;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Create by NieCheng Time 2019/12/16 9:11
 */
@Service
public class AupDecisionSecurityServiceImpl implements IAupDecisionSecurityService, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AupPlansManageMapper aupPlansManageMapper;

    @Autowired
    private AupBuildingAreaMapper aupBuildingAreaMapper;

    @Autowired
    private AupBuildingMapper aupBuildingMapper;

    @Autowired
    private AupipeJcbzWokerMapper aupipeJcbzWorkerMapper;

    @Autowired
    private AupPowerMapper aupPowerMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IAupBuildingWaterValveService aupWaterRelService;

    @Autowired
    private AupBuildingtypeMapper aupBuildingtypeMapper;

    private List ldNumList;

    @Value("classpath:/tempZnpgTree.json")
    private Resource znpgTree;// 临时用下本地的json文件

    // 定义全局变量
    private List<Map> lineList = new ArrayList<>(); // 管线集合
    private List<Map> closevalveList = new ArrayList<>();// 关阀集合
    private List<Map> valveList = new ArrayList<>();// 关阀集合
    private List<Map> fwList = new ArrayList<>();// 房屋集合

    private List<AupBuildingtype> lzxdFwList; // 拉闸限电影响房屋
    private List<Map> lzxdPdfList;// 拉闸限电影响配电房
    private String lzxdFwNo;// 拉闸限电影响配电房

    // 查询拉闸限电树形结构
    @Override
    public AjaxResult selectDecisionListTree(String type) {
        String treeJson = "";

        try {
            treeJson = IOUtils.toString(znpgTree.getInputStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return AjaxResult.success(JSONObject.parse(treeJson, JSONArray.class));
    }

    // 查询拉闸限电rightBox数据
    @Override
    public AjaxResult selectLzxdRightBox(String oid,String lzxdType) {
        HashMap<Object, Object> map = new HashMap<>();
        // oid="1576137776507";
        lzxdFwList = new ArrayList();
        lzxdPdfList = new ArrayList();
        lzxdFwNo = "";
        //long l = Long.parseLong(oid);
        String pdfId = "";
        String fwNo = "";
        if(lzxdType.equals("house")){
            List<AupHlInfo> Hlidlist = aupipeJcbzWorkerMapper.selectHlid(oid);
            if (Hlidlist.size() > 0) {
                AupHlInfo aupHlInfo = Hlidlist.get(0);
                String hlId = aupHlInfo.getHlId();
                aupHlInfo = new AupHlInfo();
                aupHlInfo.setHlId(hlId);
                List<AupHlInfo> aupHlinfos = aupipeJcbzWorkerMapper.getHlInfo(aupHlInfo);
                for (AupHlInfo hlInfo : aupHlinfos) {
                    // 获取房屋ID
                    String fwId = hlInfo.getFwId();
                    if (fwId != "") {
                        //if (fwId != 0.0f) {
                    /*AupBuildingArea aupBuildingArea = new AupBuildingArea();
                    aupBuildingArea.setId(fwId);
                    List<AupBuildingArea> aupBuildingAreas = aupBuildingAreaMapper
                            .selectAupBuildingTypeListInfoForLdno(aupBuildingArea);
                    for (AupBuildingArea b : aupBuildingAreas) {
                        String ldno = b.getNo() + ",";
                        fwNo += ldno;
                        lzxdFwList.add(b);
                    }*/
                        AupBuildingtype aupBuildingtype = new AupBuildingtype();
                        aupBuildingtype.setNo(fwId+"");
                        List<AupBuildingtype> aup = aupBuildingtypeMapper.selectAupBuildingtypeList(aupBuildingtype);
                        int count_=0;
                        for (AupBuildingtype b : aup) {
                            if(aup.size()==1){
                                String ldno = b.getNo()+",";
                                fwNo += ldno;
                                lzxdFwList.add(b);
                                count_++;
                            }else if(aup.size()>1&&count_<(aup.size()-1)){
                                String ldno = b.getNo() + ",";
                                fwNo += ldno;
                                lzxdFwList.add(b);
                                count_++;
                            }else if(count_==aup.size()){
                                String ldno = b.getNo()+",";
                                fwNo += ldno;
                                lzxdFwList.add(b);
                            }
                        }
                    }
                    // 获取配电房Id
                    if (!(hlInfo.getPdfId().equals(pdfId))) {
                        pdfId = hlInfo.getPdfId();
                        if (pdfId != "" && pdfId != null) {
                            AupPower aupPower = new AupPower();
                            //long fwId_ = hlInfo.getFwId();
                            String fwId_ = hlInfo.getFwId();
                            String hlId_ = hlInfo.getHlId();
                            List<Map> aupPowers = aupPowerMapper.selectPdfInfoList(pdfId, (fwId_), hlId_);
                            for (Map a : aupPowers) {
                                lzxdPdfList.add(a);
                            }
                            // aupPower.setBsm(pdfId);
                            /*
                             * List<AupPower> aupPowers =
                             * aupPowerMapper.selectAupPowerList(aupPower); for
                             * (AupPower a : aupPowers) { lzxdPdfList.add(a); }
                             */
                        }
                    }
                }
            }
        }else if(lzxdType.equals("pdf")){
            //oid = 配电房编号
            List<AupHlInfo> fwListForPdf =aupipeJcbzWorkerMapper.selectFwForPdf(oid);
            if(fwListForPdf.size()>0){
                for (AupHlInfo hlInfo : fwListForPdf) {
                    // 获取房屋ID
                    String fwId = hlInfo.getFwId();
                    if (fwId != "") {
                        AupBuildingtype aupBuildingtype = new AupBuildingtype();
                        aupBuildingtype.setNo(fwId+"");
                        List<AupBuildingtype> aup = aupBuildingtypeMapper.selectAupBuildingtypeList(aupBuildingtype);
                        int count_=0;
                        for (AupBuildingtype b : aup) {
                            if(aup.size()==1){
                                if(fwListForPdf.size()==1&&aup.size()==1){
                                    String ldno = b.getNo()+",";
                                    fwNo += ldno;
                                    lzxdFwList.add(b);
                                    count_++;
                                }else if(fwListForPdf.size()>1&&aup.size()==1){
                                    String ldno = b.getNo() + ",";
                                    fwNo += ldno;
                                    lzxdFwList.add(b);
                                    count_++;
                                }
                            }else if(aup.size()>1&&count_<(aup.size()-1)){
                                String ldno = b.getNo() + ",";
                                fwNo += ldno;
                                lzxdFwList.add(b);
                                count_++;
                            }else if(count_==aup.size()){
                                String ldno = b.getNo()+",";
                                fwNo += ldno;
                                lzxdFwList.add(b);
                            }
                        }
                    }
                    // 获取配电房Id
                    if (!(hlInfo.getPdfId().equals(pdfId))) {
                        pdfId = hlInfo.getPdfId();
                        if (pdfId != "" && pdfId != null) {
                            AupPower aupPower = new AupPower();
                            String fwId_ = hlInfo.getFwId();
                            String hlId_ = hlInfo.getHlId();
                            List<Map> aupPowers = aupPowerMapper.selectPdfInfoList(pdfId, (fwId_), hlId_);
                            for (Map a : aupPowers) {
                                lzxdPdfList.add(a);
                            }
                        }
                    }
                }
            }
        }

        if (lzxdPdfList != null && lzxdPdfList.size() > 0) {
            map.put("bdx", lzxdPdfList.size());
        } else {
            map.put("bdx", 0);
        }
        // map.put("gdgx", 0);

        if (lzxdFwList != null && lzxdFwList.size() > 0) {
            lzxdFwNo = fwNo.substring(0,fwNo.length()-1);
            List<Map> fwMap = aupBuildingMapper.getFwCount(Convert.toStrArray(fwNo));
            List<Map> ldAdminMap = aupBuildingMapper.getLdAdminMap(Convert.toStrArray(fwNo));
            map.put("jml", 0);
            map.put("ssl", 0);
            map.put("jxl", 0);
            map.put("syl", 0);

            map.put("ssgly", 0);
            map.put("hqb", 0);
            map.put("jmgly", 0);
            map.put("jxgly", 0);
            for (int i = 0; i < fwMap.size(); i++) {
                String fwTypeName = (String) fwMap.get(i).get("fwType");
                switch (fwTypeName) {
                    case "居民楼":
                        map.remove("jml");
                        map.put("jml", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    case "宿舍楼":
                        map.remove("ssl");
                        map.put("ssl", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    case "教学楼":
                        map.remove("jxl");
                        map.put("jxl", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    case "商业楼":
                        map.remove("syl");
                        map.put("syl", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < ldAdminMap.size(); i++) {
                String manageType = (String) ldAdminMap.get(i).get("manageType");
                if (manageType != null) {
                    switch (manageType) {
                        case "宿舍管理员":
                            map.remove("ssgly");
                            map.put("ssgly", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        case "后保部":
                            map.remove("hqb");
                            map.put("hqb", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        case "居民管理员":
                            map.remove("jmgly");
                            map.put("jmgly", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        case "教学管理员":
                            map.remove("jxgly");
                            map.put("jxgly", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            map.put("jml", 0);
            map.put("ssl", 0);
            map.put("jxl", 0);
            map.put("syl", 0);
            map.put("ssgly", 0);
            map.put("hqb", 0);
            map.put("jmgly", 0);
            map.put("jxgly", 0);
        }

        return AjaxResult.success(map);
    }

    // 获取下方DIV拉闸分析列表信息
    @Override
    public Map selectLzfxList(String type, String modelTypeName, String oid, String bhType) {
        ArrayList list = new ArrayList();
        HttpClientFwPdf httpClientFwPdf = new HttpClientFwPdf();
        String result = "";
        JSONObject parseObject = null;
        int count = 1;
        try {
            if (modelTypeName.equals("lzxd")) {
                if (type.equals("lzfx")) {
                    if (lzxdPdfList != null && lzxdPdfList.size() > 0) {
                        for (int i = 0; i < lzxdPdfList.size(); i++) {
                            // AupPower aupPower = (AupPower)
                            // lzxdPdfList.get(i);
                            Map m = (Map) lzxdPdfList.get(i);
                            HashMap<Object, Object> map = new HashMap<>();
                            map.put("serial", i + 1);
                            map.put("number", m.get("BSM"));
                            map.put("name", m.get("name"));
                            map.put("position", m.get("type"));
                            map.put("address", m.get("FJBS"));
                            map.put("hlmc", m.get("HLMC"));
                            map.put("GH", m.get("GH"));
                            /*
                             * map.put("number", aupPower.getBsm());
                             * map.put("name", aupPower.getName());
                             * map.put("position", aupPower.getType());
                             * map.put("address", aupPower.getFjbs());
                             */
                            list.add(map);
                        }
                    } /*
                     * else { HashMap<Object, Object> map = new HashMap<>();
                     * list.add(map); }
                     */
                } else if (type.equals("gxfx")) {
                    HashMap<Object, Object> map = new HashMap<>();
                    map.put("serial", 1);
                    map.put("number", "JS032165");
                    map.put("name", "供电管线");
                    map.put("position", "学府路");
                    map.put("address", "0101");
                    list.add(map);
                }

            } else if (modelTypeName.equals("gfts")) {
                if (type.equals("lzfx")) {
                    if (closevalveList != null && closevalveList.size() > 0) {
                        if (bhType.equals("JSGX")) {
                            for (int i = 0; i < closevalveList.size(); i++) {
                                List<Map> proList = new ArrayList<>();
                                JSONObject proListJson = (JSONObject) closevalveList.get(i).get("properties");
                                // 获取geometry 坐标
                                JSONObject geometryJson = (JSONObject) closevalveList.get(i).get("geometry");
                                List<Map> addressList = (List<Map>) geometryJson.get("coordinates");
                                String jsonStr = JSONObject.marshal(addressList);
                                jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
                                String[] splitArr = jsonStr.split(",");
                                String address = splitArr[0] + "," + splitArr[1] + ","
                                        + (JSONObject.marshal(proListJson.get("PIPEP_H")));
                                /** 结束 **/
                                HashMap<Object, Object> map = new HashMap<>();
                                String s = (String) proListJson.get("PIPEP_ID");
                                map.put("serial", count);
                                map.put("number", (String) proListJson.get("PIPEP_ID"));
                                map.put("name", "阀门");
                                map.put("position", (String) proListJson.get("R_NAME"));
                                // map.put("address", address);
                                map.put("address", (String) proListJson.get("OID"));
                                count++;
                                list.add(map);
                            }
                        } else {
                            for (int i = 0; i < closevalveList.size(); i++) {
                                AupBuildingWaterValve aupWaterRel = (AupBuildingWaterValve) closevalveList.get(i);
                                HashMap<Object, Object> map = new HashMap<>();
                                map.put("serial", count);
                                map.put("number", aupWaterRel.getValveNo());
                                map.put("name", aupWaterRel.getAreaName());
                                map.put("position", aupWaterRel.getBuildingName());
                                map.put("hlmc", "");
                                map.put("GH", "" );
                                map.put("address", aupWaterRel.getValveOid());
                                //map.put("relId", aupWaterRel.getRelId());
                                //map.put("relUp", aupWaterRel.getRelUp());
                                map.put("relCode", aupWaterRel.getBuildingNo());
                                count++;
                                list.add(map);
                            }
                        }
                    }
                }
                /*
                 * if (type.equals("gxfx")) { if (valveList != null &&
                 * valveList.size() > 0) { for (int i = 0; i < valveList.size();
                 * i++) { List<Map> proList = new ArrayList<>(); // 影响管线查询
                 *//*
                 * JSONObject proListJson= (JSONObject)
                 * lineList.get(i).get("properties"); //获取geometry 坐标
                 * JSONObject geometryJson= (JSONObject)
                 * lineList.get(i).get("geometry"); List<Map> addressList =
                 * (List<Map>) geometryJson.get("coordinates"); String jsonStr
                 * = JSONArray.toJSONString(addressList); jsonStr =
                 * jsonStr.substring(1,jsonStr.length()-1); String[] splitArr
                 * = jsonStr.split(","); String
                 * address=splitArr[0]+","+splitArr[1]+",26.0442";
                 *//**//** 结束 **/
                /**//*
                 * HashMap<Object, Object> map = new HashMap<>();
                 * map.put("serial", count); map.put("number", (String)
                 * proListJson.get("D_S")); map.put("name", (String)
                 * proListJson.get("MATERIAL")); map.put("position",(String)
                 * proListJson.get("R_NAME")); map.put("address", address);
                 * count++; list.add(map);
                 *//*
                 *
                 * // 影响阀门查询 JSONObject proListJson = (JSONObject)
                 * valveList.get(i).get("properties"); // 获取geometry 坐标
                 * JSONObject geometryJson = (JSONObject)
                 * valveList.get(i).get("geometry"); List<Map> addressList
                 * = (List<Map>) geometryJson.get("coordinates"); String
                 * jsonStr = JSONObject.marshal(addressList); jsonStr =
                 * jsonStr.substring(1, jsonStr.length() - 1); String[]
                 * splitArr = jsonStr.split(","); String address =
                 * splitArr[0] + "," + splitArr[1] + "," +
                 * (JSONObject.marshal(proListJson.get("PIPEP_H")));
                 *//** 结束 **//*
                 *
                 * HashMap<Object, Object> map = new
                 * HashMap<>(); String s = (String)
                 * proListJson.get("PIPEP_ID");
                 * map.put("serial", count); map.put("number",
                 * (String) proListJson.get("PIPEP_ID"));
                 * map.put("name", "阀门"); map.put("position",
                 * (String) proListJson.get("R_NAME"));
                 * //map.put("address", address);
                 * map.put("address", (String)
                 * proListJson.get("OID")); count++;
                 * list.add(map); } } }
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", 200);
        resultMap.put("msg", "");
        resultMap.put("total", count - 1);
        resultMap.put("rows", list);

        return resultMap;
    }

    // 获取下方DIV影响范围
    @Override
    public Map selectYyfwList(String type, String modelTypeName, String oid, String bhType) {
        ArrayList list = new ArrayList();

        if (modelTypeName.equals("lzxd")) {
            if (lzxdFwList != null && lzxdFwList.size() > 0) {
                for (int i = 0; i < lzxdFwList.size(); i++) {
                    AupBuildingtype aupBuildingtype = (AupBuildingtype) lzxdFwList.get(i);
                    HashMap<Object, Object> map3 = new HashMap<>();
                    map3.put("serial", i + 1);
                    map3.put("pinqu", aupBuildingtype.getAreaname());
                    //map3.put("loudong", aupBuildingArea.getName());
                    map3.put("loudong", aupBuildingtype.getBuildingname());
                    map3.put("type", aupBuildingtype.getBuildingtype());
                    //map3.put("buildNum", aupBuildingArea.getAreaNo());
                    map3.put("buildNum", aupBuildingtype.getNo());
                    list.add(map3);
                }
            } /*
             * else { HashMap<Object, Object> map3 = new HashMap<>();
             * list.add(map3); }
             */
        } else if (modelTypeName.equals("gfts")) {
            HashMap<Object, Object> map3 = new HashMap<>();
            list = getFwCountList(fwList, bhType);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", 200);
        resultMap.put("msg", "");
        if (modelTypeName.equals("lzxd")) {
            if (fwList == null) {
                resultMap.put("total", "0");
            } else {
                if (lzxdFwList != null) {
                    resultMap.put("total", lzxdFwList.size());
                } else {
                    resultMap.put("total", "0");
                }
            }
        } else if (modelTypeName.equals("gfts")) {
            if (fwList == null) {
                resultMap.put("total", "0");
            } else {
                if (fwList != null) {
                    resultMap.put("total", fwList.size());
                } else {
                    resultMap.put("total", "0");
                }
            }

        }
        resultMap.put("rows", list);

        return resultMap;
    }

    @Override
    public Map selectDbxqBoxData(String type, String modelTypeName, String oid, String bhType) {
        ArrayList list = new ArrayList();

        int count = 0;

        if (modelTypeName.equals("lzxd")) {
            if (lzxdFwNo != null) {
                String[] lzArr = lzxdFwNo.split(",");
                if (lzxdFwNo != "") {
                    List<Map> listLd = aupPlansManageMapper.selectAupUsersListArr(lzArr);
                    for (int j = 0; j < listLd.size(); j++) {
                        HashMap<Object, Object> map3 = new HashMap<>();
                        map3.put("serial", (j + 1));
                        map3.put("name", listLd.get(j).get("username"));
                        map3.put("loudong", listLd.get(j).get("ldName"));
                        map3.put("type", listLd.get(j).get("buildingType"));
                        map3.put("buildNum", listLd.get(j).get("ldNum"));
                        map3.put("openId", listLd.get(j).get("openId"));
                        map3.put("phoneNum", listLd.get(j).get("mobileNumber"));
                        map3.put("userId", listLd.get(j).get("userId"));
                        list.add(map3);
                        count++;
                    }
                    // 存放管理员
                    if(lzArr!=null&&lzArr.length>0){
                        for (int k = 0; k < lzArr.length; k++) {
                            String ld = lzArr[k];
                            List<Map> ajms = aupPlansManageMapper.selectJcbaManager(ld);
                            if(ajms.size()>0){
                                for(int i=0;i<ajms.size();i++){
                                    HashMap<Object, Object> map3 = new HashMap<>();
                                    String levSec = (String) ajms.get(i).get("levSec");
                                    String levThr = (String) ajms.get(i).get("levThr");
                                    map3.put("serial", (count + 1));
                                    map3.put("name", ajms.get(i).get("managerName"));
                                    map3.put("loudong", (levSec + levThr));
                                    map3.put("type", ajms.get(i).get("manageType"));
                                    map3.put("buildNum", "");
                                    map3.put("userId", ajms.get(i).get("userId"));
                                    // map3.put("phoneNum", ajms.get(k).get("phoneNum"));
                                    // map3.put("userId", ajms.get(k).get("userId"));
                                    list.add(map3);
                                    count++;
                                }
                            }
                        }

                    }
                    /*List<Map> ajms = aupPlansManageMapper.selectJcbaManagers(lzArr);
                    for (int k = 0; k < ajms.size(); k++) {
                        HashMap<Object, Object> map3 = new HashMap<>();
                        String levSec = (String) ajms.get(k).get("levSec");
                        String levThr = (String) ajms.get(k).get("levThr");
                        map3.put("serial", (count + 1));
                        map3.put("name", ajms.get(k).get("managerName"));
                        map3.put("loudong", (levSec + levThr));
                        map3.put("type", ajms.get(k).get("manageType"));
                        map3.put("buildNum", "");
                        map3.put("userId", ajms.get(k).get("wxNum"));
                        // map3.put("phoneNum", ajms.get(k).get("phoneNum"));
                        // map3.put("userId", ajms.get(k).get("userId"));
                        list.add(map3);
                        count++;
                    }*/
                }
            }
        } else if (modelTypeName.equals("gfts")) {
            if (fwList != null && fwList.size() > 0) {
                String ldString = "";
                if (bhType.equals("JSGX")) {
                    for (int i = 0; i < fwList.size(); i++) {
                        ldString += (fwList.get(i).get("ldno") + ",");
                    }
                } else {
                    for (int i = 0; i < fwList.size(); i++) {
                        AupBuildingWaterValve awr = (AupBuildingWaterValve) fwList.get(i);
                        ldString += (awr.getBuildingNo() + ",");
                    }
                    if(ldString!=""){
                        ldString = ldString.substring(0,ldString.length()-1);
                    }
                }
                String[] ldArr = ldString.split(",");
                List<Map> listLd = aupPlansManageMapper.selectAupUsersListArr(ldArr);
                for (int j = 0; j < listLd.size(); j++) {
                    HashMap<Object, Object> map3 = new HashMap<>();
                    map3.put("serial", (j + 1));
                    map3.put("name", listLd.get(j).get("username"));
                    map3.put("loudong", listLd.get(j).get("ldName"));
                    map3.put("type", listLd.get(j).get("buildingType"));
                    map3.put("buildNum", listLd.get(j).get("ldNum"));
                    map3.put("openId", listLd.get(j).get("openId"));
                    map3.put("phoneNum", listLd.get(j).get("mobileNumber"));
                    map3.put("userId", listLd.get(j).get("userId"));
                    list.add(map3);
                    count++;
                }
                // 存放管理员
                if(ldArr!=null&&ldArr.length>0){
                    for(int k =0;k< ldArr.length;k++){
                        String ld = ldArr[k];
                        List<Map> ajms = aupPlansManageMapper.selectJcbaManager(ld);
                        if(ajms.size()>0){
                            HashMap<Object, Object> map3 = new HashMap<>();
                            String levSec = (String) ajms.get(0).get("levSec");
                            String levThr = (String) ajms.get(0).get("levThr");
                            map3.put("serial", (k + 1));
                            map3.put("name", ajms.get(0).get("managerName"));
                            map3.put("loudong", (levSec + levThr));
                            map3.put("type", ajms.get(0).get("manageType"));
                            map3.put("buildNum", "");
                            map3.put("userId", ajms.get(0).get("userId"));
                            // map3.put("phoneNum", ajms.get(k).get("phoneNum"));
                            // map3.put("userId", ajms.get(k).get("userId"));
                            list.add(map3);
                            count++;
                        }
                    }
                }
                /*List<Map> ajms = aupPlansManageMapper.selectJcbaManagers(ldArr);
                //for (int k = count; k < (listLd.size() + count); k++) {
                for (int k = 0; k < ajms.size(); k++) {
                    HashMap<Object, Object> map3 = new HashMap<>();
                    String levSec = (String) ajms.get(k).get("levSec");
                    String levThr = (String) ajms.get(k).get("levThr");
                    map3.put("serial", (k + 1));
                    map3.put("name", ajms.get(k).get("managerName"));
                    map3.put("loudong", (levSec + levThr));
                    map3.put("type", ajms.get(k).get("manageType"));
                    map3.put("buildNum", "");
                    map3.put("userId", ajms.get(k).get("wxNum"));
                    // map3.put("phoneNum", ajms.get(k).get("phoneNum"));
                    // map3.put("userId", ajms.get(k).get("userId"));
                    list.add(map3);
                    count++;
                }*/
            }
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", 200);
        resultMap.put("msg", "");
        resultMap.put("total", count);
        resultMap.put("rows", list);

        return resultMap;
    }

    private ArrayList getFwCountList(List<Map> fwList, String bhType) {
        ArrayList list = new ArrayList();
        String fwNo = "";
        ldNumList = new ArrayList<>();
        if (fwList != null && fwList.size() > 0) {
            if (bhType.equals("JSGX")) {
                for (int i = 0; i < fwList.size(); i++) {
                    String ldno = (String) fwList.get(i).get("ldno") + ",";
                    fwNo += ldno;
                }
            } else {
                for (int i = 0; i < fwList.size(); i++) {
                    AupBuildingWaterValve awr = (AupBuildingWaterValve) fwList.get(i);
                    String ldno = awr.getBuildingNo() + ",";
                    fwNo += ldno;
                }
            }
            fwNo = fwNo.substring(0, fwNo.length() - 1);
            List<Map> fwMap = aupBuildingMapper.getFwList(Convert.toStrArray(fwNo));
            for (int i = 0; i < fwMap.size(); i++) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put("serial", i + 1);
                map.put("pinqu", (String) fwMap.get(i).get("areaName"));
                map.put("loudong", (String) fwMap.get(i).get("buildingName"));
                map.put("type", (String) fwMap.get(i).get("buildingType"));
                map.put("buildNum", (String) fwMap.get(i).get("No"));
                ldNumList.add((String) fwMap.get(i).get("No"));
                list.add(map);
            }
        } else {
            // HashMap<Object, Object> map = new HashMap<>();
            /*
             * map.put("serial", ""); map.put("pinqu", ""); map.put("loudong",
             * ""); map.put("type", ""); map.put("buildNum", "");
             * ldNumList.add("1");
             */
            // list.add(map);
        }
        return list;
    }

    /**
     * 关阀停水右边BOx数据加载
     *
     * @param
     * @return
     */
    @Override
    public AjaxResult selectGftsRightBox(String oid, String gftsBhType) {
        HashMap<Object, Object> map = new HashMap<>();
        // lineList = new ArrayList<>(); // 管线集合
        closevalveList = new ArrayList<>();// 阀门点集合
        // valveList = new ArrayList<>();// 阀门点集合
        fwList = new ArrayList<>();// 房屋集合
        String fwNo = "";
        if (gftsBhType.equals("JSGX")) {
            // 标绘管线
            HttpClientFwPdf httpClientFwPdf = new HttpClientFwPdf();
            String result = "";
            JSONObject parseObject = null;
            try {
                result = httpClientFwPdf.getFwPdf("gfts", oid);
                parseObject = JSONObject.unmarshal(result);
                // lineList = (List<Map>) parseObject.get("line");
                closevalveList = (List<Map>) parseObject.get("closevalve");
                // valveList = (List<Map>) parseObject.get("valve");
                fwList = (List<Map>) parseObject.get("fw");
                map = getFwCountMap(fwList, gftsBhType);

                if (closevalveList != null) {
                    map.put("bdx", closevalveList.size());
                } else {
                    map.put("bdx", 0);
                }
                /*
                 * if (valveList != null) { map.put("gdgx", valveList.size()); }
                 * else { map.put("gdgx", 0); }
                 */
            } catch (Exception e) {
                e.printStackTrace();
            }
            return AjaxResult.success(map);
        } else if (gftsBhType.equals("FW")) {
            // 标绘房屋查询
            String code = oid;
            ArrayList closeFmList = new ArrayList(); // 存储阀门
            ArrayList awrFwList = new ArrayList(); // 存储房屋
            String fmStr = "";
            List<AupBuildingWaterValve> aupWaterRels = aupWaterRelService.selectByBuilding(code);
            //List<AupWaterRel> aupWaterRels = aupWaterRelService.selectDirectByCode(code);
            // 保存影响阀门
            if (aupWaterRels != null) {
                for (int i = 0; i < aupWaterRels.size(); i++) {
                    closeFmList.add(aupWaterRels.get(i));
                    // 获取阀门ID
                    fmStr += aupWaterRels.get(i).getValveNo() + ",";
                    /*if (i == 0) {
                        fmStr += aupWaterRels.get(i).getRelCurr();
                    } else {
                        fmStr += "," + aupWaterRels.get(i).getRelCurr();
                    }*/
                }
                if(fmStr!=""){
                    fmStr = fmStr.substring(0,fmStr.length()-1);
                }
            }
            List<AupBuildingWaterValve> aupWaterRelsFw = aupWaterRelService.selectByValve(fmStr);
            //List<AupWaterRel> aupWaterRelsFw = aupWaterRelService.selectImpactByValve(fmStr); // 查询影响房屋
            if (aupWaterRelsFw != null) {
                for (int i = 0; i < aupWaterRelsFw.size(); i++) {
                    AupBuildingWaterValve aupWaterRel = aupWaterRelsFw.get(i);
                    String relCode = aupWaterRel.getBuildingNo();
                    //long l = Long.parseLong(relCode);
                    //AupBuildingArea aupBuildingArea = aupBuildingAreaMapper.selectAupBuildingAreaById(l);
                    AupBuildingtype abt = new AupBuildingtype();
                    abt.setNo(relCode);
                    List<AupBuildingtype> aupBuildingtypes = aupBuildingtypeMapper.selectAupBuildingtypeList(abt);
                    if(aupBuildingtypes.size()>0){
                        aupWaterRel.setBuildingNo(aupBuildingtypes.get(0).getNo());
                    }

                    awrFwList.add(aupWaterRel);
                }
            }
            closevalveList = closeFmList; // 关闭阀门集合1
            fwList = awrFwList;
            map = getFwCountMap(fwList, gftsBhType);
            if (closevalveList != null) {
                map.put("bdx", closevalveList.size());
            } else {
                map.put("bdx", 0);
            }
            return AjaxResult.success(map);
        } else {
            return AjaxResult.success("参数错误！");
        }
    }

    /**
     * 查询房屋的数量和 详细列表
     *
     * @param fwList
     * @return
     */
    private HashMap<Object, Object> getFwCountMap(List<Map> fwList, String gftsBhType) {
        HashMap<Object, Object> map = new HashMap<>();
        // fwList = (List<Map>) parseObject.get("fw");
        // 查询房屋数量和类型
        String fwNo = "";
        if (fwList != null && fwList.size() > 0) {
            if (gftsBhType.equals("JSGX")) {
                for (int i = 0; i < fwList.size(); i++) {
                    String ldno = (String) fwList.get(i).get("ldno") + ",";
                    fwNo += ldno;
                }
            } else {
                for (int i = 0; i < fwList.size(); i++) {
                    AupBuildingWaterValve awr = (AupBuildingWaterValve) fwList.get(i);
                    String ldno = awr.getBuildingNo() + ",";
                    fwNo += ldno;
                }
            }
            fwNo = fwNo.substring(0, fwNo.length() - 1);
            List<Map> fwMap = aupBuildingMapper.getFwCount(Convert.toStrArray(fwNo));
            List<Map> ldAdminMap = aupBuildingMapper.getLdAdminMap(Convert.toStrArray(fwNo));
            map.put("jml", 0);
            map.put("ssl", 0);
            map.put("jxl", 0);
            map.put("syl", 0);

            map.put("ssgly", 0);
            map.put("hqb", 0);
            map.put("jmgly", 0);
            map.put("jxgly", 0);
            for (int i = 0; i < fwMap.size(); i++) {
                String fwTypeName = (String) fwMap.get(i).get("fwType");
                switch (fwTypeName) {
                    case "居民楼":
                        map.remove("jml");
                        map.put("jml", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    case "宿舍楼":
                        map.remove("ssl");
                        map.put("ssl", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    case "教学楼":
                        map.remove("jxl");
                        map.put("jxl", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    case "商业楼":
                        map.remove("syl");
                        map.put("syl", (Integer) fwMap.get(i).get("fwCount"));
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < ldAdminMap.size(); i++) {
                String manageType = (String) ldAdminMap.get(i).get("manageType");
                if (manageType != null) {
                    switch (manageType) {
                        case "宿舍管理员":
                            map.remove("ssgly");
                            map.put("ssgly", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        case "后勤部":
                            map.remove("hqb");
                            map.put("hqb", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        case "居民管理员":
                            map.remove("jmgly");
                            map.put("jmgly", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        case "教学管理员":
                            map.remove("jxgly");
                            map.put("jxgly", (Integer) ldAdminMap.get(i).get("manageCount"));
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            map.put("jml", 0);
            map.put("ssl", 0);
            map.put("jxl", 0);
            map.put("syl", 0);
            map.put("ssgly", 0);
            map.put("hqb", 0);
            map.put("jmgly", 0);
            map.put("jxgly", 0);
        }
        return map;
    }

    /**
     * 查询右边列表数据
     *
     * @param type
     * @return
     */
    @Override
    public Map selectZnpgRightBox(String type) {

        ArrayList list = new ArrayList();

        for (int i = 0; i < 63; i++) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("number", "ELD354224" + i);
            map.put("layer", "tc" + i);
            map.put("distance", "7.34");
            map.put("standard", "0.00");
            list.add(map);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", 200);
        resultMap.put("msg", "");
        resultMap.put("total", "30");
        resultMap.put("rows", list);

        return resultMap;
    }

    /**
     * 预案管理
     *
     * @return
     * @para
     */
    @Override
    public List<Map<String, Object>> getYaglList(String type) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        AupPlansManage aupPlansManage = new AupPlansManage();
        aupPlansManage.setPlansName1(type);
        List<AupPlansManage> aupPlansManages = aupPlansManageMapper.selectAupPlansManageList(aupPlansManage);
        for (int i = 0; i < aupPlansManages.size(); i++) {
            Map<String, Object> yaList = new LinkedHashMap<String, Object>();
            yaList.put("name", aupPlansManages.get(i).getFilename());
            yaList.put("src", aupPlansManages.get(i).getFileurl());
            yaList.put("id", i + 1);
            mapList.add(yaList);
        }
        return mapList;
    }

    /**
     * 文件解析
     *
     * @param
     * @return
     */
    @Override
    public AjaxResult readFileContent(String type) {
        List<String> mapList = new ArrayList<>();
        // 拼接读取的内容(while循环中不到断尾时,将字符一个个加入拼接)
        StringBuilder sb = new StringBuilder();
        try {
            // 临时变量，存储sb去除空格的内容
            String temp = null;
            BufferedReader reader = new BufferedReader(
                    new FileReader(new File(Global.getDownloadPath() + "yaglFile/" + type)));
            int ch = 0;
            // 注意文本最后一段末尾不是'\r'。所以ch=-1时，最后一段sb已经拼接的temp还没有存入res.
            while ((ch = reader.read()) != -1) {
                // 去除前后空格，之后去除中间空格
                temp = sb.toString().trim().replaceAll("\\s+", "");
                if ((char) ch == '\r') {
                    // 判断是否是空行
                    if (!"".equals(temp)) {
                        // 说明到了段落结尾，将其加入链表，并清空sb
                        mapList.add(temp);
                    }
                    // 清空sb
                    sb.delete(0, sb.length());
                    temp = null;
                } else {
                    // 说明没到段落结尾，将结果暂存
                    sb.append((char) ch);
                }
            }
            // 最后一段如果非空， 将最后一段加入，否则不处理
            if (!"".equals(temp)) {
                mapList.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AjaxResult.success(mapList);
    }

    /**
     * 获取信息推送用户和管理员信息
     *
     * @param type
     * @return
     */
    @Override
    public AjaxResult selectLzxdUserTree(String type) {

        // 使用jackJson序列化
        ObjectMapper om = new ObjectMapper();
        String key = "dept";
        // RedisUtils.del("dept");
        if (redisTemplate.hasKey(key)) {
            // 从Redis中取出来
            String ltListStr = (String) redisTemplate.opsForValue().get(key);
            JSONArray layuiTree = JSONObject.parse(ltListStr, JSONArray.class);
            // System.out.println("从缓存中取的数据");
            return AjaxResult.success(layuiTree);
        }
        // 如果没有,从数据库中取出来并返回
        // System.out.println("从数据库中取");
        // 获取部门列表
        // List<AupBuildingArea> areaList =
        // aupBuildingAreaMapper.selectAupBuildingAreaList(area);
        List<AupBuildingArea> areaList = aupPlansManageMapper.selectAupBuildingAreaList(new AupBuildingArea());
        // 获取管理员列表
        List<AupipeJcbzWoker> jcbzManagersList = aupipeJcbzWorkerMapper.selectAupJcbzManagerList(new AupipeJcbzWoker());
        // 获取用户列表
        // List<Map> usersList = selectAllUser();
        // 构造树对象
        List<LayuiTree1> layuiTree = initLzxdUserTree(areaList, jcbzManagersList);
        try {
            redisTemplate.opsForValue().set(key, om.writeValueAsString(layuiTree));
            // System.out.println("将值存到redis");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(layuiTree);
    }

    /**
     * 跨数据库查询全部用户列表
     *
     * @return
     */
    @Override
    public List<Map> selectAllUser(String code) {
        // DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.A.name());

        List<Map> usersList = aupPlansManageMapper.selectAupUsersList(code);
        return usersList;

    }

    /**
     * 查询拉闸限电信息推送内容
     *
     * @param layuiTree
     * @return
     */
    @Override
    public AjaxResult selectLzxdXxts(LayuiTree1 layuiTree) {
        HashMap<Object, Object> map = new HashMap<>();
        String lastName = "";// 发送的全部区域楼栋名称
        for (int j = 0; j < layuiTree.getChildren().size(); j++) {
            String areaName = "";// 每个区域的名称
            String loudongName = "";// 每个楼栋的名称
            areaName = layuiTree.getChildren().get(j).getTitle();
            List<LayuiTree1> chilList = layuiTree.getChildren().get(j).getChildren();
            for (int i = 0; i < chilList.size(); i++) {
                if (i == 0) {
                    loudongName += chilList.get(i).getTitle();
                } else {
                    loudongName += ("," + chilList.get(i).getTitle());
                }
            }
            if (j == 0) {
                lastName = areaName + loudongName;
            } else {
                lastName += (";" + areaName + loudongName);
            }
        }
        map.put("area", lastName);

        return AjaxResult.success(map);
    }

    /**
     * 根据回路ID 查房屋和配电房信息
     *
     * @param hlId
     * @param modelTypeName
     * @return
     */
    @Override
    public AjaxResult getHlInfo(String hlId, String modelTypeName) {

        AupHlInfo aupHlInfo = new AupHlInfo();
        aupHlInfo.setHlId(hlId);
        List<AupHlInfo> aupHlinfos = aupipeJcbzWorkerMapper.getHlInfo(aupHlInfo);
        ArrayList fwList = new ArrayList();
        ArrayList pdfList = new ArrayList();
        String pdfId = "";
        for (AupHlInfo hlInfo : aupHlinfos) {
            // 获取房屋ID
            //long fwId = hlInfo.getFwId();
            String fwId = hlInfo.getFwId();
            if (fwId != "") {
            //if (fwId != 0.0f) {
                /*AupBuildingArea aupBuildingArea = new AupBuildingArea();
                aupBuildingArea.setId(fwId);
                List<AupBuildingArea> aupBuildingAreas = aupBuildingAreaMapper
                        .selectAupBuildingAreaListInfo(aupBuildingArea);
                fwList.add(aupBuildingAreas);*/
                AupBuildingtype aupBuildingtype = new AupBuildingtype();
                aupBuildingtype.setNo(fwId);
                List<AupBuildingtype> aupBuildingtypes = aupBuildingtypeMapper.selectAupBuildingtypeList(aupBuildingtype);
                fwList.add(aupBuildingtypes);
            }
            // 获取配电房Id
            if (!(hlInfo.getPdfId().equals(pdfId))) {
                pdfId = hlInfo.getPdfId();
                //long fwId_ = hlInfo.getFwId();
                String fwId_ = hlInfo.getFwId();
                String hlId1 = hlInfo.getHlId();
                if (pdfId != "" && pdfId != null) {
                    /*
                     * AupPower aupPower = new AupPower();
                     * aupPower.setBsm(pdfId); List<AupPower> aupPowers =
                     * aupPowerMapper.selectPdfInfoList(aupPower);
                     */
                    List<Map> aupPowers = aupPowerMapper.selectPdfInfoList(pdfId, (fwId_), hlId1);
                    pdfList.add(aupPowers);
                }
            }
        }
        HashMap map = new HashMap();
        map.put("fwInfo", fwList);
        map.put("pdfInfo", pdfList);

        return AjaxResult.success(map);
    }

    /**
     * 根据回路ID 获取属性
     *
     * @param hlId
     * @return
     */
    @Override
    public AjaxResult getHlAttriBute(String hlId) {
        HashMap map = new HashMap();
        if (!(hlId.equals(""))) {
            AupHl aupHl = new AupHl();
            aupHl.set回路id(hlId);
            List<AupHl> aupHlList = aupipeJcbzWorkerMapper.getHlAttriBute(aupHl);
            map.put("hlAttribute", aupHlList);
            return AjaxResult.success(map);
        } else {
            return AjaxResult.success("没有回路ID", map);
        }
    }

    @Override
    public AjaxResult getLdNumList(String type, String bhType) {

        ldNumList = new ArrayList<>();
        String fwNo = "";
        if (type.equals("lzxd")) {
            if (lzxdFwNo != null && (!(lzxdFwNo.equals("")))) {
                fwNo = lzxdFwNo;
                List<Map> fwMap = aupBuildingMapper.getFwList(Convert.toStrArray(fwNo));
                for (int i = 0; i < fwMap.size(); i++) {
                    ldNumList.add((String) fwMap.get(i).get("No"));
                }
            }
        } else if (type.equals("gfts")) {
            if (fwList != null && fwList.size() > 0) {
                if (bhType.equals("JSGX")) {
                    for (int i = 0; i < fwList.size(); i++) {
                        String ldno = (String) fwList.get(i).get("ldno") + ",";
                        fwNo += ldno;
                    }
                } else {
                    for (int i = 0; i < fwList.size(); i++) {
                        AupBuildingWaterValve awr = (AupBuildingWaterValve) fwList.get(i);
                        String ldno = awr.getBuildingNo() + ",";
                        fwNo += ldno;
                    }
                }
                fwNo = fwNo.substring(0, fwNo.length() - 1);
                List<Map> fwMap = aupBuildingMapper.getFwList(Convert.toStrArray(fwNo));
                for (int i = 0; i < fwMap.size(); i++) {
                    ldNumList.add((String) fwMap.get(i).get("No"));
                }
            }
        }
        ArrayList<Long> list = new ArrayList<>();
        String allNums = "";
        if (ldNumList != null && ldNumList.size() > 0) {
            for (int i = 0; i < ldNumList.size(); i++) {
                String ldnum = (String) ldNumList.get(i);
                if (i == (ldNumList.size() - 1)) {
                    allNums += (ldnum);
                } else {
                    allNums += (ldnum + ",");
                }
                // AupBuildingArea aupBuildingArea = new AupBuildingArea();
                // aupBuildingArea.setAreaNo(ldnum);
                // List<AupBuildingArea> bas =
                // aupBuildingAreaMapper.selectAupBuildingAreaList(aupBuildingArea);
            }
            List<AupBuildingArea> bas = aupBuildingAreaMapper.selectAllLdNums(Convert.toStrArray(allNums));
            for (AupBuildingArea ba : bas) {
                list.add(ba.getId());
            }

        }
        return AjaxResult.success(list);
    }

    /**
     * App 获取拉闸限电数据
     *
     * @return
     */
    @Override
    public AjaxResult appLzxd(String fwId) {
        //long l = Long.parseLong(fwId);
        List<AupHlInfo> Hlidlist = aupipeJcbzWorkerMapper.selectHlid(fwId);
        AjaxResult hlInfo = null;
        if (Hlidlist.size() > 0) {
            AupHlInfo aupHlInfo = Hlidlist.get(0);
            String hlId = aupHlInfo.getHlId();
            hlInfo = getHlInfo(hlId, "");
            return hlInfo;
        } else {
            return AjaxResult.success("未查询到该房屋ID");
        }
    }

    /**
     * 选取楼栋推送用户模块获取人员数量
     *
     * @param ldNum
     * @return
     */
    @Override
    public AjaxResult selectLdPersonNum(String ldNum) {
        //List<Map> list = aupPlansManageMapper.selectLdPersonNum(Convert.toStrArray(ldNum));
        String[] ldNumArr = ldNum.split(",");
        List<Map> listAll = new ArrayList();
        for(String s : ldNumArr){
            List<Map> list = aupPlansManageMapper.selectLdPersonNum(s);
            HashMap<Object, Object> map = new HashMap<>();
            map.put("userNum", list.get(0).get("adminUserNum"));
            map.put("adminNum", list.get(1).get("adminUserNum"));
            listAll.add(map);
        }
        return AjaxResult.success("请求成功",listAll);
    }

    /**
     * 对象转部门树
     *
     * @param areaList 部门列表
     * @return 树结构列表
     */
    public List<LayuiTree1> initLzxdUserTree(List<AupBuildingArea> areaList, List<AupipeJcbzWoker> jcbzManagersList) {

        List<LayuiTree1> lts = new ArrayList<>(); // 一级树
        List<LayuiTree1> lts2 = new ArrayList<>(); // 二级树
        List<LayuiTree1> lts3 = new ArrayList<>(); // 三级树
        for (AupBuildingArea area : areaList) {
            Long id = area.getId();
            Map<String, Object> map = new HashMap<>();
            if (area.getParentCode().equals("root")) {
                map = new HashMap<>();
                map.put("areaNo", area.getAreaNo());
                LayuiTree1 layuiTree = new LayuiTree1(id, area.getCode(), area.getName(), map, false);
                lts2.add(layuiTree);
            } else if (area.getParentCode().equals("0")) {
                map = new HashMap<>();
                map.put("areaNo", area.getAreaNo());
                LayuiTree1 layuiTree = new LayuiTree1(id, area.getCode(), area.getName(), map, false);
                lts.add(layuiTree);
            } else {
                map = new HashMap<>();
                map.put("areaNo", area.getAreaNo());
                LayuiTree1 layuiTree = new LayuiTree1(id, area.getCode(), area.getName(), map, false);
                lts3.add(layuiTree);
            }
        }
        for (LayuiTree1 lt2 : lts2) {
            List<LayuiTree1> lts_child1_ = new ArrayList<>();
            for (LayuiTree1 lt3 : lts3) {
                if ((lt3.getField()).substring(0, 9).equals(lt2.getField())) {
                    List<LayuiTree1> lts_child3_ = new ArrayList<>();
                    LayuiTree1 layuiTree1 = new LayuiTree1(1, "admin", "管理员", null, false);
                    List<LayuiTree1> ltsAdmin = new ArrayList<>(); // 管理员树
                    for (AupipeJcbzWoker jm : jcbzManagersList) {
                        String[] jmCodeArr = jm.getCode().split(",");
                        if (jmCodeArr.length > 0) {
                            for (int i = 0; i < jmCodeArr.length; i++) {
                                if (lt3.getField().equals(jmCodeArr[i])) {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("parentCode", jm.getParentcode());
                                    map.put("code", jm.getCode());
                                    map.put("phoneNum", jm.getPhonenum());
                                    map.put("openId", jm.getWxnum());
                                    map.put("manageType", jm.getManagetype());
                                    // System.out.println(jm.getManagerUuid());
                                    LayuiTree1 layuiTree = new LayuiTree1(jm.getManagerUuid(), jm.getCode(),
                                            jm.getManagername(), map, false);
                                    ltsAdmin.add(layuiTree);
                                }
                            }
                        }
                    }
                    layuiTree1.setChildren(ltsAdmin);
                    LayuiTree1 layuiTree2 = new LayuiTree1(2, "user", "用户", null, false);
                    List<LayuiTree1> ltsUser = new ArrayList<>(); // 用户树
                    List<Map> usersList = selectAllUser(lt3.getField());
                    if (usersList.size() > 0) {
                        for (int i = 0; i < usersList.size(); i++) {
                            if (usersList.get(i).get("parentid") != null) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("parentCode", ((String) (usersList.get(i).get("parentid"))).substring(0, 9));
                                map.put("code", ((String) (usersList.get(i).get("parentid"))).substring(0, 16));
                                map.put("phoneNum", usersList.get(i).get("mobileNumber"));
                                map.put("wxNum", usersList.get(i).get("wxUser"));
                                map.put("openId", usersList.get(i).get("openId"));
                                map.put("fwNameNum", usersList.get(i).get("name"));
                                int keyId = Integer.parseInt((String) usersList.get(i).get("keyId"));
                                String code = (String) (usersList.get(i).get("parentid"));
                                String userName = (String) usersList.get(i).get("username");
                                LayuiTree1 layuiTree = new LayuiTree1(keyId, code.substring(0, 16), userName, map,
                                        false);
                                ltsUser.add(layuiTree);
                            }
                        }
                    }
                    layuiTree2.setChildren(ltsUser);

                    lts_child3_.add(layuiTree1);
                    lts_child3_.add(layuiTree2);
                    lt3.setChildren(lts_child3_);
                    if (lt2.getField().equals((lt3.getField().substring(0, 9)))) {
                        lts_child1_.add(lt3);
                    }
                }

            }
            lt2.setChildren(lts_child1_);
        }
        for (LayuiTree1 lt : lts) {
            lt.setChildren(lts2);
        }

        return lts;
    }

}
