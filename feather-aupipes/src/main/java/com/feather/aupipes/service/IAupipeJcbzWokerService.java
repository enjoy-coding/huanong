package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.domain.AupipeJcbzWoker;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * Create by NieCheng
 * Time   2020/7/6 17:22
 */
public interface IAupipeJcbzWokerService {
    /**
     * 查询决策保障管理员
     *
     * @param id 决策保障管理员ID
     * @return 决策保障管理员
     */
    AupipeJcbzWoker selectAupJcbzManagerById(Long id);

    /**
     * 查询决策保障管理员列表
     *
     * @param aupJcbzManager 决策保障管理员
     * @return 决策保障管理员集合
     */
    List<AupipeJcbzWoker> selectAupJcbzManagerList(AupipeJcbzWoker aupJcbzManager);

    /**
     * 新增决策保障管理员
     *
     * @param aupJcbzManager 决策保障管理员
     * @return 结果
     */
    int insertAupJcbzManager(AupipeJcbzWoker aupJcbzManager);

    /**
     * 修改决策保障管理员
     *
     * @param aupJcbzManager 决策保障管理员
     * @return 结果
     */
    int updateAupJcbzManager(AupipeJcbzWoker aupJcbzManager);

    /**
     * 批量删除决策保障管理员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupJcbzManagerByIds(String ids);

    /**
     * 删除决策保障管理员信息
     *
     * @param id 决策保障管理员ID
     * @return 结果
     */
    int deleteAupJcbzManagerById(Long id);

    /**
     * 加载区域列表树
     */
    List<Ztree> selectAupBuildingAreaTree(AupBuildingArea aupBuildingArea);

    AjaxResult selectOptions(AupBuildingArea aupBuildingArea);

    void updateData();
}
