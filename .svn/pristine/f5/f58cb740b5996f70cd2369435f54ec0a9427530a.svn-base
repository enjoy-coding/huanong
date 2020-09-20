package com.feather.smart.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.smart.mapper.ZhsqGgMapper;
import com.feather.smart.domain.ZhsqGg;
import com.feather.smart.service.IZhsqGgService;
import com.feather.common.core.text.Convert;

/**
 * 公告Service业务层处理
 * 
 * @author fancy
 * @date 2020-05-17
 */
@Service
public class ZhsqGgServiceImpl implements IZhsqGgService 
{
    @Autowired
    private ZhsqGgMapper zhsqGgMapper;

    /**
     * 查询公告
     * 
     * @param ggid 公告ID
     * @return 公告
     */
    @Override
    public ZhsqGg selectZhsqGgById(String ggid)
    {
        return zhsqGgMapper.selectZhsqGgById(ggid);
    }

    /**
     * 查询公告列表
     * 
     * @param zhsqGg 公告
     * @return 公告
     */
    @Override
    public List<ZhsqGg> selectZhsqGgList(ZhsqGg zhsqGg)
    {
        return zhsqGgMapper.selectZhsqGgList(zhsqGg);
    }

    @Override
    public List<ZhsqGg> getZhsqGgList(String sqid, String xqid) {
        return zhsqGgMapper.getZhsqGgList( sqid,  xqid);
    }

    /**
     * 新增公告
     * 
     * @param zhsqGg 公告
     * @return 结果
     */
    @Override
    public int insertZhsqGg(ZhsqGg zhsqGg)
    {
        return zhsqGgMapper.insertZhsqGg(zhsqGg);
    }

    /**
     * 修改公告
     * 
     * @param zhsqGg 公告
     * @return 结果
     */
    @Override
    public int updateZhsqGg(ZhsqGg zhsqGg)
    {
        return zhsqGgMapper.updateZhsqGg(zhsqGg);
    }

    /**
     * 删除公告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZhsqGgByIds(String ids)
    {
        return zhsqGgMapper.deleteZhsqGgByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除公告信息
     * 
     * @param ggid 公告ID
     * @return 结果
     */
    public int deleteZhsqGgById(String ggid)
    {
        return zhsqGgMapper.deleteZhsqGgById(ggid);
    }
}
