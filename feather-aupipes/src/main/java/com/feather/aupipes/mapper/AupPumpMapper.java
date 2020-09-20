package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupPump;

import java.util.List;

/**
 * 泵房Mapper接口
 *
 * @author fancy
 * @date 2020-01-06
 */
public interface AupPumpMapper {
    /**
     * 查询泵房
     *
     * @param id 泵房ID
     * @return 泵房
     */
    public AupPump selectAupPumpById(Long id);

    /**
     * 查询泵房列表
     *
     * @param aupPump 泵房
     * @return 泵房集合
     */
    public List<AupPump> selectAupPumpList(AupPump aupPump);

    /**
     * 新增泵房
     *
     * @param aupPump 泵房
     * @return 结果
     */
    public int insertAupPump(AupPump aupPump);

    /**
     * 修改泵房
     *
     * @param aupPump 泵房
     * @return 结果
     */
    public int updateAupPump(AupPump aupPump);

    /**
     * 删除泵房
     *
     * @param id 泵房ID
     * @return 结果
     */
    public int deleteAupPumpById(Long id);

    /**
     * 批量删除泵房
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupPumpByIds(String[] ids);
}