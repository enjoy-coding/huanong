package com.feather.lpscommunity.service.impl;

import java.util.*;

import com.feather.common.utils.StringUtils;
import com.feather.lpscommunity.domain.ScGoods;
import com.feather.lpscommunity.domain.ScOrder;
import com.feather.lpscommunity.mapper.ScGoodsMapper;
import com.feather.lpscommunity.mapper.ScOrderMapper;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScGoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品信息表Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-19
 */
@Service
public class ScGoodsServiceImpl implements IScGoodsService
{
    private String code = "goods";

    @Autowired
    private ScGoodsMapper scGoodsMapper;

    @Autowired
    private IScFileInfoService scFileInfoList;

    @Autowired
    private ScOrderMapper scOrderMapper;
    /**
     * 查询商品信息表
     * 
     * @param goodsId 商品信息表ID
     * @return 商品信息表
     */
    @Override
    public ScGoods selectScGoodsById(Long goodsId)
    {
        return scGoodsMapper.selectScGoodsById(goodsId);
    }

    /**
     * 查询商品信息表
     *
     * @param goodsId 商品信息表ID
     * @return 商品信息表
     */
    @Override
    public ScGoods selectScGoodsFileById(Long goodsId,String headUrl){
        ScGoods scGoods = scGoodsMapper.selectScGoodsFileById(goodsId);
        if (scGoods.getFiles() != null) {
            for (int i = 0; i < scGoods.getFiles().size(); i++) {
                scGoods.getFiles().get(i)
                        .setFilePath(StringUtils.isEmpty(scGoods.getFiles().get(i).getFilePath()) ? ""
                                : headUrl + scGoods.getFiles().get(i).getFilePath());
            }
        }
        return scGoods;
    }

    /**
     * 查询商品信息表列表
     * 
     * @param scGoods 商品信息表
     * @return 商品信息表
     */
    @Override
    public List<ScGoods> selectScGoodsList(ScGoods scGoods)
    {
        return scGoodsMapper.selectScGoodsList(scGoods);
    }

    /**
     * 新增商品信息表
     * 
     * @param scGoods 商品信息表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScGoods(ScGoods scGoods)
    {
        return scGoodsMapper.insertScGoods(scGoods);
    }

    /**
     * 修改商品信息表
     * 
     * @param scGoods 商品信息表
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScGoods(ScGoods scGoods)
    {
        return scGoodsMapper.updateScGoods(scGoods);
    }

    /**
     * 删除商品信息表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScGoodsByIds(String ids)
    {
        return scGoodsMapper.deleteScGoodsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除商品信息表信息
     * 
     * @param goodsId 商品信息表ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScGoodsById(Long goodsId)
    {
        return scGoodsMapper.deleteScGoodsById(goodsId);
    }

    /**
     *
     * 查询指定列
     * @param scGoods 商品信息
     * @param  headUrl  请求地址
     * @return 结果
     */
    public List<Map<String, Object>> screenGoodsList(ScGoods scGoods, String headUrl){
        List<ScGoods> list = this.selectScGoodsList(scGoods);
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<String,Object>();
                data.put("goods_id",list.get(i).getGoodsId());
                data.put("goods_name",list.get(i).getGoodsName());
                data.put("goods_price",list.get(i).getGoodsPrice());
                data.put("update_time",list.get(i).getUpdateTime());
                data.put("update_by",list.get(i).getUpdateBy());
                data.put("goods_file",scFileInfoList.selectFristScFileInfoByTarget(list.get(i).getGoodsId(),code,headUrl));
                dataList.add(data);
            }
        }
        return dataList;
    }

    @Override
    public List<ScGoods> selectScGoodsListByShopId(Long shopId) {
        return scGoodsMapper.selectScGoodsListByShopId(shopId);
    }

    @Override
    public boolean checkOrderGoods(String ids) {
        for (int i = 0; i <ids.split(",").length ; i++) {
            Long goodsId = Long.parseLong(ids.split(",")[i]);
            ScOrder scOrder  = scOrderMapper.selectScOrderByGoodsId(goodsId);
            if (scOrder!=null){
                return  false;
            }
        }
        return true;
    }
}