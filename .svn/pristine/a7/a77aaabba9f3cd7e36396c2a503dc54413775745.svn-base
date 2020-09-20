package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScFileInfo;
public interface ScFileInfoMapper {
    /**
     * 查询智慧社区实体多图
     *
     * @param fileId 智慧社区实体多图ID
     * @return 智慧社区实体多图
     */
    public ScFileInfo selectScFileInfoById(Long fileId);

    /**
     * 查询智慧社区实体多图列表
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 智慧社区实体多图集合
     */
    public List<ScFileInfo> selectScFileInfoList(ScFileInfo scFileInfo);

    /**
     * 查询智慧社区实体单图
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 智慧社区实体多图集合
     */
    public ScFileInfo selectScFileInfoByTarget(Long target);

    /**
     * 新增智慧社区实体多图
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 结果
     */
    public int insertScFileInfo(ScFileInfo scFileInfo);


    /**
     * 新增智慧社区实体多图
     *
     * @param scFileInfos 智慧社区实体多图
     * @return 结果
     */
    public int insertScFileInfos(List<ScFileInfo> scFileInfos);
    /**
     * 修改智慧社区实体多图
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 结果
     */
    public int updateScFileInfo(ScFileInfo scFileInfo);

    /**
     * 删除智慧社区实体多图
     *
     * @param fileId 智慧社区实体多图ID
     * @return 结果
     */
    public int deleteScFileInfoById(Long fileId);

    /**
     * 批量删除智慧社区实体多图
     *
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScFileInfoByIds(String[] fileIds);

    /**
     * 批量删除多个实体对应的多图
     * @param targetId
     * @return
     */
    public int deleteScFileInfoByTarget(String [] targetId);

}

