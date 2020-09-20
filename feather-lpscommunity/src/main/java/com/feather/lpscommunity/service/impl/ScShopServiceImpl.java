package com.feather.lpscommunity.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScGoods;
import com.feather.lpscommunity.domain.ScOrder;
import com.feather.lpscommunity.domain.ScShop;
import com.feather.lpscommunity.mapper.ScFileInfoMapper;
import com.feather.lpscommunity.mapper.ScGoodsMapper;
import com.feather.lpscommunity.mapper.ScOrderMapper;
import com.feather.lpscommunity.mapper.ScShopMapper;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScShopService;

/**
 * 社区商家信息Service业务层处理
 *
 * @author dufan
 * @date 2019-10-16
 */
@Service
public class ScShopServiceImpl implements IScShopService {
    private String code = "shop";
    @Autowired
    private ScShopMapper scShopMapper;

    @Autowired
    private ScFileInfoMapper scFileInfoMapper;

    @Autowired
    private IScFileInfoService scFileInfoService;

    @Autowired
    private ScOrderMapper orderMapper;

    @Autowired
    private ScGoodsMapper scGoodsMapper;

    /**
     * 查询社区商家信息
     *
     * @param shopId
     *            社区商家信息ID
     * @return 社区商家信息
     */
    @Override
    public ScShop selectScShopById(Long shopId) {
        return scShopMapper.selectScShopById(shopId);
    }

    /**
     * 查询社区商家信息列表
     *
     * @param scShop
     *            社区商家信息
     * @return 社区商家信息
     */
    @Override
    public List<ScShop> selectScShopList(ScShop scShop) {
        return scShopMapper.selectScShopList(scShop);
    }

    public List<ScShop> selectShopAll(){
        return scShopMapper.selectShopAll();
    }
    /**
     * 新增社区商家信息
     *
     * @param scShop
     *            社区商家信息
     * @return 结果
     */
    @Override
    public int insertScShop(ScShop scShop) {
        return scShopMapper.insertScShop(scShop);
    }

    /**
     * 修改社区商家信息
     *
     * @param scShop
     *            社区商家信息
     * @return 结果
     */
    @Override
    public int updateScShop(ScShop scShop) {
        return scShopMapper.updateScShop(scShop);
    }

    /**
     * 删除社区商家信息对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScShopByIds(String ids) {
        // 删除图片
        scFileInfoMapper.deleteScFileInfoByTarget(new String[] { ids });
        return scShopMapper.deleteScShopByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除社区商家信息信息
     *
     * @param shopId
     *            社区商家信息ID
     * @return 结果
     */
    public int deleteScShopById(Long shopId) {
        // 删除图片
        scFileInfoMapper.deleteScFileInfoByTarget(new String[] { shopId.toString() });
        return scShopMapper.deleteScShopById(shopId);
    }

    /**
     * 筛选查询社区商家信息列表
     *
     * @param scShop
     *            社区商家查询条件
     * @param headUrl
     *            请求路径
     * @return 筛选后的社区商家信息集合
     */
    @Override
    public List<Map<String, Object>> screenScShopList(ScShop scShop, String headUrl) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        List<ScShop> list = this.selectScShopList(scShop);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<String,Object>();
                String path = scFileInfoService.selectFristScFileInfoByTarget(list.get(i).getShopId(),code,headUrl);
                data.put("shopId", list.get(i).getShopId());
                data.put("shopTitle", list.get(i).getShopTitle());
                data.put("shopAddress", list.get(i).getShopAddress());
                data.put("shopTel", list.get(i).getShopTel());
                data.put("updateTime", list.get(i).getUpdateTime());
                data.put("updateBy", list.get(i).getUpdateBy());
                data.put("indexShowPic", path);
                dataList.add(data);
            }
        }
        return dataList;
    }

    /**
     * 筛选查询社区商家信息详情
     *
     * @param shopId
     *            社区商家查询条件
     * @param domain
     *            该对象所有的缩略图路径
     * @return 筛选后的社区商家信息详情
     */
    @Override
    public Map<String, Object> screenScShopDetail(Long shopId, String domain) {
        ScShop params = new ScShop();
        params.setShopId(shopId);
        List<ScShop> scShopList = this.selectScShopList(params);

        Map<String, Object> data = new LinkedHashMap<String,Object>();
        if (scShopList.size() > 0) {
            String[] fileThumbPath = scFileInfoService.selectFileThumbPathByTarget(shopId, domain, code);

            ScShop scShop = scShopList.get(0);
            data.put("shopId", scShop.getShopId());
            data.put("shopTitle", scShop.getShopTitle());
            data.put("shopName", scShop.getShopName());
            data.put("shopTel", scShop.getShopTel());
            data.put("shopAddress", scShop.getShopAddress());
            data.put("shopContent", scShop.getShopContent());
            data.put("shopFileThumbPath", fileThumbPath);
        }
        return data;
    }

    @Override
    public boolean checkOrderShop(String shopIds) {
        for (int i = 0; i < shopIds.split(",").length; i++) {
            Long shopId =Long.parseLong(shopIds.split(",")[i]);
            //获取商家信息
            List<ScGoods> scGoodsList = scGoodsMapper.selectScGoodsListByShopId(shopId);
            for (int j = 0; j < scGoodsList.size(); j++) {
                ScGoods scGoods = scGoodsList.get(i);
                ScOrder scOrder = orderMapper.selectScOrderByGoodsId(scGoods.getGoodsId());
                if(scGoods!=null&&scOrder!=null){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean checkGoodsShop(String shopIds) {
        for (int i = 0; i < shopIds.split(",").length; i++) {
            Long shopId =Long.parseLong(shopIds.split(",")[i]);
            //获取商家信息
            List<ScGoods> scGoodsList = scGoodsMapper.selectScGoodsListByShopId(shopId);
            for (int j = 0; j < scGoodsList.size(); j++) {
                ScGoods scGoods = scGoodsList.get(i);
                if(scGoods!=null){
                    return false;
                }
            }
        }
        return true;
    }
}
