package com.feather.lpscommunity.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScGoods;
import com.feather.lpscommunity.domain.ScOrder;
import com.feather.lpscommunity.mapper.ScGoodsMapper;
import com.feather.lpscommunity.mapper.ScOrderMapper;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScOrderService;

import org.springframework.transaction.annotation.Transactional;

/**
 * 订单信息Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-25
 */
@Service
public class ScOrderServiceImpl implements IScOrderService 
{
    @Autowired
    private ScOrderMapper scOrderMapper;

    @Autowired
    private ScGoodsMapper scGoodsMapper;

    @Autowired
    private IScFileInfoService scFileInfoList;

    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息ID
     * @return 订单信息
     */
    @Override
    public ScOrder selectScOrderById(Long orderId)
    {
        return scOrderMapper.selectScOrderById(orderId);
    }

    /**
     * 查询订单信息列表
     * 
     * @param scOrder 订单信息
     * @return 订单信息
     */
    @Override
    public List<ScOrder> selectScOrderList(ScOrder scOrder)
    {
        return scOrderMapper.selectScOrderList(scOrder);
    }


    /**
     * 查询指定列
     * @param scOrder 订单信息
     * @param headUrl
     * @return
     */
    public List<Map<String,Object>> screenScOrderList(ScOrder scOrder,String headUrl){
        List<ScOrder> scOrderList = this.selectScOrderList(scOrder);
        List<Map<String,Object>> dataList =new  ArrayList<Map<String,Object>>();
        for (int i = 0; i < scOrderList.size(); i++) {
            Map<String,Object> map = new LinkedHashMap<String,Object>();
            ScGoods scGoods = scGoodsMapper.selectScGoodsById(scOrder.getGoodsId());
            map.put("order_id",scOrderList.get(i).getOrderId());
            map.put("order_name",scOrderList.get(i).getOrderId());
            map.put("goods_id",scOrderList.get(i).getGoodsId());
            map.put("goods_name",scOrderList.get(i).getGoods().getGoodsName());
            map.put("goods_price",scOrderList.get(i).getGoods().getGoodsPrice());
            map.put("update_time",scOrderList.get(i).getGoods().getUpdateTime());
            map.put("update_by",scOrderList.get(i).getGoods().getUpdateBy());
            map.put("goods_file",scFileInfoList.selectFristScFileInfoByTarget(scOrderList.get(i).getGoods().getGoodsId(),"goods",headUrl));
            dataList.add(map);
        }
        return dataList;
    }
    /**
     * 新增订单信息
     * 
     * @param scOrder 订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScOrder(ScOrder scOrder)
    {
        return scOrderMapper.insertScOrder(scOrder);
    }

    /**
     * 修改订单信息
     * 
     * @param scOrder 订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScOrder(ScOrder scOrder)
    {
        return scOrderMapper.updateScOrder(scOrder);
    }

    /**
     * 删除订单信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScOrderByIds(String ids)
    {
        return scOrderMapper.deleteScOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScOrderById(Long orderId)
    {
        return scOrderMapper.deleteScOrderById(orderId);
    }

    @Override
    public ScOrder selectScGoodsById(Long goodsId) {
        return scOrderMapper.selectScOrderByGoodsId(goodsId);
    }

    /**
     * 判断5秒内该设备是否继续提交该商品
     * @param scOrder 订单
     * @param todayDate 提交时间
     * @return
     */
    @Override
    public boolean checkRepeatInsertOrder(ScOrder scOrder,Date todayDate) {
        //获取该设备加入该商品最后一次的时间
        List<ScOrder> scOrderList = this.selectScOrderList(scOrder);
        Date lastDate = selectLastOne(scOrderList);
        if(lastDate!=null) {
            //判断时间小于等于5秒则为重复提交
            long interval = (todayDate.getTime() - lastDate.getTime()) / 1000;
            if (interval <= 5) {
                return false;
            }
        }

        return true;
    }

    /**
     *获取订单中最后一次提交的订单
     * @param list
     * @return
     */
    public Date selectLastOne(List<ScOrder> list) {
        Date lastDate = new Date();

        List<Date> datas = new ArrayList<Date>();
        Long dates[] = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            // 所以就依靠这个原理来判断距离现在最近的时间
//            dates[i - 1] = list.get(i).getUpdateTime().getTime();
            if(list.get(i).getUpdateTime()==null){
                continue;
            }
            datas.add(list.get(i).getUpdateTime());
        }
        if(datas.size()>0) {
            lastDate = Collections.max(datas);
            return lastDate;
        }else{
            return null;
        }

    }


}
