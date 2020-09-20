package com.feather.aupipes.controller;

import java.util.List;
import java.util.Map;

import com.feather.aupipes.domain.AupArea;
import com.feather.aupipes.service.AupAreaService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.LayuiTreeStringResult;
import com.feather.common.core.page.TableDataInfo;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.lettuce.core.dynamic.annotation.Param;


@Controller
@RequestMapping("/aupipes/area")
public class AupAreaController extends BaseController {

    private String prefix = "aupipes/area";

    private String fragment = "area::aupAreaFragment";

    @Autowired
    private AupAreaService aupAreaService;

    @RequiresPermissions("aupipes:area:view")
    @GetMapping()
    public String area(ModelMap modelMap) {
        AupArea aupArea = aupAreaService.selectAupAreaById("AreaLine0000");
        aupArea.setParentName("根节点");
        modelMap.put("area", aupArea);
        return prefix + "/area";
    }

    /**
     * 查询探漏列列表
     */
    @RequiresPermissions("aupipes:area:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public List<AupArea>  list(AupArea aupArea)
    {
        List<AupArea> list = aupAreaService.selectAupAreaList(aupArea);
        return list;
    }
    
    
    /**
     * 查询区域跟节点
     * @param aupArea 对象
     * @return 结果
     */
    @RequiresPermissions("aupipes:area:list")
    @GetMapping("/tree/list")
    @ResponseBody
    public AjaxResult getRootList(@Param("pid") String pid,AupArea aupArea)
    {
        if(pid==null||pid.length()==0){
            aupArea.setPid("root");
        }
        List<LayuiTreeStringResult> list = aupAreaService.selectAupAreaLayuiResultList(aupArea);
        return AjaxResult.success(list);
    }

    @RequiresPermissions("aupipes:area:view")
    @GetMapping("/updateLog")
    public String updateLog() {
        return prefix + "/updateLog";
    }

    @RequiresPermissions("aupipes:area:view")
    @PostMapping("/updateLog/list")
    @ResponseBody
    @ClearPage
    public TableDataInfo updateLogList(AupArea area) {
        startPage();
        List<Map<String,Object>> maps = aupAreaService.selectAupAreaUpdateHistoryList(area);
        return  getDataTable(maps);
    }

    /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:waterSide:edit")
    @GetMapping("/fragment")
    public String showAupAreaFragment(@Param("id") String id,ModelMap mmap)
    {
        AupArea aupArea = aupAreaService.selectAupAreaById(id);
        if(aupArea.getPid()!=null&&!"root".equals(aupArea.getPid())){
            AupArea aupAreaParent = aupAreaService.selectAupAreaById(aupArea.getPid());
            aupArea.setParentName(aupAreaParent.getName());
        }else{
            aupArea.setParentName("根节点");
        }
        mmap.put("area", aupArea);
        return prefix + "/" +fragment;
    }

}