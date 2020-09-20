package com.feather.system.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门表 sys_dept
 * 
 * @author feather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String ID_SEPARATOR = ",";
    public static final String NAME_SEPARATOR = " ";

    /** 部门ID */
    private Long deptId;

    /** 父部门ID */
    private Long parentId;

    /** 部门层级 */
    private Integer deptLevel;

    /** 部门ID路径 */
    @Size(min = 0, max = 255, message = "部门ID路径长度不能超过255个字符")
    private String idPath;

    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    /** 部门名称路径 */
    @Size(min = 0, max = 512, message = "部门名称路径长度不能超过512个字符")
    private String namePath;

    /** 部门代码 */
    @Size(min = 0, max = 16, message = "部门代码长度不能超过16个字符")
    private String deptCode;

    /** 显示顺序 */
    @NotBlank(message = "显示顺序不能为空")
    private String orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /** 邮箱 */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 父部门名称 */
    private String parentName;
}
