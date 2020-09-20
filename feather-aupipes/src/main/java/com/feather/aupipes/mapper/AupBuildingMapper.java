package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupBuilding;
import java.util.List;
import java.util.Map;

/**
 * 楼栋信息Mapper接口
 *
 * @author fancy
 * @date 2019-12-30
 */
public interface AupBuildingMapper
{
    /**
     * 查询楼栋信息
     *
     * @param id 楼栋信息ID
     * @return 楼栋信息
     */
    public AupBuilding selectAupBuildingById(String id);

    /**
     * 查询楼栋能源信息
     *
     * @param id 楼栋信息ID
     * @return 楼栋信息
     */
    public AupBuilding selectAupBuildingEnergyById(String id);

    /**
     * 查询楼栋信息列表
     *
     * @param aupBuilding 楼栋信息
     * @return 楼栋信息集合
     */
    public List<AupBuilding> selectAupBuildingList(AupBuilding aupBuilding);

    /**
     * 查询关联水电表能源信息
     * @param aupBuilding
     * @return
     */
    public List<AupBuilding> selectAupBuildingEnergyList(AupBuilding aupBuilding);

    /**
     * 新增楼栋信息
     *
     * @param aupBuilding 楼栋信息
     * @return 结果
     */
    public int insertAupBuilding(AupBuilding aupBuilding);

    /**
     * 修改楼栋信息
     *
     * @param aupBuilding 楼栋信息
     * @return 结果
     */
    public int updateAupBuilding(AupBuilding aupBuilding);

    /**
     * 删除楼栋信息
     *
     * @param id 楼栋信息ID
     * @return 结果
     */
    public int deleteAupBuildingById(String id);

    /**
     * 批量删除楼栋信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupBuildingByIds(String[] ids);

    /**
     * 根据房屋
     * 查询房屋的no查询房屋的类型和数量
     * @param fwNo
     * @return
     */
    List<Map> getFwCount(String[] fwNo);

    List<Map> getFwList(String[] fwNo);

    List<Map> getLdAdminMap(String[] strings);
}