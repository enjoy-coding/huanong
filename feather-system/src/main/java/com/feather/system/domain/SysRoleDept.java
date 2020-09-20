package com.feather.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和部门关联 sys_role_dept
 * 
 * @author feather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDept {
    /** 角色ID */
    private Long roleId;

    /** 部门ID */
    private Long deptId;
}
