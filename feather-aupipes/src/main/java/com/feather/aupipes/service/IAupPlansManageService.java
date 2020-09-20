package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPlansManage;
import com.feather.common.core.domain.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 预案管理Service接口
 *
 * @author fancy
 * @date 2019-12-30
 */
public interface IAupPlansManageService {
    /**
     * 查询预案管理
     *
     * @param id 预案管理ID
     * @return 预案管理
     */
     AupPlansManage selectAupPlansManageById(Long id);


     Long selectAupPlansManageCount(AupPlansManage aupPlansManage);

    /**
     * 查询预案管理列表
     *
     * @param aupPlansManage 预案管理
     * @return 预案管理集合
     */
     List<AupPlansManage> selectAupPlansManageList(AupPlansManage aupPlansManage);

    /**
     * 新增预案管理
     *
     * @param aupPlansManage 预案管理
     * @return 结果
     */
     int insertAupPlansManage(AupPlansManage aupPlansManage);

    /**
     * 修改预案管理
     *
     * @param aupPlansManage 预案管理
     * @return 结果
     */
     int updateAupPlansManage(AupPlansManage aupPlansManage);

    /**
     * 批量删除预案管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteAupPlansManageByIds(String ids);

    /**
     * 删除预案管理信息
     *
     * @param id 预案管理ID
     * @return 结果
     */
     int deleteAupPlansManageById(Long id);

    AjaxResult selectInitSelect(String categoryparentid);


    List<Map> selectAupUsersListArr(String ldIds);

    List<Map> selectJcbaManagers(String ldIds);

    List<Map> selectJcbaManager(String ld);

    List<Map> selectAupUsersListArrAll();
}
