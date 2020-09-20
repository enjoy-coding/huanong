package com.feather.aupipes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupTjZnafMapper;
import com.feather.aupipes.domain.AupTjZnaf;
import com.feather.aupipes.service.IAupTjZnafService;
import com.feather.common.core.text.Convert;

/**
 * 统计_智能安防Service业务层处理
 *
 * @author fancy
 * @date 2020-03-12
 */
@Component("znaf")
@Service
public class AupTjZnafServiceImpl implements IAupTjZnafService
{
    @Autowired
    private AupTjZnafMapper aupTjZnafMapper;

    /**
     * 查询统计_智能安防
     *
     * @param mj 统计_智能安防ID
     * @return 统计_智能安防
     */
    @Override
    public AupTjZnaf selectAupTjZnafById(Long mj)
    {
        return aupTjZnafMapper.selectAupTjZnafById(mj);
    }

    /**
     * 查询统计_智能安防列表
     *
     * @param aupTjZnaf 统计_智能安防
     * @return 统计_智能安防
     */
    @Override
    public List<AupTjZnaf> selectAupTjZnafList(AupTjZnaf aupTjZnaf)
    {
        return aupTjZnafMapper.selectAupTjZnafList(aupTjZnaf);
    }

    /**
     * 新增统计_智能安防
     *
     * @param aupTjZnaf 统计_智能安防
     * @return 结果
     */
    @Override
    public int insertAupTjZnaf(AupTjZnaf aupTjZnaf)
    {
        return aupTjZnafMapper.insertAupTjZnaf(aupTjZnaf);
    }

    /**
     * 修改统计_智能安防
     *
     * @param aupTjZnaf 统计_智能安防
     * @return 结果
     */
    @Override
    public int updateAupTjZnaf(AupTjZnaf aupTjZnaf)
    {
        return aupTjZnafMapper.updateAupTjZnaf(aupTjZnaf);
    }

    /**
     * 删除统计_智能安防对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupTjZnafByIds(String ids)
    {
        return aupTjZnafMapper.deleteAupTjZnafByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除统计_智能安防信息
     *
     * @param mj 统计_智能安防ID
     * @return 结果
     */
    @Override
    public int deleteAupTjZnafById(Long mj)
    {
        return aupTjZnafMapper.deleteAupTjZnafById(mj);
    }

    /**
     * 定时修改统计智能安防数据
     */
    @Override
    public void timeUpdateTjZnaf(){
        //烟感
        int ygCount =(int) (Math.random()*65);
        //水侵入
        int qsCount = (int) (Math.random()*20);
        //入侵
        int rqCount =  (int) (Math.random()*40);
        //门禁
        int mjCount =  (int) (Math.random()*50);
        AupTjZnaf aupTjZnaf = new AupTjZnaf();
        aupTjZnaf.setYg(ygCount);//烟感
        aupTjZnaf.setHwxbj(qsCount);//沁水
        aupTjZnaf.setSxt(rqCount);//入侵
        aupTjZnaf.setMj(mjCount);//门禁
        aupTjZnafMapper.deleteAupTjZnaf();
        this.insertAupTjZnaf(aupTjZnaf);
    }
}