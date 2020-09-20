package com.feather.smart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.smart.mapper.ZhsqCwMapper;
import com.feather.smart.domain.ZhsqCw;
import com.feather.smart.service.IZhsqCwService;
import com.feather.common.core.text.Convert;

/**
 * 车位Service业务层处理
 * 
 * @author fancy
 * @date 2020-05-15
 */
@Service
public class ZhsqCwServiceImpl implements IZhsqCwService
{
    @Autowired
    private ZhsqCwMapper zhsqCwMapper;

    /**
     * 查询车位
     * 
     * @param cwid 车位ID
     * @return 车位
     */
    @Override
    public ZhsqCw selectZhsqCwById(String cwid)
    {
        return zhsqCwMapper.selectZhsqCwById(cwid);
    }

    /**
     * 查询车位列表
     * 
     * @param zhsqCw 车位
     * @return 车位
     */
    @Override
    public List<ZhsqCw> selectZhsqCwList(ZhsqCw zhsqCw)
    {
        return zhsqCwMapper.selectZhsqCwList(zhsqCw);
    }

    /**
     * 新增车位
     * 
     * @param zhsqCw 车位
     * @return 结果
     */
    @Override
    public int insertZhsqCw(ZhsqCw zhsqCw)
    {
        return zhsqCwMapper.insertZhsqCw(zhsqCw);
    }

    /**
     * 修改车位
     * 
     * @param zhsqCw 车位
     * @return 结果
     */
    @Override
    public int updateZhsqCw(ZhsqCw zhsqCw)
    {
        return zhsqCwMapper.updateZhsqCw(zhsqCw);
    }

    /**
     * 删除车位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqCwByIds(String ids)
    {
        return zhsqCwMapper.deleteZhsqCwByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除车位信息
     * 
     * @param cwid 车位ID
     * @return 结果
     */
    public int deleteZhsqCwById(String cwid)
    {
        return zhsqCwMapper.deleteZhsqCwById(cwid);
    }

    /**
     * 统计主页车位信息
     * @param zhsqCw
     * @return
     */
    @Override
    public Map<String, Object> getSqCwXx(ZhsqCw zhsqCw) {
        return zhsqCwMapper.getSqCwXx(zhsqCw);
    }

    /**
     * 统计社区管理车位信息左侧列表数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getCwInfoLeft(ZhsqCw zhsqCw) {
        Map<String,Object> map=zhsqCwMapper.getCwInfoLeftOn(zhsqCw);
        List<Map<String,Object>> cwList=zhsqCwMapper.getCwInfoLeftDown(zhsqCw);
        List<Map<String,Object>> list=new ArrayList<>();
        Map map1 = new HashMap<>();
        map1.put("tjLeftOn",map);
        list.add(map1);
        map1 = new HashMap<>();
        map1.put("tjLeftDown",cwList);
        list.add(map1);

        return list;
    }

    /**
     * 获取右侧停车场信息
     */
    @Override
    public List<Map> getTccInfoRight(ZhsqCw zhsqCw) {
        List<Map> list = zhsqCwMapper.getTccInfoRight(zhsqCw);
        return list;
    }

    /**
     * 根据停车场Id获取停车场详细信息列表
     */
    @Override
    public List<Map> getTccXxInfoCenter(String tccIdLx) {
        List<Map> listAll = new ArrayList<>();
        String[] split = tccIdLx.split(",");
        String tccId = split[0];
        String tccLx = split[1];
        if(tccLx!=null){
            if(tccLx.equals("地上")){
                tccLx="1";
            }else{
                tccLx="2";
            }
        }
        //获取车位详细数据
        ZhsqCw zhsqCw = new ZhsqCw();
        zhsqCw.setTccid(tccId);
        zhsqCw.setCwlx(tccLx);
        List<Map> cwInfolist = zhsqCwMapper.getTccXxInfoCenter(zhsqCw);
        //获取统计数量
        List<Map> cwCount = zhsqCwMapper.getTccInfoRight(zhsqCw);
        Map map = new HashMap<>();
        map.put("cwInfolist",cwInfolist);
        listAll.add(map);
        map = new HashMap<>();
        map.put("cwCount",cwCount);
        listAll.add(map);

        return listAll;
    }

    /**
     * 查询空闲车位列表
     */
    @Override
    public List<Map> getKxcwInfo(ZhsqCw zhsqCw) {
        zhsqCw.setCwzt("1");
        List<Map> kxcwInfo = zhsqCwMapper.getKxcwInfo(zhsqCw);
        return kxcwInfo;
    }

    /**
     * 查询空闲车位详细列表数据
     */
    @Override
    public List<Map> getKxcwXxInfoList(ZhsqCw zhsqCw) {
        List<Map> listAll = new ArrayList<>();
        List<Map> kxcwXxInfoList =zhsqCwMapper.getKxcwXxInfoList(zhsqCw);
        //获取统计数量
        List<Map> cwCount = zhsqCwMapper.getTccInfoRight(zhsqCw);
        Map map = new HashMap<>();
        map.put("kxcwXxInfoList",kxcwXxInfoList);
        listAll.add(map);
        map = new HashMap<>();
        map.put("cwCount",cwCount);
        listAll.add(map);
        return listAll;
    }

    /**
     * 查询占用车位列表
     */
    @Override
    public List<Map> getYycwInfo(ZhsqCw zhsqCw) {
        zhsqCw.setCwzt("2");
        List<Map> kxcwInfo = zhsqCwMapper.getKxcwInfo(zhsqCw);
        return kxcwInfo;
    }

    /**
     * 查询空闲车位详细列表数据
     */
    @Override
    public List<Map> getYycwXxInfoList(ZhsqCw zhsqCw) {
        List<Map> listAll = new ArrayList<>();
        List<Map> kxcwXxInfoList =zhsqCwMapper.getKxcwXxInfoList(zhsqCw);
        //获取统计数量
        List<Map> cwCount = zhsqCwMapper.getTccInfoRight(zhsqCw);
        Map map = new HashMap<>();
        map.put("kxcwXxInfoList",kxcwXxInfoList);
        listAll.add(map);
        map = new HashMap<>();
        map.put("cwCount",cwCount);
        listAll.add(map);
        return listAll;
    }

}
