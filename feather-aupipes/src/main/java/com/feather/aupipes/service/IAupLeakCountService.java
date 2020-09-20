package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupLeakCount;

/**
 * 探漏运行统计次数Service接口
 *
 * @author fancy
 * @date 2020-01-13
 */
public interface IAupLeakCountService {

    /**
     * 新增
     * 探漏运行统计次数
     *
     * @param aupLeakCount 探漏运行统计次数
     * @return 结果
     */
    int insertAupLeakCount(AupLeakCount aupLeakCount);

    /**
     * 删除全部
     *
     * @return
     */
    int deleteAupLeakCount();

    /**
     * 根据接口插入探漏者运行情况
     *
     * @param result
     * @return
     */
    int insertLeakStatus(String result);

}