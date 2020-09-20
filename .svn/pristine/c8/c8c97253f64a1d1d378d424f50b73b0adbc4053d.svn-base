package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScParkrent;

/**
 * 租用信息发布对象 sc_parkrent
 *
 * @author dufan
 * @date 2019-10-15
 */
public interface ScParkrentMapper {
    /**
     * 查询租用信息发布
     *
     * @param parkrentId
     *            租用信息发布ID
     * @return 租用信息发布
     */
    public ScParkrent selectScParkrentById(Long parkrentId);

    /**
     * 查询租用信息发布列表
     *
     * @param scParkrent
     *            租用信息发布
     * @return 租用信息发布集合
     */
    public List<ScParkrent> selectScParkrentList(ScParkrent scParkrent);

    /**
     * 新增租用信息发布
     *
     * @param scParkrent
     *            租用信息发布
     * @return 结果
     */
    public int insertScParkrent(ScParkrent scParkrent);

    /**
     * 修改租用信息发布
     *
     * @param scParkrent
     *            租用信息发布
     * @return 结果
     */
    public int updateScParkrent(ScParkrent scParkrent);

    /**
     * 删除租用信息发布
     *
     * @param parkrentId
     *            租用信息发布ID
     * @return 结果
     */
    public int deleteScParkrentById(Long parkrentId);

    /**
     * 批量删除租用信息发布
     *
     * @param parkrentIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteScParkrentByIds(String[] parkrentIds);

    public ScParkrent selectScParkrentFileById(Long parkrentId);

    public int updateScParkrentDelFlagById(String [] ids);
}
