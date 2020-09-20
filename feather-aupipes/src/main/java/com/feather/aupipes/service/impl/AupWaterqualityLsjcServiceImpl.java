package com.feather.aupipes.service.impl;

import java.util.Date;
import java.util.List;

import com.feather.aupipes.domain.AupWaterquality;
import com.feather.common.config.UidWorker;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupWaterqualityLsjcMapper;
import com.feather.aupipes.domain.AupWaterqualityLsjc;
import com.feather.aupipes.service.IAupWaterqualityLsjcService;
import com.feather.common.core.text.Convert;

/**
 * 流速水位监测Service业务层处理
 *
 * @author fancy
 * @date 2020-03-13
 */
@Service
public class AupWaterqualityLsjcServiceImpl implements IAupWaterqualityLsjcService
{
    @Autowired
    private AupWaterqualityLsjcMapper aupWaterqualityLsjcMapper;

    @Autowired
    private UidWorker uidWorker;
    /**
     * 查询流速水位监测
     *
     * @param id 流速水位监测ID
     * @return 流速水位监测
     */
    @Override
    public AupWaterqualityLsjc selectAupWaterqualityLsjcById(Long id)
    {
        return aupWaterqualityLsjcMapper.selectAupWaterqualityLsjcById(id);
    }

    /**
     * 查询流速水位监测列表
     *
     * @param aupWaterqualityLsjc 流速水位监测
     * @return 流速水位监测
     */
    @Override
    public List<AupWaterqualityLsjc> selectAupWaterqualityLsjcList(AupWaterqualityLsjc aupWaterqualityLsjc)
    {
        return aupWaterqualityLsjcMapper.selectAupWaterqualityLsjcList(aupWaterqualityLsjc);
    }

    /**
     * 新增流速水位监测
     *
     * @param aupWaterqualityLsjc 流速水位监测
     * @return 结果
     */
    @Override
    public int insertAupWaterqualityLsjc(AupWaterqualityLsjc aupWaterqualityLsjc)
    {
        return aupWaterqualityLsjcMapper.insertAupWaterqualityLsjc(aupWaterqualityLsjc);
    }

    /**
     * 修改流速水位监测
     *
     * @param aupWaterqualityLsjc 流速水位监测
     * @return 结果
     */
    @Override
    public int updateAupWaterqualityLsjc(AupWaterqualityLsjc aupWaterqualityLsjc)
    {
        return aupWaterqualityLsjcMapper.updateAupWaterqualityLsjc(aupWaterqualityLsjc);
    }

    /**
     * 删除流速水位监测对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterqualityLsjcByIds(String ids)
    {
        return aupWaterqualityLsjcMapper.deleteAupWaterqualityLsjcByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流速水位监测信息
     *
     * @param id 流速水位监测ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterqualityLsjcById(Long id)
    {
        return aupWaterqualityLsjcMapper.deleteAupWaterqualityLsjcById(id);
    }

    @Override
    public void timeSetWaterQualityLsjc(List<AupWaterquality> waterqualityList){
        for (AupWaterquality s:waterqualityList) {
            AupWaterqualityLsjc lsjc = new AupWaterqualityLsjc();
            lsjc.setId(uidWorker.getNextId());
            lsjc.setOid(s.getOid());
            lsjc.setJcsj(DateUtils.parseDateToStr("YYYY-MM-dd HH:mm:ss",new Date()));
            lsjc.setLs(NumberUtils.getRandowForDouble(0.1,20));
            lsjc.setSw(NumberUtils.getRandowForDouble(0.8,35));
            this.insertAupWaterqualityLsjc(lsjc);
        }
    }

    /**
     * 获取最新一条数据
     * @param oid 路灯id
     * @return 结果
     */
    @Override
    public AupWaterqualityLsjc selectAupWaterqualityLsjcVoByOid(Long oid) {
        List<AupWaterqualityLsjc> aupWaterqualityLsjcList = aupWaterqualityLsjcMapper.selectAupWaterqualityLsjcVoByOid(oid);
        if (aupWaterqualityLsjcList.size()>0){
            return aupWaterqualityLsjcList.get(0);
        }
        return new AupWaterqualityLsjc();
    }

}