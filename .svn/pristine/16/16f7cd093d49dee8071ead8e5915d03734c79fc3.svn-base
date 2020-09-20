package com.feather.lpscommunity.mapper;

import java.util.List;

import com.feather.lpscommunity.domain.ScService;

/**
 * 社区服务Mapper接口
 *
 * @author dufan
 * @date 2019-10-18
 */
public interface ScServiceMapper {
    /**
     * 查询社区服务
     *
     * @param serviceDept 社区服务ID
     * @return 社区服务
     */
    public ScService selectScServiceById(Long serviceDept);

    /**
     * 查询社区服务列表
     *
     * @param scService 社区服务
     * @return 社区服务集合
     */
    public List<ScService> selectScServiceList(ScService scService);

    /**
     * 新增社区服务
     *
     * @param scService 社区服务
     * @return 结果
     */
    public int insertScService(ScService scService);

    /**
     * 修改社区服务
     *
     * @param scService 社区服务
     * @return 结果
     */
    public int updateScService(ScService scService);

    /**
     * 修改社区服务
     *
     * @param scService 社区服务
     * @return 结果
     */
    public ScService checkServiceDeptUnique(ScService scService);

    /**
     * 删除社区服务
     *
     * @param serviceDept 社区服务ID
     * @return 结果
     */
    public int deleteScServiceById(Long serviceDept);

    /**
     * 批量删除社区服务
     *
     * @param serviceDepts 需要删除的数据ID
     * @return 结果
     */
    public int deleteScServiceByIds(String[] serviceDepts);


    /**
     * 检查社区是否唯一
     * @param serviceDept 社区id
     * @return
     */
    public ScService checkServiceDeptUnique(Long serviceDept);

}
