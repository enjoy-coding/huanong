package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupLeakCount;


/**
 * 探漏运行统计次数Mapper接口
 *
 * @author fancy
 * @date 2020-01-13
 */
public interface AupLeakCountMapper {


    /**
     * 新增
     * 探漏运行统计次数
     *
     * @param aupLeakCount 探漏运行统计次数
     * @return 结果
     */
    public int insertAupLeakCount(AupLeakCount aupLeakCount);


    /**
     * 删除全部
     *
     * @return
     */
    public int deleteAupLeakCount();
}
