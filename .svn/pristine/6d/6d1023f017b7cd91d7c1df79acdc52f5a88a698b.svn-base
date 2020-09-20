package com.feather.aupipes.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupWarringUser;
import com.feather.aupipes.service.IAupWarringUserService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 预警用户Controller
 * 
 * @author fancy
 * @date 2020-04-20
 */
@Controller
@RequestMapping("/aupipes/warringuser")
public class AupWarringUserController extends BaseController {
    private String prefix = "aupipes/warringuser";

    @Autowired
    private IAupWarringUserService aupWarringUserService;

    @RequiresPermissions("aupipes:warringuser:view")
    @GetMapping()
    public String warringuser() {
        return prefix + "/warringuser";
    }

    /**
     * 查询预警用户列表
     */
    @RequiresPermissions("aupipes:warringuser:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupWarringUser aupWarringUser) {
        startPage();
        List<AupWarringUser> list = aupWarringUserService.selectAupWarringUserList(aupWarringUser);
        return getDataTable(list);
    }

    /**
     * 导出预警用户列表
     */
    @RequiresPermissions("aupipes:warringuser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupWarringUser aupWarringUser) {
        List<AupWarringUser> list = aupWarringUserService.selectAupWarringUserList(aupWarringUser);
        ExcelUtil<AupWarringUser> util = new ExcelUtil<AupWarringUser>(AupWarringUser.class);
        return util.exportExcel(list, "warringuser");
    }

    /**
     * 新增预警用户
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预警用户
     */
    @RequiresPermissions("aupipes:warringuser:add")
    @Log(title = "预警用户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(String obj, String name, String loginname, String warringcategory) {
        AupWarringUser aupWarringUser = new AupWarringUser();
        aupWarringUser.setName(name);
        aupWarringUser.setLoginname(loginname);
        aupWarringUser.setWarringcategory(warringcategory);
        aupWarringUser.setObj(obj);
        aupWarringUser.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        return toAjax(aupWarringUserService.insertAupWarringUser(aupWarringUser));
    }

    /**
     * 修改预警用户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        AupWarringUser aupWarringUser = aupWarringUserService.selectAupWarringUserById(id);
        mmap.put("aupWarringUser", aupWarringUser);
        JSONArray ja = JSONObject.parse(aupWarringUser.getObj(), JSONArray.class);
        mmap.put("map", ja);
        return prefix + "/edit";
    }

    /**
     * 修改保存预警用户
     */
    @RequiresPermissions("aupipes:warringuser:edit")
    @Log(title = "预警用户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(String id, String obj, String name, String loginname, String warringcategory) {

        AupWarringUser aupWarringUser = new AupWarringUser();
        aupWarringUser.setName(name);
        aupWarringUser.setLoginname(loginname);
        aupWarringUser.setWarringcategory(warringcategory);
        aupWarringUser.setObj(obj);
        aupWarringUser.setId(id);
        return toAjax(aupWarringUserService.updateAupWarringUser(aupWarringUser));
    }

    /**
     * 删除预警用户
     */
    @RequiresPermissions("aupipes:warringuser:remove")
    @Log(title = "预警用户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupWarringUserService.deleteAupWarringUserByIds(ids));
    }

    @RequestMapping("/getUserByName")
    @ResponseBody
    public List<Map> getUserByName(String name) {
        if (name.trim().equals("")) {
            return null;
        } else {
            return aupWarringUserService.getUserByName(name);
        }
    }

}
