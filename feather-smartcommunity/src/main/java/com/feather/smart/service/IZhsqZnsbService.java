package com.feather.smart.service;

import com.feather.smart.domain.ZhsqZnsb;
import java.util.List;

/**
 * smartService接口
 * 
 * @author fancy
 * @date 2020-09-01
 */
public interface IZhsqZnsbService 
{
    /**
     * 查询smart
     * 
     * @param id smartID
     * @return smart
     */
    public ZhsqZnsb selectZhsqZnsbById(String id);

    /**
     * 查询smart列表
     * 
     * @param zhsqZnsb smart
     * @return smart集合
     */
    public List<ZhsqZnsb> selectZhsqZnsbList(ZhsqZnsb zhsqZnsb);

    /**
     * 新增smart
     * 
     * @param zhsqZnsb smart
     * @return 结果
     */
    public int insertZhsqZnsb(ZhsqZnsb zhsqZnsb);

    /**
     * 修改smart
     * 
     * @param zhsqZnsb smart
     * @return 结果
     */
    public int updateZhsqZnsb(ZhsqZnsb zhsqZnsb);

    /**
     * 批量删除smart
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZhsqZnsbByIds(String ids);

    /**
     * 删除smart信息
     * 
     * @param id smartID
     * @return 结果
     */
    public int deleteZhsqZnsbById(String id);
}
