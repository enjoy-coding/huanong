package com.feather.smart.service;

import com.feather.smart.domain.ZhsqFw;
import java.util.List;
import java.util.Map;

/**
 * 房屋Service接口
 *
 * @author fancy
 * @date 2020-05-14
 */
public interface IZhsqFwService
{
    /**
     * 查询房屋
     *
     * @param fwid 房屋ID
     * @return 房屋
     */
    public ZhsqFw selectZhsqFwById(String fwid);

    /**
     * 查询房屋列表
     *
     * @param zhsqFw 房屋
     * @return 房屋集合
     */
    public List<ZhsqFw> selectZhsqFwList(ZhsqFw zhsqFw);

    /**
     * 新增房屋
     *
     * @param zhsqFw 房屋
     * @return 结果
     */
    public int insertZhsqFw(ZhsqFw zhsqFw);

    /**
     * 修改房屋
     *
     * @param zhsqFw 房屋
     * @return 结果
     */
    public int updateZhsqFw(ZhsqFw zhsqFw);

    /**
     * 批量删除房屋
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqFwByIds(String ids);

    /**
     * 删除房屋信息
     *
     * @param fwid 房屋ID
     * @return 结果
     */
    public int deleteZhsqFwById(String fwid);

    /**
     * 查询入住率
     * @param maps
     * @return
     */
    public Map<String,Object> selectRzlCount(Map<String,Object> maps);

    /**
     * 查询入住类型统计
     * @param maps
     * @return
     */
    public List<Map<String,Object>> selectTjCount(Map<String,Object> maps);

    public Map<String,Object> getFwXx(Map<String,Object> maps);
}