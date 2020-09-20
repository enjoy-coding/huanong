package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupWarringUser;
import java.util.List;
import java.util.Map;

/**
 * 预警用户Service接口
 * 
 * @author fancy
 * @date 2020-04-20
 */
public interface IAupWarringUserService
{
    /**
     * 查询预警用户
     * 
     * @param id 预警用户ID
     * @return 预警用户
     */
    AupWarringUser selectAupWarringUserById(String id);

    /**
     * 查询预警用户列表
     * 
     * @param aupWarringUser 预警用户
     * @return 预警用户集合
     */
    List<AupWarringUser> selectAupWarringUserList(AupWarringUser aupWarringUser);

    /**
     * 新增预警用户
     * 
     * @param aupWarringUser 预警用户
     * @return 结果
     */
    int insertAupWarringUser(AupWarringUser aupWarringUser);

    /**
     * 修改预警用户
     * 
     * @param aupWarringUser 预警用户
     * @return 结果
     */
    int updateAupWarringUser(AupWarringUser aupWarringUser);

    /**
     * 批量删除预警用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupWarringUserByIds(String ids);

    /**
     * 删除预警用户信息
     * 
     * @param id 预警用户ID
     * @return 结果
     */
    int deleteAupWarringUserById(String id);

    List<Map> getUserByName(String name);

}
