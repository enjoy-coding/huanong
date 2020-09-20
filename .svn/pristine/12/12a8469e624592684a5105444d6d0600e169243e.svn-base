package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupBuildingSide;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 楼栋分区Mapper接口
 *
 * @author fancy
 * @date 2020-03-25
 */
public interface AupBuildingSideMapper
{
    /**
     * 查询楼栋分区
     *
     * @param id 楼栋分区ID
     * @return 楼栋分区
     */
    public AupBuildingSide selectAupBuildingSideById(int id);

    /**
     * 查询楼栋分区列表
     *
     * @param aupBuildingSide 楼栋分区
     * @return 楼栋分区集合
     */
    public List<AupBuildingSide> selectAupBuildingSideList(AupBuildingSide aupBuildingSide);

    /**
     * 查询重点建筑楼栋分区列表
     *
     * @param aupBuildingSide 楼栋分区
     * @return 楼栋分区集合
     */
    public List<AupBuildingSide> selectAupBuildingSideListLdjd(AupBuildingSide aupBuildingSide);

    /**
     * 重点建筑查询楼栋分区列表
     *
     * @param aupBuildingSide 楼栋分区
     * @return 楼栋分区集合
     */
    public List<AupBuildingSide> selectAupBuildingSideZdjzList(AupBuildingSide aupBuildingSide);

    /**
     * 新增楼栋分区
     *
     * @param aupBuildingSide 楼栋分区
     * @return 结果
     */
    public int insertAupBuildingSide(AupBuildingSide aupBuildingSide);

    /**
     * 修改楼栋分区
     *
     * @param aupBuildingSide 楼栋分区
     * @return 结果
     */
    public int updateAupBuildingSide(AupBuildingSide aupBuildingSide);

    /**
     * 删除楼栋分区
     *
     * @param id 楼栋分区ID
     * @return 结果
     */
    public int deleteAupBuildingSideById(Long id);

    /**
     * 批量删除楼栋分区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupBuildingSideByIds(String[] ids);

    public List<Map> getBuildSide(@Param("areano") String areano);
}