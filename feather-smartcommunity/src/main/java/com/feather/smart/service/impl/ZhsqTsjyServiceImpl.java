package com.feather.smart.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.smart.mapper.ZhsqTsjyMapper;
import com.feather.smart.domain.ZhsqTsjy;
import com.feather.smart.service.IZhsqTsjyService;
import com.feather.common.core.text.Convert;

/**
 * 投诉建议Service业务层处理
 * 
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqTsjyServiceImpl implements IZhsqTsjyService 
{
    @Autowired
    private ZhsqTsjyMapper zhsqTsjyMapper;

    /**
     * 查询投诉建议
     * 
     * @param tsjyid 投诉建议ID
     * @return 投诉建议
     */
    @Override
    public ZhsqTsjy selectZhsqTsjyById(String tsjyid)
    {
        return zhsqTsjyMapper.selectZhsqTsjyById(tsjyid);
    }

    /**
     * 查询投诉建议列表
     * 
     * @param zhsqTsjy 投诉建议
     * @return 投诉建议
     */
    @Override
    public List<ZhsqTsjy> selectZhsqTsjyList(ZhsqTsjy zhsqTsjy)
    {
        return zhsqTsjyMapper.selectZhsqTsjyList(zhsqTsjy);
    }

    /**
     * 新增投诉建议
     * 
     * @param zhsqTsjy 投诉建议
     * @return 结果
     */
    @Override
    public int insertZhsqTsjy(ZhsqTsjy zhsqTsjy)
    {
        return zhsqTsjyMapper.insertZhsqTsjy(zhsqTsjy);
    }

    /**
     * 修改投诉建议
     * 
     * @param zhsqTsjy 投诉建议
     * @return 结果
     */
    @Override
    public int updateZhsqTsjy(ZhsqTsjy zhsqTsjy)
    {
        return zhsqTsjyMapper.updateZhsqTsjy(zhsqTsjy);
    }

    /**
     * 删除投诉建议对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqTsjyByIds(String ids)
    {
        return zhsqTsjyMapper.deleteZhsqTsjyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投诉建议信息
     * 
     * @param tsjyid 投诉建议ID
     * @return 结果
     */
    public int deleteZhsqTsjyById(String tsjyid)
    {
        return zhsqTsjyMapper.deleteZhsqTsjyById(tsjyid);
    }

    @Override
    public List<Map> getSuggestionList(String sqid, String xqid) {
        return zhsqTsjyMapper.getSuggestionList(sqid,xqid);
    }

}
