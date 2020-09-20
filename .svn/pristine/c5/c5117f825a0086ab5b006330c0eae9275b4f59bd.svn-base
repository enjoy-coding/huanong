package com.feather.smart.service.impl;

import com.feather.common.core.text.Convert;
import com.feather.smart.domain.ZhsqJm;
import com.feather.smart.mapper.ZhsqJmMapper;
import com.feather.smart.service.IZhsqJmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 居民Service业务层处理
 * 
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqJmServiceImpl implements IZhsqJmService 
{
    @Autowired
    private ZhsqJmMapper zhsqJmMapper;

    /**
     * 查询居民
     * 
     * @param jmid 居民ID
     * @return 居民
     */
    @Override
    public ZhsqJm selectZhsqJmById(String jmid)
    {
        return zhsqJmMapper.selectZhsqJmById(jmid);
    }

    /**
     * 查询居民列表
     * 
     * @param zhsqJm 居民
     * @return 居民
     */
    @Override
    public List<ZhsqJm> selectZhsqJmList(ZhsqJm zhsqJm)
    {
        return zhsqJmMapper.selectZhsqJmList(zhsqJm);
    }

    /**
     * 新增居民
     * 
     * @param zhsqJm 居民
     * @return 结果
     */
    @Override
    public int insertZhsqJm(ZhsqJm zhsqJm)
    {
        return zhsqJmMapper.insertZhsqJm(zhsqJm);
    }

    /**
     * 修改居民
     * 
     * @param zhsqJm 居民
     * @return 结果
     */
    @Override
    public int updateZhsqJm(ZhsqJm zhsqJm)
    {
        return zhsqJmMapper.updateZhsqJm(zhsqJm);
    }

    /**
     * 删除居民对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqJmByIds(String ids)
    {
        return zhsqJmMapper.deleteZhsqJmByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除居民信息
     * 
     * @param jmid 居民ID
     * @return 结果
     */
    public int deleteZhsqJmById(String jmid)
    {
        return zhsqJmMapper.deleteZhsqJmById(jmid);
    }


    @Override
    public List<Map> getPersonInfo(Map<String,Object> maps) {
        return zhsqJmMapper.getPersonInfo(maps);
    }

    @Override
    public List<Map<String, Object>> getFwRy(Map<String,Object> maps) {
        return zhsqJmMapper.getFwRy(maps);
    }

    @Override
    public Map<String, Object> getZdRy(Map<String,Object> maps) {
        Map<String,Object> map = new HashMap<>();
        Object ldmc = "";
        List<Map<String, Object>> zdRy = zhsqJmMapper.getZdRy(maps);
        int size = zdRy.size();
        for (Map<String, Object> stringObjectMap : zdRy) {
            ldmc = stringObjectMap.get("LDMC");
            break;
        }
        map.put("list",zdRy);
        map.put("ldmc",ldmc);
        map.put("num",size);
        return map;
    }

    /**
     * 查询重点人员楼栋信息
     * @return
     */
    @Override
    public List<Map<String, Object>> getZdRyLd() {
        return zhsqJmMapper.getZdRyLd();
    }
}
