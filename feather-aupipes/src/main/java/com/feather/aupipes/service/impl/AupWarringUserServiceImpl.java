package com.feather.aupipes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feather.aupipes.mapper.AupWarringUserMapper;
import com.feather.aupipes.domain.AupWarringUser;
import com.feather.aupipes.service.IAupWarringUserService;
import com.feather.common.core.text.Convert;

/**
 * 预警用户Service业务层处理
 * 
 * @author fancy
 * @date 2020-04-20
 */
@Service
public class AupWarringUserServiceImpl implements IAupWarringUserService 
{
    @Autowired
    private AupWarringUserMapper aupWarringUserMapper;

    /**
     * 查询预警用户
     * 
     * @param id 预警用户ID
     * @return 预警用户
     */
    @Override
    public AupWarringUser selectAupWarringUserById(String id)
    {
        return aupWarringUserMapper.selectAupWarringUserById(id);
    }

    /**
     * 查询预警用户列表
     * 
     * @param aupWarringUser 预警用户
     * @return 预警用户
     */
    @Override
    public List<AupWarringUser> selectAupWarringUserList(AupWarringUser aupWarringUser)
    {
        return aupWarringUserMapper.selectAupWarringUserList(aupWarringUser);
    }

    /**
     * 新增预警用户
     * 
     * @param aupWarringUser 预警用户
     * @return 结果
     */
    @Override
    public int insertAupWarringUser(AupWarringUser aupWarringUser)
    {
        return aupWarringUserMapper.insertAupWarringUser(aupWarringUser);
    }

    /**
     * 修改预警用户
     * 
     * @param aupWarringUser 预警用户
     * @return 结果
     */
    @Override
    public int updateAupWarringUser(AupWarringUser aupWarringUser)
    {
        return aupWarringUserMapper.updateAupWarringUser(aupWarringUser);
    }

    /**
     * 删除预警用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWarringUserByIds(String ids)
    {
        return aupWarringUserMapper.deleteAupWarringUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预警用户信息
     * 
     * @param id 预警用户ID
     * @return 结果
     */
    @Override
    public int deleteAupWarringUserById(String id)
    {
        return aupWarringUserMapper.deleteAupWarringUserById(id);
    }

    @Override
    public List<Map> getUserByName(String name) {
        return aupWarringUserMapper.getUserByName(name);
    }
}
