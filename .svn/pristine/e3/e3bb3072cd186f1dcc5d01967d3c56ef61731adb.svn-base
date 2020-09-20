package com.feather.system.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.config.UidWorker;
import com.feather.common.constant.Constants;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.StringUtils;
import com.feather.system.domain.SysMenu;
import com.feather.system.domain.SysRole;
import com.feather.system.domain.SysUser;
import com.feather.system.mapper.SysMenuMapper;
import com.feather.system.mapper.SysRoleMenuMapper;
import com.feather.system.service.ISysMenuService;

/**
 * 菜单 业务层处理
 * 
 * @author feather
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 根据用户查询菜单
     * 
     * @param user
     *            用户信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenu> menus = new LinkedList<SysMenu>();
        // 管理员显示所有菜单信息
        if (user.isAdmin()) {
            menus = menuMapper.selectMenuNormalAll();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId)) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    @Override
    public List<SysMenu> selectMenuAll(Long userId) {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId)) {
            menuList = menuMapper.selectMenuAll();
        } else {
            menuList = menuMapper.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId
     *            用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 根据角色ID查询菜单
     * 
     * @param role
     *            角色对象
     * @return 菜单列表
     */
    @Override
    public List<Ztree> roleMenuTreeData(SysRole role, Long userId) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysMenu> menuList = selectMenuAll(userId);
        if (roleId != null) {
            List<String> roleMenuList = new ArrayList<>();
            List<SysMenu> menu = menuMapper.selectMenuTree(roleId);
            if (menu != null) {
                for (SysMenu item : menu) {
                    roleMenuList.add(item.getMenuId() + item.getPerms());
                }
            }
            ztrees = initZtree(menuList, roleMenuList, true);
        } else {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    /**
     * 查询所有菜单
     * 
     * @return 菜单列表
     */
    @Override
    public List<Ztree> menuTreeData(Long userId) {
        List<SysMenu> menuList = selectMenuAll(userId);
        List<Ztree> ztrees = initZtree(menuList);
        return ztrees;
    }

    /**
     * 查询系统所有权限
     * 
     * @return 权限列表
     */
    @Override
    public LinkedHashMap<String, String> selectPermsAll(Long userId) {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<SysMenu> permissions = selectMenuAll(userId);
        if (StringUtils.isNotEmpty(permissions)) {
            for (SysMenu menu : permissions) {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     * 
     * @param menuList
     *            菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysMenu> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     * 
     * @param menuList
     *            菜单列表
     * @param roleMenuList
     *            角色已存在菜单列表
     * @param permsFlag
     *            是否需要显示权限标识
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = roleMenuList != null;
        for (SysMenu menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(SysMenu menu, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        String perms = menu.getPerms();
        if (permsFlag && StringUtils.isNotEmpty(perms)) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + perms + "</font>");
        }
        return sb.toString();
    }

    /**
     * 删除菜单管理信息
     * 
     * @param menuId
     *            菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        roleMenuMapper.deleteByMenuId(menuId);
        int res = menuMapper.deleteMenuById(menuId);
        return res;
    }

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId
     *            菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        if (menuId == null) {
            return null;
        }
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     * 
     * @param parentId
     *            父级菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     * 
     * @param menuId
     *            菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增保存菜单信息
     * 
     * @param menu
     *            菜单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMenu(SysMenu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getMenuId() == null) {
            menu.setMenuId(uidWorker.getNextId());
        }
        if ("M".equals(menu.getMenuType()) || "F".equals(menu.getMenuType())) {
            menu.setUrl("#");
        }
        if (StringUtils.isBlank(menu.getVisible())) {
            menu.setVisible(Constants.SUCCESS_OR_ENABLED);
        }
        // 如果是菜单按钮
        if ("C".equals(menu.getMenuType())) {
            Long parentId = menu.getMenuId();
            // 截取模块code
            String listPerms = menu.getPerms().replace("view", "list");
            // 如果不存在就新增list权限
            if (!this.hasPermissionButton(new SysMenu(parentId, listPerms))) {
                this.insetPermissionButton(parentId, listPerms, "1", "列表");
            }
            // 如果不存在就新增add权限
            String addPerm = menu.getPerms().replace("view", "add");
            if (!this.hasPermissionButton(new SysMenu(parentId, addPerm))) {
                this.insetPermissionButton(parentId, addPerm, "2", "新增");
            }
            // 如果不存在就新增eidt权限
            String editPerm = menu.getPerms().replace("view", "edit");
            if (!this.hasPermissionButton(new SysMenu(parentId, editPerm))) {
                this.insetPermissionButton(parentId, editPerm, "3", "编辑");
            }
            // 如果不存在就新增remove权限
            String removePerm = menu.getPerms().replace("view", "remove");
            if (!this.hasPermissionButton(new SysMenu(parentId, removePerm))) {
                this.insetPermissionButton(parentId, removePerm, "4", "删除");
            }
        }
        return menuMapper.insertMenu(menu);
    }

    /**
     * 插入权限按钮
     * 
     * @param perms
     *            权限值
     * @param sort
     *            排序
     * @param menuName
     *            菜单名称
     * @return
     */
    @Transactional
    @Override
    public int insetPermissionButton(Long parentId, String perms, String sort, String menuName) {
        String target = "menuItem";
        String menuType = "F";
        SysMenu btn = new SysMenu();
        btn.setMenuId(uidWorker.getNextId());
        btn.setParentId(parentId);
        btn.setTarget(target);
        btn.setUrl("#");
        btn.setMenuType(menuType);
        btn.setMenuName(menuName);
        btn.setVisible(Constants.SUCCESS_OR_ENABLED);
        btn.setOrderNum(sort);
        btn.setPerms(perms);
        return menuMapper.insertMenu(btn);
    }

    /**
     * 修改保存菜单信息
     * 
     * @param menu
     *            菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        return menuMapper.updateMenu(menu);
    }

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menu
     *            菜单信息
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(SysMenu menu) {
        Long menuId = menu.getMenuId() == null ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (info != null && info.getMenuId().longValue() != menuId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 根据父节点的ID获取所有子节点
     * 
     * @param list
     *            分类表
     * @param parentId
     *            传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t != null) {
                if (t.getParentId() != null && t.getParentId() == parentId) {
                    recursionFn(list, t);
                    returnList.add(t);
                }
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     * 
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId() != null && n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 判断是否存在权限按钮
     * 
     * @param a
     * @return
     */
    @Override
    public boolean hasPermissionButton(SysMenu a) {
        if (menuMapper.checkHasPermission(a.getParentId(), a.getPerms()) == null) {
            return false;
        }
        return true;
    }

    /**
     * 只加载根节点
     * 
     * @param menu
     * @return
     */
    @Override
    public List<LayuiTreeResult> selectMenuByIdList(SysMenu menu, Long userId) {
        List<SysMenu> sysMenuList = new ArrayList<SysMenu>();
        if (userId != null) {
            sysMenuList = this.selectMenuList(menu, userId);
        } else {
            sysMenuList = menuMapper.selectMenuList(menu);
        }
        // 构造返回数据结构LayuiTreeResult
        List<LayuiTreeResult> layuiTreeResultList = new ArrayList<LayuiTreeResult>();
        for (int i = 0; i < sysMenuList.size(); i++) {
            SysMenu menu1 = sysMenuList.get(i);
            LayuiTreeResult layuiTreeResult = selectLayuiTreeMenu(menu1);
            layuiTreeResultList.add(layuiTreeResult);
        }
        menu.setParentId(0L);
        // 构造树
        List<LayuiTreeResult> returnList = new ArrayList<LayuiTreeResult>();
        if (layuiTreeResultList.size() > 0) {
            for (LayuiTreeResult label : layuiTreeResultList) {
                if (label.getPid().longValue() == menu.getParentId().longValue()) {
                    LayuiTreeResult labelData = new LayuiTreeResult(label.getId(), label.getName(), label.getPid(), "0",
                            label.getParams());
                    labelData = LayuiTreeResult.setChildren(labelData, layuiTreeResultList);
                    returnList.add(labelData);
                }
            }
        }
        // 设置展开折叠节点
        List<LayuiTreeResult> layuiTreeResultList1 = LayuiTreeResult.isHaveChildren(returnList);
        return layuiTreeResultList1;
    }

    /**
     * 根据id获取所有子节点
     * 
     * @param menu
     * @return
     */
    @Override
    public List<LayuiTreeResult> selectMenuChildrenByIdList(SysMenu menu) {
        // 根据pid获取当前pid下面的节点
        List<LayuiTreeResult> layuiTreeResultList = this.selectMenuByIdList(new SysMenu(), null);
        List<LayuiTreeResult> results = getChildrens(menu.getParentId(), layuiTreeResultList, new ArrayList<>());
        return results;
    }

    public List<LayuiTreeResult> getChildrens(Long pid, List<LayuiTreeResult> t,
            List<LayuiTreeResult> layuiTreeResult) {
        if (layuiTreeResult.size() > 0) {
            return layuiTreeResult;
        } else {
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i).getId().longValue() == pid.longValue()) {
                    layuiTreeResult = t.get(i).getChildren();
                    return layuiTreeResult;
                }
            }
            if (layuiTreeResult.size() == 0) {
                for (int i = 0; i < t.size(); i++) {
                    if (layuiTreeResult.size() > 0) {
                        break;
                    }
                    layuiTreeResult = getChildrens(pid, t.get(i).getChildren(), layuiTreeResult);
                }
            }
        }
        return layuiTreeResult;
    }

    /**
     * 是否存在子节点
     * 
     * @param parentId
     * @return
     */
    @Override
    public Boolean checkHaveChild(Long parentId) {
        SysMenu menu = new SysMenu();
        menu.setParentId(parentId);
        List<SysMenu> sysMenuList = menuMapper.selectMenuList(menu);
        if (sysMenuList.size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public LayuiTreeResult selectLayuiTreeMenu(SysMenu menu) {
        LayuiTreeResult treeResult = new LayuiTreeResult();
        treeResult.setName(menu.getMenuName());
        treeResult.setId(menu.getMenuId());
        treeResult.setPid(menu.getParentId());
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("url", menu.getUrl());
        params.put("perms", menu.getPerms());
        params.put("orderNum", menu.getOrderNum());
        params.put("menuType", menu.getMenuType());
        params.put("visible", menu.getVisible());
        params.put("selectItem", menu.getTarget());
        params.put("icon", menu.getIcon());
        treeResult.setParams(params);
        return treeResult;
    }

    @Override
    public Boolean hasLastChildren(Long menuId) {
        SysMenu menu = this.selectMenuById(menuId);
        // 根据当前id获取父节点
        SysMenu parentMenu = new SysMenu();
        parentMenu.setParentId(menu.getParentId());
        // 判断父节点是否存在当前最后一个节点
        List<SysMenu> sysMenuList = this.selectMenuList(parentMenu);
        boolean isLastChildren = false;
        if (sysMenuList.size() == 1 && sysMenuList.get(0).getMenuId().longValue() == menu.getMenuId().longValue()) {
            isLastChildren = true;
        }
        return isLastChildren;
    }
}
