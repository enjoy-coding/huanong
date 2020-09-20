package com.feather.aupipes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.service.AupSearchService;
import com.feather.aupipes.service.IAupYjinfotablesService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "搜索周边")
@Controller
@RequestMapping("/search")
public class AupSearchController extends BaseController {
    @Autowired
    private AupSearchService searchService;

    @Autowired
    private IAupYjinfotablesService iAupYjinfotablesService;

    /**
     * 搜索设备
     * 
     * @param keywords
     *            关键字
     * @return
     */
    @ApiOperation("搜索设备")
    @GetMapping("/equipment")
    @ResponseBody
    public AjaxResult queryEquipment(@Param("keywords") String keywords) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("keywords", keywords == null ? "" : keywords);
        return AjaxResult.success(searchService.queryEquipment(maps));

    }

    /**
     * 搜索预警
     * 
     * @param keywords
     *            关键字
     * @return
     */
    @ApiOperation("搜索预警")
    @GetMapping("/warring")
    @ResponseBody
    public AjaxResult queryWarring(@Param("keywords") String keywords) {
        // 先查询小分类
        List<String> types = searchService.getType(keywords);
        List<Map> list0 = iAupYjinfotablesService.getSearchWarringList("", "", keywords, "", "", "", "", "");
        List list1 = null;
        Map<String, List> maps = new HashMap<String, List>();
        if (types != null && types.size() > 0) {
            for (int i = 0; i < types.size(); i++) {
                list1 = new ArrayList();
                for (int j = 0; j < list0.size(); j++) {
                    if (types.get(i).equals(list0.get(j).get("formSysName"))) {
                        list1.add(list0.get(j));
                    }
                }
                maps.put(types.get(i), list1);
            }
        }
        return AjaxResult.success(maps);

    }
}
