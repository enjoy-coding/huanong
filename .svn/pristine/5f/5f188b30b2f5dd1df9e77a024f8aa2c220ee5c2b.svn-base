package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupStreetlightDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 灯具Mapper接口
 *
 * @author fancy
 * @date 2020-03-05
 */
public interface AupStreetlightDetailMapper
{
    /**
     * 查询灯具
     *
     * @param luid 灯具ID
     * @return 灯具
     */
    public List<AupStreetlightDetail> selectAupStreetlightDetailByIuid(String luid);

    /**
     * 判读是否存在重复
     * @param aupStreetlightDetail
     * @return
     */
    public List<AupStreetlightDetail> checkAupStreetlightDetailByIuid(AupStreetlightDetail aupStreetlightDetail);

    public List<AupStreetlightDetail> selectAupStreetlightDetailExpList(AupStreetlightDetail aupStreetlightDetail);
    /**
     * 查询灯具列表
     *
     * @param aupStreetlightDetail 灯具
     * @return 灯具集合
     */
    public List<AupStreetlightDetail> selectAupStreetlightDetailList(AupStreetlightDetail aupStreetlightDetail);

    /**
     * 新增灯具
     *
     * @param aupStreetlightDetail 灯具
     * @return 结果
     */
    public int insertAupStreetlightDetail(AupStreetlightDetail aupStreetlightDetail);

    /**
     * 修改灯具
     *
     * @param aupStreetlightDetail 灯具
     * @return 结果
     */
    public int updateAupStreetlightDetail(AupStreetlightDetail aupStreetlightDetail);

    /**
     * 删除灯具
     *
     * @param id 灯具ID
     * @return 结果
     */
    public int deleteAupStreetlightDetailById(Long id);

    /**
     * 批量删除灯具
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupStreetlightDetailByIds(String[] ids);

    public void  deleteStreetlightDetailTime();

    public Map<String,Object> getTypename(@Param("typeid") String typeid);

}