package com.feather.smart.service;

import com.feather.smart.domain.ZhsqGg;
import java.util.List;

/**
 * 公告Service接口
 * 
 * @author fancy
 * @date 2020-05-17
 */
public interface IZhsqGgService 
{
    /**
     * 查询公告
     * 
     * @param ggid 公告ID
     * @return 公告
     */
    public ZhsqGg selectZhsqGgById(String ggid);

    /**
     * 查询公告列表
     * 
     * @param zhsqGg 公告
     * @return 公告集合
     */
    public List<ZhsqGg> selectZhsqGgList(ZhsqGg zhsqGg);
    public List<ZhsqGg> getZhsqGgList(String sqid,String xqid);

    /**
     * 新增公告
     * 
     * @param zhsqGg 公告
     * @return 结果
     */
    public int insertZhsqGg(ZhsqGg zhsqGg);

    /**
     * 修改公告
     * 
     * @param zhsqGg 公告
     * @return 结果
     */
    public int updateZhsqGg(ZhsqGg zhsqGg);

    /**
     * 批量删除公告
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqGgByIds(String ids);

    /**
     * 删除公告信息
     * 
     * @param ggid 公告ID
     * @return 结果
     */
    public int deleteZhsqGgById(String ggid);



}
