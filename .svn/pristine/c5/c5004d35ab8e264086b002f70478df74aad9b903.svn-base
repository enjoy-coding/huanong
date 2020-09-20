package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupLeak;

import java.util.List;

/**
 * 探漏Mapper接口
 *
 * @author fancy
 * @date 2019-12-11
 */
public interface AupLeakMapper {
    /**
     * 查询探漏列
     *
     * @param placeid 探漏列ID
     * @return 探漏列
     */
    public AupLeak selectAupLeakById(int placeid);

    /**
     * 查询探漏列列表
     *
     * @param aupLeak 探漏列
     * @return 探漏列集合
     */
    public List<AupLeak> selectAupLeakList(AupLeak aupLeak);

    /**
     * 新增探漏列
     *
     * @param aupLeak 探漏列
     * @return 结果
     */
    public int insertAupLeak(AupLeak aupLeak);

    /**
     * 修改探漏列
     *
     * @param aupLeak 探漏列
     * @return 结果
     */
    public int updateAupLeak(AupLeak aupLeak);

    /**
     * 删除探漏列
     *
     * @param placeid 探漏列ID
     * @return 结果
     */
    public int deleteAupLeakById(String placeid);

    /**
     * 批量删除探漏列
     *
     * @param placeids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupLeakByIds(String[] placeids);
}