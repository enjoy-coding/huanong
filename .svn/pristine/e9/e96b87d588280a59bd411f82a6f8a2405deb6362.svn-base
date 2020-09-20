package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupLeak;
import com.feather.common.core.domain.Ztree;

import java.util.List;
import java.util.Map;

/**
 * 探漏
 */
public interface IAupLeakService {

    Map<String, Object> getCount(int online, int total);


    /**
     * 查询探漏列
     *
     * @param placeid 探漏列ID
     * @return 探漏列
     */
    AupLeak selectAupLeakById(int placeid);

    /**
     * 查询探漏列列表
     *
     * @param aupLeak 探漏列
     * @return 探漏列集合
     */
    List<AupLeak> selectAupLeakList(AupLeak aupLeak);

    /**
     * 获取树结构
     *
     * @return
     */
    List<Ztree> selectAupLeakTreeList(AupLeak aupLeak);

    /**
     * 新增探漏列
     *
     * @param aupLeak 探漏列
     * @return 结果
     */
    int insertAupLeak(AupLeak aupLeak);

    /**
     * 修改探漏列
     *
     * @param aupLeak 探漏列
     * @return 结果
     */
    int updateAupLeak(AupLeak aupLeak);

    /**
     * 批量删除探漏列
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupLeakByIds(String ids);

    /**
     * 删除探漏列信息
     *
     * @param placeid 探漏列ID
     * @return 结果
     */
    int deleteAupLeakById(String placeid);

    /**
     * @param result
     * @return
     */
    int insetOrUpdateLeak(String result);

}
