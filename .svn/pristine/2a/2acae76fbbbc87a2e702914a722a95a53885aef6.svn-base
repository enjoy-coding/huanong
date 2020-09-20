package com.feather.smart.service.impl;

import java.util.List;
import java.util.Map;

import com.feather.smart.domain.ZhsqYc;
import com.feather.smart.mapper.ZhsqYcMapper;
import com.feather.smart.service.IZhsqYcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;

/**
 * 异常信息Service业务层处理
 * 
 * @author fancy
 * @date 2020-05-14
 */
@Service
public class ZhsqYcServiceImpl implements IZhsqYcService
{
    @Autowired
    private ZhsqYcMapper zhsqYcMapper;

    /**
     * 查询异常信息
     * 
     * @param ycid 异常信息ID
     * @return 异常信息
     */
    @Override
    public ZhsqYc selectZhsqYcById(String ycid)
    {
        return zhsqYcMapper.selectZhsqYcById(ycid);
    }

    /**
     * 查询异常信息列表
     * 
     * @param zhsqYc 异常信息
     * @return 异常信息
     */
    @Override
    public List<ZhsqYc> selectZhsqYcList(ZhsqYc zhsqYc)
    {
        return zhsqYcMapper.selectZhsqYcList(zhsqYc);
    }

    /**
     * 新增异常信息
     * 
     * @param zhsqYc 异常信息
     * @return 结果
     */
    @Override
    public int insertZhsqYc(ZhsqYc zhsqYc)
    {
        return zhsqYcMapper.insertZhsqYc(zhsqYc);
    }

    /**
     * 修改异常信息
     * 
     * @param zhsqYc 异常信息
     * @return 结果
     */
    @Override
    public int updateZhsqYc(ZhsqYc zhsqYc)
    {
        return zhsqYcMapper.updateZhsqYc(zhsqYc);
    }

    /**
     * 删除异常信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqYcByIds(String ids)
    {
        return zhsqYcMapper.deleteZhsqYcByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除异常信息信息
     * 
     * @param ycid 异常信息ID
     * @return 结果
     */
    public int deleteZhsqYcById(String ycid)
    {
        return zhsqYcMapper.deleteZhsqYcById(ycid);
    }

    @Override
    public List<Map> getSourceCount(String sqid, String xqid) {
        return zhsqYcMapper.getSourceCount(sqid,xqid);
    }

    @Override
    public List<Map> getStatusCount(String sqid, String xqid) {
        return zhsqYcMapper.getStatusCount(sqid,xqid);
    }

}
