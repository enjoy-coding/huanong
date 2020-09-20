package com.feather.smart.mapper;

import com.feather.smart.domain.ZhsqJm;

import java.util.List;
import java.util.Map;

/**
 * 居民Mapper接口
 * 
 * @author fancy
 * @date 2020-05-14
 */
public interface ZhsqJmMapper 
{
    /**
     * 查询居民
     * 
     * @param jmid 居民ID
     * @return 居民
     */
    public ZhsqJm selectZhsqJmById(String jmid);

    /**
     * 查询居民列表
     * 
     * @param zhsqJm 居民
     * @return 居民集合
     */
    public List<ZhsqJm> selectZhsqJmList(ZhsqJm zhsqJm);

    /**
     * 新增居民
     * 
     * @param zhsqJm 居民
     * @return 结果
     */
    public int insertZhsqJm(ZhsqJm zhsqJm);

    /**
     * 修改居民
     * 
     * @param zhsqJm 居民
     * @return 结果
     */
    public int updateZhsqJm(ZhsqJm zhsqJm);

    /**
     * 删除居民
     * 
     * @param jmid 居民ID
     * @return 结果
     */
    public int deleteZhsqJmById(String jmid);

    /**
     * 批量删除居民
     * 
     * @param jmids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqJmByIds(String[] jmids);


    public List<Map> getPersonInfo(Map<String,Object> maps);

    public List<Map<String,Object>> getFwRy(Map<String,Object> maps);

    public List<Map<String,Object>> getZdRy(Map<String,Object> maps);

    /**
     * 查询重点人员楼栋信息
     * @return
     */
    public List<Map<String,Object>> getZdRyLd();
}
