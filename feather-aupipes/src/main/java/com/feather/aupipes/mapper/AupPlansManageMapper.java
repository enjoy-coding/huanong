package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.domain.AupPlansManage;
import com.feather.aupipes.domain.AupWarringCategory;

import java.util.List;
import java.util.Map;

/**
 * 预案管理Mapper接口
 *
 * @author niecheng
 * @date 2019-12-30
 */
public interface AupPlansManageMapper {
    /**
     * 查询预案管理
     *
     * @param id 预案管理ID
     * @return 预案管理
     */
    public AupPlansManage selectAupPlansManageById(Long id);


    public Long selectAupPlansManageCount(AupPlansManage aupPlansManage);

    /**
     * 查询预案管理列表
     *
     * @param aupPlansManage 预案管理
     * @return 预案管理集合
     */
    public List<AupPlansManage> selectAupPlansManageList(AupPlansManage aupPlansManage);

    /**
     * 新增预案管理
     *
     * @param aupPlansManage 预案管理
     * @return 结果
     */
    public int insertAupPlansManage(AupPlansManage aupPlansManage);

    /**
     * 修改预案管理
     *
     * @param aupPlansManage 预案管理
     * @return 结果
     */
    public int updateAupPlansManage(AupPlansManage aupPlansManage);

    /**
     * 删除预案管理
     *
     * @param id 预案管理ID
     * @return 结果
     */
    public int deleteAupPlansManageById(Long id);

    /**
     * 批量删除预案管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupPlansManageByIds(String[] ids);

    //查询树
    List<AupWarringCategory> selectAupWarringCategoryList(AupWarringCategory aupWarringCategory);

    List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea aupBuildingArea);

    List<Map> selectAupUsersList(String code);

    List<Map> selectAupUsersListArr(String[] lzArr);

    List<Map> selectJcbaManagers(String[] lzArr);
    List<Map> selectJcbaManager(String lzArr);

    //List<Map> selectLdPersonNum(String[] ldNum);
    List<Map> selectLdPersonNum(String ldNum);

    List<Map> selectAupUsersListArrAll();
}
