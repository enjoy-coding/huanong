package com.feather.lpscommunity.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScService;
import com.feather.lpscommunity.service.IScServiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 社区服务Controller
 *
 * @author dufan
 * @date 2019-10-18
 */
@Api(tags = { "2-服务之窗" })
@Controller
@RequestMapping("/sc/service")
public class ScServiceController extends BaseController {
    private String prefix = "lpscommunity/service";

    @Autowired
    private IScServiceService scServiceService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("sc:service:view")
    @GetMapping()
    public String service() {
        return prefix + "/service";
    }

    /**
     * 查询社区服务列表
     */
    @RequiresPermissions("sc:service:view")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScService scService) {
        startPage().setOrderBy("dic.dict_sort,s.service_ctype");
        List<ScService> list = scServiceService.selectScServiceListByDicData(scService);
        return getDataTable(list);
    }

    /**
     * 导出社区服务列表
     */
    @RequiresPermissions("sc:service:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScService scService) {
        List<ScService> list = scServiceService.selectScServiceList(scService);
        ExcelUtil<ScService> util = new ExcelUtil<ScService>(ScService.class);
        return util.exportExcel(list, "service");
    }

    /**
     * 新增社区服务
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        ScService scService = new ScService();
        scService.setServiceControlType("add");
        mmap.put("scService", scService);
        return prefix + "/edit";
    }

    /**
     * 新增保存社区服务
     */
    @RequiresPermissions("sc:service:add")
    @Log(title = "社区服务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScService scService) {
        scService.setServiceId(uidWorker.getNextId());
        if (!scServiceService.checkServiceDeptUnique(scService)) {
            return AjaxResult.error("新增'" + scService.getServiceName() + "'失败，该社区类型已存在");
        }
        return toAjax(scServiceService.insertScService(scService), scService);
    }

    /**
     * 校验社区
     */
    @PostMapping("/checkServiceDeptUnique")
    @ResponseBody
    public String checkServiceDeptUnique(ScService scService) {
        return scServiceService.checkServiceDeptUnique(scService) ? "0" : "1";
    }

    /**
     * 修改社区服务
     */
    @GetMapping("/edit/{serviceDept}")
    public String edit(@PathVariable("serviceDept") Long serviceId, ModelMap mmap) {
        ScService scService = scServiceService.selectScServiceById(serviceId);
        scService.setServiceControlType("edit");
        mmap.put("scService", scService);
        return prefix + "/edit";
    }

    /**
     * 修改保存社区服务
     */
    @RequiresPermissions("sc:service:edit")
    @Log(title = "社区服务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScService scService) {
        if (!scServiceService.checkServiceDeptUnique(scService)) {
            return AjaxResult.error("编辑'" + scService.getServiceName() + "'失败，该社区类型已存在");
        }
        return toAjax(scServiceService.updateScService(scService));
    }

    /**
     * 新增社区服务
     */
    @GetMapping("/detail/{serviceDept}")
    public String detail(@PathVariable("serviceDept") Long serviceDept, ModelMap mmap) {
        ScService scService = scServiceService.selectScServiceById(serviceDept);
        mmap.put("scService", scService);
        return prefix + "/detail";
    }

    /**
     * 删除社区服务
     */
    @RequiresPermissions("sc:service:remove")
    @Log(title = "社区服务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scServiceService.deleteScServiceByIds(ids));
    }

    /**
     * 判断社区唯一
     */
    @PostMapping("/checkServiceUnique")
    @ResponseBody
    public String checkServiceUnique(ScService scService) {
        return scServiceService.checkServiceDeptUnique(scService) ? "0" : "1";
    }

    @ApiOperation("服务之窗栏目")
    @RequestMapping(value = "/api/column/list", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult restList() {
        List<Map<String, Object>> dataList = scServiceService.getServiceValue();
        return AjaxResult.success(dataList);
    }

    /**
     * 移动端获取服务之窗
     */
    @ApiOperation("服务之窗子栏目各种服务")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "service_type", value = "服务类型", required = true) //
    })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult restCList(@RequestParam(name = "service_type", required = true) String serviceType,
            HttpServletRequest request) {
        String basePath = getBasePath();
        List<Map<String, Object>> dataList = scServiceService.getServiceChildrenValue(basePath, serviceType);
        return AjaxResult.success(dataList);
    }

}
