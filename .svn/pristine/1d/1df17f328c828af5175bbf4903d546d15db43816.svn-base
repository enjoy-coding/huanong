package com.feather.aupipes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.domain.AupWarring1;
import com.feather.aupipes.mapper.AupWarringMapper;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.aupipes.service.IAupYjinfotablesService;
import com.feather.common.core.text.Convert;

/**
 * 预警记录信息Service业务层处理
 * 
 * @author fancy
 * @date 2020-01-15
 */
@Service
public class AupWarringServiceImpl implements IAupWarringService {
    @Autowired
    private AupWarringMapper aupWarringMapper;

    @Autowired
    private IAupYjinfotablesService iAupYjinfotablesService;

    /**
     * 查询预警记录信息
     * 
     * @param id
     *            预警记录信息ID
     * @return 预警记录信息
     */
    @Override
    public AupWarring selectAupWarringById(String id) {
        return aupWarringMapper.selectAupWarringById(id);
    }

    /**
     * 查询预警记录信息列表
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 预警记录信息
     */
    @Override
    public List<AupWarring> selectAupWarringList(AupWarring aupWarring) {
        return aupWarringMapper.selectAupWarringList(aupWarring);
    }

    /**
     * 查询所有带外部图片的
     * 
     * @return 预警记录信息集合
     */
    @Override
    public List<AupWarring> selectAllWithOuterPic() {
        return aupWarringMapper.selectAllWithOuterPic();
    }

    /**
     * 新增预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    @Override
    public int insertAupWarring(AupWarring aupWarring) {
        return aupWarringMapper.insertAupWarring(aupWarring);
    }

    /**
     * 修改预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    @Override
    public int updateAupWarring(AupWarring aupWarring) {
        return aupWarringMapper.updateAupWarring(aupWarring);
    }

    /**
     * 删除预警记录信息对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWarringByIds(String ids) {
        return aupWarringMapper.deleteAupWarringByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预警记录信息信息
     * 
     * @param id
     *            预警记录信息ID
     * @return 结果
     */
    @Override
    public int deleteAupWarringById(String id) {
        return aupWarringMapper.deleteAupWarringById(id);
    }

    @Override
    public List<AupWarring1> downWarringList(String content, String level, String author, String dateTime1,
            String dateTime2, String state, String name, String formSysName) {
        return aupWarringMapper.downWarringList(content, level, author, dateTime1, dateTime2, state, name, formSysName);
    }

    @Override
    public List<Map> localWarring() {
        return aupWarringMapper.localWarring();
    }

    @Override
    public List<Map> getlocalWarring(String formSysName, String id, int state) {
        return aupWarringMapper.getlocalWarring(formSysName, id, state);
    }

    @Override
    public Map getPosition(String itid, String name) {
        List<Map> list = iAupYjinfotablesService.getAllinfo(itid, 3, "", "", "");
        Map map1 = list.get(0);
        String value;
        Map map = null;

        if ("路灯".equals(name)) {
            String content = map1.get("content").toString();
            value = content.substring(content.indexOf("控制器") + 3, content.indexOf(",")).split("-")[0];
            if ("Built".equals(value)) {
                String sname = content.substring(0, content.indexOf("控制器") + 3);
                map = aupWarringMapper.getPosition("aup_streetlight_control", "sname", sname).get(0);
            } else {
                map = aupWarringMapper.getPosition("aup_streetlight", "name", value).get(0);
            }
        } else if ("探漏".equals(name)) {
            value = map1.get("code").toString();
            List<Map> position = aupWarringMapper.getPosition("aup_leak", "placeName", value);
            if (position.size() > 0) {
                map = position.get(0);
            }
        } else if ("水电".equals(name)) {
            String content = map1.get("content").toString();
            String names = content.substring(0, content.indexOf("表具"));
            map = aupWarringMapper.getPosition("aup_area", "name", names).get(0);
        } else if ("电表".equals(name)) {
            String lat = map1.get("Latitude").toString();
            String lont = map1.get("Longitude").toString();
            Map maps = aupWarringMapper.getAreaNo(lat, lont).get(0);
            String no = maps.get("No").toString();
            map = aupWarringMapper.getPosition("aup_area", "Buildingno", no).get(0);
        } else if ("水质".equals(name)) {
            value = map1.get("code").toString();
            map = aupWarringMapper.getPosition("aup_waterQuality", "oid", value).get(0);
        } else if ("监控".equals(name)) {
            value = map1.get("code").toString();
            List<Map> l = aupWarringMapper.getPosition("aup_region_camera", "cameraIndexCode", value);
            if (l.size() > 0) {
                map = l.get(0);
            }
        } else if ("泵房".equals(name)) {
            value = map1.get("path").toString();
            // value = content.substring(0,content.indexOf("泵房")+2);
            List<Map> l = aupWarringMapper.getPosition("aup_pump", "name", value);
            if (l.size() > 0) {
                map = l.get(0);
            }
        }
        return map;
    }

    @Override
    public Map getInfoBySn(String sn) {
        return aupWarringMapper.getInfoBySn(sn);
    }

}
