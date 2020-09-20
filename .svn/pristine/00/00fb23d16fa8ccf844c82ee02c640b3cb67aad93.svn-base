package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPump;
import com.feather.common.core.domain.Ztree;

import java.util.List;
import java.util.Map;

public interface IAupPumpService {

    /**
     * 树结构
     *
     * @param id
     * @return
     */
    List<Ztree> getTree(Long id);

    /**
     * 基本信息表格
     *
     * @param id
     * @return
     */
    List<AupPump> getBaseTable(Long id);

    /**
     * 状态表格
     *
     * @param id
     * @return
     */
    List<AupPump> getStateTable(Long id);

    /**
     * 仪表盘
     *
     * @param id
     * @return
     */
    Map<String, Object> getGaugeData(Long id);

    /**
     * 查询泵房
     *
     * @param id 泵房ID
     * @return 泵房
     */
    AupPump selectAupPumpById(Long id);

    /**
     * 查询泵房列表
     *
     * @param aupPump 泵房
     * @return 泵房集合
     */
    List<AupPump> selectAupPumpList(AupPump aupPump);

    /**
     * 新增泵房
     *
     * @param aupPump 泵房
     * @return 结果
     */
    int insertAupPump(AupPump aupPump);

    /**
     * 修改泵房
     *
     * @param aupPump 泵房
     * @return 结果
     */
    int updateAupPump(AupPump aupPump);

    /**
     * 批量删除泵房
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupPumpByIds(String ids);

    /**
     * 删除泵房信息
     *
     * @param id 泵房ID
     * @return 结果
     */
    int deleteAupPumpById(Long id);


}
