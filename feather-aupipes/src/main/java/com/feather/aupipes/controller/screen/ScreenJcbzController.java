package com.feather.aupipes.controller.screen;

import com.feather.aupipes.config.AupipesMsgSender;
import com.feather.aupipes.domain.AupPlansType;
import com.feather.aupipes.domain.LayuiTree1;
import com.feather.aupipes.service.IAupDecisionSecurityService;
import com.feather.aupipes.service.IAupPlansManageService;
import com.feather.aupipes.service.IAupPlansTypeService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import com.feather.prd.domain.PrdMsg;
import com.feather.prd.service.IPrdMsgService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大屏 子模块 - 决策保障
 */
@Controller
@RequestMapping("/screen/jcbz")
public class ScreenJcbzController extends BaseController {
    String prefix = "/aupipes/screen/jcbz";

    @Autowired
    IAupDecisionSecurityService iAupDecisionSecurityService;

    @Autowired
    private IAupPlansTypeService aupPlansTypeService;

    @Autowired
    private IPrdMsgService prdMsgService;

    @Autowired
    private IAupPlansManageService iAupPlansManageService;

    /**
     * 拉闸限电设备列表
     */
    @RequestMapping("/sblb")
    public String sblb(ModelMap mmap) {
        Object data = null;
        mmap.put("leftListTree", data);
        mmap.put("dataA", "aaaa");
        return prefix + "/lzxd/sblb";
    }

    /**
     * 拉闸限电事故分析
     */
    @RequestMapping("/jcbzSgfxInit")
    public String jcbzSgfxInit(ModelMap mmap, String name) throws Exception {

        mmap.put("rightBox", null);
        if (name.equals("lzxd")) {
            return prefix + "/lzxd/sgfxLzxdInit";
        } else if (name.equals("gfts")) {
            return prefix + "/gfts/sgfxGftsInit";
        }
        return "";
    }

    /**
     * 拉闸限电事故分析 oid : 房屋id， 或者配电房id lzxdType： 选中的类型是房屋还是配电房
     */
    @RequestMapping("/sgfx")
    public String sgfx(ModelMap mmap, String oid, String lzxdType) throws Exception {
        AjaxResult ajaxResult = iAupDecisionSecurityService.selectLzxdRightBox(oid, lzxdType);
        Object data = ajaxResult.get("data");
        mmap.put("rightBox", data);
        return prefix + "/lzxd/sgfx";
    }

    /**
     * 加载拉闸限电用户树
     */
    @GetMapping("/lzxdUserTree")
    @ResponseBody
    public AjaxResult lzxdUserTree(String type, String bhType) {

        AjaxResult ajaxResult = iAupDecisionSecurityService.selectLzxdUserTree(type);
        int sum = 0;
        int ssManageSum = 0;
        int hqbSum = 0;
        int jxManageSum = 0;
        ArrayList<String> countList = new ArrayList<>();
        ArrayList<Long> ldNumList = (ArrayList) iAupDecisionSecurityService.getLdNumList(type, bhType).get("data");
        JSONArray ajaxResult1 = (JSONArray) ajaxResult.get("data");

        for (long ob1 : ldNumList) {
            for (Object ob2 : ajaxResult1) {
                JSONObject jb2 = (JSONObject) ob2;
                if (jb2.get("children") != null) {
                    JSONArray ja3 = (JSONArray) jb2.get("children");
                    for (Object ob3 : ja3) {
                        JSONObject jb3 = (JSONObject) ob3;
                        if (jb3.get("children") != null) {
                            JSONArray ja4 = (JSONArray) jb3.get("children");
                            for (Object ob4 : ja4) {
                                JSONObject jb4 = (JSONObject) ob4;
                                String s = (jb4.get("id") + "");
                                if (String.valueOf(ob1).equals(s)) {
                                    if (jb4.get("children") != null) {
                                        JSONArray ja5 = (JSONArray) jb4.get("children");
                                        JSONObject jbMange5 = (JSONObject) ja5.get(0);
                                        JSONArray jaManage6 = (JSONArray) jbMange5.get("children");
                                        if (jaManage6.size() > 0) {
                                            for (int i = 0; i < jaManage6.size(); i++) {
                                                JSONObject jb7 = (JSONObject) jaManage6.get(i);
                                                String manageId = jb7.get("id") + "";
                                                if (countList != null && countList.size() > 0) {
                                                    for (String ss : countList) {
                                                        if (!(ss.equals(manageId))) {
                                                            countList.add(manageId);
                                                            Map<String, Object> maps = (Map<String, Object>) jb7
                                                                    .get("maps");
                                                            String manageType = (String) maps.get("manageType");
                                                            switch (manageType) {
                                                            case "宿舍管理员":
                                                                ssManageSum++;
                                                                break;
                                                            case "后勤部":
                                                                hqbSum++;
                                                                break;
                                                            case "教学管理员":
                                                                jxManageSum++;
                                                                break;
                                                            default:
                                                                break;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    countList.add(manageId);
                                                    Map<String, Object> maps = (Map<String, Object>) jb7.get("maps");
                                                    String manageType = (String) maps.get("manageType");
                                                    switch (manageType) {
                                                    case "宿舍管理员":
                                                        ssManageSum++;
                                                        break;
                                                    case "后勤部":
                                                        hqbSum++;
                                                        break;
                                                    case "教学管理员":
                                                        jxManageSum++;
                                                        break;
                                                    default:
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        JSONObject jb5 = (JSONObject) ja5.get(1);
                                        JSONArray ja6 = (JSONArray) jb5.get("children");
                                        if (ja6.size() > 0) {
                                            sum += ja6.size();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ajaxResult.put("sum", sum);
        ajaxResult.put("ssManageSum", ssManageSum);
        ajaxResult.put("hqbSum", hqbSum);
        ajaxResult.put("jxManageSum", jxManageSum);
        ajaxResult.put("ldNumList", ldNumList);
        return ajaxResult;
    }

    /**
     * 预案管理
     */
    @RequestMapping("/yagl")
    public String yagl(ModelMap mmap) {
        AupPlansType aupPlansType = new AupPlansType();
        List<AupPlansType> list = aupPlansTypeService.selectAupPlansTypeList(aupPlansType);
        List<AupPlansType> listNew = new ArrayList<>();
        for (AupPlansType li : list) {
            if (!(li.getParetid().equals("0"))) {
                listNew.add(li);
            }
        }
        mmap.put("listNew", listNew);
        // mmap.put("xxxxxxx", null);
        // return "/aupipes/screen/jcbz::yagl";
        return prefix + "/yagl/yagl";
    }

    /**
     * 预案列表
     */
    @RequestMapping("/yalb")
    public String yalb(ModelMap mmap, String type) {
        List<Map<String, Object>> list = iAupDecisionSecurityService.getYaglList(type);
        mmap.put("list", list);
        return prefix + "/yagl/yalb";
    }

    /**
     * 预案列表右侧
     */
    @RequestMapping("/yabz")
    public String yaxq(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/yagl/yabz";
    }

    /**
     * 拉闸分析右侧
     */
    @RequestMapping("/cxfx")
    public String cxfx(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/lzxd/cxfx";
    }

    /**
     * 拉闸分析右侧
     */
    @RequestMapping("/power")
    public String power(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/lzxd/power";
    }

    /**
     * 预案步骤示意
     */
    @RequestMapping("/bzsy")
    public String bzsy(ModelMap mmap, String type) {
        AjaxResult ajaxResult = iAupDecisionSecurityService.readFileContent(type);
        Object data = ajaxResult.get("data");
        mmap.put("data", data);
        return prefix + "/yagl/bzsy";
    }

    /**
     * 底部分析详情弹框
     */
    @RequestMapping("/dbxq") // 底部弹框详情
    public String dbxq(ModelMap mmap, String lzxdBhsgName) {
        mmap.put("modelName", lzxdBhsgName);
        return prefix + "/lzxd/dbxq";
    }

    /**
     * 底部分析详情弹框数据
     *
     * @param type
     * @return
     */
    @GetMapping("/dbxqBoxData")
    @ResponseBody
    public Map dbxqBoxData(String type, String modelTypeName, String oid, String bhType, ModelMap mmap) {
        Map map = new HashMap();
        if (type.equals("lzfx") || type.equals("gxfx")) {
            map = iAupDecisionSecurityService.selectLzfxList(type, modelTypeName, oid, bhType);
        } else if (type.equals("yxfw")) {
            map = iAupDecisionSecurityService.selectYyfwList(type, modelTypeName, oid, bhType);
        } else if (type.equals("tsyh")) {
            map = iAupDecisionSecurityService.selectDbxqBoxData(type, modelTypeName, oid, bhType);
        }
        return map;
    }

    /**
     * 信息推送
     *
     * @param mmap
     * @return
     */
    @RequestMapping("/xxts") // 信息推送
    public String xxts(ModelMap mmap, String typeName) {

        String name = "";
        if (typeName.equals("拉闸分析")) {
            name = "lzxd";
        } else {
            name = "gfts";
        }
        mmap.put("typeName", name);
        return prefix + "/lzxd/xxts";
    }

    @RequestMapping("/xxts1") // 信息推送 上传树结构选中信息
    @ResponseBody
    public void xxts1(LayuiTree1 layTree) {
        String field = layTree.getField();
        layTree = JSONObject.parse(field, LayuiTree1.class);
    }

    @RequestMapping("/ldXxts") // 选取楼栋信息推送
    public String ldXxts(ModelMap mmap) {

        mmap.put("typeName", null);
        return prefix + "/tsxx/xxts";
    }

    /**
     * 关阀停水设备列表
     */
    @RequestMapping("/gftsSblb")
    public String gftsSblb(ModelMap mmap) {
        Object data = null;
        mmap.put("leftListTree", data);
        mmap.put("dataA", "aaaa");
        return prefix + "/gfts/sblb";
    }

    /**
     * 关阀停水右边数据详情 gftsBhsgName: 分析类型是关阀还是拉闸 oid: 查询值 gftsBhType : 标绘类型是房屋还是管线
     */
    @RequestMapping("/gftsSgfx") // 关阀停水事故分析
    public String gftsSgfx(ModelMap mmap, String oid, String gftsBhType) {
        AjaxResult ajaxResult = iAupDecisionSecurityService.selectGftsRightBox(oid, gftsBhType);
        Object data = ajaxResult.get("data");
        mmap.put("rightBox", data);
        return prefix + "/gfts/sgfx";
    }

    /**
     * 智能排管
     */
    @RequestMapping("/znpg") // 底部弹框详情
    public String znpg(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/znpg/pgfx";
    }

    /**
     * 加载智能排管树结构
     */
    @GetMapping("/znpgTree")
    @ResponseBody
    public AjaxResult znpgTreeList(String type) {

        return null;
    }

    /**
     * 查询右边 rightBox数据
     *
     * @param type
     * @return
     */
    @PostMapping("/znpgRightBox")
    @ResponseBody
    public Map znpgRightBox(String type) {
        return iAupDecisionSecurityService.selectZnpgRightBox(type);
    }

    /**
     * 通知用户发送信息
     */
    @PostMapping("/sendMessage")
    @RequiresRoles("message_push")
    @ResponseBody
    public AjaxResult sendMessage(String type, String typeName, String troubleYy, String userIds) throws Exception {
        if (StringUtils.contains(type, AupipesMsgSender.WX_MSG)) {
            PrdMsg wxMsg = new PrdMsg();
            wxMsg.setMsgApp(AupipesMsgSender.WX_MSG);
            wxMsg.setMsgContent(troubleYy);
            if ("lzxd".equals(typeName)) {
                wxMsg.setMsgType("blackout");
                wxMsg.setMsgTitle("停电通知!");
            } else {
                wxMsg.setMsgType("water_shutoff");
                wxMsg.setMsgTitle("停水通知!");
            }
            prdMsgService.sendToUser(wxMsg, userIds, false);
        }
        if (StringUtils.contains(type, AupipesMsgSender.SMS_MSG)) {
            PrdMsg smsMsg = new PrdMsg();
            smsMsg.setMsgApp(AupipesMsgSender.SMS_MSG);
            if ("lzxd".equals(typeName)) {
                smsMsg.setMsgType("blackout");
                smsMsg.setMsgTitle("停电通知!");
            } else {
                smsMsg.setMsgType("water_shutoff");
                smsMsg.setMsgTitle("停水通知!");
            }
            prdMsgService.sendToUser(smsMsg, userIds, false);
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 选取楼栋主动推送用户消息
     */
    @PostMapping("/ldxxSendMsg")
    @RequiresRoles("message_push")
    @ResponseBody
    public AjaxResult ldxxSendMsg(String type, String troubleYy, String ldIds) throws Exception {
        // 用户id字符串
        String userIds = "";
        // 影响用户列表
        if(ldIds!=""&&ldIds.equals("全校推送")){
            List<Map> listLd = iAupPlansManageService.selectAupUsersListArrAll();
            for (int j = 0; j < listLd.size(); j++) {
                if (j == 0) {
                    userIds += listLd.get(j).get("userId");
                } else {
                    userIds += ("," + listLd.get(j).get("userId"));
                }
            }
        }else{
            List<Map> listLd = iAupPlansManageService.selectAupUsersListArr(ldIds);
            for (int j = 0; j < listLd.size(); j++) {
                if (j == 0) {
                    userIds += listLd.get(j).get("userId");
                } else {
                    userIds += ("," + listLd.get(j).get("userId"));
                }
            }
            // 影响管理员列表
            String[] ldArr = ldIds.split(",");
            for (int k = 0; k < ldArr.length; k++) {
                String ld = ldArr[k];
                List<Map> ajms = iAupPlansManageService.selectJcbaManager(ld);
                if (ajms.size() > 0) {
                    if (userIds == "") {
                        userIds += ajms.get(0).get("userId");
                    } else {
                        userIds += ("," + ajms.get(0).get("userId"));
                    }
                }
            }
        }
        /*
         * List<Map> ajms = iAupPlansManageService.selectJcbaManagers(ldIds);
         * for (int j = 0; j < ajms.size(); j++) { if (userIds == "") { userIds
         * += ajms.get(j).get("wxNum"); } else { userIds += ("," +
         * ajms.get(j).get("wxNum")); } }
         */
        if (StringUtils.contains(type, AupipesMsgSender.WX_MSG)) {
            PrdMsg wxMsg = new PrdMsg();
            wxMsg.setMsgApp(AupipesMsgSender.WX_MSG);
            wxMsg.setMsgType("water_shutoff");
            wxMsg.setMsgContent(troubleYy);
            wxMsg.setMsgTitle("消息提醒!");
            prdMsgService.sendToUser(wxMsg, userIds, false);
        }
        if (StringUtils.contains(type, AupipesMsgSender.SMS_MSG)) {
            PrdMsg smsMsg = new PrdMsg();
            smsMsg.setMsgApp(AupipesMsgSender.SMS_MSG);
            smsMsg.setMsgType("water_shutoff");
            smsMsg.setMsgTitle("消息提醒!");
            prdMsgService.sendToUser(smsMsg, userIds, false);
        }
        return AjaxResult.success("发送成功");
    }

    /**
     * 根据回路ID获取房屋信息 配电房信息
     *
     * @param hlId
     *            回路ID
     * @param modelTypeName
     *            选择拉闸还是停水
     * @return
     */
    @RequestMapping("/getHlInfo")
    @ResponseBody
    public AjaxResult getHlInfo(String hlId, String modelTypeName) {

        return iAupDecisionSecurityService.getHlInfo(hlId, modelTypeName);

    }

    /**
     * 根据回路ID获取属性
     *
     * @param hlId
     *            回路ID
     * @return
     */
    @RequestMapping("/getHlAttriBute")
    @ResponseBody
    public AjaxResult getHlAttriBute(String hlId) {

        return iAupDecisionSecurityService.getHlAttriBute(hlId);

    }

    /**
     * 开挖分析
     */
    @RequestMapping("/kwfx")
    public String kwfx(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/znpg/kwfx";
    }

    /**
     * 横断面分析
     */
    @RequestMapping("/hdmfx")
    public String hdmfx(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/znpg/hdmfx";
    }

    /**
     * 搜索结果
     */
    @RequestMapping("/searchTab")
    public String searchTab(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/znpg/searchTab";
    }

    /**
     * 选取楼栋推送用户
     */
    @RequestMapping("/tsxx")
    public String tsxx(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/tsxx/sendMsg";
    }

    /**
     * 根据标会的楼栋查询用户
     */
    @GetMapping("/getLdInfo")
    @ResponseBody
    public AjaxResult getLdInfo(String ldNum) {

        return iAupDecisionSecurityService.selectLdPersonNum(ldNum);
    }

    /**
     * 楼栋阀门关联关系
     */
    @RequestMapping("/ldfm")
    public String ldfm(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/ldfm/ldfmInit";
    }

    /** ---------------------手机App---------------------------------------------- **/
    @RequestMapping("/appLzxd")
    @ResponseBody
    public AjaxResult appLzxd(String fwId) {
        return iAupDecisionSecurityService.appLzxd(fwId);
    }

}
