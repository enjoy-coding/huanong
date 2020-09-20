package com.feather.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.annotation.DataScope;
import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.core.domain.Ztree;
import com.feather.common.exception.BusinessException;
import com.feather.system.domain.SysDept;
import com.feather.system.domain.SysRole;
import com.feather.system.mapper.SysDeptMapper;
import com.feather.system.service.ISysDeptService;

/**
 * 部门管理 服务实现
 * 
 * @author feather
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询部门管理数据
     * 
     * @param dept
     *            部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     * 
     * @param dept
     *            部门信息
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysDept dept) {
        List<SysDept> deptList = deptMapper.selectDeptList(dept);
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role
     *            角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDept> deptList = selectDeptList(new SysDept());
        if (roleId != null) {
            List<String> roleDeptList = new ArrayList<>();
            List<SysDept> dept = deptMapper.selectRoleDeptTree(roleId);
            if (dept != null) {
                for (SysDept item : dept) {
                    roleDeptList.add(item.getDeptId() + item.getDeptName());
                }
            }
            ztrees = initZtree(deptList, roleDeptList);
        } else {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList
     *            部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList
     *            部门列表
     * @param roleDeptList
     *            角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = roleDeptList != null;
        for (SysDept dept : deptList) {
            if (Constants.SUCCESS_OR_ENABLED.equals(dept.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setLevel(dept.getDeptLevel());
                ztree.setName(dept.getDeptName());
                ztree.setNamePath(dept.getNamePath());
                ztree.setCode(dept.getDeptCode());
                ztree.setTitle(dept.getDeptName());
                if (isCheck) {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门人数
     * 
     * @param parentId
     *            部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId) {
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId
     *            部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId
     *            部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {
        SysDept parent = null;
        if (dept.getParentId() != 0) {
            parent = deptMapper.selectDeptById(dept.getParentId());
            if (parent == null && dept.getParentId() != 0) {
                throw new BusinessException("未找到上级");
            }
            // 如果父节点不为"正常"状态,则不允许新增子节点
            if (parent != null && !Constants.SUCCESS_OR_ENABLED.equals(parent.getStatus())) {
                throw new BusinessException("部门停用，不允许新增");
            }
        }
        if (dept.getDeptId() == null) {
            dept.setDeptId(uidWorker.getNextId());
        }
        if (StringUtils.isBlank(dept.getStatus())) {
            dept.setStatus(Constants.SUCCESS_OR_ENABLED);
        }
        if (parent == null) {
            dept.setDeptLevel(1);
            dept.setIdPath(SysDept.ID_SEPARATOR + dept.getDeptId() + SysDept.ID_SEPARATOR);
            dept.setNamePath(dept.getDeptName());
        } else {
            dept.setDeptLevel(parent.getDeptLevel() + 1);
            dept.setIdPath(parent.getIdPath() + dept.getDeptId() + SysDept.ID_SEPARATOR);
            dept.setNamePath(parent.getNamePath() + SysDept.NAME_SEPARATOR + dept.getDeptName());
        }

        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDept(SysDept dept) {
        SysDept oldDept = selectDeptById(dept.getDeptId());
        if (dept.getParentId() == 0) {
            if (oldDept != null) {
                String newIdPath = SysDept.ID_SEPARATOR + dept.getDeptId() + SysDept.ID_SEPARATOR;
                String oldIdPath = oldDept.getIdPath();

                String newNamePath = dept.getDeptName();
                String oldNamePath = oldDept.getNamePath();

                dept.setDeptLevel(1);
                dept.setIdPath(newIdPath);
                dept.setNamePath(newNamePath);
                updateDeptChildren(dept, oldIdPath, oldNamePath);
            }
        } else {
            SysDept newParent = deptMapper.selectDeptById(dept.getParentId());
            if (newParent != null && oldDept != null) {
                String newIdPath = newParent.getIdPath() + dept.getDeptId() + SysDept.ID_SEPARATOR;
                String oldIdPath = oldDept.getIdPath();

                String newNamePath = newParent.getNamePath() + SysDept.NAME_SEPARATOR + dept.getDeptName();
                String oldNamePath = oldDept.getNamePath();

                dept.setDeptLevel(newIdPath.split(",").length - 1);
                dept.setIdPath(newIdPath);
                dept.setNamePath(newNamePath);
                updateDeptChildren(dept, oldIdPath, oldNamePath);
            }
        }
        int result = deptMapper.updateDept(dept);
        if (Constants.SUCCESS_OR_ENABLED.equals(dept.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept
     *            当前部门
     */
    private void updateParentDeptStatus(SysDept dept) {
        dept = deptMapper.selectDeptById(dept.getDeptId());
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId
     *            被修改的部门ID
     * @param newIdPath
     *            新的ID路径
     * @param oldIdPath
     *            旧的ID路径
     * @param newNamePath
     *            新的名称路径
     * @param oldNamePath
     *            旧的名称路径
     */
    private void updateDeptChildren(SysDept dept, String oldIdPath, String oldNamePath) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(dept.getDeptId());
        for (SysDept child : children) {
            String idPath = StringUtils.replace(child.getIdPath(), oldIdPath, dept.getIdPath());
            String namePath = StringUtils.replace(child.getNamePath(), oldNamePath, dept.getNamePath());

            if (child.getDeptId().longValue() == dept.getDeptId().longValue()) {
                child.setParentId(dept.getParentId());
            }
            child.setDeptLevel(idPath.split(",").length - 1);
            child.setIdPath(idPath);
            child.setNamePath(namePath);
            deptMapper.updateDeptPath(child);
        }
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId
     *            部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public SysDept selectDeptByParentId(Long deptParentId) {
        return deptMapper.selectDeptByParentId(deptParentId);
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    @Override
    public boolean checkDeptNameUnique(SysDept dept) {
        Long deptId = dept.getDeptId() == null ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (info != null && info.getDeptId().longValue() != deptId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 校验部门代码是否唯一
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    @Override
    public boolean checkDeptCodeUnique(SysDept dept) {
        if (dept == null || StringUtils.isEmpty(dept.getDeptCode())) {
            return true;
        }
        Long deptId = dept.getDeptId() == null ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptCodeUnique(dept.getDeptCode(), dept.getParentId());
        if (info != null && info.getDeptId().longValue() != deptId.longValue()) {
            return false;
        }
        return true;
    }
}
