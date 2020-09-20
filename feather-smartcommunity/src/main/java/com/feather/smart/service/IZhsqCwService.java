package com.feather.smart.service;

import com.feather.smart.domain.ZhsqCw;

import java.util.List;
import java.util.Map;

/**
 * 车位Service接口
 * 
 * @author fancy
 * @date 2020-05-15
 */
public interface IZhsqCwService 
{
    /**
     * 查询车位
     * 
     * @param cwid 车位ID
     * @return 车位
     */
    public ZhsqCw selectZhsqCwById(String cwid);

    /**
     * 查询车位列表
     * 
     * @param zhsqCw 车位
     * @return 车位集合
     */
    public List<ZhsqCw> selectZhsqCwList(ZhsqCw zhsqCw);

    /**
     * 新增车位
     * 
     * @param zhsqCw 车位
     * @return 结果
     */
    public int insertZhsqCw(ZhsqCw zhsqCw);

    /**
     * 修改车位
     * 
     * @param zhsqCw 车位
     * @return 结果
     */
    public int updateZhsqCw(ZhsqCw zhsqCw);

    /**
     * 批量删除车位
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqCwByIds(String ids);

    /**
     * 删除车位信息
     * 
     * @param cwid 车位ID
     * @return 结果
     */
    public int deleteZhsqCwById(String cwid);

    Map<String,Object> getSqCwXx(ZhsqCw zhsqCw);

    List<Map<String,Object>> getCwInfoLeft(ZhsqCw zhsqCw);

    List<Map>  getTccInfoRight(ZhsqCw zhsqCw);

    List<Map> getTccXxInfoCenter(String tccId);

    List<Map> getKxcwInfo(ZhsqCw zhsqCw);

    List<Map> getKxcwXxInfoList(ZhsqCw zhsqCw);

    List<Map> getYycwInfo(ZhsqCw zhsqCw);

    List<Map> getYycwXxInfoList(ZhsqCw zhsqCw);
}
