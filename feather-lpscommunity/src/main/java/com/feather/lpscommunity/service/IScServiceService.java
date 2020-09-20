package com.feather.lpscommunity.service;

import java.util.List;
import java.util.Map;

import com.feather.lpscommunity.domain.ScService;

/**
 * 社区服务Service接口
 *
 * @author dufan
 * @date 2019-10-18
 */
public interface IScServiceService
{
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
     * 筛选指定列返回列表
     *
     * @param scService 社区服务
     * @return 社区服务
     */
    public List<Map<String,Object>> screenScServiceList(ScService scService);

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
     * 批量删除社区服务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScServiceByIds(String ids);

    /**
     * 删除社区服务信息
     *
     * @param serviceDept 社区服务ID
     * @return 结果
     */
    public int deleteScServiceById(Long serviceDept);


    /**
     * 检查社区是否唯一
     * @param scService 社区服务
     * @return
     */
    public boolean checkServiceDeptUnique(ScService scService);


    /**
     * 获取服务列表
     * @return
     */
    public  List<Map<String,Object>> getServiceValue();

    /**
     * 服务子类型列表
     * @param headUrl
     * @param serviceType
     * @return
     */
    public  List<Map<String,Object>> getServiceChildrenValue(String headUrl,String serviceType);

    /**
     * 获取字典服务以及服务子类型
     * @param scService
     * @return
     */
    public List<ScService> selectScServiceListByDicData(ScService scService);


}
