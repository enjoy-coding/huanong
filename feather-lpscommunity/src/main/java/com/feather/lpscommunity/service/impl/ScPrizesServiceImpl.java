package com.feather.lpscommunity.service.impl;

import java.util.*;

import com.feather.common.config.UidWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.core.text.Convert;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScPrizes;
import com.feather.lpscommunity.mapper.ScPrizesMapper;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScPrizesService;

/**
 * 奖品Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-21
 */
@Service
public class ScPrizesServiceImpl implements IScPrizesService {
    @Autowired
    private ScPrizesMapper scPrizesMapper;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private IScFileInfoService scFileInfoService;

    /**
     * 查询奖品
     * 
     * @param prizesId 奖品ID
     * @return 奖品
     */
    @Override
    public ScPrizes selectScPrizesById(Long prizesId) {
        return scPrizesMapper.selectScPrizesById(prizesId);
    }

    /**
     * 判断奖品是否还存在
     */
    public boolean checkPrizesNumById(Long prizesId){
        if(scPrizesMapper.checkPrizesNumById(prizesId) == null){
            return false;
        }
        return true;
    }
    /**
     * 查询奖品列表
     * 
     * @param scPrizes 奖品
     * @return 奖品
     */
    @Override
    public List<ScPrizes> selectScPrizesList(ScPrizes scPrizes) {
        return scPrizesMapper.selectScPrizesList(scPrizes);
    }

    /**
     * 新增奖品
     * 
     * @param scPrizes 奖品
     * @return 结果
     */
    @Override
    public int insertScPrizes(ScPrizes scPrizes) {
        scPrizes.setPrizesId(uidWorker.getNextId());
        return scPrizesMapper.insertScPrizes(scPrizes);
    }

    /**
     * 修改奖品
     * 
     * @param scPrizes 奖品
     * @return 结果
     */
    @Override
    public int updateScPrizes(ScPrizes scPrizes) {
        return scPrizesMapper.updateScPrizes(scPrizes);
    }

    /**
     * 删除奖品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScPrizesByIds(String ids) {
        return scPrizesMapper.deleteScPrizesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除奖品信息
     * 
     * @param prizesId 奖品ID
     * @return 结果
     */
    public int deleteScPrizesById(Long prizesId) {
        return scPrizesMapper.deleteScPrizesById(prizesId);
    }

    @Override
    public ScPrizes selectScPrizesFileById(Long prizesId, String headUrl) {
        ScPrizes scPrizes = scPrizesMapper.selectScPrizesFileById(prizesId);
        for (ScFileInfo f : scPrizes.getFiles()) {
            f.setFilePath(f.getFilePath() == null ? "" : headUrl + f.getFilePath());
        }
        return scPrizes;
    }

    @Override
    public List<ScPrizes> selectScPrizesList(ScPrizes scPrizes, String headUrl) {
        List<ScPrizes> scPrizes2 = scPrizesMapper.selectScPrizesList(scPrizes);
        for (ScPrizes prizes : scPrizes2) {
            String filePath = scFileInfoService.selectFristScFileInfoByTarget(prizes.getPrizesId(), "prizes",headUrl);
            prizes.setPrizesCover(filePath);
        }
        return scPrizes2;
    }

    @Override
    public List<Map<String, Object>> screenScPrizesList(ScPrizes scPrizes, String headUrl) {
        List<ScPrizes> scPrizes2 = this.selectScPrizesList(scPrizes,headUrl);
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        for (ScPrizes scPrizes3 : scPrizes2) {
            Map<String,Object> map = new LinkedHashMap<String,Object>();
            map.put("prizes_id", scPrizes3.getPrizesId());
            map.put("prizes_name", scPrizes3.getPrizesName());
            map.put("prizes_num", scPrizes3.getPrizesNum());
            map.put("prizes_score", scPrizes3.getPrizesScore());
            map.put("prizes_corver", scPrizes3.getPrizesCover());
            mList.add(map);
        }
        return mList;
    }

    /**
     * 兑换后减去奖品数量
     */
    @Override
    @Transactional
    public int updatePrizesNum(Long prizesId, int num) {
        ScPrizes scPrizes = scPrizesMapper.selectScPrizesById(prizesId);
        scPrizes.setPrizesNum(scPrizes.getPrizesNum()-num);
        return scPrizesMapper.updateScPrizes(scPrizes);
    }
}