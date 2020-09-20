package com.feather.aupipes.service.impl;

import com.feather.aupipes.HttpClient.HttpClinetLights;
import com.feather.aupipes.domain.AupStreetlight;
import com.feather.aupipes.domain.AupStreetlightControl;
import com.feather.aupipes.mapper.AupStreetlightControlMapper;
import com.feather.aupipes.mapper.AupStreetlightMapper;
import com.feather.aupipes.service.IAupStreetlightControlService;
import com.feather.aupipes.service.IAupStreetlightService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 路灯控制Service业务层处理
 *
 * @author fancy
 * @date 2019-12-13
 */
@Service
public class AupStreetlightControlServiceImpl implements IAupStreetlightControlService {
    @Autowired
    private AupStreetlightControlMapper aupStreetlightControlMapper;

    @Autowired
    private IAupStreetlightService aupStreetlightService;

    @Autowired
    private AupStreetlightMapper aupStreetlightMapper;
    /**
     * 查询路灯控制
     *
     * @param sid 路灯控制ID
     * @return 路灯控制
     */
    @Override
    public AupStreetlightControl selectAupStreetlightControlById(String sid) {
        return aupStreetlightControlMapper.selectAupStreetlightControlById(sid);
    }

    /**
     * 查询路灯控制列表
     *
     * @param aupStreetlightControl 路灯控制
     * @return 路灯控制
     */
    @Override
    public List<AupStreetlightControl> selectAupStreetlightControlList(AupStreetlightControl aupStreetlightControl) {
        return aupStreetlightControlMapper.selectAupStreetlightControlList(aupStreetlightControl);
    }

    /**
     * 一次性获取全部路灯和控制器
     *
     * @return 结果
     */
    @Override
    public List<Ztree> selectAupStreetlightTree() {
        List<AupStreetlightControl> aupStreetlightControls = aupStreetlightControlMapper.selectAupStreetlightControlList(new AupStreetlightControl());
        List<AupStreetlight> aupStreetlights = aupStreetlightMapper.selectAupStreetlightList(new AupStreetlight());

        List<Ztree> ztrees = new ArrayList<>();
        List<JSONObject> lightStatusList = new ArrayList<>();
        String unLineCode = "2";
        for (AupStreetlightControl aupStreetlightControl : aupStreetlightControls) {
            //获取路灯的实时亮灯状态
            String cuid = aupStreetlightControl.getCuid();
            HttpClinetLights httpClinetLights = new HttpClinetLights();
            JSONObject[] lightStatus = httpClinetLights.getLightStatus(cuid);
            if (lightStatus != null) {
                lightStatusList.addAll(new ArrayList<>(Arrays.asList(lightStatus)));
            }

            Ztree ztree = new Ztree();
            ztree.setId(aupStreetlightControl.getId());
            ztree.setpId(null);
            ztree.setName(aupStreetlightControl.getSname());
            ztree.setCode(aupStreetlightControl.getSid());
            ztree.setTitle(aupStreetlightControl.getSname());
            ztree.setMaps(BeanUtils.beanToMap(aupStreetlightControl));

            ztrees.add(ztree);
        }
        for (AupStreetlight aupStreetlight : aupStreetlights) {
            String statusName = "开启";
            for (JSONObject obj : lightStatusList) {
                String luid = "luid";
                String ls = "ls";
                if (obj.getString(luid).equals(aupStreetlight.getLuid())) {
                    String status = obj.getString(ls);
                    if (status.equals(unLineCode)) {
                        statusName = "离线";
                    }
                }
                Ztree ztree = new Ztree();
                StringBuilder sb = new StringBuilder(16);
                sb.append("<span>");
                sb.append(aupStreetlight.getName());
                sb.append("<i style='display:none'>").append(statusName).append("</i>");
                sb.append("</span>");
                ztree.setId(aupStreetlight.getId());
                ztree.setpId(aupStreetlight.getParentId());
                ztree.setName(sb.toString());
                ztree.setCode(aupStreetlight.getLid());
                ztree.setTitle(aupStreetlight.getLid());
                ztree.setMaps(BeanUtils.beanToMap(aupStreetlight));

                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 按父节点获取全部状态路灯或者控制器
     *
     * @param pid 父id
     * @return 结果
     */
    @Override
    public List<Ztree> selectAupStreetlightTree(String pid) {
        List<Map<String,Object>> mapList = aupStreetlightService.queryStreetlightControlTree(pid);
        List<Ztree> ztrees = new ArrayList<>();
        if (mapList.size() > 0) {
            for (Map<String,Object> m : mapList) {
                String type = m.get("type").toString();
                if ("streetlightControl".equals(type)) {
                    ztrees.add(this.getStreetlightControlZtree(m));
                }
                if ("streetlight".equals(type)) {
                    ztrees.add(this.getStreetlightZtree(m));
                }

            }
        }
        return ztrees;
    }

    /**
     * 按父节点获取全部离线的路灯包括所属于的控制器
     * @param pid 父id
     * @return 结果
     */
    @Override
    public List<Ztree> selectAupStreetlightTreeUnline(String pid) {
        List<Map<String,Object>> mapList = aupStreetlightService.queryStreetlightControlTree(pid);
        List<Ztree> ztrees = new ArrayList<>();
        HttpClinetLights httpClinetLights = new HttpClinetLights();
        if (mapList.size() > 0) {
            String cuid = mapList.get(0).get("cuid").toString();
            for (Map<String,Object> m : mapList) {
                String type = m.get("type").toString();
                if ("streetlightControl".equals(type)) {
                    ztrees.add(this.getStreetlightControlZtree(m));
                }
                if ("streetlight".equals(type)) {
                    //根据cuid获取路灯的在线离线状态
                    JSONObject[] obj = httpClinetLights.getLightStatus(cuid);
                    if (obj == null){
                        return null;
                    }
                    for (JSONObject o:obj) {
                        if (o.getString("luid").equals(m.get("luid"))) {
                            if ("2".equals(o.getString("ls"))) {
                                ztrees.add(this.getStreetlightZtree(m));
                            }
                        }
                    }
                }
            }
        }
        return ztrees;
    }

    /**
     * 设置路灯
     * @param m 参数
     * @return 结果
     */
    private Ztree getStreetlightZtree(Map<String,Object> m){
            Ztree sztree = new Ztree();

            sztree.setId_(m.get("id").toString());
            sztree.setPid_(m.get("pid").toString());
            sztree.setName(m.get("name").toString());
            sztree.setCode(m.get("lid").toString());
            sztree.setTitle(m.get("lid").toString());
            sztree.setMaps(m);
            sztree.setIsParent(false);

            return sztree;
        }

    /**
     * 设置路灯控制器
     * @param m 参数
     * @return 结果
     */
    private Ztree getStreetlightControlZtree(Map<String,Object> m){
            Ztree ztree = new Ztree();
            ztree.setId_(m.get("sid").toString());
            ztree.setPid_("0");
            ztree.setName(m.get("sname").toString());
            ztree.setCode(m.get("sid").toString());
            ztree.setTitle(m.get("sname").toString());
            ztree.setMaps(m);
            ztree.setIsParent(true);
           return ztree;
        }
    /**
     * 获取所有离线的路灯
     * @return 结果
     */
    @Override
    public List<Map<String,Object>> queryStreetlightTimeStatus(){
        List<Map<String,Object>> unlineStreetlight  = new ArrayList<>();
        List<AupStreetlightControl> aupStreetlightControls = aupStreetlightControlMapper.selectAupStreetlightControlList(new AupStreetlightControl());
        for (AupStreetlightControl streetlightControl:aupStreetlightControls) {
            List<AupStreetlight> aupStreetlights = aupStreetlightMapper.selectAupStreetlightList(new AupStreetlight(streetlightControl.getCuid()));
            HttpClinetLights httpClinetLights = new HttpClinetLights();
            JSONObject[] lightStatus = httpClinetLights.getLightStatus(streetlightControl.getCuid());
            for (AupStreetlight aupStreetlight1 : aupStreetlights) {
                String sluic = aupStreetlight1.getLuid();
                if(lightStatus!=null){
                    for (JSONObject obj: lightStatus) {
                        String luid = obj.getString("luid");
                        if(sluic.equals(luid)){
                            String status = obj.getString("ls");
                            if("2".equals(status)) {
                                Map<String, Object> maps = new HashMap<>(16);
                                maps.put("dingweiCode", aupStreetlight1.getLid());
                                maps.put("cname",streetlightControl.getSname());
                                maps.put("sname", aupStreetlight1.getName());
                                maps.put("status", "灭灯");
                                unlineStreetlight.add(maps);
                            }
                        }
                    }
                }
            }
        }
        return unlineStreetlight;
    }


}