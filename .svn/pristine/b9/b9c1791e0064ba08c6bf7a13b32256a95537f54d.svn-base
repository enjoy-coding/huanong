package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.feather.aupipes.service.IAupWaterqualityLsjcService;
import com.feather.aupipes.service.IAupWaterqualitySzjcService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupWaterqualityMapper;
import com.feather.aupipes.domain.AupWaterquality;
import com.feather.aupipes.service.IAupWaterqualityService;
import com.feather.common.core.text.Convert;

/**
 * 水质Service业务层处理
 *
 * @author fancy
 * @date 2020-03-13
 */
@Component("wq")
@Service
public class AupWaterqualityServiceImpl implements IAupWaterqualityService
{
    @Autowired
    private AupWaterqualityMapper aupWaterqualityMapper;

    @Autowired
    private IAupWaterqualityLsjcService waterqualityLsjcService;

    @Autowired
    private IAupWaterqualitySzjcService waterqualitySzjcService;

    /**
     * 查询水质
     *
     * @param oid 水质ID
     * @return 水质
     */
    @Override
    public AupWaterquality selectAupWaterqualityById(Long oid)
    {
        return aupWaterqualityMapper.selectAupWaterqualityById(oid);
    }

    /**
     * 查询水质列表
     *
     * @param aupWaterquality 水质
     * @return 水质
     */
    @Override
    public List<AupWaterquality> selectAupWaterqualityList(AupWaterquality aupWaterquality)
    {
        return aupWaterqualityMapper.selectAupWaterqualityList(aupWaterquality);
    }

    /**
     * 新增水质
     *
     * @param aupWaterquality 水质
     * @return 结果
     */
    @Override
    public int insertAupWaterquality(AupWaterquality aupWaterquality)
    {
        return aupWaterqualityMapper.insertAupWaterquality(aupWaterquality);
    }

    /**
     * 修改水质
     *
     * @param aupWaterquality 水质
     * @return 结果
     */
    @Override
    public int updateAupWaterquality(AupWaterquality aupWaterquality)
    {
        return aupWaterqualityMapper.updateAupWaterquality(aupWaterquality);
    }

    /**
     * 删除水质对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterqualityByIds(String ids)
    {
        return aupWaterqualityMapper.deleteAupWaterqualityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除水质信息
     *
     * @param oid 水质ID
     * @return 结果
     */
    @Override
    public int deleteAupWaterqualityById(Long oid)
    {
        return aupWaterqualityMapper.deleteAupWaterqualityById(oid);
    }

    @Override
    public List<Ztree> getWaterQualityTree(){
        List<Ztree> ztreeList = new ArrayList<>();
        List<AupWaterquality> aupPumpList = this.selectAupWaterqualityList(new AupWaterquality());
        for (AupWaterquality aupWaterquality : aupPumpList) {
            ztreeList.add(new Ztree(aupWaterquality.getOid(), 0L, aupWaterquality.getSname(), false, BeanUtils.beanToMap(aupWaterquality)));
        }
        return ztreeList;
    }

    /**
     * 定时插入数据
     */
    @Override
    public void timeSetWaterQuality() {
        List<AupWaterquality> waterqualityList =this.selectAupWaterqualityList(new AupWaterquality());
        waterqualitySzjcService.timeSetWaterQualitySzjc(waterqualityList);
        waterqualityLsjcService.timeSetWaterQualityLsjc(waterqualityList);
    }
}