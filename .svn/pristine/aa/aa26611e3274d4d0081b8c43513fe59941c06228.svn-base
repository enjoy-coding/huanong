package com.feather.system.mapper;

import java.util.List;

import com.feather.system.domain.SysUser;

/**
 * 用户表 数据层
 * 
 * @author feather
 */
public interface SysUserMapper {
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser
     *            用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询未已配用户角色列表
     * 
     * @param user
     *            用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user
     *            用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     * 
     * @param loginName
     *            用户名
     * @return 用户对象信息
     */
    public List<SysUser> selectUserByLoginName(String loginName);

    /**
     * 通过手机号码查询用户
     * 
     * @param phoneNumber
     *            手机号码
     * @return 用户对象信息
     */
    public List<SysUser> selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过身份证号查询用户
     * 
     * @param idCard
     *            身份证号
     * @return 用户对象信息
     */
    public List<SysUser> selectUserByIdCard(String idCard);

    /**
     * 通过openid查询用户
     * 
     * @param openid
     *            openid
     * @return 用户对象信息
     */
    public List<SysUser> selectUserByOpenid(String openid);

    /**
     * 通过邮箱查询用户
     * 
     * @param email
     *            邮箱
     * @return 用户对象信息
     */
    public List<SysUser> selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId
     *            用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId
     *            用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     * 
     * @param user
     *            用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 新增用户信息
     * 
     * @param user
     *            用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);
}
