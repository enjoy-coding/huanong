package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupWarringUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预警用户Mapper接口
 * 
 * @author fancy
 * @date 2020-04-20
 */
public interface AupWarringUserMapper 
{
    /**
     * 查询预警用户
     * 
     * @param id 预警用户ID
     * @return 预警用户
     */
    public AupWarringUser selectAupWarringUserById(String id);

    /**
     * 查询预警用户列表
     * 
     * @param aupWarringUser 预警用户
     * @return 预警用户集合
     */
    public List<AupWarringUser> selectAupWarringUserList(AupWarringUser aupWarringUser);

    /**
     * 新增预警用户
     * 
     * @param aupWarringUser 预警用户
     * @return 结果
     */
    public int insertAupWarringUser(AupWarringUser aupWarringUser);

    /**
     * 修改预警用户
     * 
     * @param aupWarringUser 预警用户
     * @return 结果
     */
    public int updateAupWarringUser(AupWarringUser aupWarringUser);

    /**
     * 删除预警用户
     * 
     * @param id 预警用户ID
     * @return 结果
     */
    public int deleteAupWarringUserById(String id);

    /**
     * 批量删除预警用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupWarringUserByIds(String[] ids);

    List<Map> getUserByName(@Param("name") String name);
}
