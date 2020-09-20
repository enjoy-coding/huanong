package com.feather.smart.service.impl;

import com.feather.common.core.text.Convert;
import com.feather.smart.domain.ZhsqFw;
import com.feather.smart.mapper.ZhsqFwMapper;
import com.feather.smart.service.IZhsqFwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房屋Service业务层处理
 *
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqFwServiceImpl implements IZhsqFwService
{
    @Autowired
    private ZhsqFwMapper zhsqFwMapper;

    /**
     * 查询房屋
     *
     * @param fwid 房屋ID
     * @return 房屋
     */
    @Override
    public ZhsqFw selectZhsqFwById(String fwid)
    {
        return zhsqFwMapper.selectZhsqFwById(fwid);
    }

    /**
     * 查询房屋列表
     *
     * @param zhsqFw 房屋
     * @return 房屋
     */
    @Override
    public List<ZhsqFw> selectZhsqFwList(ZhsqFw zhsqFw)
    {
        return zhsqFwMapper.selectZhsqFwList(zhsqFw);
    }

    /**
     * 新增房屋
     *
     * @param zhsqFw 房屋
     * @return 结果
     */
    @Override
    public int insertZhsqFw(ZhsqFw zhsqFw)
    {
        return zhsqFwMapper.insertZhsqFw(zhsqFw);
    }

    /**
     * 修改房屋
     *
     * @param zhsqFw 房屋
     * @return 结果
     */
    @Override
    public int updateZhsqFw(ZhsqFw zhsqFw)
    {
        return zhsqFwMapper.updateZhsqFw(zhsqFw);
    }

    /**
     * 删除房屋对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqFwByIds(String ids)
    {
        return zhsqFwMapper.deleteZhsqFwByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除房屋信息
     *
     * @param fwid 房屋ID
     * @return 结果
     */
    @Override
    public int deleteZhsqFwById(String fwid)
    {
        return zhsqFwMapper.deleteZhsqFwById(fwid);
    }

    /**
     * 查询入住率
     * @param maps
     * @return
     */
    @Override
    public Map<String, Object> selectRzlCount(Map<String, Object> maps) {
        return zhsqFwMapper.selectRzlCount(maps);
    }

    /**
     * 查询入住统计
     * @param maps
     * @return
     */
    @Override
    public List<Map<String, Object>> selectTjCount(Map<String, Object> maps) {
        return zhsqFwMapper.selectTjCount(maps);
    }

    @Override
    public Map<String, Object> getFwXx(Map<String,Object> maps) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> fwXx = zhsqFwMapper.getFwXx(maps);
        List<Map<String, Object>> fwXxDyy =new ArrayList<>();
        List<Map<String, Object>> fwXxDye = new ArrayList<>();
        Map<String,Object> mapFl = new HashMap<>();
        int rySl = zhsqFwMapper.getRySl(maps);
        Object ldmc = "";
        for (Map<String, Object> xx : fwXx) {
            ldmc = xx.get("LDMC");
            Object dy = xx.get("DY");
            if ("1".equals(dy)){
                fwXxDyy.add(xx);
            }else {
                fwXxDye.add(xx);
            }
        }
        mapFl.put("一单元",fwXxDyy);
        mapFl.put("二单元",fwXxDye);
        map.put("fwXx",mapFl);
        map.put("ldmc",ldmc);
        map.put("num",rySl);
        return map;
    }


}