package com.feather.smart.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.smart.mapper.ZhsqZnsbMapper;
import com.feather.smart.domain.ZhsqZnsb;
import com.feather.smart.service.IZhsqZnsbService;
import com.feather.common.core.text.Convert;

/**
 * smartService业务层处理
 * 
 * @author fancy
 * @date 2020-09-01
 */
@Service
public class ZhsqZnsbServiceImpl implements IZhsqZnsbService 
{
    @Autowired
    private ZhsqZnsbMapper zhsqZnsbMapper;

    /**
     * 查询smart
     * 
     * @param id smartID
     * @return smart
     */
    @Override
    public ZhsqZnsb selectZhsqZnsbById(String id)
    {
        return zhsqZnsbMapper.selectZhsqZnsbById(id);
    }

    /**
     * 查询smart列表
     * 
     * @param zhsqZnsb smart
     * @return smart
     */
    @Override
    public List<ZhsqZnsb> selectZhsqZnsbList(ZhsqZnsb zhsqZnsb)
    {
        return zhsqZnsbMapper.selectZhsqZnsbList(zhsqZnsb);
    }

    /**
     * 新增smart
     * 
     * @param zhsqZnsb smart
     * @return 结果
     */
    @Override
    public int insertZhsqZnsb(ZhsqZnsb zhsqZnsb)
    {
        return zhsqZnsbMapper.insertZhsqZnsb(zhsqZnsb);
    }

    /**
     * 修改smart
     * 
     * @param zhsqZnsb smart
     * @return 结果
     */
    @Override
    public int updateZhsqZnsb(ZhsqZnsb zhsqZnsb)
    {
        return zhsqZnsbMapper.updateZhsqZnsb(zhsqZnsb);
    }

    /**
     * 删除smart对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqZnsbByIds(String ids)
    {
        return zhsqZnsbMapper.deleteZhsqZnsbByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除smart信息
     * 
     * @param id smartID
     * @return 结果
     */
    public int deleteZhsqZnsbById(String id)
    {
        return zhsqZnsbMapper.deleteZhsqZnsbById(id);
    }
}
