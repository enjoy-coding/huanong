package com.feather.system.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单权限表 sys_menu
 * 
 * @author feather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    private Long menuId;

    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;

    /** 父菜单名称 */
    private String parentName;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    @NotBlank(message = "显示顺序不能为空")
    private String orderNum;

    /** 菜单URL */
    @Size(min = 0, max = 200, message = "请求地址不能超过200个字符")
    private String url;

    /** 打开方式：menuItem页签 menuBlank新窗口 */
    private String target;

    /** 类型:0目录,1菜单,2按钮 */
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /** 菜单状态:0显示,1隐藏 */
    private String visible;

    /** 权限字符串 */
    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /** 菜单图标 */
    private String icon;

    /** 子菜单 */
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public SysMenu(Long parentId, @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符") String perms) {
        this.parentId = parentId;
        this.perms = perms;
    }
}
