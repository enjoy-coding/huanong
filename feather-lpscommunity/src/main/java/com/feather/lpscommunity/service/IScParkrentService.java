package com.feather.lpscommunity.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.feather.lpscommunity.domain.ScParkrent;
import com.feather.lpscommunity.domain.ScVolunteer;

/**
 * 租用信息发布Service接口
 *
 * @author dufan
 * @date 2019-10-15
 */
public interface IScParkrentService {
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
     * 筛选查询租用信息发布列表
     *
     * @param scParkrent
     *            租用信息发布
     * @return 租用信息发布集合
     */
    public List<Map<String, Object>> screenScParkrentList(ScParkrent scParkrent, String domain);

    /**
     * 筛选查询租用信息发布详情
     *
     * @param scParkrent
     *            租用信息发布
     * @return 租用信息发布集合
     */
    public Map<String, Object> screenScParkrentDetail(Long parkrentId, String domain);

     /**
     * 筛选查询租用信息发布详情
     *
     * @param parkrentId
     *            租用信息发布
     * @return 租用信息发布集合
     */
    public List<ScVolunteer> selectVolunteerByParkrent(Long parkrentId);

    /**
     * 新增租用信息发布
     *
     * @param scParkrent
     *            租用信息发布
     * @return 结果
     */
    public int insertScParkrent(ScParkrent scParkrent);

    /**
     * 新增租用信息发布和照片
     *
     * @param scParkrent
     *            租用信息发布
     * @return 结果
     */
    public ScParkrent insertScParkrentAndPic(ScParkrent scParkrent, MultipartFile[] parkrentFile);

    /**
     * 修改租用信息发布
     *
     * @param scParkrent
     *            租用信息发布
     * @return 结果
     */
    public int updateScParkrent(ScParkrent scParkrent);

    /**
     * 结项
     *
     * @param scParkrent
     *            租用信息发布
     * @return 结果
     */
    public int overScParkrent(ScParkrent scParkrent);

    /**
     * 批量删除租用信息发布
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteScParkrentByIds(String ids);

    /**
     * 删除租用信息发布信息
     *
     * @param parkrentId
     *            租用信息发布ID
     * @return 结果
     */
    public int deleteScParkrentById(Long parkrentId);

    /**
     * 逻辑删除
     *
     * @param parkrentId
     *            租用信息发布ID
     * @return 结果
     */
    public int updateScParkrentDelFlagById(String ids);

   /**
     * 查询租用信息发布和图片
     *
     * @param parkrentId
     *            租用信息发布ID
     * @return 租用信息发布
     */
    public ScParkrent selectScParkrentFileById(Long parkrentId,String headUrl);

}
