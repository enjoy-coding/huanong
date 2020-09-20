package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupLeak;
import com.feather.aupipes.mapper.AupLeakMapper;
import com.feather.aupipes.service.IAupLeakService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import com.feather.common.utils.Arith;
import com.feather.common.utils.bean.BeanUtils;

/**
 * 探漏
 */
@Service
public class AupLeakServiceImpl implements IAupLeakService {

    @Autowired
    private AupLeakMapper aupLeakMapper;

    /**
     * 获取泵房在线和总数
     *
     * @return 结果
     */
    @Override
    public Map<String, Object> getCount(int online, int total) {
        Map<String, Object> map = new HashMap<>();
        // int online = 46;
        // int total = 53;
        String percent = Arith.percent(online, total);
        map.put("name", "探漏");
        map.put("icon", "icon-neiwangtance");
        map.put("code", "leak");
        map.put("number", online);
        map.put("total", total);
        map.put("percent", percent);
        return map;
    }

    /**
     * 查询探漏列
     *
     * @param placeid
     *            探漏列ID
     * @return 探漏列
     */
    @Override
    public AupLeak selectAupLeakById(int placeid) {
        return aupLeakMapper.selectAupLeakById(placeid);
    }

    /**
     * 查询探漏列列表
     *
     * @param aupLeak
     *            探漏列
     * @return 探漏列
     */
    @Override
    public List<AupLeak> selectAupLeakList(AupLeak aupLeak) {
        return aupLeakMapper.selectAupLeakList(aupLeak);
    }

    /**
     * 获取树结构
     *
     * @return 树集合
     */
    @Override
    public List<Ztree> selectAupLeakTreeList(AupLeak aupLeak) {
        // 手动插入根节点
        List<AupLeak> aupLeakList = this.selectAupLeakList(aupLeak);
        return this.initZtree(aupLeakList);
    }

    /**
     * 拼装树结构
     *
     * @param aupLeakList 探漏列表
     * @return 结果
     */
    private List<Ztree> initZtree(List<AupLeak> aupLeakList) {
        List<Ztree> ztrees = new ArrayList<>();
        ztrees.add(new Ztree(0L, null, "探漏", "探漏", true));
        for (AupLeak leak : aupLeakList) {
            Ztree ztree = new Ztree();
            Long placeId = Long.parseLong(leak.getPlaceid() + "");
            ztree.setId(placeId);
            ztree.setpId(0L);
            // ztree.setName(leak.getSiteno());
            ztree.setName(leak.getPlaceaddress());
            ztree.setCode(leak.getSiteno());
            ztree.setTitle(leak.getPlaceaddress());
            ztree.setMaps(BeanUtils.beanToMap(leak));
            ztrees.add(ztree);

        }
        return ztrees;
    }

    /**
     * 新增探漏列
     *
     * @param aupLeak
     *            探漏列
     * @return 结果
     */
    @Override
    public int insertAupLeak(AupLeak aupLeak) {
        return aupLeakMapper.insertAupLeak(aupLeak);
    }

    /**
     * 修改探漏列
     *
     * @param aupLeak
     *            探漏列
     * @return 结果
     */
    @Override
    public int updateAupLeak(AupLeak aupLeak) {
        return aupLeakMapper.updateAupLeak(aupLeak);
    }

    /**
     * 删除探漏列对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupLeakByIds(String ids) {
        return aupLeakMapper.deleteAupLeakByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除探漏列信息
     *
     * @param placeid
     *            探漏列ID
     * @return 结果
     */
    @Override
    public int deleteAupLeakById(String placeid) {
        return aupLeakMapper.deleteAupLeakById(placeid);
    }

    /**
     * 根据接口更新探漏
     *
     * @param result 接口结果
     * @return 结果
     */
    @Override
    public int insetOrUpdateLeak(String result) {
        // . Lat: 24.49626,
        // . Lng: 118.171379,
        // . PipeId: 1334,
        // . PlaceAddress: "7号楼东北方向路口",
        // . PlaceId: "000001",
        // . SiteNO: "90000834",
        int count = 0;
        if (result == null) {
            return count;
        }
        JSONArray json = JSONObject.parse(result, JSONArray.class);
        if(json == null){
            return 0;
        }
        for (int i = 0; i < json.size(); i++) {
            JSONObject obj = json.getJSONObject(i);
            String placeId = obj.getString("placeId");
            AupLeak leak = this.selectAupLeakById(Integer.parseInt(placeId));
            if (leak != null) {
                leak.setSiteno(obj.getString("SiteNO"));
                leak.setAreaname(obj.getString("PlaceAddress"));
                count = this.insertAupLeak(leak);
            } else {
                leak = new AupLeak();
                leak.setPlaceid(Integer.parseInt(placeId));
                leak.setSiteno(obj.getString("SiteNO"));
                leak.setAreaname(obj.getString("PlaceAddress"));
                count = this.updateAupLeak(leak);
            }

        }
        return count;
    }
}
