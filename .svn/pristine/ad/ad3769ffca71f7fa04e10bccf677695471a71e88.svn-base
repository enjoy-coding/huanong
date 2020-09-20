package com.feather.lpscommunity.service;

import java.util.List;
import java.util.Map;

import com.feather.lpscommunity.domain.ScPrizes;

/**
 * 奖品Service接口
 * 
 * @author fancy
 * @date 2019-11-21
 */
public interface IScPrizesService 
{
    /**
     * 查询奖品
     * 
     * @param prizesId 奖品ID
     * @return 奖品
     */
    public ScPrizes selectScPrizesById(Long prizesId);

    /**
     * 获取奖品以及图片
     */
    public ScPrizes selectScPrizesFileById(Long prizesId,String headUrl);

    /**
     * 查询奖品列表
     * 
     * @param scPrizes 奖品
     * @return 奖品集合
     */
    public List<ScPrizes> selectScPrizesList(ScPrizes scPrizes);
    
    /**
     * 查询奖品列表
     * 
     * @param scPrizes 奖品
     * @return 奖品集合
     */
    public List<ScPrizes> selectScPrizesList(ScPrizes scPrizes,String headUrl);

    /**
     * 接口返回指定参数
     */
    public List<Map<String,Object>> screenScPrizesList(ScPrizes scPrizes,String headUrl);

    /**
     * 判断奖品是否还存在
     */
    public boolean checkPrizesNumById(Long prizesId);

    /**
     * 减少库存
     */
    public int updatePrizesNum(Long prizesId,int num);
    
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
     * 批量删除奖品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScPrizesByIds(String ids);

    /**
     * 删除奖品信息
     * 
     * @param prizesId 奖品ID
     * @return 结果
     */
    public int deleteScPrizesById(Long prizesId);
}