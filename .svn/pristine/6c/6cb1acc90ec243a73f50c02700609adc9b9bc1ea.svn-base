package com.feather.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.annotation.DataScope;
import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.core.text.Convert;
import com.feather.common.exception.BusinessException;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.spring.SpringUtils;
import com.feather.system.domain.SysRole;
import com.feather.system.domain.SysRoleDept;
import com.feather.system.domain.SysRoleMenu;
import com.feather.system.domain.SysUserRole;
import com.feather.system.mapper.SysRoleDeptMapper;
import com.feather.system.mapper.SysRoleMapper;
import com.feather.system.mapper.SysRoleMenuMapper;
import com.feather.system.mapper.SysUserRoleMapper;
import com.feather.system.service.ISysRoleService;

/**
 * 角色 业务层处理
 * 
 * @author feather
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role
     *            角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId
     *            用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<SysRole> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (perm != null) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     * 
     * @param userId
     *            用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId
     *            角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }



    /**
     * 通过角色key查找用户
     * 
     * @param roleKeys
     *            角色key
     * @return 结果
     */
    @Override
    public List<SysUserRole> selectUserByRoleKeys(String roleKeys) {
        return userRoleMapper.selectUserByRoleKeys(Convert.toStrArray(roleKeys));
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId
     *            角色ID
     * @return 结果
     */
    @Override
    public boolean deleteRoleById(Long roleId) {
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    /**
     * 批量删除角色信息
     * 
     * @param ids
     *            需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteRoleByIds(String ids) throws BusinessException {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds) {
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 新增保存角色信息
     * 
     * @param role
     *            角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        if (role.getRoleId() == null) {
            role.setRoleId(uidWorker.getNextId());
        }
        if (StringUtils.isBlank(role.getStatus())) {
            role.setStatus(Constants.SUCCESS_OR_ENABLED);
        }
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     * 
     * @param role
     *            角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改数据权限信息
     * 
     * @param role
     *            角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     * 
     * @param role
     *            角色对象
     */
    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role
     *            角色对象
     */
    public int insertRoleDept(SysRole role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role
     *            角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role) {
        Long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (info != null && info.getRoleId().longValue() != roleId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验角色权限是否唯一
     * 
     * @param role
     *            角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role) {
        Long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (info != null && info.getRoleId().longValue() != roleId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId
     *            角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 角色状态修改
     * 
     * @param role
     *            角色信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysRole role) {
        return roleMapper.updateRole(role);
    }

    /**
     * 取消授权用户角色
     * 
     * @param userRole
     *            用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     * 
     * @param roleId
     *            角色ID
     * @param userIds
     *            需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, String userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    /**
     * 批量选择授权用户角色
     * 
     * @param roleId
     *            角色ID
     * @param userIds
     *            需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色管理
        // List<SysUserRole> list = new ArrayList<SysUserRole>();
        int insertCount = 0;
        for (Long userId : users) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            if (userRoleMapper.countUserRoleByRoleIdAndUserId(ur) == 0) {
                if (userRoleMapper.insertUserRole(ur) > 0) {
                    insertCount++;
                }
            }
            // list.add(ur);
        }
        return insertCount;
        // return userRoleMapper.batchUserRole(list);
    }

    @Override
    public SysRole selectRoleByRoleKey(String roleKey) {
        return roleMapper.selectRoleByRoleKey(roleKey);
    }
}
