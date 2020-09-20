package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScPrizes;

/**
 * 奖品Mapper接口
 * 
 * @author fancy
 * @date 2019-11-21
 */
public interface ScPrizesMapper 
{
    /**
     * 查询奖品
     * 
     * @param prizesId 奖品ID
     * @return 奖品
     */
    public ScPrizes selectScPrizesById(Long prizesId);

    /**
     * 判断奖品数量
     */
    public ScPrizes checkPrizesNumById(Long prizesId);

    /**
     * 查询奖品
     * 
     * @param prizesId 奖品ID
     * @return 奖品
     */
    public ScPrizes selectScPrizesFileById(Long prizesId);

    /**
     * 查询奖品列表
     * 
     * @param scPrizes 奖品
     * @return 奖品集合
     */
    public List<ScPrizes> selectScPrizesList(ScPrizes scPrizes);
    

    /**
     * 新增奖品
     * 
     * @param scPrizes 奖品
     * @return 结果
     */
    public int insertScPrizes(ScPrizes scPrizes);

    /**
     * 修改奖品
     * 
     * @param scPrizes 奖品
     * @return 结果
     */
    public int updateScPrizes(ScPrizes scPrizes);

    /**
     * 删除奖品
     * 
     * @param prizesId 奖品ID
     * @return 结果
     */
    public int deleteScPrizesById(Long prizesId);

    /**
     * 批量删除奖品
     * 
     * @param prizesIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteScPrizesByIds(String[] prizesIds);
}