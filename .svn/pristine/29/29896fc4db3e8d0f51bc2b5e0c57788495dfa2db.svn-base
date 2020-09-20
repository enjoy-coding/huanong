package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.domain.AupJcbzManager;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 决策保障管理员Service接口
 * 
 * @author fancy
 * @date 2020-01-14
 */
public interface IAupJcbzManagerService 
{
    /**
     * 查询决策保障管理员
     * 
     * @param id 决策保障管理员ID
     * @return 决策保障管理员
     */
    public AupJcbzManager selectAupJcbzManagerById(Long id);

    /**
     * 查询决策保障管理员列表
     * 
     * @param aupJcbzManager 决策保障管理员
     * @return 决策保障管理员集合
     */
    public List<AupJcbzManager> selectAupJcbzManagerList(AupJcbzManager aupJcbzManager);

    /**
     * 新增决策保障管理员
     * 
     * @param aupJcbzManager 决策保障管理员
     * @return 结果
     */
    public int insertAupJcbzManager(AupJcbzManager aupJcbzManager);

    /**
     * 修改决策保障管理员
     * 
     * @param aupJcbzManager 决策保障管理员
     * @return 结果
     */
    public int updateAupJcbzManager(AupJcbzManager aupJcbzManager);

    /**
     * 批量删除决策保障管理员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupJcbzManagerByIds(String ids);

    /**
     * 删除决策保障管理员信息
     * 
     * @param id 决策保障管理员ID
     * @return 结果
     */
    public int deleteAupJcbzManagerById(Long id);

    /**
     * 加载区域列表树
     */
    List<Ztree> selectAupBuildingAreaTree(AupBuildingArea aupBuildingArea);

    AjaxResult selectOptions(AupBuildingArea aupBuildingArea);

    void updateData();
}
