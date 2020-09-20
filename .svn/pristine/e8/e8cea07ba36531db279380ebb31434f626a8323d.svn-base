package com.feather.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.feather.system.domain.SysDept;

/**
 * 部门管理 数据层
 * 
 * @author feather
 */
public interface SysDeptMapper {
    /**
     * 查询部门人数
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    public int selectDeptCount(SysDept dept);

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId
     *            部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询部门管理数据
     * 
     * @param dept
     *            部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 删除部门管理信息
     * 
     * @param deptId
     *            部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增部门信息
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部门信息
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改部门路径信息
     * 
     * @param dept
     *            部门信息
     * @return 结果
     */
    public int updateDeptPath(SysDept dept);

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId
     *            部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据部门父节点ID查询信息
     *
     * @param deptParentId
     *            部门父ID
     * @return 部门信息
     */
    public SysDept selectDeptByParentId(Long deptParentId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param deptName
     *            部门名称
     * @param parentId
     *            父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 校验部门代码是否唯一
     * 
     * @param deptName
     *            部门代码
     * @param parentId
     *            父部门ID
     * @return 结果
     */
    public SysDept checkDeptCodeUnique(@Param("deptCode") String deptCode, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId
     *            角色ID
     * @return 部门列表
     */
    public List<SysDept> selectRoleDeptTree(Long roleId);

    /**
     * 修改所在部门的父级部门状态
     * 
     * @param dept
     *            部门
     */
    public void updateDeptStatus(SysDept dept);

    /**
     * 根据ID查询所有子部门
     * 
     * @param deptId
     *            部门ID
     * @return 部门列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);
}
