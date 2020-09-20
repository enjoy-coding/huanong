package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScPartyBuild;

public interface ScPartyBuildMapper {
    /**
     * 查询信息接口
     *
     * @param partyBuildId 信息id
     * @return 党建信息
     */
    public ScPartyBuild selectScPartyBuildById(Long partyBuildId);

    public ScPartyBuild selectScPartyBuildFileById(Long partyBuildId);
    
    /**
     * 查询信息列表接口
     * @param scPartyBuild 信息对象
     * @return 党建信息集合
     */
    public List<ScPartyBuild> selectScPartyBuildByList(ScPartyBuild scPartyBuild);

    /**
     * 查询党建以及文件
     */
    public List<ScPartyBuild> selectScPartyBuildFileByList(ScPartyBuild scPartyBuild);

    /**
     * 插入信息
     * @param scPartyBuild 信息对象
     * @return 新增结果
     */
    public int insertScPartyBuild(ScPartyBuild scPartyBuild);

    /**
     * 修改信息
     * @param scPartyBuild 信息对象
     * @return 修改结果
     */
    public int updateScPartyBuild(ScPartyBuild scPartyBuild);

    /**
     * 删除信息
     * @param partyBuildId 一个信息对象id
     * @return 删除结果
     */
    public int deleteScPartyBuildById(Long partyBuildId);

    /**
     * 批量删除信息
     * @param partyBuildIds 多个信息对象id
     * @return 批量删除结果
     */
    public int deleteScPartyBuildByIds(String [] partyBuildIds);
}
