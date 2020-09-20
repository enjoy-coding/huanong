package com.feather.patrol.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.feather.patrol.domain.PtrCard;

/**
 * 巡点Service接口
 */
public interface IPtrCardService {
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
    public List<PtrCard> selectPtrCardByCode(String qrcode, boolean scope);

    /**
     * 查询重复巡点
     * 
     * @param cardId
     *            巡点ID
     * @param cardQrcode
     *            巡点二维码
     * @return 巡点
     */
    public List<PtrCard> selectDupicateByCodeNotId(String cardQrcode, Long cardId);

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
     * @param cardId
     * @param logId
     * @return 结果
     */
    public int insertFacadeFromLog(Long cardId, Long logId);

    /**
     * 批量删除巡点
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deletePtrCardByIds(String ids);

    /**
     * 删除巡点信息
     * 
     * @param cardId
     *            巡点ID
     * @return 结果
     */
    public int deletePtrCardById(Long cardId);

    /**
     * 导入数据
     * 
     * @param file
     * @param updateSupport
     * @return
     */
    public int importData(MultipartFile file, boolean updateSupport) throws Exception;
}