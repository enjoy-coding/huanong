package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 区域树结构Service接口
 *
 * @author fancy
 * @date 2019-12-11
 */
public interface IAupBuildingAreaService
{

    /**
     * 查询区域树结构
     *
     * @param id 区域树结构ID
     * @return 区域树结构
     */
    AupBuildingArea selectAupBuildingAreaById(Long id);


    /**
     * 查询区域树结构
     *
     * @param pid 区域树结构PID
     * @return 区域树结构
     */
    AupBuildingArea selectAupBuildingAreaByPid(Long pid);

    List<Ztree> selectZtreeDataByPid(Long pid);

    /**
     * 根据id查询楼栋
     * @param pid
     * @return
     */
    List<Ztree> selectBuildingListByPid(Long pid);

    /**
     * 构造树
     * @param area
     * @return
     */
    List<Ztree> selectAupBuildingAreaTree(AupBuildingArea area);
    /**
     * 查询区域树结构
     *
     * @param code 区域树结构code
     * @return 区域树结构
     */
    AupBuildingArea selectAupBuildingAreaByCode(String code);

    AupBuildingArea selectAupBuildingAreaByAreaNo(String areaNo);


    /**
     * 查询区域树结构列表
     *
     * @param aupBuildingArea 区域树结构
     * @return 区域树结构集合
     */
    List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea aupBuildingArea);

    /**
     * 根据code查询
     * @param Code
     * @return
     */
    List<AupBuildingArea> selectAupBuildingAreaList(String Code);


}