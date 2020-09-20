package com.feather.smart.mapper;

import com.feather.smart.domain.ZhsqGg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告Mapper接口
 * 
 * @author fancy
 * @date 2020-05-17
 */
public interface ZhsqGgMapper 
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
     * 删除公告
     * 
     * @param ggid 公告ID
     * @return 结果
     */
    public int deleteZhsqGgById(String ggid);

    /**
     * 批量删除公告
     * 
     * @param ggids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqGgByIds(String[] ggids);

    public List<ZhsqGg> getZhsqGgList(@Param("sqid") String sqid, @Param("xqid") String xqid);
}
