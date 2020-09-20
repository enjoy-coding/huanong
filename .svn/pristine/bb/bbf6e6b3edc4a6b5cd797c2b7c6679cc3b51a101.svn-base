package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupBuildingArea;
import java.util.List;

/**
 * 区域树结构Mapper接口
 *
 * @author fancy
 * @date 2019-12-11
 */
public interface AupBuildingAreaMapper
{
    /**
     * 查询区域树结构
     *
     * @param id 区域树结构ID
     * @return 区域树结构
     */
    public AupBuildingArea selectAupBuildingAreaById(Long id);

    /**
     * 根据pid查询
     * @param pid 父节点
     * @return
     */
    public AupBuildingArea selectAupBuildingAreaByPid(Long pid);

    /**
     *
     * @param code
     * @return
     */
    public AupBuildingArea selectAupBuildingAreaByCode(String code);

    public AupBuildingArea selectAupBuildingAreaByAreaNo(String areaNo);

    /**
     * 查询区域树结构列表
     *
     * @param aupBuildingArea 区域树结构
     * @return 区域树结构集合
     */
    public List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea aupBuildingArea);

    /**
     * 查询区域树结构列表
     *
     * @param aupBuildingArea 区域树结构
     * @return 区域树结构集合
     */
    public List<AupBuildingArea> selectAupBuildingAreaListSd(AupBuildingArea aupBuildingArea);

    /**
     * 根据parentCode查询
     * @param aupBuildingArea
     * @return
     */
    public List<AupBuildingArea> selectBuildingAppointParentCodeList(AupBuildingArea aupBuildingArea);

    /**
     * 新增区域树结构
     *
     * @param aupBuildingArea 区域树结构
     * @return 结果
     */
    public int insertAupBuildingArea(AupBuildingArea aupBuildingArea);

    /**
     * 修改区域树结构
     *
     * @param aupBuildingArea 区域树结构
     * @return 结果
     */
    public int updateAupBuildingArea(AupBuildingArea aupBuildingArea);


    public List<AupBuildingArea> selectAupBuildingLessThanLevelList(AupBuildingArea aupBuildingArea);


    public List<AupBuildingArea> selectAupBuildingAreaListInfo(AupBuildingArea aupBuildingArea);

    List<AupBuildingArea> selectAllLdNums(String[] allNums);

    List<AupBuildingArea> selectAupBuildingAreaListInfoForLdno(AupBuildingArea aupBuildingArea);

    List<AupBuildingArea> selectAupBuildingTypeListInfoForLdno(AupBuildingArea aupBuildingArea);

    List<AupBuildingArea> selectAupBuildingAreaViewList(AupBuildingArea area);
}