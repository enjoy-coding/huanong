package com.feather.patrol.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.feather.patrol.domain.PtrCard;

/**
 * 巡点Mapper接口
 */
public interface PtrCardMapper {
    /**
     * 查询巡点
     * 
     * @param cardId
     *            巡点ID
     * @return 巡点
     */
    public PtrCard selectPtrCardById(Long cardId);

    /**
     * 通过二维码查询巡点
     * 
     * @param qrcode
     *            二维码
     * @return 巡点
     */
    public List<PtrCard> selectPtrCardByCode(String qrcode);

    /**
     * 查询重复巡点
     * 
     * @param map(cardId
     *            + cardQrcode)
     * @return 巡点
     */
    public List<PtrCard> selectDupicateByCodeNotId(@Param("cardId") Long cardId, @Param("params") String cardQrcode);

    /**
     * 查询重复巡点
     * 
     * @param ptrCard
     *            巡点ID
     * @return 巡点
     */
    public List<PtrCard> selectDupicatePtrCard(PtrCard ptrCard);

    /**
     * 查询巡点列表
     * 
     * @param ptrCard
     *            巡点
     * @return 巡点集合
     */
    public List<PtrCard> selectPtrCardList(PtrCard ptrCard);

    /**
     * 新增巡点
     * 
     * @param ptrCard
     *            巡点
     * @return 结果
     */
    public int insertPtrCard(PtrCard ptrCard);

    /**
     * 修改巡点
     * 
     * @param ptrCard
     *            巡点
     * @return 结果
     */
    public int updatePtrCard(PtrCard ptrCard);

    /**
     * 插入外观图片（来源于日志）
     * 
     * @param map(cardId
     *            + logId)
     * @return 结果
     */
    public int insertFacadeFromLog(Map<String, Object> map);

    /**
     * 插入外观图片（来源于日志地图）
     * 
     * @param map(cardId
     *            + logId)
     * @return 结果
     */
    public int insertFacadeFromLogMap(Map<String, Object> map);

    /**
     * 删除巡点
     * 
     * @param cardId
     *            巡点ID
     * @return 结果
     */
    public int deletePtrCardById(Long cardId);

    /**
     * 批量删除巡点
     * 
     * @param cardIds
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePtrCardByIds(String[] cardIds);
}