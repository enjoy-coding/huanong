package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.domain.AupHl;
import com.feather.aupipes.domain.AupHlInfo;
import com.feather.aupipes.domain.AupJcbzManager;

import java.util.List;

/**
 * 决策保障管理员Mapper接口
 *
 * @author fancy
 * @date 2020-01-14
 */
public interface AupJcbzManagerMapper
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
     * 删除决策保障管理员
     *
     * @param id 决策保障管理员ID
     * @return 结果
     */
    public int deleteAupJcbzManagerById(Long id);

    /**
     * 批量删除决策保障管理员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupJcbzManagerByIds(String[] ids);

    /**
     * 查询列表信息
     * @param area
     * @return
     */
    public List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea area);

    List<AupHlInfo> getHlInfo(AupHlInfo aupHlInfo);

    List<AupHl> getHlAttriBute(AupHl aupHl);

    List<AupHlInfo> selectHlid(Long fwId);
}
