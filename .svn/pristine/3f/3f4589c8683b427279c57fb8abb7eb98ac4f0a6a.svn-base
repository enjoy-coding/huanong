package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupWaterquality;
import com.feather.aupipes.domain.AupWaterqualitySzjc;
import com.feather.aupipes.mapper.AupWaterqualitySzjcMapper;
import com.feather.aupipes.service.IAupWaterqualitySzjcService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 水质监测Service业务层处理
 *
 * @author fancy
 * @date 2020-03-13
 */
@Service
public class AupWaterqualitySzjcServiceImpl implements IAupWaterqualitySzjcService {
    @Autowired
    private AupWaterqualitySzjcMapper aupWaterqualitySzjcMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询水质监测
     *
     * @param id
     *            水质监测ID
     * @return 水质监测
     */
    @Override
    public AupWaterqualitySzjc selectAupWaterqualitySzjcById(Long id) {
        return aupWaterqualitySzjcMapper.selectAupWaterqualitySzjcById(id);
    }

    /**
     * 查询水质监测列表
     *
     * @param aupWaterqualitySzjc
     *            水质监测
     * @return 水质监测
     */
    @Override
    public List<AupWaterqualitySzjc> selectAupWaterqualitySzjcList(AupWaterqualitySzjc aupWaterqualitySzjc) {
        return aupWaterqualitySzjcMapper.selectAupWaterqualitySzjcList(aupWaterqualitySzjc);
    }

    /**
     * 新增水质监测
     *
     * @param aupWaterqualitySzjc
     *            水质监测
     * @return 结果
     */
    @Override
    public int insertAupWaterqualitySzjc(AupWaterqualitySzjc aupWaterqualitySzjc) {
        return aupWaterqualitySzjcMapper.insertAupWaterqualitySzjc(aupWaterqualitySzjc);
    }

    /**
     * 修改水质监测
     *
     * @param aupWaterqualitySzjc
     *            水质监测
     * @return 结果
     */
    @Override
    public int updateAupWaterqualitySzjc(AupWaterqualitySzjc aupWaterqualitySzjc) {
        return aupWaterqualitySzjcMapper.updateAupWaterqualitySzjc(aupWaterqualitySzjc);
    }

    /**
     * 删除水质监测对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterqualitySzjcByIds(String ids) {
        return aupWaterqualitySzjcMapper.deleteAupWaterqualitySzjcByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除水质监测信息
     *
     * @param id
     *            水质监测ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterqualitySzjcById(String id) {
        return aupWaterqualitySzjcMapper.deleteAupWaterqualitySzjcById(id);
    }

    @Override
    public AupWaterqualitySzjc selectAupWaterqualitySzjcByOid(Long oid) {
        List<AupWaterqualitySzjc> aupWaterqualitySzjcList = aupWaterqualitySzjcMapper
                .selectAupWaterqualitySzjcByOid(oid);
        if (aupWaterqualitySzjcList.size() > 0) {
            return aupWaterqualitySzjcList.get(0);
        }
        return new AupWaterqualitySzjc();
    }

    /**
     * 定时插入水质监测
     */
    @Override
    public void timeSetWaterQualitySzjc(List<AupWaterquality> waterqualityList) {
        for (AupWaterquality w:waterqualityList) {
            AupWaterqualitySzjc szjc = new AupWaterqualitySzjc();
            szjc.setId(uidWorker.getNextId());
            szjc.setOid(w.getOid());
            // 监测时间
            szjc.setJcsj(DateUtils.parseDateToStr("YYYY-MM-dd HH:mm:ss", new Date()));
            // 荧光法溶解氧 检测范围 0~20mg/L 或 0~200%饱和度
            szjc.setYgfrjy(NumberUtils.getRandom(0, 20));
            // 电磁式电导率 量程:20μs/cm~600ms/cm
            szjc.setDcsddl(NumberUtils.getRandom(20, 600));
            szjc.setZd(NumberUtils.getRandom(0, 10));
            szjc.setPh(NumberUtils.getRandom(7, 9));
            szjc.setOrp(NumberUtils.getRandom(-1500, 1500));
            szjc.setWd(NumberUtils.getRandom(20, 30));
            this.insertAupWaterqualitySzjc(szjc);
        }

    }

}